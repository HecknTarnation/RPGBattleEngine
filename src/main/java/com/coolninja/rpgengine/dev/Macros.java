package com.coolninja.rpgengine.dev;

import com.coolninja.rpgengine.Engine;
import com.coolninja.rpgengine.enums.LangKeys;

/**
 *
 * @author Ben
 */
public class Macros {

    //some macros for me
    public static String localize(String key) {
        return Engine.localizationHandler.getLocalizedString(key);
    }

    public static String localize(LangKeys key) {
        return localize(key.key);
    }

    public static void print(LangKeys key) {
        print(localize(key));
    }

    public static void print(String str) {
        System.out.print(str);
    }

    public static void println(LangKeys key) {
        println(localize(key));
    }

    public static void println(String str) {
        System.out.println(str);
    }
    //end macros

}
