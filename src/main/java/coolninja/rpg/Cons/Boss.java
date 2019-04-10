package coolninja.rpg.Cons;

/**
 * The boss object
 * 
 * @author Ben Ballard
 * @version 1.0
 * @since 1.0
 */
public class Boss extends Enemy{
    
    public Boss(String name, int health, int attack, int defense, int luck, int mAttack, int mDefense, int expValue, Move[] moves) {
        super(name, health, attack, defense, luck, mAttack, mDefense, expValue, moves);
    }
    
    /**
     * Run when the boss is killed (after the exp is given)
     * (Should be overridden)
     * @since 1.0
     */
    @Override
    public void onDeath(){
        System.out.println("Oh noes, you killed me D:");
    }

    static final long serialVersionUID = 9;
    
    
}
