package com.eternalsoap.icbmopencomputersaddon.drivers;

import icbm.classic.content.machines.emptower.TileEMPTower;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EMPDriver extends DriverSidedTileEntity {

    @Override
    public Class<?> getTileEntityClass() {
        return TileEMPTower.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, BlockPos pos, EnumFacing side) {
        return new EMPEnvironment((TileEMPTower) world.getTileEntity(pos));
    }
}
