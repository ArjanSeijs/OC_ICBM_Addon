package com.eternalsoap.icbmopencomputersaddon.drivers;

import com.eternalsoap.icbmopencomputersaddon.util.ManagedTileEntityEnvironment;
import icbm.classic.content.blocks.emptower.EMPMode;
import icbm.classic.content.blocks.emptower.TileEMPTower;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;

public class EMPEnvironment extends ManagedTileEntityEnvironment<TileEMPTower> {

    public EMPEnvironment(TileEMPTower tileEntity) {
        super(tileEntity, "component_emp_tower");
    }

    @Callback(doc = "function(s:string):boolean -- Method for finding this component when looping through the component list, returns true iff s == \"component_emp_tower\"")
    public Object[] isICBM(final Context context, final Arguments arguments) {
        return new Object[]{arguments.checkString(0).equals("component_emp_tower")};
    }

    @Callback(doc = "function():number -- Get the Radius of the EMP Tower")
    public Object[] getRadius(final Context context, final Arguments args) {
        return new Object[]{tileEntity.empRadius};
    }

    @Callback(doc = "function(r:number):boolean -- Set the Radius of the EMP Tower.")
    public Object[] setRadius(final Context context, final Arguments args) {
        tileEntity.empRadius = (args.checkInteger(0));
        return null;
    }

    @Callback(doc = "function():boolean -- Returns true if the EMP tower is ready to fire an EMP")
    public Object[] isReady(final Context context, final Arguments args) {
        return new Object[]{tileEntity.isReady()};
    }

    @Callback(doc = "function():boolean -- Activate the EMP Tower, returns true if it successfully fired, false otherwise ")
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

    @Callback(doc = "function(mode:number):number -- Set the mode of the EMP Tower, 0 = Both, 1 = Missile, 2 = Energy")
    public Object[] setMode(final Context context, final Arguments args) {
        switch (args.checkInteger(0)) {
            case 0:
                tileEntity.empMode = EMPMode.ALL;
                break;
            case 1:
                tileEntity.empMode = EMPMode.MISSILES_ONLY;
                break;
            case 2:
                tileEntity.empMode = EMPMode.ELECTRICITY_ONLY;
                break;
            default:
                throw new IllegalArgumentException("Invalid mode, Mode should be 0, 1 or 2");
        }
        return null;
    }
}
