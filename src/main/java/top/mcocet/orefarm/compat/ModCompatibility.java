package top.mcocet.orefarm.compat;

import net.minecraftforge.fml.common.Loader;

public class ModCompatibility {
    
    /**
     * 检测IC2模组是否存在
     * @return 如果IC2模组已安装则返回true，否则返回false
     */
    public static boolean isIC2Loaded() {
        return Loader.isModLoaded("ic2");
    }
}