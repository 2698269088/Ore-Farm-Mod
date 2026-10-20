package top.mcocet.ofm.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import top.mcocet.ofm.OreFarm;
import top.mcocet.ofm.init.CreativeTabOreFarm;

/**
 * Ore seed item. Right-click on farmland plants the matching ore stem one block above,
 * mirroring the reference mod's ItemSeeds behaviour but with an explicit farmland check.
 */
public class ItemOreSeeds extends Item
{
    private final Block crops;

    /** RGB tint applied to the seed icon (compat seeds reuse a grayscale/vanilla base). */
    private final int tintColor;

    public ItemOreSeeds(String name, Block crops)
    {
        this(name, crops, 0xFFFFFF);
    }

    public ItemOreSeeds(String name, Block crops, int tintColor)
    {
        this.crops = crops;
        this.tintColor = tintColor;
        this.setUnlocalizedName(OreFarm.MODID + "." + name + "_seeds");
        this.setRegistryName(new ResourceLocation(OreFarm.MODID, name + "_seeds"));
        this.setCreativeTab(CreativeTabOreFarm.INSTANCE);
        this.setMaxStackSize(64);
    }

    public Block getCrops()
    {
        return this.crops;
    }

    public int getSeedColor()
    {
        return this.tintColor;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos,
            EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = playerIn.getHeldItem(hand);

        if (side == EnumFacing.UP)
        {
            BlockPos target = pos.up();

            if (worldIn.getBlockState(pos).getBlock() == Blocks.FARMLAND
                    && worldIn.isAirBlock(target)
                    && playerIn.canPlayerEdit(target, side, stack))
            {
                if (worldIn.setBlockState(target, this.crops.getDefaultState(), 3))
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        stack.shrink(1);
                    }
                    return EnumActionResult.SUCCESS;
                }
            }
        }

        return EnumActionResult.PASS;
    }
}
