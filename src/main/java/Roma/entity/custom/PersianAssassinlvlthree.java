package Roma.entity.custom;

import Roma.item.Moditems;
import net.minecraft.world.damagesource.DamageSource;
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


public class PersianAssassinlvlthree extends Monster {
    private int noPlayerVisibleTicks = 0;




    public PersianAssassinlvlthree(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 999999999,3,false,false));
        this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 999999999,12,false,false));
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 999999999,1,false,false));

        this.xpReward = 240;
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Moditems.BRASSCHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Moditems.BRASSLEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Moditems.BRASSHELMET.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Moditems.BRASSBOOTS.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.LEGS, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        this.setDropChance(EquipmentSlot.FEET, 0.0F);


    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ARMOR, 48.0D)
                .add(Attributes.ATTACK_SPEED, 25.0D)
                .add(Attributes.MAX_HEALTH, 280.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.9D)
                .add(Attributes.ATTACK_DAMAGE, 48.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        if (this.random.nextFloat() < 0.15F ) {
            this.spawnAtLocation(Moditems.RAWIRON.get());
        }
        if (this.random.nextFloat() < 0.15F ) {
            this.spawnAtLocation(Moditems.RAWCOPPER.get());
        }
        if (this.random.nextFloat() < 0.15F ) {
            this.spawnAtLocation(Moditems.COAL.get());
        }
        if (this.random.nextFloat() < 0.15F ) {
            this.spawnAtLocation(Moditems.WHEATSEEDS.get());
        }
        if (this.random.nextFloat() < 0.1F ) {
            this.spawnAtLocation(Moditems.RAWGOLD.get());
        }
        if (this.random.nextFloat() < 0.1F ) {
            this.spawnAtLocation(Moditems.RAWSILVER.get());
        }
        if (this.random.nextFloat() < 0.1F ) {
            this.spawnAtLocation(Moditems.RAWCOBALT.get());
        }
        if (this.random.nextFloat() < 0.1F ) {
            this.spawnAtLocation(Moditems.RAWTIN.get());
        }
        if (this.random.nextFloat() < 0.05F ) {
            this.spawnAtLocation(Moditems.RAWNICKEL.get());
        }
        if (this.random.nextFloat() < 0.05F ) {
            this.spawnAtLocation(Moditems.RAWALUMINUM.get());
        }
        if (this.random.nextFloat() < 0.01F ) {
            this.spawnAtLocation(Moditems.BREAD.get());
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
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            protected int getAttackInterval() {
                return 10; // ticks between attacks (default is 20)
            }
        });
        this.goalSelector.addGoal(3, new CustomReachAttackGoal(this, 1.2D, false, 6.0F));
        this.goalSelector.addGoal(2, new ChargeatplayerGoal(this, 1.4D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }
}