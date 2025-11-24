package top.mcocet.orefarm.init.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import top.mcocet.orefarm.OreFarm;

public class ItemShard extends Item {
    public ItemShard(String name) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(OreFarm.orefarmtab);
    }

    public ItemShard(String name, CreativeTabs creativeTab) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(creativeTab);
    }
}
