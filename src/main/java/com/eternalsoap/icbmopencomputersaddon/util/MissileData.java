package com.eternalsoap.icbmopencomputersaddon.util;

public class MissileData {

    public static String damageToString(int itemDamage) {
        switch (itemDamage) {
            case 0:
                return "Conventional_Missile";
            case 1:
                return "Shrapnel_Missile";
            case 2:
                return "Incendiary_Missile";
            case 3:
                return "Debilitation_Missile";
            case 4:
                return "Chemical_Missile";
            case 5:
                return "Anvil_Missile";
            case 6:
                return "Repulsive_Missile";
            case 7:
                return "Attractive_Missile";
            case 8:
                return "Fragmentation_Missile";
            case 9:
                return "Contagious_Missile";
            case 10:
                return "Sonic_Missile";
            case 11:
                return "Breaching_Missile";
            case 13:
                return "Thermobaric_Missile";
            case 15:
                return "Nuclear_Missile";
            case 16:
                return "EMP_Missile";
            case 17:
                return "Exothermic_Missile";
            case 18:
                return "Endothermic_Missile";
            case 19:
                return "Anti-Gravitational_Missile";
            case 20:
                return "Ender_Missile";
            case 21:
                return "Hypersonic_Missile";
            case 22:
                return "Antimatter_Missile";
            case 23:
                return "Red_Matter_Missile";
            case 26:
                return "Anti_Ballistic_Missile";
            default:
                return null;
        }
    }
}
