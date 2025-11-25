package top.mcocet.orefarm.handlers;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import top.mcocet.orefarm.init.ItemInit;

public class SmeltingHandler {
    public static void registerSmelting() {
        // Register smelting recipe for poor diamond to diamond
        GameRegistry.addSmelting(ItemInit.poor_diamond, new ItemStack(Items.DIAMOND), 15.0F);
    }
}