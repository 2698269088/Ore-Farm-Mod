package top.mcocet.orefarm.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import top.mcocet.orefarm.init.ItemInit;

public class OreFarmTab extends CreativeTabs {
    public OreFarmTab(String label) {
        super(label);
    }

    public ItemStack getTabIconItem() {
        return new ItemStack(ItemInit.poor_diamond);
    }
}
