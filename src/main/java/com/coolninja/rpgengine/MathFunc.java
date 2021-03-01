package com.coolninja.rpgengine;

/**
 *
 * @author Ben
 */
public class MathFunc {

    public static double randomD() {
        return (Math.random() * 10);
    }

    public static int randomInt() {
        return (int) Math.round(randomD());
    }

}
