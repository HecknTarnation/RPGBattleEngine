package com.coolninja.rpgengine;

import com.coolninja.rpgengine.Cons.Move;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Ben
 */
public class MathFunc {

    /**
     * Returns a number between 0 and 100, as a double (which means it can have
     * a random decimal value). <br>
     * This function is not guaranteed to be random.
     *
     * @param min
     * @param max
     * @return
     */
    public static double randomD(int min, int max) {
        return (Math.random() * ((max - min) + 1)) + min;
    }

    public static int randomInt(int min, int max) {
        return (int) Math.round(randomD(min, max));
    }

    /**
     * Runs a virtual hat pull. Format: <br>
     * amountOgEach = {p1Amount, p2Amount, ...} <br>
     * elements = {p1, p2, ...} <br>
     * Note: This functions runs System.gc() after it's done.
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
        int index = randomInt(0, t.size() - 1);
        Object result = t.get(index);
        t.clear();
        t = null;
        System.gc();
        return result;
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
        return selectedMove.accuracy > (randomD(0, 1));
    }

    /**
     * Calculates the experience required for the next level.
     *
     * @param level
     * @param baseExp
     * @param exponent
     * @return
     */
    //TODO: change temp formula
    public static int expToNextLevel(int level, int baseExp, int exponent) {
        int i = 0;
        i = (int) Math.floor(baseExp * (level ^ exponent));
        return i;
    }

    //TODO: change temp formula
    public static int statIncrease(int oldStat, int levelNeeded, double growthRate) {
        int newStat = oldStat;
        for (int i = 0; i < levelNeeded; i++) {
            newStat = newStat + (int) (1 * growthRate);
        }
        return newStat;
    }

}
