package coolninja.rpg;

import coolninja.rpg.Cons.Enemy;
import coolninja.rpg.Cons.Item;
import coolninja.rpg.Required.Player;

/**
 * The built-in math functions
 *
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class MathFunc {

    /**
     * Gets a random number from 0-10 (or clamp) (0 = max of 10)
     *
     * @param clamp
     * @return
     * @since 1.0
     */
    public static int random(int clamp) {
        int temp = (int) Math.round(Math.random() * 10);
        if (clamp == 0) {
            clamp = 10;
        }

        return temp > clamp ? random(clamp) : temp;
    }

    /**
     * Calculates if the move should hit
     *
     * @param acc
     * @return
     * @since 1.0
     */
    public static boolean accHitCalc(double acc) {
        return (acc > random(0) / 10) ? true : acc == 1;
    }

    /**
     * Calculates extra damage using luck
     *
     * @param luck
     * @return
     * @since 1.0
     */
    public static double luckDamageCalc(int luck) {
        return luck * (MathFunc.random(0) / 2.2);
    }

    /**
     * Calculates if an item should drop (chance of 1 will always drop). Luck
     * boost will add to the drop chance in this calculation, meaning it will be
     * easier for the random number to be less than the chance.
     *
     * @param enemy
     * @param drop
     * @param luckBoost
     * @return
     * @since 1.0
     */
    public static boolean shouldDrop(Enemy enemy, Item drop, double luckBoost) {

        if (drop.chance >= 1) {
            return true;
        }

        return (random(0) / 10) < drop.chance + luckBoost;
    }

    public static boolean shouldDrop(Enemy enemy, Item drop) {
        return shouldDrop(enemy, drop, 0);
    }

    /**
     * Sees if the player can run from a battle
     *
     * @return
     * @since 1.0
     * @param pLuck the player's luck
     * @param eLuck the enemy's luck
     */
    public static boolean canRun(int pLuck, int eLuck) {
        int t = pLuck - eLuck;
        if (t <= 0) {
            return false;
        }
        return random(0) < t;
    }

    /**
     * Adds the luck of enemies together
     *
     * @return
     * @since 1.0
     * @param enemies an array of enemies
     */
    public static int addLuck(Enemy[] enemies) {
        int t = 0;
        for (Enemy enemy : enemies) {
            t += enemy.luck;
        }
        return t;
    }

    /**
     * Adds the player and companions luck
     *
     * @return
     * @since 1.0
     * @param plrAndComps an array of the player and companions
     */
    public static int addPlayerLuck(Player[] plrAndComps) {
        int t = 0;
        for (Player plrAndComp : plrAndComps) {
            t += plrAndComp.luck;
        }
        return t;
    }

    /**
     * Calculates how much to increase a stat
     *
     * @return int
     * @since 1.0
     * @param level the character's level
     * @param growth the character's growth rate of the stat to increase
     */
    public static int statInc(int level, double growth) {
        return (int) Math.round((growth * level) / 10 + random(2));
    }
}
