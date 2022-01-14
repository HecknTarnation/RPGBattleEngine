package com.heckntarnation.rpgbattleengine.lua;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.Vars;
import com.heckntarnation.rpgbattleengine.cons.Characters.Companion;
import com.heckntarnation.rpgbattleengine.cons.Characters.Enemy;
import com.heckntarnation.rpgbattleengine.cons.Characters.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

/**
 *
 * @author Ben
 */
public class character_lookup extends TwoArgFunction {

    public static Object lookup(int characterID) {
        if (characterID == 0) {
            return Vars.player;
        } else if ((characterID - 1) < Vars.companions.length) {
            return Vars.companions[characterID - 1];
        } else {
            return BattleEngine.battleHandler.ens[characterID - 1];
        }
    }

    public static int lookupID(Object character) {
        if (character instanceof Player) {
            return 0;
        } else if (character instanceof Companion) {
            for (int i = 0; i < Vars.companions.length; i++) {
                if ((Companion) character == Vars.companions[i]) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < BattleEngine.battleHandler.ens.length; i++) {
                if ((Enemy) character == BattleEngine.battleHandler.ens[i]) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();
        library.set("lookup", new lookup());
        library.set("lookupID", new lookupID());
        env.set("character_lookup", library);
        return library;
    }

    static class lookup extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue lv) {
            return (LuaValue) lookup(lv.checkint());
        }

    }

    static class lookupID extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue lv) {
            //TODO: this
            return LuaValue.valueOf(lookupID(lv.checkint()));
        }

    }

}
