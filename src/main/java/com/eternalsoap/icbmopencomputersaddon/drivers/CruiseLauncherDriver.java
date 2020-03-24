package com.eternalsoap.icbmopencomputersaddon.drivers;

import icbm.classic.content.machines.launcher.cruise.TileCruiseLauncher;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CruiseLauncherDriver extends DriverSidedTileEntity {

    @Override
    public Class<?> getTileEntityClass() {
        return TileCruiseLauncher.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, BlockPos pos, EnumFacing side) {
        return new CruiseLauncherEnvironment((TileCruiseLauncher) world.getTileEntity(pos));
    }
}
