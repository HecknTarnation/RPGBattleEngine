package coolninja.rpg.Cons;

import java.io.Serializable;

/**
 * The item class
 *
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class Item implements Serializable {

    static final long serialVersionUID = 6;

    public String name;
    public String desc;
    /**
     * An item's chance to drop off an enemy
     */
    public double chance;

    /**
     * @param name
     * @param desc
     */
    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * Sets the drop chance for an item off an enemy
     *
     * @param chance
     * @return
     * @since 1.0
     */
    public Item setDropChance(double chance) {
        this.chance = chance;
        return this;
    }

    /**
     * Used when an item is used (Should Be Overwritten!)
     *
     * @since 1.0
     */
    public void Use() {
        
    }

}
