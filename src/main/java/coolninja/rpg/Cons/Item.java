package coolninja.rpg.Cons;

import coolninja.rpg.Required.Player;

/**
 * The item class 
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class Item implements Serializable{
    
    public String name;
    public String desc;
    public double chance;
    
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
     * The void used when an item is used
     * (Should Be Overwritten!)
     * @since 1.0
     */
    public void Use(Player player, Enemy enemy){
        
    }
    
    public void Use(){
        
    }
    
}
