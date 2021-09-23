package com.heckntarnation.rpgbattleengine;

import com.heckntarnation.rpgbattleengine.Cons.*;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 *
 * @author Ben
 */
public class Vars {

    public static Player player;
    public static Companion[] companions;

    /**
     * The key board is set up like this: <br>
     * Up, Left, Down, Right, Select,
     */
    public static int[] Controls = {NativeKeyEvent.VC_W, NativeKeyEvent.VC_A, NativeKeyEvent.VC_S, NativeKeyEvent.VC_D, NativeKeyEvent.VC_ENTER};
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

}
