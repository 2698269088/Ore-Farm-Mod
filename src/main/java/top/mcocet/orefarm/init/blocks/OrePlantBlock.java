package top.mcocet.orefarm.init.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import top.mcocet.orefarm.init.ItemInit;
import top.mcocet.orefarm.utils.IC2Helper;

import java.util.Random;

public class OrePlantBlock extends BlockStem {
    private final Block oreBlock;
    private final String oreType;

    public OrePlantBlock(String name, Block oreBlock) {
        super(oreBlock);
        this.oreBlock = oreBlock;
        this.oreType = name.replace("_plant", "");
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos down = pos.down();
        IBlockState soil = worldIn.getBlockState(pos.down());
        if (soil.getBlock() != Blocks.FARMLAND) {
            return false;
        } else {
            return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this);
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        
        // 根据方块名称确定掉落的额外物品
        String registryName = this.getRegistryName().toString();
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        
        if (registryName.contains("copper")) {
            ItemStack copperIngot = IC2Helper.getCopperIngot();
            if (copperIngot != null) {
                drops.add(copperIngot.copy());
            }
        } else if (registryName.contains("tin")) {
            ItemStack tinIngot = IC2Helper.getTinIngot();
            if (tinIngot != null) {
                drops.add(tinIngot.copy());
            }
        } else if (registryName.contains("lead")) {
            ItemStack leadIngot = IC2Helper.getLeadIngot();
            if (leadIngot != null) {
                drops.add(leadIngot.copy());
            }
        } else if (registryName.contains("uranium")) {
            ItemStack uran238 = IC2Helper.getUran238();
            ItemStack uran235 = IC2Helper.getUran235();
            
            if (uran238 != null) {
                // 添加2个铀238
                ItemStack uran238Copy = uran238.copy();
                uran238Copy.setCount(2);
                drops.add(uran238Copy);
            }
            
            if (uran235 != null) {
                // 添加1个铀235
                drops.add(uran235.copy());
            }
        }
    }
}
