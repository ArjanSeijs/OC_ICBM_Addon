package com.eternalsoap.icbmopencomputersaddon.drivers;

import icbm.classic.content.machines.radarstation.TileRadarStation;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Driver for {@link TileRadarStation}
 */
public class RadarDriver extends DriverSidedTileEntity {

    @Override
    public Class<?> getTileEntityClass() {
        return TileRadarStation.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, BlockPos blockPos, EnumFacing enumFacing) {
        return new RadarEnvironment((TileRadarStation) world.getTileEntity(blockPos));
    }

}
