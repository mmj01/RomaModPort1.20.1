package Roma.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import net.minecraft.resources.ResourceLocation;
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

import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;


public class ReachItem extends SwordItem {

    private static final UUID CATTACK_DAMAGE_UUID    = UUID.fromString("d4a1c3b2-5f6e-4a7b-8c9d-0e1f2a3b4c9d");
    private static final UUID CATTACK_SPEED_UUID     = UUID.fromString("d4a1c3b2-9f6e-4a7b-8c9d-0e1f2a3b4c5d");
    private static final UUID CATTACK_REACH_UUID     = UUID.fromString("d4a1c3b2-5f6e-4a7b-8c9d-0e1f2a3b4c5d");
    private static final UUID CATTACK_KNOCKBACK_UUID = UUID.fromString("d4a1c3b2-5f6e-4a7b-8c9d-9e1f2a3b4c5d");


    private double reach;
    private double knockBack;
    private double damage;
    private double attackSpd;



    // Lazily build the attribute map once per item instance
    private final Supplier<Multimap<Attribute, AttributeModifier>> attributeMap = Suppliers.memoize(() -> {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        // Vanilla stats
        builder.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(CATTACK_DAMAGE_UUID, "citem_attack_damage", damage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(CATTACK_SPEED_UUID, "citem_attack_speed", attackSpd, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK,
                new AttributeModifier(CATTACK_KNOCKBACK_UUID, "citem_attack_knockback", knockBack, AttributeModifier.Operation.ADDITION));

        // Forge reach attribute
        Attribute reachAttr = ForgeMod.BLOCK_REACH.get();
        builder.put(
                reachAttr,
                new AttributeModifier(CATTACK_REACH_UUID, "citem_attack_reach", reach, AttributeModifier.Operation.ADDITION)
        );

        return builder.build();
    });


    public ReachItem(Tier tier, int damage, float attackSpd, double reach, double knockBack, Properties props) {
        super(tier, damage, attackSpd, new Properties());
        this.damage    = damage;
        this.attackSpd = attackSpd;
        this.reach     = reach;
        this.knockBack = knockBack;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND
                ? attributeMap.get()
                : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity.level().isClientSide) return super.onEntitySwing(stack, entity);

        double reachValue = entity.getAttributeValue(ForgeMod.BLOCK_REACH.get());
        double reachSqr   = reachValue * reachValue;

        Vec3 eyePos    = entity.getEyePosition(1.0F);
        Vec3 lookDir   = entity.getLookAngle();
        Vec3 targetPos = eyePos.add(lookDir.scale(reachValue));

        AABB searchBox = entity.getBoundingBox()
                .expandTowards(lookDir.scale(reachValue))
                .inflate(1.0D);

        EntityHitResult result = ProjectileUtil.getEntityHitResult(
                entity.level(), entity, eyePos, targetPos, searchBox,
                e -> e instanceof LivingEntity && !e.isSpectator() && e.isPickable()
        );

        if (result != null && result.getEntity() instanceof LivingEntity target) {
            double distSqr = entity.distanceToSqr(target);
            if (distSqr <= reachSqr) {
                float atkDmg = (float) entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                if (entity instanceof Player p) {
                    target.hurt(entity.damageSources().playerAttack(p), atkDmg);
                } else {
                    target.hurt(entity.damageSources().mobAttack(entity), atkDmg);
                }
            }
        }

        return super.onEntitySwing(stack, entity);
    }
}
