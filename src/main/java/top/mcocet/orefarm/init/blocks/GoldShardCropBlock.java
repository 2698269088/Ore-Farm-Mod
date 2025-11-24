package top.mcocet.orefarm.init.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import top.mcocet.orefarm.init.ItemInit;

public class GoldShardCropBlock extends BlockCrops {
    public GoldShardCropBlock(String name) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }

    protected Item getSeed() {
        return ItemInit.gold_shard_seed;
    }

    protected Item getCrop() {
        return Items.GOLD_NUGGET;
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos down = pos.down();
        IBlockState soil = worldIn.getBlockState(pos.down());
        if (soil.getBlock() != Blocks.FARMLAND) {
            return false;
        } else {
            return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this);
        }
    }
}
