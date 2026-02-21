package top.mcocet.orefarm.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import top.mcocet.orefarm.compat.ModCompatibility;
import top.mcocet.orefarm.init.BlockInit;
import top.mcocet.orefarm.init.ItemInit;

public class CraftingHandler {
    public static void registerCrafting() {
        // Register crafting recipes
        GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:coal_shard_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.coal_shard_seed, 4), new Object[]{"ZXZ", "XZX", "ZXZ", 'Z', Items.COAL, 'X', Items.WHEAT_SEEDS});
        GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:coal_plant_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.coal_plant_seed, 4), new Object[]{"YXY", "XZX", "YXY", 'Z', Blocks.COAL_BLOCK, 'X', Items.WHEAT_SEEDS, 'Y', Items.COAL});
        
        // Register IC2 related crafting recipes only if IC2 is loaded
        if (ModCompatibility.isIC2Loaded()) {
            // Copper seed recipes
            GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:copper_shard_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.copper_shard_seed, 4), new Object[]{"YXY", "XZX", "YXY", 'X', Items.WHEAT_SEEDS, 'Y', "ingotCopper", 'Z', "ingotCopper"});
            GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:copper_plant_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.copper_plant_seed, 4), new Object[]{"YXY", "XZX", "YXY", 'X', Items.WHEAT_SEEDS, 'Y', "ingotCopper", 'Z', "blockCopper"});
            
            // Tin seed recipes
            GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:tin_shard_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.tin_shard_seed, 4), new Object[]{"YXY", "XZX", "YXY", 'X', Items.WHEAT_SEEDS, 'Y', "ingotTin", 'Z', "ingotTin"});
            GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:tin_plant_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.tin_plant_seed, 4), new Object[]{"YXY", "XZX", "YXY", 'X', Items.WHEAT_SEEDS, 'Y', "ingotTin", 'Z', "blockTin"});
            
            // Lead seed recipes
            GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:lead_shard_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.lead_shard_seed, 4), new Object[]{"YXY", "XZX", "YXY", 'X', Items.WHEAT_SEEDS, 'Y', "ingotLead", 'Z', "ingotLead"});
            GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:lead_plant_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.lead_plant_seed, 4), new Object[]{"YXY", "XZX", "YXY", 'X', Items.WHEAT_SEEDS, 'Y', "ingotLead", 'Z', "blockLead"});
            
            // Uranium seed recipes
            GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:uranium_shard_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.uranium_shard_seed, 4), new Object[]{"YXY", "XZX", "YXY", 'X', Items.WHEAT_SEEDS, 'Y', "ingotUranium", 'Z', "ingotUranium"});
            GameRegistry.addShapedRecipe(new ResourceLocation("ore_farm:uranium_plant_seed"), new ResourceLocation("ore_farm:seeds"), new ItemStack(ItemInit.uranium_plant_seed, 4), new Object[]{"YXY", "XZX", "YXY", 'X', Items.WHEAT_SEEDS, 'Y', "ingotUranium", 'Z', "blockUranium"});
        }
    }
}