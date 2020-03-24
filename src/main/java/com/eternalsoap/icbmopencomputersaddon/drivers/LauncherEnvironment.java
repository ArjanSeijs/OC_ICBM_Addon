package com.eternalsoap.icbmopencomputersaddon.drivers;

import com.eternalsoap.icbmopencomputersaddon.util.ManagedTileEntityEnvironment;
import icbm.classic.content.machines.launcher.base.TileLauncherBase;
import icbm.classic.lib.transform.vector.Pos;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;

import java.util.HashMap;
import java.util.Map;

public class LauncherEnvironment extends ManagedTileEntityEnvironment<TileLauncherBase> {

    public LauncherEnvironment(TileLauncherBase launcherBase) {
        super(launcherBase, "component_missile_launcher");
    }

    @Callback(doc = "function():number -- Get the X & Z coordinate of the target position")
    public Object[] getTargetPos(final Context context, final Arguments args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("x", tileEntity.launchScreen.getTarget().xi());
        map.put("z", tileEntity.launchScreen.getTarget().zi());
        return new Object[]{map};
    }

    @Callback(doc = "function(x: number,z: number) -- Set the X & Z coordinate of the target position")
    public Object[] setTargetPos(final Context context, final Arguments args) {
        tileEntity.launchScreen.setTarget(new Pos(args.checkInteger(0), tileEntity.launchScreen.getTarget().yi(), args.checkInteger(1)));
        return null;
    }

    @Callback(doc = "function(y: number) -- The y coordinate")
    public Object[] setDetonationHeight(final Context context, final Arguments args) {
        if (this.tileEntity.getTier().ordinal() >= 1)
            tileEntity.launchScreen.setTarget(new Pos(tileEntity.launchScreen.getTarget().xi(), args.checkInteger(0), tileEntity.launchScreen.getTarget().zi()));
        return null;
    }

    @Callback(doc = "function() -- The y coordinate")
    public Object[] getDetonationHeight(final Context context, final Arguments args) {
        return new Object[]{tileEntity.launchScreen.getTarget().yi()};
    }

    @Callback(doc = "function(x: number,z: number):boolean -- Launch missile at stored position or by providing the x and z coordinates")
    public Object[] launch(final Context context, final Arguments args) {
        return launchMissile(context, args);
    }

    @Callback(doc = "function(x: number,z: number):boolean -- Launch missile at stored position or by providing the x and z coordinates")
    public Object[] launchMissile(final Context context, final Arguments args) {
        Pos target = tileEntity.launchScreen.getTarget();
        if (args.isInteger(0) && args.isInteger(1)) {
            target = new Pos(args.checkInteger(0), args.checkInteger(1));
        }
        int lockHeight = tileEntity.launchScreen.lockHeight;
        boolean launchMissile = tileEntity.launchMissile(target, lockHeight);
        return new Object[]{launchMissile};
    }

    @Callback(doc = "function():boolean -- Checks whether a missile is contained inside the launcher base")
    public Object[] containsMissile(final Context context, final Arguments args) {
        return new Object[]{!tileEntity.getMissileStack().isEmpty()};
    }

    @Callback(doc = "function():number -- Get the range of the current missile launcher")
    public Object[] getRange(final Context context, final Arguments args) {
        return new Object[]{tileEntity.getRange()};
    }

    @Callback(doc = "function(x: number,z: number):boolean -- Get whether the given position is within range.")
    public Object[] isInRange(final Context context, final Arguments args) {
        if (args.isInteger(0) && args.isInteger(1)) {
            Pos target = new Pos(args.checkInteger(0), args.checkInteger(1));
            return new Object[]{tileEntity.isInRange(target)};
        }
        return new Object[]{false};
    }

    @Callback(doc = "function():number -- Get the inaccuracy of the current missile launcher")
    public Object[] getInaccuracy(final Context context, final Arguments args) {
        int inaccuracy = 30;
        if (tileEntity.supportFrame != null) {
            inaccuracy = tileEntity.supportFrame.getInaccuracy();
        }
        return new Object[]{inaccuracy};
    }

    @Callback(doc = "function():number -- Get the Frequency of the launcher, only works when the tier supports the frequency")
    public Object[] getFrequency(final Context context, final Arguments args) {
        return new Object[]{tileEntity.launchScreen.getFrequency()};
    }

    @Callback(doc = "function(number):boolean -- Set the Frequency of the launcher, only works when the tier supports the frequency")
    public Object[] setFrequency(final Context context, final Arguments args) {
        tileEntity.launchScreen.setFrequency(args.checkInteger(0));
        return null;
    }

    @Callback(doc = "function():number -- Get the LockHeight of the launcher, only works when the tier supports the LockHeight")
    public Object[] getLockHeight(final Context context, final Arguments args) {
        return new Object[]{tileEntity.launchScreen.lockHeight};
    }

    @Callback(doc = "function(number):boolean -- Set the LockHeight of the launcher, only works when the tier supports the LockHeight")
    public Object[] setLockHeight(final Context context, final Arguments args) {
        tileEntity.launchScreen.lockHeight = (short) (args.checkInteger(0));
        return null;
    }
}
