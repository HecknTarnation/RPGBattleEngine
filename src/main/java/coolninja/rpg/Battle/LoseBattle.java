package coolninja.rpg.Battle;

import coolninja.rpg.Cons.Companion;
import coolninja.rpg.Required.Player;

public interface LoseBattle {
    
    public void BattleLost(Player player, Companion[] comps);
    
}
