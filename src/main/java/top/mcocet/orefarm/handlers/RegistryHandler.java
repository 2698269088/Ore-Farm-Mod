package top.mcocet.orefarm.handlers;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import top.mcocet.orefarm.gen.OreFarmOreGen;
import top.mcocet.orefarm.handlers.SmeltingHandler;
import top.mcocet.orefarm.init.BlockInit;
import top.mcocet.orefarm.init.ItemInit;

public class RegistryHandler {
    @SideOnly(Side.CLIENT)
    public static void Client() {
        // 注册客户端特有的内容，包括渲染
        registerRenders();
    }

    public static void Common() {
        // 注册通用内容，方块和物品等
        BlockInit.init();
        BlockInit.register();
        ItemInit.init();
        ItemInit.register();
        GameRegistry.registerWorldGenerator(new OreFarmOreGen(), 0);
        
        // 注册烧制配方
        SmeltingHandler.registerSmelting();
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        // 注册方块的渲染
        BlockInit.registerRender(BlockInit.poor_diamond_ore);
        BlockInit.registerRender(BlockInit.diamond_crop);
        BlockInit.registerRender(BlockInit.emerald_crop);
        BlockInit.registerRender(BlockInit.coal_crop);
        BlockInit.registerRender(BlockInit.iron_crop);
        BlockInit.registerRender(BlockInit.gold_crop);
        BlockInit.registerRender(BlockInit.redstone_crop);
        BlockInit.registerRender(BlockInit.lapis_crop);
        BlockInit.registerRender(BlockInit.diamond_plant);
        BlockInit.registerRender(BlockInit.emerald_plant);
        BlockInit.registerRender(BlockInit.coal_plant);
        BlockInit.registerRender(BlockInit.iron_plant);
        BlockInit.registerRender(BlockInit.gold_plant);
        BlockInit.registerRender(BlockInit.redstone_plant);
        BlockInit.registerRender(BlockInit.lapis_plant);

        // 注册物品的渲染
        ItemInit.registerRender(ItemInit.poor_diamond);
        ItemInit.registerRender(ItemInit.diamond_shard);
        ItemInit.registerRender(ItemInit.emerald_shard);
        ItemInit.registerRender(ItemInit.diamond_shard_seed);
        ItemInit.registerRender(ItemInit.emerald_shard_seed);
        ItemInit.registerRender(ItemInit.coal_shard_seed);
        ItemInit.registerRender(ItemInit.iron_shard_seed);
        ItemInit.registerRender(ItemInit.gold_shard_seed);
        ItemInit.registerRender(ItemInit.redstone_shard_seed);
        ItemInit.registerRender(ItemInit.lapis_shard_seed);
        ItemInit.registerRender(ItemInit.diamond_plant_seed);
        ItemInit.registerRender(ItemInit.emerald_plant_seed);
        ItemInit.registerRender(ItemInit.coal_plant_seed);
        ItemInit.registerRender(ItemInit.iron_plant_seed);
        ItemInit.registerRender(ItemInit.gold_plant_seed);
        ItemInit.registerRender(ItemInit.redstone_plant_seed);
        ItemInit.registerRender(ItemInit.lapis_plant_seed);
    }
}