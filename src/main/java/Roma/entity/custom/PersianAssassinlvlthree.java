package Roma.entity.custom;

import Roma.enchantment.ModEnchantments;
import Roma.entity.custom.goals.*;
import Roma.item.Moditems;
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;

public class PersianAssassinlvlthree extends Monster {
    private int noPlayerVisibleTicks = 0;

    public PersianAssassinlvlthree(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.hurtDuration = 0;
        this.xpReward = 300;
        this.setPersistenceRequired();

        // Permanent effects using native infinite duration (-1)
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, MobEffectInstance.INFINITE_DURATION, 3, false, false));
        this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, MobEffectInstance.INFINITE_DURATION, 14, false, false));
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, MobEffectInstance.INFINITE_DURATION, 1, false, false));
        this.addEffect(new MobEffectInstance(MobEffects.JUMP, MobEffectInstance.INFINITE_DURATION, 2, false, false));

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
                .add(Attributes.ARMOR, 64.0D)
                .add(Attributes.ATTACK_SPEED, 25.0D)
                .add(Attributes.MAX_HEALTH, 316.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.9D)
                .add(Attributes.ATTACK_DAMAGE, 64.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        tryDropItem(Moditems.RAWIRON.get(), 0.15F);
        tryDropItem(Moditems.RAWCOPPER.get(), 0.15F);
        tryDropItem(Moditems.COAL.get(), 0.15F);
        tryDropItem(Moditems.WHEATSEEDS.get(), 0.15F);
        tryDropItem(Moditems.RAWGOLD.get(), 0.1F);
        tryDropItem(Moditems.RAWSILVER.get(), 0.1F);
        tryDropItem(Moditems.RAWCOBALT.get(), 0.1F);
        tryDropItem(Moditems.RAWTIN.get(), 0.1F);
        tryDropItem(Moditems.RAWNICKEL.get(), 0.05F);
        tryDropItem(Moditems.RAWALUMINUM.get(), 0.05F);
        tryDropItem(Moditems.BREAD.get(), 0.01F);

        tryDropEnchantmentBook(ModEnchantments.FLEET.get(), 0.0001F);
        tryDropEnchantmentBook(ModEnchantments.HEAVY.get(), 0.0001F);
        tryDropEnchantmentBook(ModEnchantments.SHARP.get(), 0.0001F);
        tryDropEnchantmentBook(ModEnchantments.ROBUST.get(), 0.001F);
    }

    private void tryDropItem(Item item, float chance) {
        if (this.random.nextFloat() < chance) {
            this.spawnAtLocation(item);
        }
    }

    private void tryDropEnchantmentBook(Enchantment enchantment, float chance) {
        if (this.random.nextFloat() < chance) {
            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(book, new EnchantmentInstance(enchantment, 1));
            this.spawnAtLocation(book);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            // Throttling raycasts to once per second (every 20 ticks)
            if (this.tickCount % 20 == 0) {
                Player nearest = this.level().getNearestPlayer(this, 40.0D);

                if (nearest != null && this.hasLineOfSight(nearest)) {
                    this.noPlayerVisibleTicks = 0;
                } else {
                    this.noPlayerVisibleTicks += 20;
                }

                // Despawn after 8 minutes (9600 ticks) of no line-of-sight
                if (this.noPlayerVisibleTicks > 9600) {
                    this.discard();
                }
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ComboAttackAndJumpGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TeleportGoal(this, 1.2D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }
}