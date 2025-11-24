package top.mcocet.orefarm.init.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OrePlantBlock extends BlockStem {
    public OrePlantBlock(String name, Block crop) {
        super(crop);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
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
