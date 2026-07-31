package Roma.entity.custom.boss;

import Roma.enchantment.ModEnchantments;
import Roma.entity.Modentities;
import Roma.item.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PersianShawman extends Monster{
    private int fireballCooldown = 200; // Launch every 10 seconds
    private int fireballsToSpawn = 0;
    private int fireballSpawnDelay = 0;
    private int barrageChargeTicks = 0; // counts down before barrage





    public void launchFireballBarrage() {



        this.barrageChargeTicks = 40;     // 2 seconds of buildup
        this.fireballsToSpawn = 0;        // fireballs start AFTER this phase
        this.fireballSpawnDelay = 0;
        if (this.level().isClientSide) return;

        Player target = this.level().getNearestPlayer(this, 64.0);
        if (target == null) return;

        RandomSource random = this.getRandom();
        int fireballCount = 50; // Number of fireballs in the barrage

        for (int i = 0; i < fireballCount; i++) {
            // Boss position with height offset
            double spawnX = this.getX();
            double spawnY = this.getY() + this.getBbHeight() * 0.8;
            double spawnZ = this.getZ();

            // Target eye position
            Vec3 targetPos = target.getEyePosition();

            // Direction vector from spawn to target
            Vec3 dir = targetPos.subtract(spawnX, spawnY, spawnZ).normalize();

            // Optional: add slight random spread if desired (comment out if not wanted)
            double spread = 0.1; // tweak this for how much spread you want
            dir = dir.add(
                    (random.nextDouble() - 0.5) * spread,
                    (random.nextDouble() - 0.5) * spread,
                    (random.nextDouble() - 0.5) * spread
            ).normalize();

            PlayerOnlyFireball fireball = new PlayerOnlyFireball(this.level(), this, dir.x, dir.y, dir.z);
            fireball.setPos(spawnX, spawnY, spawnZ);

            this.level().addFreshEntity(fireball);
        }

        this.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 1.0F, 0.9F + random.nextFloat() * 0.2F);
    }


    private void spawnSingleFireball() {
        Player target = this.level().getNearestPlayer(this, 64.0); // pick nearest player
        if (target == null) return;

        Vec3 start = this.position().add(0, this.getBbHeight() * 0.8, 0);
        Vec3 end = target.getEyePosition();
        Vec3 dir = end.subtract(start).normalize();

        PlayerOnlyFireball fireball = new PlayerOnlyFireball(this.level(), this, dir.x, dir.y, dir.z);
        fireball.setPos(start.x, start.y, start.z);

        this.level().addFreshEntity(fireball);

        this.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
    }





    private void updatePlayersInRange(double range) {
        List<ServerPlayer> allPlayers = ((ServerLevel) this.level()).players();

        for (ServerPlayer player : allPlayers) {
            double distanceSq = player.distanceToSqr(this);

            if (distanceSq <= range * range) {
                if (!this.bossEvent.getPlayers().contains(player)) {
                    this.bossEvent.addPlayer(player);
                }
            } else {
                this.bossEvent.removePlayer(player);
            }
        }
    }


    private final ServerBossEvent bossEvent = new ServerBossEvent(
            Component.literal("Persian Shawman").withStyle(ChatFormatting.RED, ChatFormatting.OBFUSCATED),

            BossEvent.BossBarColor.RED,
            BossEvent.BossBarOverlay.PROGRESS
    );

    private int summonCooldown = 0;

    public PersianShawman(EntityType<? extends Monster> type, Level level) {
        super(type, level);


        this.setCustomName(Component.literal("Persian Shaman").withStyle(style -> style.withColor(0xFF0000).withObfuscated(true)));
        this.xpReward = 60000;
        this.setPersistenceRequired();
        this.setPersistenceRequired();
        this.setNoAi(false);

    }


    @Override
    public float getStepHeight() {
        return 8.0F;  // or any height you want
    }

    @Override
    public void knockback(double strength, double x, double z) {
        // Do nothing — disables all knockback
    }

    @Override
    public void push(double x, double y, double z) {
        // Ignore knockback from fireballs
        if (this.level().getEntitiesOfClass(PlayerOnlyFireball.class, this.getBoundingBox().inflate(1.0))
                .stream().anyMatch(fireball -> fireball.getOwner() == this)) {
            return; // ignore knockback from own fireballs
        }
        super.push(x, y, z);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidPlayerGoal(this, 1.0D, 12.0D)); // keep 12 blocks away
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 0.7D));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }


    private void summonAllies() {
        for (int i = 0; i < 10; i++) {
            Mob minion = Modentities.PERSIANASSASSIN.get().create(this.level());
            if (minion != null) {
                double x = this.getX() + (this.getRandom().nextDouble() - 0.5) * 5;
                double y = this.getY();
                double z = this.getZ() + (this.getRandom().nextDouble() - 0.5) * 5;
                minion.moveTo(x, y, z, this.getYRot(), 0.0F);
                this.level().addFreshEntity(minion);
            }
        }

        this.level().playSound(null, this.blockPosition(), SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.HOSTILE, 1.0F, 1.0F);
        ((ServerLevel) this.level()).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 10, 0.5, 0.5, 0.5, 0.0);

        for (int i = 0; i < 8; i++) {
            Mob minion = Modentities.PERSIANASSASSINLVLTWO.get().create(this.level());
            if (minion != null) {
                double x = this.getX() + (this.getRandom().nextDouble() - 0.5) * 5;
                double y = this.getY();
                double z = this.getZ() + (this.getRandom().nextDouble() - 0.5) * 5;
                minion.moveTo(x, y, z, this.getYRot(), 0.0F);
                this.level().addFreshEntity(minion);
            }
        }

        this.level().playSound(null, this.blockPosition(), SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.HOSTILE, 1.0F, 1.0F);
        ((ServerLevel) this.level()).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 10, 0.5, 0.5, 0.5, 0.0);

        for (int i = 0; i < 6; i++) {
            Mob minion = Modentities.PERSIANASSASSINLVLTHREE.get().create(this.level());
            // Change to custom mob if you want
            if (minion != null) {
                double x = this.getX() + (this.getRandom().nextDouble() - 0.5) * 5;
                double y = this.getY();
                double z = this.getZ() + (this.getRandom().nextDouble() - 0.5) * 5;
                minion.moveTo(x, y, z, this.getYRot(), 0.0F);
                this.level().addFreshEntity(minion);
            }
        }

        this.level().playSound(null, this.blockPosition(), SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.HOSTILE, 1.0F, 1.0F);
        ((ServerLevel) this.level()).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 10, 0.5, 0.5, 0.5, 0.0);

        for (int i = 0; i < 4; i++) {
            Mob minion = Modentities.PERSIANASSASSINLVLFOUR.get().create(this.level());
            if (minion != null) {
                double x = this.getX() + (this.getRandom().nextDouble() - 0.5) * 5;
                double y = this.getY();
                double z = this.getZ() + (this.getRandom().nextDouble() - 0.5) * 5;
                minion.moveTo(x, y, z, this.getYRot(), 0.0F);
                this.level().addFreshEntity(minion);
            }
        }

        this.level().playSound(null, this.blockPosition(), SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.HOSTILE, 1.0F, 1.0F);
        ((ServerLevel) this.level()).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 10, 0.5, 0.5, 0.5, 0.0);

    }
    @Override
    public void startSeenByPlayer(ServerPlayer player) {

    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        this.bossEvent.removePlayer(player);
    }



    private void spawnWarningParticles() {
        for (int i = 0; i < 2; i++) {
            double x = this.getX() + (this.getRandom().nextDouble() - 0.5) * 2;
            double y = this.getY() + this.getBbHeight() * 0.8;
            double z = this.getZ() + (this.getRandom().nextDouble() - 0.5) * 2;

            this.level().addParticle(ParticleTypes.FLAME, x, y, z, 0, 0.02, 0);
            this.level().addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0.02, 0);
        }
    }





    @Override
    public void tick() {
        super.tick();

        // === PARTICLES DURING CHARGE ===
        if (barrageChargeTicks > 0) {
            spawnWarningParticles();
        }

        if (!this.level().isClientSide) {

            // === BOSS BAR LOGIC ===
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
            BossEvent.BossBarColor newColor = this.canTakeDamage() ? BossEvent.BossBarColor.RED : BossEvent.BossBarColor.BLUE;
            if (this.bossEvent.getColor() != newColor) {
                this.bossEvent.setColor(newColor);
            }
            this.updatePlayersInRange(50);

            // === BARRAGE CHARGING ===
            if (barrageChargeTicks > 0) {
                if (--barrageChargeTicks == 0) {
                    fireballsToSpawn = 50;
                    fireballSpawnDelay = 0;
                }
            }

            // === FIREBALL BARRAGE LAUNCHING ===
            if (fireballsToSpawn > 0 && --fireballSpawnDelay <= 0) {
                fireballSpawnDelay = 1;
                spawnSingleFireball();
                fireballsToSpawn--;
            }

            // === BARRAGE TRIGGER ===
            if (getTarget() != null && fireballCooldown-- <= 0) {
                launchFireballBarrage();
                fireballCooldown = 600 + this.getRandom().nextInt(100);
            }

            // === SUMMONING MINIONS ===
            if (getTarget() != null && summonCooldown-- <= 0) {
                summonAllies();
                summonCooldown = 400 + this.getRandom().nextInt(20);
            }
        }
    }


    @Override
    public boolean isPersistenceRequired() {
        return true;
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ARMOR, 800.0D)
                .add(Attributes.ATTACK_SPEED, 15.0D)
                .add(Attributes.MAX_HEALTH, 10000.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.ATTACK_DAMAGE, 16.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!this.level().isClientSide && !canTakeDamage()) {
            // Play sound once per immunity tick
            if (this.tickCount % 20 == 0) { // Once per second

                ((ServerLevel) this.level()).sendParticles(
                        ParticleTypes.SMOKE,
                        this.getX(), this.getY() + 1, this.getZ(),
                        10, 0.5, 0.5, 0.5, 0.01
                );
                this.level().playSound(
                        null,
                        this.blockPosition(),
                        SoundEvents.SHIELD_BLOCK,
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                );
            }

            return false; // Cancel damage
        }

        return super.hurt(source, amount);
    }


    private boolean canTakeDamage() {
        List<LivingEntity> blockers = this.level().getEntitiesOfClass(
                LivingEntity.class,
                this.getBoundingBox().inflate(15),
                entity ->
                        entity != this &&
                                entity.isAlive() &&
                                (
                                        entity.getType() == Modentities.PERSIANASSASSIN.get() ||
                                                entity.getType() == Modentities.PERSIANASSASSINLVLTWO.get() ||
                                                    entity.getType() == Modentities.PERSIANASSASSINLVLTHREE.get() ||
                                                            entity.getType() == Modentities.PERSIANASSASSINLVLFOUR.get()
                                )
        );

        return blockers.isEmpty();
    }





    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        if (this.random.nextFloat() < 1.0F) {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantmentInstance enchantmentInstance = new EnchantmentInstance(ModEnchantments.FLEET.get(), 1);
            EnchantedBookItem.addEnchantment(enchantedBook, enchantmentInstance);
            this.spawnAtLocation(enchantedBook);
        }
        if (this.random.nextFloat() < 1.0F) {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantmentInstance enchantmentInstance = new EnchantmentInstance(ModEnchantments.HEAVY.get(), 1);
            EnchantedBookItem.addEnchantment(enchantedBook, enchantmentInstance);
            this.spawnAtLocation(enchantedBook);
        }
        if (this.random.nextFloat() < 1.0F) {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantmentInstance enchantmentInstance = new EnchantmentInstance(ModEnchantments.SHARP.get(), 1);
            EnchantedBookItem.addEnchantment(enchantedBook, enchantmentInstance);
            this.spawnAtLocation(enchantedBook);
        }
        if (this.random.nextFloat() < 1.0F) {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantmentInstance enchantmentInstance = new EnchantmentInstance(ModEnchantments.ROBUST.get(), 1);
            EnchantedBookItem.addEnchantment(enchantedBook, enchantmentInstance);
            this.spawnAtLocation(enchantedBook);
        }
        if (this.random.nextFloat() < 0.75F ) {
            this.spawnAtLocation(new ItemStack(Moditems.PLATINUMCOINS.get(), 35));

        }

    }
}
