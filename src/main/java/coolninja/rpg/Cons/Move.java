package coolninja.rpg.Cons;

import java.net.URI;

import coolninja.rpg.Enums.WeaknessType;
import coolninja.rpg.Cons.Graphic;
import coolninja.rpg.Required.Player;

/**
 * The move object
 * 
 * @author Ben Ballard
 * @version 1.0
 * @since 1.0
 */
public class Move implements Serializable{
    
    public String name, text;
    public int damage, mDamage, manaCost;
    public double accuracy;
    public Graphic graphic;
    public URI sound;
    public WeaknessType type;
    
    /**
     * Creates a move
     * 
     * @param name
     * @param text
     * @param damage
     * @param mDamage
     * @param acc
     */
    public Move(String name, String text, int damage, int mDamage, double acc){
        this.name = name;
        this.text = " " + text + " ";
        this.damage = damage;
        this.mDamage = mDamage;
        this.accuracy = acc;
    }
    
    /**
     * Sets the move's graphic
     * @param graphic
     * @since 1.0
     */
    public Move setGraphic(Graphic graphic){
        this.graphic = graphic;
        return this;
    }
    
    /**
     * Sets the move's sound
     * @since 1.0
     * @param location
     */
    public Move setSound(URI location){
        this.sound = location;
        return this;
    }
    
    /**
     * Sets the mana cost of this move (default: 0)
     * @since 1.0
     * @param manaCost
     */
    public Move setManaCost(int manaCost){
        this.manaCost = manaCost;
        return this;
    }
    
    /**
     * Sets move type
     * @since 1.0
     * @param type
     */
    public Move setType(WeaknessType type){
        this.type = type;
        return this;
    }
    
    /**
     * The function called when a move is used (Can be overriden)
     * Player is the character that used the move, enemy is the target, and damage is the total damage the move dealt
     * @since 1.0
     */
    public void Use(int damage, Player player, Enemy en){
        
    }
}
