package top.mcocet.ofm.init;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import top.mcocet.ofm.OreFarm;
import top.mcocet.ofm.block.BlockOreStem;
import top.mcocet.ofm.compat.OreCompat;

/**
 * Registers the 7 ore stem blocks. Each stem grows the matching ORE block next to it
 * when mature (the mod is called "Plants-To-Ores", so the produced block is the raw ore).
 */
@Mod.EventBusSubscriber(modid = OreFarm.MODID)
public class ModBlocks
{
    public static BlockOreStem LAPIS_STEM;
    public static BlockOreStem COAL_STEM;
    public static BlockOreStem DIAMOND_STEM;
    public static BlockOreStem EMERALD_STEM;
    public static BlockOreStem GOLD_STEM;
    public static BlockOreStem IRON_STEM;
    public static BlockOreStem REDSTONE_STEM;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        LAPIS_STEM    = new BlockOreStem("lapis",    Blocks.LAPIS_ORE,     0x3300FF);
        COAL_STEM     = new BlockOreStem("coal",     Blocks.COAL_ORE,      0x000000);
        DIAMOND_STEM  = new BlockOreStem("diamond",  Blocks.DIAMOND_ORE,   0x00FFFF);
        EMERALD_STEM  = new BlockOreStem("emerald",  Blocks.EMERALD_ORE,   0x00FF00);
        GOLD_STEM     = new BlockOreStem("gold",     Blocks.GOLD_ORE,      0xFFFF00);
        IRON_STEM     = new BlockOreStem("iron",     Blocks.IRON_ORE,      0xCCCCCC);
        REDSTONE_STEM = new BlockOreStem("redstone", Blocks.REDSTONE_ORE,  0xFF0000);

        event.getRegistry().registerAll(
                LAPIS_STEM, COAL_STEM, DIAMOND_STEM, EMERALD_STEM,
                GOLD_STEM, IRON_STEM, REDSTONE_STEM);

        // IC2 compat stems (copper/tin/lead/uranium) only when IC2 is loaded.
        OreCompat.registerBlocks(event.getRegistry());
    }
}
