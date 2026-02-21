package top.mcocet.orefarm.init.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import top.mcocet.orefarm.init.ItemInit;
import top.mcocet.orefarm.utils.IC2Helper;

import java.util.Random;

public class CopperShardCropBlock extends BlockCrops {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)
    };

    public CopperShardCropBlock(String name) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }

    @Override
    protected PropertyInteger getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    protected Item getSeed() {
        return ItemInit.copper_shard_seed;
    }

    @Override
    protected Item getCrop() {
        // 返回铜锭作为收获产物
        ItemStack copperIngot = IC2Helper.getCopperIngot();
        return copperIngot != null ? copperIngot.getItem() : ItemInit.copper_shard_seed;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        int age = getAge(state);
        Random rand = new Random();

        if (age >= getMaxAge()) {
            // 成熟时掉落2-4个铜碎片种子
            int count = 2 + rand.nextInt(3);
            for (int i = 0; i < count; i++) {
                drops.add(new ItemStack(this.getSeed(), 1, 0));
            }
            
            // 额外掉落铜锭
            ItemStack copperIngot = IC2Helper.getCopperIngot();
            if (copperIngot != null) {
                drops.add(copperIngot.copy());
            }
        } else {
            // 未成熟时只掉落1个种子
            drops.add(new ItemStack(this.getSeed(), 1, 0));
        }
    }
}