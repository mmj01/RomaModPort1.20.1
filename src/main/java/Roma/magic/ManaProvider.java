package Roma.magic;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ManaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("rma", "mana");

    // Fixed: Create ManaImplementation instead of ManaProvider
    private final ManaImplementation manaImpl = new ManaImplementation();
    private final LazyOptional<IMana> optional = LazyOptional.of(() -> manaImpl);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == ManaCapability.MANA_CAPABILITY ? optional.cast() : LazyOptional.empty();
    }

    public void invalidate() {
        optional.invalidate();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        manaImpl.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        manaImpl.loadNBTData(tag);
    }
}