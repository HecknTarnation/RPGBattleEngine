package com.heckntarnation.rpgbattleengine.lua;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.exceptions.IncorrectObjectRecieved;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

/**
 *
 * @author Ben
 */
public class battle_handler extends TwoArgFunction {

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();
        library.set("start_battle", new start_battle());
        env.set("battle_handler", library);
        return library;
    }

    static class start_battle extends TwoArgFunction {

        @Override
        public LuaValue call(LuaValue canRun, LuaValue enemies) {
            try {
                BattleEngine.battleHandler.startBattle(canRun.checkboolean(), enemies.checktable());
            } catch (IncorrectObjectRecieved ex) {
                Logger.getLogger(battle_handler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

}
