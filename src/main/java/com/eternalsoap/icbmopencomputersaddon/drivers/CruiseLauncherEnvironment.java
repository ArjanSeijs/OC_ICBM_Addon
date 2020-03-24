package com.eternalsoap.icbmopencomputersaddon.drivers;

import icbm.classic.content.machines.launcher.cruise.TileCruiseLauncher;
import icbm.classic.lib.transform.vector.Pos;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;

import java.util.HashMap;
import java.util.Map;

public class CruiseLauncherEnvironment extends FrequencyEnvironment<TileCruiseLauncher> {

    public CruiseLauncherEnvironment(TileCruiseLauncher tileEntity) {
        super(tileEntity, "component_cruise_launcher");
    }


    @Callback(doc = "function():number -- Get the X & Z coordinate of the target position")
    public Object[] getTargetPos(final Context context, final Arguments args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("x", tileEntity.getTarget().xi());
        map.put("y", tileEntity.getTarget().yi());
        map.put("z", tileEntity.getTarget().zi());
        return new Object[]{map};
    }

    @Callback(doc = "function(x: number, y: number, z: number) -- Set the X & Y & Z coordinate of the target position")
    public Object[] setTargetPos(final Context context, final Arguments args) {
        tileEntity.setTarget(new Pos(args.checkInteger(0), args.checkInteger(1), args.checkInteger(2)));
        return null;
    }

    @Callback(doc = "function():boolean -- Checks whether a missile can be launched")
    public Object[] canLaunch(final Context context, final Arguments args) {
        return new Object[]{tileEntity.canLaunch()};
    }

    @Callback(doc = "function():boolean -- Launch the missile")
    public Object[] launch(final Context context, final Arguments args) {
        if (tileEntity.canLaunch()) {
            tileEntity.launch();
            return new Object[]{true};
        }
        return new Object[]{false};
    }

    @Override
    @Callback(doc = "function():number -- Get the Frequency the device operates on")
    public Object[] getFrequency(final Context context, final Arguments args) {
        return super.getFrequency(context, args);
    }

    @Override
    @Callback(doc = "function():number -- Set the Frequency the device operates on")
    public Object[] setFrequency(final Context context, final Arguments args) {
        return super.setFrequency(context, args);
    }
}
