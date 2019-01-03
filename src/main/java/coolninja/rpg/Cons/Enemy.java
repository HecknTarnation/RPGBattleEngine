package coolninja.rpg.Cons;

/**
 * The basic enemy class
 * 
 * @author Ben Ballard
 * @version 1.0
 * @since 1.0
 */
public class Enemy {
    
    public String name;
    public int health, attack, defense, luck, mAttack, mDefense, expValue;
    public Move[] moves;
    public byte AIID;
    public Weakness weakness;
    public Item drop;
    
    /**
     * Creates a new enemy with the given parameters
     * @param String name
     * @param int health
     * @param int attack
     * @param int defense
     * @param int luck
     * @param int mAttack
     * @param int mDefense
     * @param int the exp value
     * @param Move[] moves
     */
    public Enemy(String name, int health, int attack, int defense, int luck, int mAttack, int mDefense, int expValue, Move[] moves){
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.luck = luck;
        this.mAttack = mAttack;
        this.mDefense = mDefense;
        this.expValue = expValue;
        this.moves = moves;
    }
    
    /**
     * Sets the enemy's AI level (0-3)
     * 0 = random move
     * 1 = uses players weakness to decide best move
     * 2 = uses players health to decide best move
     * if bigger than 2, it will be set to 2
     * @param byte level
     * @since 1.0
     */
    public Enemy setAILevel(byte level){
        this.AIID = level;
        if(level > 2){
            this.AIID = 2;
        }
        return this;
    }
    
    /**
     * Sets the enemy's weakness
     * @since 1.0
     * @param Weakness weakness
     */
    public Enemy setWeakness(Weakness weakness){
        this.weakness = weakness;
        return this;
    }
    
    /**
     * Sets the enemy's item drop
     * @since 1.0
     */
    public Enemy setDrop(Item item){
        this.drop = item;
        return this;
    }
    
    /**
     * Converts Enemy to Boss
     * @since 1.0
     */
    public Boss EnemyToBoss(){
        return (Boss)this;
    }
    
}
