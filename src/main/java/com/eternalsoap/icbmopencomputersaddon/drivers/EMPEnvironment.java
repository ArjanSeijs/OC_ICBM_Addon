package com.eternalsoap.icbmopencomputersaddon.drivers;

import com.eternalsoap.icbmopencomputersaddon.util.ManagedTileEntityEnvironment;
import icbm.classic.content.machines.emptower.TileEMPTower;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;

public class EMPEnvironment extends ManagedTileEntityEnvironment<TileEMPTower> {

    public EMPEnvironment(TileEMPTower tileEntity) {
        super(tileEntity, "component_emp_tower");
    }

    @Callback(doc = "function():number -- Get the Radius of the launcher, only works when the tier supports the Radius")
    public Object[] getRadius(final Context context, final Arguments args) {
        return new Object[]{tileEntity.empRadius};
    }

    @Callback(doc = "function(r:number):boolean -- Set the Radius of the launcher, only works when the tier supports the Radius")
    public Object[] setRadius(final Context context, final Arguments args) {
        tileEntity.empRadius = (args.checkInteger(0));
        return null;
    }

    @Callback(doc = "function():boolean -- Activate the EMP Tower")
    public Object[] launch(final Context context, final Arguments args) {
        if (tileEntity.isReady()) {
            tileEntity.fire();
            return new Object[]{true};
        }
        return new Object[]{false};
    }

    @Callback(doc = "function():number -- Gets the mode of the EMP Tower, 0 = Both, 1 = Missile, 2 =  Energy")
    public Object[] getMode(final Context context, final Arguments args) {
        return new Object[]{tileEntity.empMode};
    }

    @Callback(doc = "function(mode:number):number -- Activate the EMP Tower")
    public Object[] setMode(final Context context, final Arguments args) {
        if (args.isInteger(0)) {
            switch (args.checkInteger(0)) {
                case 0:
                    tileEntity.empMode = (byte) 0;
                    break;
                case 1:
                    tileEntity.empMode = (byte) 1;
                    break;
                case 2:
                    tileEntity.empMode = (byte) 2;
                    break;
                default:
            }
        }
        return null;
    }
}
