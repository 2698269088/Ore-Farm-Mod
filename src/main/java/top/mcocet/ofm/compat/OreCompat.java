package top.mcocet.ofm.compat;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import top.mcocet.ofm.block.BlockOreStem;
import top.mcocet.ofm.item.ItemOreSeeds;

/**
 * IC2 soft-dependency compat: adds 4 plantable ore stems/seeds (copper, tin, lead, uranium)
 * only when IndustrialCraft 2 is present.
 *
 * IC2 1.12 represents all its ores as ONE block ("ic2:resource") distinguished by metadata,
 * and registers them into the Forge Ore Dictionary (oreCopper/oreTin/oreLead/oreUranium).
 * We therefore resolve the concrete ore block+meta LAZILY at runtime via the ore dictionary,
 * which means:
 *   - no compile-time dependency on IC2 (this file uses only vanilla/Forge APIs), and
 *   - no crash when IC2 is absent (registration is gated by {@link #isPresent()}).
 */
public final class OreCompat
{
    public static final String IC2_MODID = "ic2";

    public static BlockOreStem COPPER_STEM;
    public static BlockOreStem TIN_STEM;
    public static BlockOreStem LEAD_STEM;
    public static BlockOreStem URANIUM_STEM;

    public static ItemOreSeeds COPPER_SEEDS;
    public static ItemOreSeeds TIN_SEEDS;
    public static ItemOreSeeds LEAD_SEEDS;
    public static ItemOreSeeds URANIUM_SEEDS;

    // Vine / seed tints (RGB), matching the look of each metal.
    private static final int COPPER  = 0xC87137;   // orange
    private static final int TIN     = 0xC8CDD6;   // light silver
    private static final int LEAD    = 0x5573B0;   // blue-grey (was near-black; now clearly blue)
    private static final int URANIUM = 0x39D64F;   // green

    private OreCompat() {}

    public static boolean isPresent()
    {
        return Loader.isModLoaded(IC2_MODID);
    }

    public static void registerBlocks(IForgeRegistry<Block> registry)
    {
        if (!isPresent()) return;
        COPPER_STEM  = newStem("copper",  "oreCopper",  COPPER);
        TIN_STEM     = newStem("tin",     "oreTin",     TIN);
        LEAD_STEM    = newStem("lead",    "oreLead",    LEAD);
        URANIUM_STEM = newStem("uranium", "oreUranium", URANIUM);
        registry.register(COPPER_STEM);
        registry.register(TIN_STEM);
        registry.register(LEAD_STEM);
        registry.register(URANIUM_STEM);
    }

    public static void registerItems(IForgeRegistry<Item> registry)
    {
        if (!isPresent()) return;
        COPPER_SEEDS  = new ItemOreSeeds("copper",  COPPER_STEM,  COPPER);
        TIN_SEEDS     = new ItemOreSeeds("tin",     TIN_STEM,     TIN);
        LEAD_SEEDS    = new ItemOreSeeds("lead",    LEAD_STEM,    LEAD);
        URANIUM_SEEDS = new ItemOreSeeds("uranium", URANIUM_STEM, URANIUM);
        registry.register(COPPER_SEEDS);
        registry.register(TIN_SEEDS);
        registry.register(LEAD_SEEDS);
        registry.register(URANIUM_SEEDS);
    }

    /** Builds a stem whose fruit block state is resolved lazily from the ore dictionary. */
    private static BlockOreStem newStem(String name, String oreDict, int tint)
    {
        OreFruit fruit = new OreFruit(oreDict);
        return new BlockOreStem(name, fruit::state, fruit::matches, tint);
    }

    /**
     * Lazily resolves a concrete block + metadata from an Ore Dictionary entry and caches it.
     * Returns null / false when nothing is registered yet (e.g. IC2 not loaded or not initialized).
     */
    private static final class OreFruit
    {
        private final String oreDict;
        private Block block;
        private int meta;
        private boolean resolved;

        OreFruit(String oreDict)
        {
            this.oreDict = oreDict;
        }

        private boolean ensure()
        {
            if (resolved) return block != null;
            resolved = true;
            NonNullList<ItemStack> ores = OreDictionary.getOres(oreDict, false);
            for (ItemStack stack : ores)
            {
                if (stack.isEmpty()) continue;
                Block b = Block.getBlockFromItem(stack.getItem());
                if (b != null)
                {
                    this.block = b;
                    this.meta = stack.getMetadata();
                    return true;
                }
            }
            return false;
        }

        IBlockState state()
        {
            return ensure() ? block.getStateFromMeta(meta) : null;
        }

        boolean matches(IBlockState state)
        {
            return ensure() && state.getBlock() == block && block.getMetaFromState(state) == meta;
        }
    }
}
