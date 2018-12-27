package coolninja.rpg.Cons;

import java.net.URL;

import coolninja.rpg.WeaknessType;
import coolninja.rpg.Cons.Graphic;
import coolninja.rpg.Required.Player;

/**
 * The move object
 * 
 * @author Ben Ballard
 * @version 1.0
 * @since 1.0
 */
public class Move {
    
    public String name, text;
    public int damage, mDamage, manaCost;
    public double accuracy;
    public Graphic graphic;
    public URL sound;
    public WeaknessType type;
    
    /**
     * Creates a move
     * 
     * @param name
     * @param text
     * @param damage
     * @param mDamage
     * @param accuracy
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
     * @param location of wav file
     */
    public Move setSound(URL location){
        this.sound = location;
        return this;
    }
    
    /**
     * Sets the mana cost of this move (default: 0)
     * @since 1.0
     * @param cost
     */
    public Move setManaCost(int manaCost){
        this.manaCost = manaCost;
        return this;
    }
    
    /**
     * Sets move type
     * @since 1.0
     * @param WeaknessType type
     */
    public Move setType(WeaknessType type){
        this.type = type;
        return this;
    }
    
    /**
     * The function called when a move is used (Can be overriden)
     * @since 1.0
     */
    public void Use(int damage, Player player, Enemy en){
        
    }
}
