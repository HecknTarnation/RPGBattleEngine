package com.heckntarnation.rpgbattleengine.lua;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.exceptions.StringAlreadyDefinedException;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.*;

/**
 *
 * @author Ben
 */
public class localization extends TwoArgFunction {

    public localization() {
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();
        library.set("get_string", new get_string());
        env.set("localization", library);
        return library;
    }

    static class get_string extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue lv) {
            return LuaValue.valueOf(BattleEngine.localizationHandler.getLocalizedString(lv.checkstring().toString()));
        }

    }

    static class add_string extends TwoArgFunction {

        @Override
        public LuaValue call(LuaValue key, LuaValue value) {
            try {
                BattleEngine.localizationHandler.addString(key.checkstring().toString(), value.checkstring().toString());
            } catch (StringAlreadyDefinedException ex) {
                return LuaValue.valueOf(false);
            }
            return LuaValue.valueOf(true);
        }

    }

}
