package com.coolninja.rpgengine;

import com.coolninja.rpgengine.Battle.BattleHandler;
import com.coolninja.rpgengine.Cons.Enemy;

/**
 * The main file for the engine
 *
 * @version 1.0
 * @since 1.0
 * @author Ben
 */
public class Engine {

    public static BattleHandler battleHandler;
    public static InputHandler inputHandler;

    public static void init() {

    }

    /**
     * Used to change handlers to custom ones (leave any null to use default
     * ones)
     *
     * @param bHandler
     * @param iHandler
     */
    public static void init(BattleHandler bHandler, InputHandler iHandler) {
        battleHandler = bHandler == null ? new BattleHandler() : bHandler;
        inputHandler = iHandler == null ? new InputHandler() : iHandler;

        init();
    }

    public static void startBattle(Enemy[] en) {
        battleHandler.startBattle(en);
    }
}
