package Roma.entity.custom;

import Roma.item.Moditems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.A;


public class PersianAssassinlvlfour extends Monster {
    private int noPlayerVisibleTicks = 0;




    public PersianAssassinlvlfour(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.hurtDuration=0;
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 999999999,6,false,false));
        this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 999999999,48,false,false));
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 999999999,2,false,false));
        this.addEffect(new MobEffectInstance(MobEffects.JUMP, 999999999,2,false,false));

        this.xpReward = 2400;
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_AXE));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Moditems.LSTEELCHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Moditems.LSTEELLEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Moditems.LSTEELHELMET.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Moditems.LSTEELBOOTS.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.LEGS, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        this.setDropChance(EquipmentSlot.FEET, 0.0F);


    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ARMOR, 384.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 156.00D)
                .add(Attributes.ATTACK_SPEED, 30.0D)
                .add(Attributes.MAX_HEALTH, 2400.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.8D)
                .add(Attributes.ATTACK_DAMAGE, 156.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        if (this.random.nextFloat() < 0.2F ) {
            this.spawnAtLocation(Moditems.RAWIRON.get());
        }
        if (this.random.nextFloat() < 0.2F ) {
            this.spawnAtLocation(Moditems.RAWCOPPER.get());
        }
        if (this.random.nextFloat() < 0.2F ) {
            this.spawnAtLocation(Moditems.COAL.get());
        }
        if (this.random.nextFloat() < 0.2F ) {
            this.spawnAtLocation(Moditems.WHEATSEEDS.get());
        }
        if (this.random.nextFloat() < 0.15F ) {
            this.spawnAtLocation(Moditems.RAWGOLD.get());
        }
        if (this.random.nextFloat() < 0.15F ) {
            this.spawnAtLocation(Moditems.RAWSILVER.get());
        }
        if (this.random.nextFloat() < 0.15F ) {
            this.spawnAtLocation(Moditems.RAWCOBALT.get());
        }
        if (this.random.nextFloat() < 0.15F ) {
            this.spawnAtLocation(Moditems.RAWTIN.get());
        }
        if (this.random.nextFloat() < 0.1F ) {
            this.spawnAtLocation(Moditems.RAWNICKEL.get());
        }
        if (this.random.nextFloat() < 0.1F ) {
            this.spawnAtLocation(Moditems.RAWALUMINUM.get());
        }
        if (this.random.nextFloat() < 0.05F ) {
            this.spawnAtLocation(Moditems.BREAD.get());
        }
        if (this.random.nextFloat() < 0.002F ) {
            this.spawnAtLocation(Moditems.RAWCHROMIUM.get());
        }
        if (this.random.nextFloat() < 0.005F ) {
            this.spawnAtLocation(Moditems.RAWPLATINUM.get());
        }

    }
    @Override
    public void tick() {
        super.tick();

        // Only run on server side
        if (!this.level().isClientSide) {
            Player nearest = this.level().getNearestPlayer(this, 40.0D); // check within 40 blocks

            if (nearest != null && this.hasLineOfSight(nearest)) {
                noPlayerVisibleTicks = 0; // player seen, reset timer
            } else {
                noPlayerVisibleTicks++;
            }

            if (noPlayerVisibleTicks > 9600) { // e.g. 600 ticks = 30 seconds
                this.discard(); // despawn entity
            }
        }
    }




    @Override
    protected void registerGoals() {
        // Core behavior
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // Aggressive charge behavior (top priority)
        this.goalSelector.addGoal(2, new ChargeatplayerGoal(this, 1.6D)); // Faster charge speed

        // Melee attack with reduced cooldown
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4D, true) {
            @Override
            protected int getAttackInterval() {
                return 5; // Very fast attacks
            }
        });

        // Extended reach custom attack (lower priority if it supplements melee)
        this.goalSelector.addGoal(3, new CustomReachAttackGoal(this, 1.4D, false, 6.0F));

        // Lower-priority passive movement
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        // Observational behavior (very low priority)
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        // Targeting logic
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }
}