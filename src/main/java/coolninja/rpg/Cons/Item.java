package coolninja.rpg.Cons;

import java.io.Serializable;

import coolninja.rpg.Required.Player;

/**
 * The item class 
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class Item implements Serializable{
    
    static final long serialVersionUID = 6;
    
    public String name;
    public String desc;
    /**
     * An item's chance to drop off an enemy
     */
    public double chance;
    /**
     * Whether the item is meant to be used on companions
     */
    public boolean useOnFriends = false;
    
    /**
     * @param name
     * @param desc
     */
    public Item(String name, String desc){
        this.name = name;
        this.desc = desc;
    }
    
    /**
     * Sets the drop chance for an item off an enemy
     * @param chance
     * @since 1.0
     */
    public Item setDropChance(double chance){
        this.chance = chance;
        return this;
    }
    
    /**
     * Used when an item is used
     * (Should Be Overwritten!)
     * @since 1.0
     */
    public void Use(Player player, Enemy enemy){
        
    }
    
    /**
     * Companion version
     * (Should Be Overwritten!)
     * @since 1.0
     */
    public void Use(Player player, Companion comp){
        
    }
    
}
