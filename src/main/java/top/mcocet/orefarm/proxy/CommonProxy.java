package top.mcocet.orefarm.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import top.mcocet.orefarm.handlers.RegistryHandler;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        RegistryHandler.Common();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
