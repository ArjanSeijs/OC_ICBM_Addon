package com.eternalsoap.icbmopencomputersaddon.drivers;

import com.eternalsoap.icbmopencomputersaddon.util.ManagedTileEntityEnvironment;
import icbm.classic.content.blocks.launcher.base.TileLauncherBase;
import icbm.classic.lib.transform.vector.Pos;
import icbm.classic.api.EnumTier;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * The launcher environment for {@link TileLauncherBase}
 */
public class LauncherEnvironment extends ManagedTileEntityEnvironment<TileLauncherBase> {

    public LauncherEnvironment(TileLauncherBase launcherBase) {
        super(launcherBase, "component_missile_launcher");
    }

    @Callback(doc = "function(s:string):boolean -- Method for finding this component when looping through the component list, returns true iff s == \"component_missile_launcher\"")
    public Object[] isICBM(final Context context, final Arguments arguments) {
        return new Object[]{arguments.checkString(0).equals("component_missile_launcher")};
    }

    @Callback(doc = "function():number -- Get the X & Z coordinate of the target position")
    public Object[] getTargetPos(final Context context, final Arguments args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("x", tileEntity.launchScreen.getTarget().xi());
        map.put("z", tileEntity.launchScreen.getTarget().zi());
        return new Object[]{map};
    }

    @Callback(doc = "function(x: number, z: number) -- Set the X & Z coordinate of the target position")
    public Object[] setTargetPos(final Context context, final Arguments args) {
        tileEntity.launchScreen.setTarget(new Pos(args.checkInteger(0), tileEntity.launchScreen.getTarget().yi(), args.checkInteger(1)));
        return null;
    }

    @Callback(doc = "function(y: number) -- Sets the detonation height for tier 2 & tier 3 launchers, will not work on tier 1 launchers")
    public Object[] setDetonationHeight(final Context context, final Arguments args) {
        if (this.tileEntity.getTier() == EnumTier.ONE) {
            throw new IllegalArgumentException("Setting the Detonation-Height is not supported on launchers of tier 1");
        }
        tileEntity.launchScreen.setTarget(new Pos(tileEntity.launchScreen.getTarget().xi(), args.checkInteger(0), tileEntity.launchScreen.getTarget().zi()));
        return null;
    }

    @Callback(doc = "function(): number -- Gets the detonation height for tier 2 & tier 3 launchers, will not work on tier 1 launchers")
    public Object[] getDetonationHeight(final Context context, final Arguments args) {
        if (this.tileEntity.getTier() == EnumTier.ONE) {
            throw new IllegalArgumentException("Getting the Detonation-Height is not supported on launchers of tier 1");
        }
        return new Object[]{tileEntity.launchScreen.getTarget().yi()};
    }

    /**
     * Alias for {@link #launchMissile(Context, Arguments)}
     */
    @Callback(doc = "function(x: number,z: number):boolean -- Launch missile at stored position or by providing the x and z coordinates")
    public Object[] launch(final Context context, final Arguments args) {
        return launchMissile(context, args);
    }

    @Callback(doc = "function(x: number,z: number):boolean -- Launch missile towards the x and z coordinates, if no coordinates are specified use the coordinates set by setTargetPos")
    public Object[] launchMissile(final Context context, final Arguments args) {
        Pos target = tileEntity.launchScreen.getTarget();
        if (args.isInteger(0)) {
            target = new Pos(args.checkInteger(0), args.checkInteger(1));
        }
        int lockHeight = tileEntity.launchScreen.lockHeight;
        boolean launchMissile = tileEntity.launchMissile(target, lockHeight);
        return new Object[]{launchMissile};
    }

    @Callback(doc = "function():boolean -- Checks whether a missile is loaded inside the launcher")
    public Object[] containsMissile(final Context context, final Arguments args) {
        return new Object[]{!tileEntity.getMissileStack().isEmpty()};
    }

    @Callback(doc = "function():number -- Get the maximum of the missile launcher")
    public Object[] getRange(final Context context, final Arguments args) {
        return new Object[]{tileEntity.getRange()};
    }

    @Callback(doc = "function(x: number,z: number):boolean -- Get whether the given position is within range.")
    public Object[] isInRange(final Context context, final Arguments args) {
        Pos target = new Pos(args.checkInteger(0), args.checkInteger(1));
        return new Object[]{tileEntity.isInRange(target)};
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
        if (this.tileEntity.getTier() == EnumTier.ONE || this.tileEntity.getTier() == EnumTier.TWO) {
            throw new IllegalArgumentException("Getting the frequency is not supported on launchers of tier 1 and tier 2");
        }
        return new Object[]{tileEntity.launchScreen.getFrequency()};
    }

    @Callback(doc = "function(number):boolean -- Set the Frequency of the launcher, only works when the tier supports the frequency")
    public Object[] setFrequency(final Context context, final Arguments args) {
        if (this.tileEntity.getTier() == EnumTier.ONE || this.tileEntity.getTier() == EnumTier.TWO) {
            throw new IllegalArgumentException("Setting the frequency is not supported on launchers of tier 1 and tier 2");
        }
        tileEntity.launchScreen.setFrequency(args.checkInteger(0));
        return null;
    }

    @Callback(doc = "function():number -- Get the LockHeight of the launcher, only works when the tier supports the LockHeight")
    public Object[] getLockHeight(final Context context, final Arguments args) {
        if (this.tileEntity.getTier() == EnumTier.ONE) {
            throw new IllegalArgumentException("Getting the Lock-Height is not supported on launchers of tier 1");
        }
        return new Object[]{tileEntity.launchScreen.lockHeight};
    }

    @Callback(doc = "function(number):boolean -- Set the LockHeight of the launcher, only works when the tier supports the LockHeight")
    public Object[] setLockHeight(final Context context, final Arguments args) {
        if (this.tileEntity.getTier() == EnumTier.ONE) {
            throw new IllegalArgumentException("Setting the Lock-Height is not supported on launchers of tier 1");
        }
        tileEntity.launchScreen.lockHeight = (short) (args.checkInteger(0));
        return null;
    }

    @Callback(doc = "function():number -- Get the tier of the launcher, [1,2,3]")
    public Object[] getTier(final Context context, final Arguments args) {
        switch (this.tileEntity.getTier()) {
            case ONE:
                return new Object[]{1};
            case TWO:
                return new Object[]{2};
            case THREE:
                return new Object[]{3};
            default:
                return null;
        }

    }
}
