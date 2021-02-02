package fr.skah.playersize;

import fr.skah.playersize.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = PlayerSizeMod.MODID, name = PlayerSizeMod.NAME, version = PlayerSizeMod.VERSION)
public class PlayerSizeMod {

    public static final String MODID = "playersize";
    public static final String NAME = "PlayerSize";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "fr.skah.playersize.proxy.ClientProxy", serverSide = "fr.skah.playersize.proxy.CommonProxy")
    private static CommonProxy proxy;
    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    public static CommonProxy getProxy() {
        return proxy;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
