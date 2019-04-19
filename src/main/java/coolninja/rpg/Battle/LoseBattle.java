package coolninja.rpg.Battle;

import coolninja.rpg.Cons.Companion;
import coolninja.rpg.Required.Player;

/**
 * Class for handing losing a battle
 * @since 1.0
 * @version 1.0
 * @author Ben Ballard
 */
public class LoseBattle {
    
    /**
     * Run when player loses battle
     * @param player The player
     * @param comps The player's companions
     * @since 1.0
     */
    public void BattleLost(Player player, Companion[] comps){
        //Default Text
        System.out.println("You Lost!");
    }
    
}
