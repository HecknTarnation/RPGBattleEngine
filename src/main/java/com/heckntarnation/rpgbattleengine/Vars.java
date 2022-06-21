package com.heckntarnation.rpgbattleengine;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.heckntarnation.rpgbattleengine.cons.Characters.Companion;
import com.heckntarnation.rpgbattleengine.cons.Characters.Player;
import java.util.HashMap;

/**
 *
 * @author Ben
 */
public class Vars {

    public static Player player;
    public static Companion[] companions;

    /**
     * The keyboard control mapping.
     */
    public static HashMap<ControlMapping, Integer> Controls = new HashMap<ControlMapping, Integer>() {
        {
            put(ControlMapping.Up, NativeKeyEvent.VC_W);
            put(ControlMapping.Left, NativeKeyEvent.VC_A);
            put(ControlMapping.Down, NativeKeyEvent.VC_S);
            put(ControlMapping.Right, NativeKeyEvent.VC_D);
            put(ControlMapping.Select, NativeKeyEvent.VC_ENTER);
        }
    };
    public static String Selected_Color = Colors.GREEN_BACKGROUND;

    public static String Enemy_Color = Colors.RED_BACKGROUND;
    public static String Ally_Color = Colors.BLUE_BACKGROUND;
    public static short maxLevel = 99;
    /**
     * How many skill points are given to the player to increase their stats on
     * level up. <br>
     * Set to zero to disable this feature.
     */
    public static short skillPointsOnLevel = 5;

    /**
     * If this is true, no audio should play.
     */
    public static boolean disableAudio = false;

    /**
     * Maximum Threads playing at once (if this is reached, and a new one is
     * created, it will destroy the oldest one (the first one in the list).
     */
    public static int maxThreads = 3;

    public static void setPlayer(Player p) {
        player = p;
    }

    public static void SetCompanions(Companion[] comps) {
        companions = comps;
    }

    public enum ControlMapping {
        Up, Left, Down, Right, Select;
    }
}
