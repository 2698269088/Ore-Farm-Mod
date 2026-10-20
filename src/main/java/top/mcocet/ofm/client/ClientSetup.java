package top.mcocet.ofm.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import top.mcocet.ofm.OreFarm;
import top.mcocet.ofm.block.BlockOreStem;
import top.mcocet.ofm.compat.OreCompat;
import top.mcocet.ofm.init.ModBlocks;
import top.mcocet.ofm.init.ModItems;
import top.mcocet.ofm.item.ItemOreSeeds;

/**
 * Client-only seed item model registration. Restricted to Side.CLIENT so the class is
 * never registered/loaded on a dedicated server (ModelLoader is client-only).
 *
 * Stem blocks render from their blockstate JSON in assets/ore_farm/blockstates.
 */
@Mod.EventBusSubscriber(modid = OreFarm.MODID, value = Side.CLIENT)
public class ClientSetup
{
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        registerSeed(ModItems.LAPIS_SEEDS);
        registerSeed(ModItems.COAL_SEEDS);
        registerSeed(ModItems.DIAMOND_SEEDS);
        registerSeed(ModItems.EMERALD_SEEDS);
        registerSeed(ModItems.GOLD_SEEDS);
        registerSeed(ModItems.IRON_SEEDS);
        registerSeed(ModItems.REDSTONE_SEEDS);

        if (OreCompat.isPresent())
        {
            registerSeed(OreCompat.COPPER_SEEDS);
            registerSeed(OreCompat.TIN_SEEDS);
            registerSeed(OreCompat.LEAD_SEEDS);
            registerSeed(OreCompat.URANIUM_SEEDS);
        }
    }

    private static void registerSeed(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    /**
     * Tint each ore stem with the fixed colour of its corresponding ore (coal=black,
     * diamond=cyan, ...), exactly like the reference Plants-To-Ores mod's colorMultiplier.
     * The vine textures are grayscale and the inherited block/stem_growth & block/stem_fruit
     * models use tintindex 0, so this colour is multiplied over the whole plant.
     */
    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event)
    {
        IBlockColor stemColor = (state, world, pos, tintIndex) ->
                ((BlockOreStem) state.getBlock()).getTintColor();

        event.getBlockColors().registerBlockColorHandler(stemColor,
                ModBlocks.LAPIS_STEM, ModBlocks.COAL_STEM, ModBlocks.DIAMOND_STEM,
                ModBlocks.EMERALD_STEM, ModBlocks.GOLD_STEM, ModBlocks.IRON_STEM,
                ModBlocks.REDSTONE_STEM);

        if (OreCompat.isPresent())
        {
            event.getBlockColors().registerBlockColorHandler(stemColor,
                    OreCompat.COPPER_STEM, OreCompat.TIN_STEM, OreCompat.LEAD_STEM,
                    OreCompat.URANIUM_STEM);
        }
    }

    /**
     * Tint the IC2 compat seed icons by their metal colour. Compat seeds reuse a shared
     * base seed texture, so the per-metal colour is what tells them apart in the inventory.
     */
    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event)
    {
        if (!OreCompat.isPresent()) return;

        IItemColor seedColor = (stack, tintIndex) ->
                ((ItemOreSeeds) stack.getItem()).getSeedColor();

        event.getItemColors().registerItemColorHandler(seedColor,
                OreCompat.COPPER_SEEDS, OreCompat.TIN_SEEDS, OreCompat.LEAD_SEEDS,
                OreCompat.URANIUM_SEEDS);
    }
}
