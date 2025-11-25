package top.mcocet.orefarm.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import top.mcocet.orefarm.init.BlockInit;
import top.mcocet.orefarm.init.ItemInit;

public class CraftingHandler {
    public static void registerCrafting() {
        // Register crafting recipes
        GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:coal_shard_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.coal_shard_seed, 4), new Object[]{"ZXZ", "XZX", "ZXZ", 'Z', Items.COAL, 'X', Items.WHEAT_SEEDS});
        GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:coal_plant_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.coal_plant_seed, 4), new Object[]{"YXY", "XZX", "YXY", 'Z', Blocks.COAL_BLOCK, 'X', Items.WHEAT_SEEDS, 'Y', Items.COAL});
    }
}