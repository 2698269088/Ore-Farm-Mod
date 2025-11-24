package top.mcocet.orefarm.init.items;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import top.mcocet.orefarm.OreFarm;

public class OreSeeds extends ItemSeeds {
    public OreSeeds(String name, Block crops) {
        super(crops, Blocks.FARMLAND);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(OreFarm.orefarmtab);
    }
}