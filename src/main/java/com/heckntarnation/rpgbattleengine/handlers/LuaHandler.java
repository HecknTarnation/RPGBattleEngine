package com.heckntarnation.rpgbattleengine.handlers;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * This class handlers Lua scripting.
 *
 * @author Ben
 */
public class LuaHandler {

    public Globals globals;

    public void init() {
        globals = JsePlatform.standardGlobals();
    }

    public void runscript(String scriptPath) {
        LuaValue chunk = globals.loadfile(scriptPath);
        chunk.call();
    }

    public void runscript(String scriptPath, LuaValue param, String paramName) {
        globals.set(paramName, param);
        runscript(scriptPath);
        globals.set(paramName, LuaValue.valueOf((String) null));
    }

}
