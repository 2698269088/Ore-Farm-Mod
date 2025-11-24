package top.mcocet.orefarm.init.blocks;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import top.mcocet.orefarm.OreFarm;
import top.mcocet.orefarm.init.ItemInit;

public class PoorDiamondOre extends CustomBlock {
    public PoorDiamondOre(String name, float hardness, float resistance, int harvestLevel) {
        super(name, hardness, resistance);
        this.setHarvestLevel("pickaxe", harvestLevel);
        this.setCreativeTab(OreFarm.orefarmtab);
    }

    public PoorDiamondOre(String name, float hardness, float resistance, int harvestLevel, CreativeTabs creativeTab) {
        super(name, hardness, resistance);
        this.setHarvestLevel("pickaxe", harvestLevel);
        this.setCreativeTab(creativeTab);
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemInit.poor_diamond;
    }

    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), random, fortune)) {
            int i = random.nextInt(fortune + 2) - 1;
            if (i < 0) {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        } else {
            return this.quantityDropped(random);
        }
    }

    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
            int i = 0;
            i = MathHelper.getInt(rand, 0, 2);
            return i;
        } else {
            return 0;
        }
    }
}
