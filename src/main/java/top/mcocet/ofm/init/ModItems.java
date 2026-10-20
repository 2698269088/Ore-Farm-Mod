package top.mcocet.ofm.init;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import top.mcocet.ofm.OreFarm;
import top.mcocet.ofm.item.ItemOreSeeds;
import top.mcocet.ofm.compat.OreCompat;

/**
 * Registers the 7 ore seed items. Blocks register before items (Forge fires
 * Register&lt;Block&gt; ahead of Register&lt;Item&gt;), so the stem references are ready here.
 */
@Mod.EventBusSubscriber(modid = OreFarm.MODID)
public class ModItems
{
    public static ItemOreSeeds LAPIS_SEEDS;
    public static ItemOreSeeds COAL_SEEDS;
    public static ItemOreSeeds DIAMOND_SEEDS;
    public static ItemOreSeeds EMERALD_SEEDS;
    public static ItemOreSeeds GOLD_SEEDS;
    public static ItemOreSeeds IRON_SEEDS;
    public static ItemOreSeeds REDSTONE_SEEDS;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        LAPIS_SEEDS    = new ItemOreSeeds("lapis",    ModBlocks.LAPIS_STEM);
        COAL_SEEDS     = new ItemOreSeeds("coal",     ModBlocks.COAL_STEM);
        DIAMOND_SEEDS  = new ItemOreSeeds("diamond",  ModBlocks.DIAMOND_STEM);
        EMERALD_SEEDS  = new ItemOreSeeds("emerald",  ModBlocks.EMERALD_STEM);
        GOLD_SEEDS     = new ItemOreSeeds("gold",     ModBlocks.GOLD_STEM);
        IRON_SEEDS     = new ItemOreSeeds("iron",     ModBlocks.IRON_STEM);
        REDSTONE_SEEDS = new ItemOreSeeds("redstone", ModBlocks.REDSTONE_STEM);

        event.getRegistry().registerAll(
                LAPIS_SEEDS, COAL_SEEDS, DIAMOND_SEEDS, EMERALD_SEEDS,
                GOLD_SEEDS, IRON_SEEDS, REDSTONE_SEEDS);

        // IC2 compat seeds, mirroring the compat stems registered earlier.
        OreCompat.registerItems(event.getRegistry());
    }
}
