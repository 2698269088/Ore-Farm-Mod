package top.mcocet.ofm.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Mod creative tab. In 1.12.2 simply constructing a CreativeTabs registers it,
 * and its label ("tab_ore_farm") is localized via the lang key itemGroup.tab_ore_farm.
 */
public class CreativeTabOreFarm extends CreativeTabs
{
    public static final CreativeTabOreFarm INSTANCE = new CreativeTabOreFarm();

    private CreativeTabOreFarm()
    {
        super("tab_ore_farm");
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(ModItems.DIAMOND_SEEDS);
    }
}
