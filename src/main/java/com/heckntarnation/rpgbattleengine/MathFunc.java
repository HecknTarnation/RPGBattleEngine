package com.heckntarnation.rpgbattleengine;

import com.heckntarnation.rpgbattleengine.Cons.Enemy;
import com.heckntarnation.rpgbattleengine.Cons.Move;
import com.heckntarnation.rpgbattleengine.enums.StatusArrayPosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Ben
 */
public class MathFunc {

    private static final Random rand = new Random();

    /**
     * Returns a number between min and max, as a double. <br>
     * This function is not guaranteed to be random.
     *
     * @param min
     * @param max
     * @return
     */
    public static double randomD(int min, int max) {
        return min + (max - min) * rand.nextDouble();
    }

    /**
     * Returns a number between min and max. <br>
     * This function is not guaranteed to be random.
     *
     * @param min
     * @param max
     * @return
     */
    public static int randomInt(int min, int max) {
        return rand.nextInt(max + 1 - min) + min;
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
     * @param targetEvasion
     * @return
     */
    public static boolean checkHit(Move selectedMove, float targetEvasion) {
        boolean hit;
        if (selectedMove.accuracy == 1.0f) {
            return true;
        }
        hit = selectedMove.accuracy > (randomD(0, 1));
        if (hit && targetEvasion > randomD(0, 1)) {
            hit = false;
        }
        return hit;
    }

    /**
     * Calculates the experience required for the next level.
     *
     * @param level
     * @param baseExp
     * @param exponent
     * @return
     */
    public static int expToNextLevel(int level, int baseExp, float exponent) {
        int i;
        i = (int) Math.round(baseExp * ((Math.pow(level, exponent))));
        return i;
    }

    public static int statIncrease(int oldStat, int levelNeeded, double growthRate) {
        int newStat = oldStat;
        for (int i = 0; i < levelNeeded; i++) {
            newStat += randomInt(1, 2) * growthRate;
        }
        return newStat;
    }

    public static float addStatFromArray(Enemy[] ens, StatusArrayPosition pos) {
        float stat = 0;
        switch (pos) {
            case ATK: {
                for (Enemy en : ens) {
                    stat += en.attack;
                }
            }
            case DEF: {
                for (Enemy en : ens) {
                    stat += en.defense;
                }
            }
            case Luck: {
                for (Enemy en : ens) {
                    stat += en.luck;
                }
                break;
            }
            case MATK: {
                for (Enemy en : ens) {
                    stat += en.mAttack;
                }
            }
            case MDEF: {
                for (Enemy en : ens) {
                    stat += en.mDefense;
                }
            }
            case Evasion: {
                for (Enemy en : ens) {
                    stat += en.evasion;
                }
            }
            case Health: {
                for (Enemy en : ens) {
                    stat += en.health;
                }
            }
        }
        return stat;
    }

}
