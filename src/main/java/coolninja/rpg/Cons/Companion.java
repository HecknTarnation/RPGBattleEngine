package coolninja.rpg.Cons;

import java.io.Serializable;
import java.util.ArrayList;
import coolninja.rpg.Console.Colors;
import coolninja.rpg.Required.Player;

/**
 * The companion class
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class Companion extends Player implements Serializable{
    
    static final long serialVersionUID = 2;
    
    /**
     *  The companion's moves
     */
    public ArrayList<Move> moves;
    /**
     * 0 = feet
     * 1 = legs
     * 2 = arms
     * 3 = chest
     * 4 = head
     * 5 = weapon
     * 6 = mod
     */
    public Equipment[] equipment;
    
     public Companion(String name, int health, int mana, int maxMana, int attack, int defense, 
        int luck, int mAttack, int mDefense, int specialAttack){
            super(name, health, mana, maxMana, attack, defense, luck, mAttack, mDefense, specialAttack);
            this.equipment = new Equipment[7];
            this.moves = new ArrayList<>();
            this.expToNextLevel = 10;
            this.exp = 0;
            this.level = 1;
    }
    
    /**
     * Sets the companions moves
     * @return 
     * @since 1.0
     * @param moves
     */
    public final Companion setMoves(ArrayList<Move> moves){
        this.moves = moves;
        return this;
    }
    
    /**
     * Adds a move to the companion
     * @return 
     * @since 1.0
     * @param move
     */
    @Override
    public final Companion addMove(Move move){
        this.moves.add(move);
        return this;
    }
    
    /**
     * Adds a array move to the companion
     * @param ms
     * @return 
     * @since 1.0
     */
    @Override
    public Companion addMoves(Move[] ms){
        for (Move m : ms) {
            moves.add(m);
        }
        return this;
    }
    
    /**
     * Increases the exp
     * @since 1.0
     * @param exp
     */
    @Override
    public void increaseEXP(int exp){
        this.exp += exp;
    }
    
    /**
     * Prints the companion's moves
     * @since 1.0
     */
    @Override
    public final void printMoves(){
        for(int i = 0; i < moves.size(); i++){
            Move m = moves.get(i);
            if(m.manaCost != 0){
                System.out.print(Colors.BLACK);
                System.out.println("  -" + Colors.WHITE_BACKGROUND + m.name + " | Mana: " + m.manaCost + Colors.reset());
            }else{
                System.out.print(Colors.BLACK);
                System.out.println("  -" + Colors.WHITE_BACKGROUND + m.name + Colors.reset());
            }
        }
    }
    
    /**
     * Returns the companion's moves
     * @return 
     * @since 1.0
     */
    @Override
    public ArrayList<Move> getMoves(){
        return this.moves;
    }
    
    /**
     * Heals the companion to full health and mana
     * @since 1.0
     */
    @Override
    public void heal(){
        this.health = maxHealth;
        this.mana = maxMana;
    }
}
