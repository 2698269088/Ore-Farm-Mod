package top.mcocet.orefarm.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import top.mcocet.orefarm.compat.ModCompatibility;
import top.mcocet.orefarm.init.blocks.*;
import top.mcocet.orefarm.utils.IC2Helper;

import java.util.concurrent.TimeUnit;

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
    // IC2作物方块
    public static Block copper_crop;
    public static Block tin_crop;
    public static Block lead_crop;
    public static Block uranium_crop;
    public static Block copper_plant;
    public static Block tin_plant;
    public static Block lead_plant;
    public static Block uranium_plant;

    /**
     * 带重试机制获取IC2矿石
     */
    private static ItemStack getIC2OreWithRetry(String oreType) {
        ItemStack stack = null;
        int maxRetries = 3;
        
        for (int i = 0; i < maxRetries; i++) {
            try {
                switch (oreType) {
                    case "copper":
                        stack = IC2Helper.getCopperOreBlock();
                        break;
                    case "tin":
                        stack = IC2Helper.getTinOreBlock();
                        break;
                    case "lead":
                        stack = IC2Helper.getLeadOreBlock();
                        break;
                    case "uranium":
                        stack = IC2Helper.getUraniumOreBlock();
                        break;
                }
                
                if (stack != null && stack.getItem() != null) {
                    System.out.println("[OreFarm] Successfully got " + oreType + " ore on attempt " + (i + 1));
                    return stack;
                }
                
                if (i < maxRetries - 1) {
                    System.out.println("[OreFarm] Failed to get " + oreType + " ore, retrying in 100ms...");
                    Thread.sleep(100); // 等待100毫秒后重试
                }
                
            } catch (Exception e) {
                System.out.println("[OreFarm] Exception getting " + oreType + " ore on attempt " + (i + 1) + ": " + e.getMessage());
                if (i < maxRetries - 1) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        
        System.out.println("[OreFarm] Failed to get " + oreType + " ore after " + maxRetries + " attempts");
        return null;
    }
    
    /**
     * 格式化物品堆栈信息用于日志输出
     */
    private static String formatItemStackInfo(ItemStack stack) {
        if (stack == null) {
            return "null";
        }
        if (stack.getItem() == null) {
            return "ItemStack with null item, stackSize: " + stack.getCount();
        }
        return stack.toString() + " (item: " + stack.getItem().getRegistryName() + ", count: " + stack.getCount() + ")";
    }
    
    /**
     * 安全地从物品堆栈获取方块
     */
    private static Block getBlockFromStack(ItemStack stack, Block fallback, String oreType) {
        if (stack != null && stack.getItem() != null) {
            Block block = Block.getBlockFromItem(stack.getItem());
            if (block != null && block != Blocks.AIR) {
                System.out.println("[OreFarm] Using actual IC2 " + oreType + " ore block");
                return block;
            } else {
                System.out.println("[OreFarm] Warning: Could not get valid block from " + oreType + " item, using fallback");
            }
        } else {
            System.out.println("[OreFarm] Warning: Invalid " + oreType + " ore stack, using fallback");
        }
        return fallback;
    }

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
        
        // 只有在IC2模组存在时才初始化IC2作物方块
        if (ModCompatibility.isIC2Loaded()) {
            copper_crop = new CopperShardCropBlock("copper_crop");
            tin_crop = new TinShardCropBlock("tin_crop");
            lead_crop = new LeadShardCropBlock("lead_crop");
            uranium_crop = new UraniumShardCropBlock("uranium_crop");
            ItemStack copperOreStack = IC2Helper.getCopperOreBlock();
            ItemStack tinOreStack = IC2Helper.getTinOreBlock();
            ItemStack leadOreStack = IC2Helper.getLeadOreBlock();
            ItemStack uraniumOreStack = IC2Helper.getUraniumOreBlock();
            
            Block copperOreBlock = copperOreStack != null ? Block.getBlockFromItem(copperOreStack.getItem()) : Blocks.COAL_ORE;
            Block tinOreBlock = tinOreStack != null ? Block.getBlockFromItem(tinOreStack.getItem()) : Blocks.COAL_ORE;
            Block leadOreBlock = leadOreStack != null ? Block.getBlockFromItem(leadOreStack.getItem()) : Blocks.COAL_ORE;
            Block uraniumOreBlock = uraniumOreStack != null ? Block.getBlockFromItem(uraniumOreStack.getItem()) : Blocks.COAL_ORE;

            copper_plant = new OrePlantBlock("copper_plant", copperOreBlock);
            tin_plant = new OrePlantBlock("tin_plant", tinOreBlock);
            lead_plant = new OrePlantBlock("lead_plant", leadOreBlock);
            uranium_plant = new OrePlantBlock("uranium_plant", uraniumOreBlock);
        }
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
        
        // 只有在IC2模组存在时才注册IC2作物方块
        if (ModCompatibility.isIC2Loaded()) {
            registerBlock(copper_crop);
            registerBlock(tin_crop);
            registerBlock(lead_crop);
            registerBlock(uranium_crop);
            registerBlock(copper_plant);
            registerBlock(tin_plant);
            registerBlock(lead_plant);
            registerBlock(uranium_plant);
        }
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