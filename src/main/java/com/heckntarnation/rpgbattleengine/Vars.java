package com.heckntarnation.rpgbattleengine;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.heckntarnation.rpgbattleengine.Cons.*;
import java.util.HashMap;

/**
 *
 * @author Ben
 */
public class Vars {

    public static Player player;
    public static Companion[] companions;

    /**
     * The keyboard is set up like this: <br>
     * Up, Left, Down, Right, Select,
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
    public static int maxLevel = 99;

    public static boolean mute = false;

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
