package com.eternalsoap.icbmopencomputersaddon.drivers;

import icbm.classic.content.machines.launcher.base.TileLauncherBase;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Driver for {@link TileLauncherBase}
 */
public class LauncherDriver extends DriverSidedTileEntity {

    @Override
    public Class<?> getTileEntityClass() {
        return TileLauncherBase.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, BlockPos pos, EnumFacing side) {
        return new LauncherEnvironment((TileLauncherBase) world.getTileEntity(pos));
    }
}
