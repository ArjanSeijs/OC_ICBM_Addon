package com.eternalsoap.icbmopencomputersaddon.drivers;

import icbm.classic.content.machines.radarstation.TileRadarStation;
import icbm.classic.content.missile.EntityMissile;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Field;
import java.util.*;

public class RadarEnvironment extends FrequencyEnvironment<TileRadarStation> {


    public RadarEnvironment(TileRadarStation radarStation) {
        super(radarStation, "component_radar_station");
    }

    @Callback(doc = "function(s:string):boolean -- Method for finding this component when looping through the component list, returns true iff s. == \"component_radar_station\"")
    public Object[] isICBM(final Context context, final Arguments arguments) {
        return new Object[]{arguments.checkString(0).equals("component_radar_station")};
    }

    @Callback(doc = "function():number -- Get the Alarm Range of the Radar")
    public Object[] getAlarmRange(final Context context, final Arguments args) {
        return new Object[]{tileEntity.alarmRange};
    }

    @Callback(doc = "function(number) -- Set the Alarm Range of the radar. Number must be in range of [1," + TileRadarStation.MAX_DETECTION_RANGE + "]")
    public Object[] setAlarmRange(final Context context, final Arguments args) {
        int newRange = args.checkInteger(0);
        if (newRange > TileRadarStation.MAX_DETECTION_RANGE)
            throw new IllegalArgumentException("Number must be in range of [1," + TileRadarStation.MAX_DETECTION_RANGE + "]");
        tileEntity.alarmRange = newRange;
        return null;
    }

    @Callback(doc = "function():number -- Get the Safety Range of the Radar")
    public Object[] getSafetyRange(final Context context, final Arguments args) {
        return new Object[]{tileEntity.safetyRange};
    }

    @Callback(doc = "function(number):boolean -- Set the Safety Range of the Radar. Number must be in range of [1," + TileRadarStation.MAX_DETECTION_RANGE + "]")
    public Object[] setSafetyRange(final Context context, final Arguments args) {
        int newRange = args.checkInteger(0);
        if (newRange > TileRadarStation.MAX_DETECTION_RANGE)
            throw new IllegalArgumentException("Number must be in range of [1," + TileRadarStation.MAX_DETECTION_RANGE + "]");
        tileEntity.safetyRange = newRange;
        return new Object[]{true};
    }

    @Callback(doc = "function():table -- A list of all incoming missiles")
    public Object[] getMissiles(final Context context, final Arguments args) {
        try {
            List<EntityMissile> missiles = getMissiles();
            return new Object[]{mapToTable(missiles)};
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<EntityMissile> getMissiles() throws NoSuchFieldException, IllegalAccessException {
        Field field = TileRadarStation.class.getDeclaredField("incomingMissiles");
        field.setAccessible(true);
        //noinspection unchecked
        return (List<EntityMissile>) field.get(tileEntity);
    }

    private Object[] mapToTable(List<EntityMissile> missiles) {
        Object[] result = new Object[missiles.size()];
        int i = 0;
        for (EntityMissile missile : missiles) {
            BlockPos position = missile.getPosition();
            Map<String, Object> value = new LinkedHashMap<>();
            value.put("x", position.getX());
            value.put("y", position.getY());
            value.put("z", position.getZ());

            //We create a new UUID from the UUID so that we abstract away the entity UUID
            String originalUUID = missile.getUniqueID().toString();
            String newUUID = UUID.nameUUIDFromBytes(originalUUID.getBytes()).toString();
            value.put("UUID", newUUID);

            result[i] = value;
            i++;
        }
        return result;
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
