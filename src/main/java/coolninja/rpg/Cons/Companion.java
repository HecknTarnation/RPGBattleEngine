package coolninja.rpg.Cons;

import java.util.ArrayList;

import coolninja.rpg.InputHandler;
import coolninja.rpg.MathFunc;
import coolninja.rpg.Vars;
import coolninja.rpg.Console.Console;
import coolninja.rpg.Required.Player;

/**
 * The companion class
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class Companion extends Player{
    
    //public String name;
    //public int level = 1, health, maxHealth, mana, maxMana, attack, defense, luck, mAttack, mDefense, exp, expToNextLevel;
    public ArrayList<Move> moves = new ArrayList<Move>();
    /**
     * 0 = feet
     * 1 = legs
     * 2 = arms
     * 3 = chest
     * 4 = head
     * 5 = weapon
     * 6 = mod
     */
    public Equipment[] equipment = new Equipment[5];
    
    private int skillPoints = 0;
    
     public Companion(String name, int health, int mana, int maxMana, int attack, int defense, 
        int luck, int mAttack, int mDefense, int specialAttack){
        super(name, health, mana, maxMana, attack, defense, luck, mAttack, mDefense, specialAttack);
        /*this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.mana = mana;
        this.maxMana = maxMana;
        this.attack = attack;
        this.defense = defense;
        this.luck = luck;
        this.mAttack = mAttack;
        this.mDefense = mDefense;
        this.specialAttack = specialAttack;*/
        this.expToNextLevel = 10;
        this.exp = 0;
        this.level = 1;
    }
    
    /**
     * Sets the companions moves
     * @since 1.0
     * @param ArrayList<Move> the moves
     */
    public final Companion setMoves(ArrayList<Move> moves){
        this.moves = moves;
        return this;
    }
    
    /**
     * Adds a move to the companion
     * @since 1.0
     * @param Move move to add
     */
    public final Companion addMove(Move move){
        this.moves.add(move);
        return this;
    }
    
    /**
     * Increases the exp
     * @since 1.0
     * @param int exp
     */
    public void increaseEXP(int exp){
        this.exp += exp;
    }
    
    /**
     * Used to level up the companion when the required exp is obtained
     * (Can be overwritten)
     * @since 1.0
     */
    public void levelUp(){
        
        if(exp >= expToNextLevel){
            
            exp -= expToNextLevel;
            
            System.out.println(name + " has leveled up!");
            
            Console.waitFull(2);
            
            Console.clear();
            
            System.out.println("Level " + level);
            
            stat = null;
        
            stat = new int[]{maxHealth, maxMana, attack, defense, luck, mAttack, mDefense, specialAttack};
        
            LevelUpHelper();
            
            levelUp();
            
            heal();
        }
    }
    
    private void LevelUpHelper(){
        level += 1;
        Console.clear();
        
        System.out.println("Level " + level + "!");
        Console.waitHalf(5);
        
        for(int i = 0; i < stat.length; i++){
            String name = names[i];
            int temp = (int) Math.round((((MathFunc.random(0)/2)))+1+(luck/4));
            stat[i] = temp;
            System.out.println(name + " increased by " + temp);
            if(Vars.shouldScroll){
                for(int x = 0; x < 5; x++){
                    System.out.println("\n");
                    Console.waitReal(50);
                }
            }
            Console.waitFull(1);
        }
        
        maxHealth += stat[0];
        maxMana += stat[1];
        attack += stat[2];
        defense += stat[3];
        luck += stat[4];
        mAttack += stat[5];
        mDefense += stat[6];
        specialAttack += stat[7];
        expToNextLevel += MathFunc.random(0)+3*level;
        health = maxHealth;
        mana = maxMana;
        skillPoints = MathFunc.random(3)+2*level;
        
        while(skillPoints != 0){
            pickStat(skillPoints);
        }
        
        //addMove(level);
        
        Console.waitHalf(1);
        
        InputHandler.pressEnter();
        
        Console.clear();
    }
    
    private void pickStat(int skillPoints){
        stat = new int[]{maxHealth, maxMana, attack, defense, luck, mAttack, mDefense, specialAttack};

        for(int i = 0; i < stat.length; i++){
            System.out.println("(" + i + ")" + names[i] + ": " + stat[i]);
            Console.waitReal(300);
        }
        
        System.out.println("You have " + skillPoints + " skill points.\n Which one? (Use number)");
        
        int s;
        
        try{
            s = Integer.parseInt(InputHandler.getInput());
        }catch(Exception e){
            System.out.println("That's Not A Number!");   
            Console.clear();
            return;
        }
        
        usePoint(s);
        
        Console.clear();
        
    }
    
    private void usePoint(int index){
        System.out.println("How Much?");
        
        int s;
        try{
            s = Integer.parseInt(InputHandler.getInput());
        }catch(Exception e){
            Console.printError("That's Not A Number!", 500);
            return;
        }
   
        if(s > skillPoints){
           Console.printError("That's Too Much!", 500);
            return;
        }
        
        stat[index] += s;
        skillPoints -= s;
        
        maxHealth = stat[0];
        maxMana = stat[1];
        attack = stat[2];
        defense = stat[3];
        luck = stat[4];
        mAttack = stat[5];
        mDefense = stat[6];
        specialAttack = stat[7];
        
    }
    
    /**
     * Prints the companion's moves
     * @since 1.0
     */
    public final void printMoves(){
        for(int i = 0; i < moves.size(); i++){
            Move m = moves.get(i);
            if(m.manaCost != 0){
                System.out.println(m.name + " | Mana: " + m.manaCost);
            }else{
                System.out.println(m.name);
            }
        }
    }
    
    /**
     * Returns the companion's moves
     * @since 1.0
     */
    public ArrayList<Move> getMoves(){
        return this.moves;
    }
    
    /**
     * Heals the companion to full health and mana
     * @since 1.0
     */
    public void heal(){
        this.health = maxHealth;
        this.mana = maxMana;
    }
}
