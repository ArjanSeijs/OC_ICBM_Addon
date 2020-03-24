package com.eternalsoap.icbmopencomputersaddon.drivers;

import com.eternalsoap.icbmopencomputersaddon.util.ManagedTileEntityEnvironment;
import icbm.classic.prefab.tile.TileFrequency;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;

public abstract class FrequencyEnvironment<T extends TileFrequency> extends ManagedTileEntityEnvironment<T> {

    public FrequencyEnvironment(T tileEntity, String name) {
        super(tileEntity, name);
    }

    @Callback(doc = "function():number -- Get the Frequency the device operates on")
    public Object[] getFrequency(final Context context, final Arguments args) {
        return new Object[]{tileEntity.getFrequency()};
    }

    @Callback(doc = "function():number -- Set the Frequency the device operates on")
    public Object[] setFrequency(final Context context, final Arguments args) {
        if (args.isInteger(0)) {
            tileEntity.setFrequency(args.checkInteger(0));
        }
        return null;
    }
}
