package com.eternalsoap.icbmopencomputersaddon;

import com.eternalsoap.icbmopencomputersaddon.drivers.CruiseLauncherDriver;
import com.eternalsoap.icbmopencomputersaddon.drivers.EMPDriver;
import com.eternalsoap.icbmopencomputersaddon.drivers.LauncherDriver;
import com.eternalsoap.icbmopencomputersaddon.drivers.RadarDriver;
import li.cil.oc.api.Driver;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = OpenComputersICBMAddon.MODID, name = OpenComputersICBMAddon.NAME, version = OpenComputersICBMAddon.VERSION)
public class OpenComputersICBMAddon {
    public static final String MODID = "eternalsoap.icbm.opencomputers";
    public static final String NAME = "ICBM OpenComputers Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // some eternalsoap code
        logger.info("Loaded OpenComputers Addon for ICBM-Classic");
        Driver.add(new RadarDriver());
        Driver.add(new LauncherDriver());
        Driver.add(new EMPDriver());
        Driver.add(new CruiseLauncherDriver());
    }
}
