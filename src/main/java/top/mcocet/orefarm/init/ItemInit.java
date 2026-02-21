package top.mcocet.orefarm.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import top.mcocet.orefarm.compat.ModCompatibility;
import top.mcocet.orefarm.init.items.ItemShard;
import top.mcocet.orefarm.init.items.OreSeeds;

public class ItemInit {
    public static Item poor_diamond;
    public static Item diamond_shard;
    public static Item emerald_shard;
    public static Item diamond_shard_seed;
    public static Item emerald_shard_seed;
    public static Item coal_shard_seed;
    public static Item iron_shard_seed;
    public static Item gold_shard_seed;
    public static Item redstone_shard_seed;
    public static Item lapis_shard_seed;
    public static Item diamond_plant_seed;
    public static Item emerald_plant_seed;
    public static Item coal_plant_seed;
    public static Item iron_plant_seed;
    public static Item gold_plant_seed;
    public static Item redstone_plant_seed;
    public static Item lapis_plant_seed;
    // IC2种子物品
    public static Item copper_shard_seed;
    public static Item tin_shard_seed;
    public static Item lead_shard_seed;
    public static Item uranium_shard_seed;
    // IC2植物种子物品
    public static Item copper_plant_seed;
    public static Item tin_plant_seed;
    public static Item lead_plant_seed;
    public static Item uranium_plant_seed;

    public static void init() {
        poor_diamond = new ItemShard("poor_diamond");
        diamond_shard = new ItemShard("diamond_shard");
        emerald_shard = new ItemShard("emerald_shard");
        diamond_shard_seed = new OreSeeds("diamond_shard_seed", BlockInit.diamond_crop);
        emerald_shard_seed = new OreSeeds("emerald_shard_seed", BlockInit.emerald_crop);
        coal_shard_seed = new OreSeeds("coal_shard_seed", BlockInit.coal_crop);
        iron_shard_seed = new OreSeeds("iron_shard_seed", BlockInit.iron_crop);
        gold_shard_seed = new OreSeeds("gold_shard_seed", BlockInit.gold_crop);
        redstone_shard_seed = new OreSeeds("redstone_shard_seed", BlockInit.redstone_crop);
        lapis_shard_seed = new OreSeeds("lapis_shard_seed", BlockInit.lapis_crop);
        diamond_plant_seed = new OreSeeds("diamond_plant_seed", BlockInit.diamond_plant);
        emerald_plant_seed = new OreSeeds("emerald_plant_seed", BlockInit.emerald_plant);
        coal_plant_seed = new OreSeeds("coal_plant_seed", BlockInit.coal_plant);
        iron_plant_seed = new OreSeeds("iron_plant_seed", BlockInit.iron_plant);
        gold_plant_seed = new OreSeeds("gold_plant_seed", BlockInit.gold_plant);
        redstone_plant_seed = new OreSeeds("redstone_plant_seed", BlockInit.redstone_plant);
        lapis_plant_seed = new OreSeeds("lapis_plant_seed", BlockInit.lapis_plant);
        
        // 只有在IC2模组存在时才初始化IC2种子物品
        if (ModCompatibility.isIC2Loaded()) {
            copper_shard_seed = new OreSeeds("copper_shard_seed", BlockInit.copper_crop);
            tin_shard_seed = new OreSeeds("tin_shard_seed", BlockInit.tin_crop);
            lead_shard_seed = new OreSeeds("lead_shard_seed", BlockInit.lead_crop);
            uranium_shard_seed = new OreSeeds("uranium_shard_seed", BlockInit.uranium_crop);
            copper_plant_seed = new OreSeeds("copper_plant_seed", BlockInit.copper_plant);
            tin_plant_seed = new OreSeeds("tin_plant_seed", BlockInit.tin_plant);
            lead_plant_seed = new OreSeeds("lead_plant_seed", BlockInit.lead_plant);
            uranium_plant_seed = new OreSeeds("uranium_plant_seed", BlockInit.uranium_plant);
        }
    }

    public static void register() {
        registerItem(poor_diamond);
        registerItem(diamond_shard);
        registerItem(emerald_shard);
        registerItem(diamond_shard_seed);
        registerItem(emerald_shard_seed);
        registerItem(coal_shard_seed);
        registerItem(iron_shard_seed);
        registerItem(gold_shard_seed);
        registerItem(redstone_shard_seed);
        registerItem(lapis_shard_seed);
        registerItem(diamond_plant_seed);
        registerItem(emerald_plant_seed);
        registerItem(coal_plant_seed);
        registerItem(iron_plant_seed);
        registerItem(gold_plant_seed);
        registerItem(redstone_plant_seed);
        registerItem(lapis_plant_seed);
        
        // 只有在IC2模组存在时才注册IC2种子物品
        if (ModCompatibility.isIC2Loaded()) {
            registerItem(copper_shard_seed);
            registerItem(tin_shard_seed);
            registerItem(lead_shard_seed);
            registerItem(uranium_shard_seed);
            registerItem(copper_plant_seed);
            registerItem(tin_plant_seed);
            registerItem(lead_plant_seed);
            registerItem(uranium_plant_seed);
        }
    }

    public static void registerItem(Item item) {
        // 注册物品，这部分在服务端和客户端都需要
        ForgeRegistries.ITEMS.register(item);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender(Item item) {
        // 只在客户端注册渲染相关内容
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}