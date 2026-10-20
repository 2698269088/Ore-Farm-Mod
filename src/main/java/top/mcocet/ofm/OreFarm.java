package top.mcocet.ofm;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = OreFarm.MODID, name = OreFarm.NAME, version = OreFarm.VERSION,
        acceptedMinecraftVersions = "[1.12,1.13)")
public class OreFarm
{
    public static final String MODID = "ore_farm";
    public static final String NAME = "Ore Farm Mod";
    public static final String VERSION = "1.3";

    private static Logger logger;

    public static Logger getLogger()
    {
        return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        logger.info("Ore Farm Mod preInit - blocks/items registered via registry events.");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // Crafting recipes are provided as JSON (assets/ore_farm/recipes).
        logger.info("Ore Farm Mod loaded: plant ore seeds to grow ores like melons!");
    }
}
