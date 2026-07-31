package Roma.entity.custom;

import Roma.enchantment.ModEnchantments;
import Roma.entity.custom.goals.ComboAttackAndJumpGoal;
import Roma.entity.custom.goals.TeleportGoal;
import Roma.item.Moditems;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;

public class PersianAssassin extends Monster {
    private int noPlayerVisibleTicks = 0;

    public PersianAssassin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.hurtDuration = 0;
        this.xpReward = 20;
        this.setPersistenceRequired();

        // Use modern vanilla infinite duration (-1) for permanent Jump Boost II
        this.addEffect(new MobEffectInstance(MobEffects.JUMP, MobEffectInstance.INFINITE_DURATION, 2, false, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.ATTACK_SPEED, 10.0D)
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.7D) // Note: 0.7D is extremely fast! Standard zombies are ~0.23D
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        // Clean, sequential resource drops (5% chance each)
        tryDropItem(Moditems.RAWIRON.get(), 0.05F);
        tryDropItem(Moditems.RAWCOPPER.get(), 0.05F);
        tryDropItem(Moditems.COAL.get(), 0.05F);
        tryDropItem(Moditems.WHEATSEEDS.get(), 0.1F);

        // Ultra-rare custom enchantment book drops
        tryDropEnchantmentBook(ModEnchantments.FLEET.get(), 0.000001F);
        tryDropEnchantmentBook(ModEnchantments.HEAVY.get(), 0.000001F);
        tryDropEnchantmentBook(ModEnchantments.SHARP.get(), 0.000001F);
        tryDropEnchantmentBook(ModEnchantments.ROBUST.get(), 0.00001F);
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
            // Throttle raycasting to once per second (every 20 ticks) to prevent server lag
            if (this.tickCount % 20 == 0) {
                Player nearest = this.level().getNearestPlayer(this, 40.0D);

                if (nearest != null && this.hasLineOfSight(nearest)) {
                    this.noPlayerVisibleTicks = 0;
                } else {
                    this.noPlayerVisibleTicks += 20; // Add the 20 elapsed ticks
                }

                // 9600 ticks = 8 minutes of no line-of-sight before despawning
                if (this.noPlayerVisibleTicks > 9600) {
                    this.discard();
                }
            }
        }
    }

    @Override
    protected void registerGoals() {
        // Priority 0: Survival (Never drown)
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // Priority 1: Primary Combat Loop -> Rush in, do 3 attacks, and vault over the player
        // Using 1.0D speed modifier since your base MOVEMENT_SPEED (0.7D) is already lightning fast
        this.goalSelector.addGoal(1, new ComboAttackAndJumpGoal(this, 1.0D));

        // Priority 2: Secondary Combat -> While Priority 1 is on its 5s cooldown, teleport every 3s
        this.goalSelector.addGoal(2, new TeleportGoal(this, 1.2D));

        // Priority 5: Passive wandering when idle
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));

        // Priority 6 & 7: Idle observation
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }
}