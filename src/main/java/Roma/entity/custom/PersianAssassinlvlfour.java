package Roma.entity.custom;

import Roma.item.Moditems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;


public class PersianAssassinlvlfour extends Monster {




    public PersianAssassinlvlfour(EntityType<? extends Monster> type, Level level) {
        super(type, level);
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
                .add(Attributes.ARMOR, 96.0D)
                .add(Attributes.ATTACK_SPEED, 30.0D)
                .add(Attributes.MAX_HEALTH, 1200.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.8D)
                .add(Attributes.ATTACK_DAMAGE, 96.0D)
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
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new CustomReachAttackGoal(this, 1.2D, false, 6.0F));
        this.goalSelector.addGoal(3, new ChargeatplayerGoal(this, 1.4D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }
}