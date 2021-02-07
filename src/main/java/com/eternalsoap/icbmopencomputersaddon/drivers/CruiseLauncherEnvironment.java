package com.eternalsoap.icbmopencomputersaddon.drivers;

import icbm.classic.content.blocks.launcher.cruise.TileCruiseLauncher;
import icbm.classic.lib.transform.vector.Pos;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class CruiseLauncherEnvironment extends FrequencyEnvironment<TileCruiseLauncher> implements NamedBlock {

    public static final String COMPONENT_NAME = "icbm_cruise_launcher";

    public CruiseLauncherEnvironment(TileCruiseLauncher tileEntity) {
        super(tileEntity, COMPONENT_NAME);
    }

    @Callback(doc = "function(s:string):boolean -- Method for finding this component when looping through the component list, returns true iff s == \""+ COMPONENT_NAME + "\"")
    public Object[] isICBM(final Context context, final Arguments arguments) {
        return new Object[]{arguments.checkString(0).equals(COMPONENT_NAME)};
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

    @Override
    public String preferredName() {
        return COMPONENT_NAME;
    }

    @Override
    public int priority() {
        return 1;
    }
}
