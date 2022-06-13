package example.basicbattle;

import com.heckntarnation.rpgbattleengine.cons.Characters.Enemy;

/**
 *
 * @author Ben
 */
public class MyEnemy extends Enemy {

    /*
    As with the MyPlayer object, you don't need to make a custom one.
    
    The name is once again the name of the enemy.
    The health is the base health of the enemy.
    expVal is how much experience points the enemy will give to the player, and any companions.
     */
    public MyEnemy(String name, int health, int expVal) {
        super(name, health, expVal);
    }

}
