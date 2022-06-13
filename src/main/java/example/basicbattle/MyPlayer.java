package example.basicbattle;

import com.heckntarnation.rpgbattleengine.cons.Characters.Player;

/**
 *
 * @author Ben
 */
public class MyPlayer extends Player {

    /*
    This a custom player object. You don't have to make a custom one, you can just use the default 'player' object.
    Using a custom object does have advantages, which will be covered in other examples. For now, let's just explain what you need.
    The 'name' variable is just that, the player's name. If it is set equal to LocalizationHandler.SECOND_PERSON_STRING it will be second person (ie: You and Your).
    baseExp is the base amount of experience points to get to the next level, which will be scaled each level using the expMod variable. You can see the function
    that does this in MathFunc.expToNextLevel.
     */
    public MyPlayer(String name, int baseExp, int expmod) {
        super(name, baseExp, expmod);
    }

}
