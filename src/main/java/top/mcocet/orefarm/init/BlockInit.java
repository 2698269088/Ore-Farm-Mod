package top.mcocet.orefarm.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import top.mcocet.orefarm.init.blocks.CoalShardCropBlock;
import top.mcocet.orefarm.init.blocks.DiamondShardCropBlock;
import top.mcocet.orefarm.init.blocks.EmeraldShardCropBlock;
import top.mcocet.orefarm.init.blocks.GoldShardCropBlock;
import top.mcocet.orefarm.init.blocks.IronShardCropBlock;
import top.mcocet.orefarm.init.blocks.LapisShardCropBlock;
import top.mcocet.orefarm.init.blocks.OrePlantBlock;
import top.mcocet.orefarm.init.blocks.PoorDiamondOre;
import top.mcocet.orefarm.init.blocks.RedstoneShardCropBlock;

public class BlockInit {
    public static Block poor_diamond_ore;
    public static Block diamond_crop;
    public static Block emerald_crop;
    public static Block coal_crop;
    public static Block iron_crop;
    public static Block gold_crop;
    public static Block redstone_crop;
    public static Block lapis_crop;
    public static Block diamond_plant;
    public static Block emerald_plant;
    public static Block coal_plant;
    public static Block iron_plant;
    public static Block gold_plant;
    public static Block redstone_plant;
    public static Block lapis_plant;

    public static void init() {
        poor_diamond_ore = new PoorDiamondOre("poor_diamond_ore", 3.0F, 15.0F, 2);
        diamond_crop = new DiamondShardCropBlock("diamond_crop");
        emerald_crop = new EmeraldShardCropBlock("emerald_crop");
        coal_crop = new CoalShardCropBlock("coal_crop");
        iron_crop = new IronShardCropBlock("iron_crop");
        gold_crop = new GoldShardCropBlock("gold_crop");
        redstone_crop = new RedstoneShardCropBlock("redstone_crop");
        lapis_crop = new LapisShardCropBlock("lapis_crop");
        diamond_plant = new OrePlantBlock("diamond_plant", poor_diamond_ore);
        emerald_plant = new OrePlantBlock("emerald_plant", Blocks.EMERALD_ORE);
        coal_plant = new OrePlantBlock("coal_plant", Blocks.COAL_ORE);
        iron_plant = new OrePlantBlock("iron_plant", Blocks.IRON_ORE);
        gold_plant = new OrePlantBlock("gold_plant", Blocks.GOLD_ORE);
        redstone_plant = new OrePlantBlock("redstone_plant", Blocks.REDSTONE_ORE);
        lapis_plant = new OrePlantBlock("lapis_plant", Blocks.LAPIS_ORE);
    }

    public static void register() {
        registerBlock(poor_diamond_ore);
        registerBlock(diamond_crop);
        registerBlock(emerald_crop);
        registerBlock(coal_crop);
        registerBlock(iron_crop);
        registerBlock(gold_crop);
        registerBlock(redstone_crop);
        registerBlock(lapis_crop);
        registerBlock(diamond_plant);
        registerBlock(emerald_plant);
        registerBlock(coal_plant);
        registerBlock(iron_plant);
        registerBlock(gold_plant);
        registerBlock(redstone_plant);
        registerBlock(lapis_plant);
    }

    public static void registerBlock(Block block) {
        // 注册方块和物品，这部分在服务端和客户端都需要
        ForgeRegistries.BLOCKS.register(block);
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(item);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender(Block block) {
        // 只在客户端注册渲染相关内容
        ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        System.out.println("registered render for " + block.getRegistryName().toString());
    }
}