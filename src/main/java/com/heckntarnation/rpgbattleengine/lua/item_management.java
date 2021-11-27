package com.heckntarnation.rpgbattleengine.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.*;

/**
 *
 * @author Ben
 */
public class item_management extends TwoArgFunction {

    public item_management() {
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();
        library.set("delete", new delete());
        env.set("item_management", library);
        return library;
    }

    static class delete extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue lv) {
            return null;
        }

    }
}
