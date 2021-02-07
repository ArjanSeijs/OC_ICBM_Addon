package com.eternalsoap.icbmopencomputersaddon.drivers;

import icbm.classic.api.caps.IMissile;
import icbm.classic.content.blocks.radarstation.TileRadarStation;
import icbm.classic.content.entity.missile.EntityMissile;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings("unused")
public class RadarEnvironment extends FrequencyEnvironment<TileRadarStation> implements NamedBlock {


    public static final String COMPONENT_NAME = "icbm_radar_station";

    public RadarEnvironment(TileRadarStation radarStation) {
        super(radarStation, COMPONENT_NAME);
    }

    @Callback(doc = "function(s:string):boolean -- Method for finding this component when looping through the component list, returns true iff s == \"" + COMPONENT_NAME + "\"")
    public Object[] isICBM(final Context context, final Arguments arguments) {
        return new Object[]{arguments.checkString(0).equals(COMPONENT_NAME)};
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
    public Object[] getIncomingMissiles(final Context context, final Arguments args) {
        return getMissiles(context, args);
    }

    @Callback(doc = "function():table -- A list of all incoming missiles")
    public Object[] getMissiles(final Context context, final Arguments args) {
        try {
            List<IMissile> missiles = getMissiles();
            return new Object[]{mapToTable(missiles)};
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Callback(doc = "function():table -- A list of all incoming missiles")
    public Object[] getMissilesAlt(final Context context, final Arguments args) {
        final BlockPos pos = tileEntity.getPos();
        final int x = pos.getX();
        final int y = pos.getY();
        final int z = pos.getZ();

        final int alarmRange = tileEntity.alarmRange;
        final Object[] objects = tileEntity.getWorld().getEntitiesWithinAABB(EntityMissile.class, new AxisAlignedBB(x - alarmRange, y - 10, z - alarmRange, x + alarmRange, y + 512, z + alarmRange)).stream().map(missile -> {
            BlockPos position = missile.getPos();
            Map<String, Object> value = new LinkedHashMap<>();
            value.put("x", position.getX());
            value.put("y", position.getY());
            value.put("z", position.getZ());

            //We create a new UUID from the UUID so that we abstract away the entity UUID
            String originalUUID = missile.getUniqueID().toString();
            String newUUID = UUID.nameUUIDFromBytes(originalUUID.getBytes()).toString();
            value.put("UUID", newUUID);
            return value;
        }).toArray();
        return new Object[]{objects};
    }

    private List<IMissile> getMissiles() throws NoSuchFieldException, IllegalAccessException {
        Field field = TileRadarStation.class.getDeclaredField("incomingMissiles");
        field.setAccessible(true);
        //noinspection unchecked
        return (List<IMissile>) field.get(tileEntity);
    }

    private Object[] mapToTable(List<IMissile> missiles) {
        Object[] result = new Object[missiles.size()];
        int i = 0;
        for (IMissile missile : missiles) {
            BlockPos position = missile.getPos();
            Map<String, Object> value = new LinkedHashMap<>();
            value.put("x", position.getX());
            value.put("y", position.getY());
            value.put("z", position.getZ());

            //We create a new UUID from the UUID so that we abstract away the entity UUID
            String originalUUID = missile.getMissileEntity().getUniqueID().toString();
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

    @Override
    public String preferredName() {
        return COMPONENT_NAME;
    }

    @Override
    public int priority() {
        return 1;
    }
}
