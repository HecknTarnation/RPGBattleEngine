package com.coolninja.rpgengine;

import com.coolninja.rpgengine.Cons.Move;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Ben
 */
public class MathFunc {
    
    public static double randomD() {
        return (Math.random() * 10);
    }
    
    public static double randomD(double clamp) {
        double i = randomInt();
        if (i > clamp) {
            i = randomD(clamp);
        }
        return i;
    }
    
    public static int randomInt() {
        return (int) Math.round(randomD());
    }
    
    public static int randomInt(int clamp) {
        int i = randomInt();
        if (i > clamp) {
            i = randomInt(clamp);
        }
        return i;
    }

    /**
     *
     * @param amountOfEach
     * @param elements
     * @return
     */
    public static Object hatpull(int[] amountOfEach, Object[] elements) {
        ArrayList<Object> t = new ArrayList<>();
        for (int i = 0; i < elements.length; i++) {
            for (int x = 0; x < amountOfEach[i] - 1; x++) {
                t.add(elements[i]);
            }
        }
        Collections.shuffle(t);
        int index = randomInt(t.size() - 1);
        return t.get(index);
    }

    /**
     * Checks to see if a move should hit. An accuracy of 1.0f will always hit.
     *
     * @param selectedMove
     * @return
     */
    public static boolean checkHit(Move selectedMove) {
        if (selectedMove.accuracy == 1.0f) {
            return true;
        }
        return selectedMove.accuracy > (randomD() / 10);
    }
    
}
