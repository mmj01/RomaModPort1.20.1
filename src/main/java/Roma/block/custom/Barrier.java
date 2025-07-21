package Roma.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Barrier extends Block {

    public Barrier() {
        super(Properties.of()
                .noOcclusion()
                .strength(-1.0F, 3600000.0F) // Unbreakable
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
        );
    }

    // Do NOT make this return true or the game will treat it like actual air and skip interactions
    @Override
    public boolean isAir(BlockState state) {
        return false;
    }

    // Prevent mining entirely
    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        return 0.0F; // Cannot be mined
    }

    // Prevent left-click interaction altogether
    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {}

    // Don't even show an outline
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.block();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }


    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    // Allow right-click passthrough
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockHitResult newHit = traceBehindBlock(pos, player, level);
        if (newHit != null) {
            BlockPos targetPos = newHit.getBlockPos();
            BlockState targetState = level.getBlockState(targetPos);
            return targetState.use(level, player, hand, newHit);
        }
        return InteractionResult.PASS;
    }

    private BlockHitResult traceBehindBlock(BlockPos pos, Player player, Level level) {
        Vec3 eye = player.getEyePosition(1.0F);
        Vec3 look = player.getViewVector(1.0F);
        Vec3 end = eye.add(look.scale(5)); // Ray length

        BlockHitResult hit = level.clip(new ClipContext(eye, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        if (!hit.getBlockPos().equals(pos)) {
            return hit;
        }
        return null;
    }
}
