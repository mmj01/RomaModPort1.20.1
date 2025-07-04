package Roma.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;


import java.util.List;
import java.util.UUID;

public class ReachItem extends SwordItem {

    // UUIDs for attributes — generate unique UUIDs per modifier
    public static final String ATTACK_SPEED_UUID = ("11111111-2222-3333-4444-555555555555");
    public static final String ATTACK_DAMAGE_UUID = ("22222222-3333-4444-5555-666666666666");
    public static final String REACH_MOD_UUID = ("33333333-4444-5555-6666-777777777777");
    public static final String KNOCKBACK_MOD_UUID =("44444444-5555-6666-7777-888888888888");

    private double reach;
    private double knockBack;
    private int damage;
    private int attackSpd;

    private final Supplier<Multimap<Attribute, AttributeModifier>> attributeMap = Suppliers.memoize(() -> {

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(ATTACK_DAMAGE_UUID, damage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(ATTACK_SPEED_UUID, attackSpd, AttributeModifier.Operation.ADDITION));

        builder.put(CustomAttribute.ATTACK_REACH.get(),
                new AttributeModifier(REACH_MOD_UUID, reach, AttributeModifier.Operation.ADDITION));

        builder.put(Attributes.ATTACK_KNOCKBACK,
                new AttributeModifier(KNOCKBACK_MOD_UUID, knockBack, AttributeModifier.Operation.ADDITION));

        // Add custom attributes if needed (e.g. CustomAttribute.ATTACK_REACH)

        return builder.build();
    });

    public ReachItem(Tier tier, int damage, int attackSpd, double reach, double knockBack, Properties properties) {
        super(tier, damage, (float) attackSpd, properties);
        this.reach = reach;
        this.knockBack = knockBack;
        this.damage = (int) ((float) damage + tier.getAttackDamageBonus());
        this.attackSpd = attackSpd;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? attributeMap.get() : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity.level().isClientSide) return super.onEntitySwing(stack, entity); // Server-side only

        double reach = entity.getAttributeValue(CustomAttribute.ATTACK_REACH.get());
        double reachSqr = reach * reach;

        Vec3 eyePos = entity.getEyePosition(1.0F); // with partial tick
        Vec3 lookVec = entity.getLookAngle();
        Vec3 targetVec = eyePos.add(lookVec.scale(reach));

        AABB searchBox = entity.getBoundingBox().expandTowards(lookVec.scale(reach)).inflate(1.0D);

        EntityHitResult result = ProjectileUtil.getEntityHitResult(
                entity.level(), entity, eyePos, targetVec, searchBox,
                e -> e instanceof LivingEntity && !e.isSpectator() && e.isPickable()
        );

        if (result != null && result.getEntity() instanceof LivingEntity target) {
            double distSqr = entity.distanceToSqr(target);
            if (distSqr <= reachSqr) {
                float attackDamage = (float) entity.getAttributeValue(Attributes.ATTACK_DAMAGE);

                if (entity instanceof Player player) {
                    target.hurt(entity.damageSources().playerAttack(player), attackDamage);
                } else {
                    target.hurt(entity.damageSources().mobAttack(entity), attackDamage);
                }
            }
        }

        return super.onEntitySwing(stack, entity);
    }

}