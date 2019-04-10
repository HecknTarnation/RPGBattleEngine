package coolninja.rpg.Cons;

import java.io.Serializable;

/**
 * The basic enemy class
 * 
 * @author Ben Ballard
 * @version 1.0
 * @since 1.0
 */
public class Enemy implements Serializable{
    
    static final long serialVersionUID = 3;
    
    public String name;
    public int health, attack, defense, luck, mAttack, mDefense, expValue;
    public Move[] moves;
    public byte AIID;
    public Weakness weakness;
    public Item drop;
    
    /**
     * Creates a new enemy with the given parameters
     * @param name
     * @param health
     * @param attack
     * @param defense
     * @param luck
     * @param mAttack
     * @param mDefense
     * @param expValue
     * @param moves
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
     * Sets the enemy's AI level (0-2)
     * 0 = random move
     * 1 = uses players weakness to decide best move
     * 2 = uses players health to decide best move
     * if bigger than 2, it will be set to 2
     * @param level
     * @return enemy
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
     * @return enemy
     * @since 1.0
     * @param weakness
     */
    public Enemy setWeakness(Weakness weakness){
        this.weakness = weakness;
        return this;
    }
    
    /**
     * Sets the enemy's item drop
     * @return enemy
     * @since 1.0
     * @param item
     */
    public Enemy setDrop(Item item){
        this.drop = item;
        return this;
    }
    
    /**
     * Run at end of battle
     * (Optional)
     */
    public void onDeath(){
        
    }
    
    /**
     * Clones enemy
     * @return enemy
     * @since 1.0
     */
    @Override
    public Enemy clone(){
        return new Enemy(this.name,
        this.health,
        this.attack,
        this.defense,
        this.luck,
        this.mAttack,
        this.mDefense,
        this.expValue,
        this.moves).setAILevel(this.AIID).setDrop(this.drop.setDropChance(this.drop.chance)).setWeakness(this.weakness);
    }
    
}
