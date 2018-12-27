package coolninja.rpg.Cons;

/**
 * The item class 
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class Item {
    
    public String name;
    public String desc;
    public Action action;
    public double chance;
    
    /**
     * @param name
     * @param description
     * @param action
     */
    public Item(String name, String desc, Action action){
        this.name = name;
        this.desc = desc;
        this.action = action;
    }
    
    /**
     * Sets the drop chance for an item off an enemy
     * @param the chance of droping
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
    public void Use(){
        
    }
    
}
