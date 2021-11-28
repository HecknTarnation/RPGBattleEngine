package com.heckntarnation.rpgbattleengine.lua;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.exceptions.ObjectAlreadyLoadedException;
import com.heckntarnation.rpgbattleengine.handlers.JSONHandler.TypedObject;
import java.io.File;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.*;

/**
 *
 * @author Ben
 */
public class json extends TwoArgFunction {

    public json() {
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();
        library.set("load", new load());
        env.set("json", library);
        return library;
    }

    static class load extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue path) {
            try {
                TypedObject obj = BattleEngine.jsonHandler.fromJSON(new File(path.checkstring().toString()));
                return LuaValue.valueOf(obj.namespace + ":" + obj.id);
            } catch (ObjectAlreadyLoadedException ex) {
                return LuaValue.valueOf("loaded");
            }
        }

    }

}
