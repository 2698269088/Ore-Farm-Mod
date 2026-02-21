package top.mcocet.orefarm.utils;

import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

public class IC2Helper {
    
    /**
     * 获取IC2物品
     * @param name 物品名称
     * @return 对应的物品堆栈
     */
    @Optional.Method(modid = "ic2")
    public static ItemStack getItemStack(String name) {
        return IC2Items.getItem(name);
    }
    
    /**
     * 获取铜锭
     * @return 铜锭物品堆栈
     */
    @Optional.Method(modid = "ic2")
    public static ItemStack getCopperIngot() {
        ItemStack stack = IC2Items.getItem("copperIngot");
        return stack != null ? stack.copy() : null;
    }
    
    /**
     * 获取锡锭
     * @return 锡锭物品堆栈
     */
    @Optional.Method(modid = "ic2")
    public static ItemStack getTinIngot() {
        ItemStack stack = IC2Items.getItem("tinIngot");
        return stack != null ? stack.copy() : null;
    }
    
    /**
     * 获取铅锭
     * @return 铅锭物品堆栈
     */
    @Optional.Method(modid = "ic2")
    public static ItemStack getLeadIngot() {
        ItemStack stack = IC2Items.getItem("leadIngot");
        return stack != null ? stack.copy() : null;
    }
    
    /**
     * 获取铀238
     * @return 铀238物品堆栈
     */
    @Optional.Method(modid = "ic2")
    public static ItemStack getUran238() {
        ItemStack stack = IC2Items.getItem("Uran238");
        return stack != null ? stack.copy() : null;
    }
    
    /**
     * 获取铀235
     * @return 铀235物品堆栈
     */
    @Optional.Method(modid = "ic2")
    public static ItemStack getUran235() {
        ItemStack stack = IC2Items.getItem("Uran235");
        return stack != null ? stack.copy() : null;
    }
    
    /**
     * 获取铜矿石方块
     * @return 铜矿石方块物品堆栈
     */
    @Optional.Method(modid = "ic2")
    public static ItemStack getCopperOreBlock() {
        try {
            // 首先尝试直接获取IC2铜矿石
            ItemStack stack = IC2Items.getItem("copperOre");
            if (stack != null && stack.getItem() != null) {
                System.out.println("[OreFarm] Successfully got IC2 copperOre: " + stack.toString());
                return stack.copy();
            }
            
            System.out.println("[OreFarm] IC2 copperOre is null, trying ore dictionary");
            // 备选方案：使用矿物词典
            return getOreFromOreDictionary("oreCopper");
        } catch (Exception e) {
            System.out.println("[OreFarm] Exception getting copperOre: " + e.getMessage());
            return getOreFromOreDictionary("oreCopper");
        }
    }
    
    /**
     * 获取锡矿石方块
     * @return 锡矿石方块物品堆栈
     */
    @Optional.Method(modid = "ic2")
    public static ItemStack getTinOreBlock() {
        try {
            // 首先尝试直接获取IC2锡矿石
            ItemStack stack = IC2Items.getItem("tinOre");
            if (stack != null && stack.getItem() != null) {
                System.out.println("[OreFarm] Successfully got IC2 tinOre: " + stack.toString());
                return stack.copy();
            }
            
            System.out.println("[OreFarm] IC2 tinOre is null, trying ore dictionary");
            // 备选方案：使用矿物词典
            return getOreFromOreDictionary("oreTin");
        } catch (Exception e) {
            System.out.println("[OreFarm] Exception getting tinOre: " + e.getMessage());
            return getOreFromOreDictionary("oreTin");
        }
    }
    
    /**
     * 获取铅矿石方块
     * @return 铅矿石方块物品堆栈
     */
    @Optional.Method(modid = "ic2")
    public static ItemStack getLeadOreBlock() {
        try {
            // 首先尝试直接获取IC2铅矿石
            ItemStack stack = IC2Items.getItem("leadOre");
            if (stack != null && stack.getItem() != null) {
                System.out.println("[OreFarm] Successfully got IC2 leadOre: " + stack.toString());
                return stack.copy();
            }
            
            System.out.println("[OreFarm] IC2 leadOre is null, trying ore dictionary");
            // 备选方案：使用矿物词典
            return getOreFromOreDictionary("oreLead");
        } catch (Exception e) {
            System.out.println("[OreFarm] Exception getting leadOre: " + e.getMessage());
            return getOreFromOreDictionary("oreLead");
        }
    }
    
    /**
     * 获取铀矿石方块
     * @return 铀矿石方块物品堆栈
     */
    @Optional.Method(modid = "ic2")
    public static ItemStack getUraniumOreBlock() {
        try {
            // 首先尝试直接获取IC2铀矿石
            ItemStack stack = IC2Items.getItem("uraniumOre");
            if (stack != null && stack.getItem() != null) {
                System.out.println("[OreFarm] Successfully got IC2 uraniumOre: " + stack.toString());
                return stack.copy();
            }
            
            System.out.println("[OreFarm] IC2 uraniumOre is null, trying ore dictionary");
            // 备选方案：使用矿物词典
            return getOreFromOreDictionary("oreUranium");
        } catch (Exception e) {
            System.out.println("[OreFarm] Exception getting uraniumOre: " + e.getMessage());
            return getOreFromOreDictionary("oreUranium");
        }
    }
    
    /**
     * 从矿物词典获取矿石
     * @param oreName 矿物词典名称
     * @return 矿石物品堆栈
     */
    @Optional.Method(modid = "ic2")
    private static ItemStack getOreFromOreDictionary(String oreName) {
        try {
            System.out.println("[OreFarm] Looking for " + oreName + " in ore dictionary");
            
            // 直接使用OreDictionary静态方法
            net.minecraft.util.NonNullList<net.minecraft.item.ItemStack> ores = net.minecraftforge.oredict.OreDictionary.getOres(oreName);
            
            if (ores != null && !ores.isEmpty()) {
                // 遍历找到第一个有效的矿石
                for (ItemStack oreStack : ores) {
                    if (oreStack != null && oreStack.getItem() != null) {
                        System.out.println("[OreFarm] Found valid " + oreName + " from ore dictionary: " + oreStack.toString());
                        return oreStack.copy();
                    }
                }
                System.out.println("[OreFarm] Found " + oreName + " entries but all were invalid");
            } else {
                System.out.println("[OreFarm] No entries found for " + oreName + " in ore dictionary");
            }
        } catch (Exception e) {
            System.out.println("[OreFarm] Exception getting ore from dictionary " + oreName + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}