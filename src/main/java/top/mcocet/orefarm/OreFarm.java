package top.mcocet.orefarm;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import top.mcocet.orefarm.proxy.CommonProxy;
import top.mcocet.orefarm.tabs.OreFarmTab;

@Mod(
        modid = "ore_farm",
        name = "Ore Farm",
        version = "1.0",
        acceptedMinecraftVersions = "[1.12.2]"
)
public class OreFarm {
    public static final CreativeTabs orefarmtab = new OreFarmTab("orefarmtab");
    @SidedProxy(
            clientSide = "top.mcocet.orefarm.proxy.ClientProxy",
            serverSide = "top.mcocet.orefarm.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @EventHandler
    public static void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}