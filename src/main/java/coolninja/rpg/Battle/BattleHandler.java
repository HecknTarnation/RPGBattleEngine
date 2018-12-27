package coolninja.rpg.Battle;

import java.util.ArrayList;
import java.util.List;

import coolninja.rpg.*;
import coolninja.rpg.Cons.*;
import coolninja.rpg.Console.Colors;
import coolninja.rpg.Console.Console;
import coolninja.rpg.Exception.NoPlayerSetException;
import coolninja.rpg.Required.Player;

/**
 * The battle handler class
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class BattleHandler {
    
    private static Enemy[] enemies;
    private static Player player;
    private static Companion[] comps;
    private static Player currentPlayer;
    private static int prevIndex;
    private static int expVal = 0;
    
    /**
    * Starts battle with the given enemy/enemies
    * @since 1.0
    * @param Enemy[] the enemy/enemies
    * @param boolean if the player can run
    */
    public static void StartBattle(Enemy en, boolean canRun){
        enemies = new Enemy[1];
        
        enemies[0] = new Enemy(en.name, en.health, en.attack, en.defense, en.luck, en.mAttack, en.mDefense, en.expValue, en.moves);
        
        try {
            BattleLoop(canRun);
        } catch (NoPlayerSetException e) {
            e.printStackTrace();
        }
    }
    
    public static void StartBattle(Enemy[] ens, boolean canRun){
        
        enemies = new Enemy[ens.length];
        
        for(int i = 0; i < ens.length; i++){
            Enemy t = ens[i];
            enemies[i] = new Enemy(t.name, t.health, t.attack, t.defense, t.luck, t.mAttack, t.mDefense, t.expValue, t.moves);
        }
        
        try {
            BattleLoop(canRun);
        } catch (NoPlayerSetException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Deals damage to the enemy given the index
     * @since 1.0
     * @param int index
     * @param int damage
     */
    public static void dealEnemyDamage(int index, int damage){
        enemies[index].health -= damage;
    }
    
    //private methods
    
    private static void BattleLoop(boolean canRun) throws NoPlayerSetException{
        
        player = Vars.player;
        
        comps = Vars.comps;
        
        for(int i = 0; i < enemies.length; i++){
            expVal += enemies[i].expValue;
        }
        
        if(player == null){
            throw new NoPlayerSetException("No player was set");
        }
        
        //main battle loop
        while(enemies != null){
            
            if(CheckIfShouldFinish()){
                enemies = null;
                break;
            }
            
            if(prevIndex == 0){
                currentPlayer = player;
                prevIndex = 1;
            }else{
                currentPlayer = findTurn();
            }
            
            if(currentPlayer == null){
                currentPlayer = player;
                prevIndex = 1;
                EnemyTurns();
            }
            
            Console.clear();
            
            System.out.println("\n -Health: " + player.health + "/" + player.maxHealth + "\n -Mana: " + player.mana + "/" + player.maxMana + "\n");
            
            if(comps != null){
                for(int i = 0; i < comps.length; i++){
                    System.out.println(Vars.compColorCode+" -" + comps[i].name + ": " + comps[i].health + "/" + comps[i].maxHealth + " | " + comps[i].mana + "/" + comps[i].maxMana);
                }   
            }
            
            System.out.print("\n");
            
            for(int i = 0; i < enemies.length; i++){
                System.out.println(Vars.enemyColorCode + " -" + enemies[i].name + ": " + enemies[i].health);
            }
            
            Colors.RESET();
            
            System.out.print("\n");
            
            System.out.println(Runtime.getRuntime().freeMemory());
            
            float freemem = (Runtime.getRuntime().freeMemory())/1073741824;
            
            System.out.println("Memory(giga): " + freemem);
            
            if(currentPlayer.name.equalsIgnoreCase("you")){
                System.out.println("It's Your Turn");
            }else{
                System.out.println("It's " + currentPlayer.name + "'s Turn");
            }
            
            System.out.print(" -Attack\n -Item\n -Idle\n");
            if(canRun){
                System.out.println(" -Run");
            }
            
            String input = InputHandler.getInput();
            
            switch(input){
                case "attack":
                    Attack(currentPlayer);
                    break;
                case "item":
                    Item();
                    break;
                case "idle":
                    Idle();
                    break;
                case "run":
                    Run(canRun);
                    break;
            }
            
        }
        
        BattleFinished();
        
        
    }
    
    public static void Attack(Player player){
        player.printMoves();
        
        ArrayList<Move> m = player.getMoves();
        
        Move move = null;
        
        String input = InputHandler.getInput();
        
        for(int i = 0; i < m.size(); i++){
            if(m.get(i).name.equalsIgnoreCase(input)){
                move = m.get(i);
            }
        }
        
        while(move == null){
            Console.printError("Not A Move", 1050);
            Console.clear();
            player.printMoves();
            move = PickMove();
        }
        
        System.out.println("Target?");
        for(int i = 0; i < enemies.length; i++){
            System.out.println(Colors.BLACK);
            System.out.println("  -" + Colors.WHITE_BACKGROUND + enemies[i].name + Colors.reset());
        }
        
        String target = InputHandler.getInput();
        
        for(int i = 0; i < enemies.length; i++){
            if(target.equalsIgnoreCase(enemies[i].name)){
                EnemyTakeDamage(i, move, player);
                return;
            }
        }
        
        Console.printError("Not A Valid Target!", 1000);
        
        Attack(currentPlayer);
        
    }
    
    /**
     * Uses an item
     * @since 1.0
     */
    public static void Item(){
        player.printInv();
        
        String input = InputHandler.getInput();
        
        for(int i = 0; i < player.getInv().size(); i++){
            if(input.equalsIgnoreCase(player.getInv().get(i).name)){
                player.getInv().get(i).Use();
            }
        }
        
    }
    
    private static void Idle(){
        if(currentPlayer.name.equalsIgnoreCase("you")){
            System.out.print("You did nothing");
        }else{
            System.out.print(currentPlayer.name+" did nothing");
        }
        Console.Dots(3, 300);
    }
    
    private static void Run(boolean canRun){
        System.out.print("You started to run");
        Console.Dots(3, 400);
        
        if(!canRun){
            System.out.println("You can't run from this battle!");
            return;
        }
        Player[] t = new Player[comps.length + 1];
        for(int i = 0; i < t.length-1; i++){
            t[i] = comps[i];
        }
        t[t.length-1] = player;
        int a = MathFunc.addPlayerLuck(t);
        boolean can = MathFunc.canRun(a, MathFunc.addLuck(enemies));
        if(can){
            System.out.println("and you got away!");
            enemies = null;
            expVal = 0;
        }else{
            System.out.println("and you couldn't get away!");
        }
        
        Console.waitFull(1);
    }
    
    private static void EnemyTakeDamage(int enemyIndex, Move move, Player player){
        Console.clear();
        
        if(move.manaCost > 0){
            player.mana -= move.manaCost;
        }
        
        int t = 0;
        
        int a = (move.damage + player.attack)-enemies[enemyIndex].defense;
        int mA = (move.mDamage + player.mAttack)-enemies[enemyIndex].mDefense;
        
        t = a + mA;
        
        if(t < 0){
            t = 0;
        }
        
        printGraphic(move);
        
        enemies[enemyIndex].health -= t;
        
        
        Console.Dots(3, 300);
        
        if(t != 0){
            System.out.println(enemies[enemyIndex].name + move.text + "for " + t + " damage");
        }else{
            System.out.println(enemies[enemyIndex].name + " took no damage");
        }
        
        Console.waitFull(1);
        
        
    }
    
    private static void EnemyTurns(){
        
        for(int i = 0; i < enemies.length; i++){
            
            Player p = pick();
            
            Enemy e = enemies[i];
        
            Move m = pickEnemyMove(e, p);
        
            PlayerTakeDamage(p, m, e);

        }
        
    }
    
    private static Move pickEnemyMove(Enemy e, Player p){
        
        switch(e.AIID){
            case 1:
                for(int i = 0; i < e.moves.length; i++){
                    if(p.currentWeakness != null && e.moves[i].type.type == p.currentWeakness.type){
                        return e.moves[i];
                    }
                }
                break;
            case 2:
               for(int i = 0; i < e.moves.length; i++){
                    if(e.moves[i].damage >= p.health){
                        return e.moves[i];
                    }
                }
                break;            
        }
        
        return e.moves[MathFunc.random(e.moves.length-1)];
    }
    
    private static Player pick(){
        int t = MathFunc.random(comps.length);
        if(t <= 0){
            return player;
        }else{
            return comps[t-1];
        }
    }
    
    private static void PlayerTakeDamage(Player p, Move move, Enemy enemy){
        Console.clear();
        
        int t = 0;
        
        int a = (move.damage + enemy.attack)-p.defense;
        int mA = (move.mDamage + enemy.mAttack)-p.mDefense;
        
        t = a + mA;
        
        if(t < 0){
            t = 1;
        }
        
        printGraphic(move);

        p.health -= t;
        
        Console.Dots(3, 300);
        
        if(t >= 0){
            System.out.println(p.name + move.text + "for " + t + " damage");
        }else{
            System.out.println(p.name + " took no damage");
        }
        
        Console.waitFull(1);
    }
    
    private static void BattleFinished(){
        
        if(expVal > 0){
            System.out.println(Vars.winText);
        
            System.out.println("You earned " + expVal + " exp");
        
            player.increaseEXP(expVal);
        
            for(int i = 0; i < comps.length; i++){
                comps[i].increaseEXP(expVal);
            }
        
            player.levelUp();
        
            for(int i = 0; i < comps.length; i++){
                comps[i].levelUp();
            }
        }else{
            
        }
        
        currentPlayer = null;
        
        prevIndex = 0;
    }
    
    private static boolean CheckIfShouldFinish(){
        
        updateArray();
        
        if(enemies.length == 0){
            return true;
        }else{
            return false;
        }
        
    }
    
    private static void updateArray(){
        List<Enemy> z = new ArrayList<Enemy>();
        
        for(int i = 0; i < enemies.length; i++){
            if(enemies[i].health > 0){
                z.add(enemies[i]);
            }
        }        
        
        enemies = new Enemy[z.size()];
        z.toArray(enemies);
    }
    
    private static Move PickMove(){
        String input = InputHandler.getInput();
        
        for(int i = 0; i < currentPlayer.getMoves().size(); i++){
            if(input.equalsIgnoreCase(currentPlayer.getMoves().get(i).name)){
                return currentPlayer.getMoves().get(i);
            }
        }
        
        return null;
    }
    
    private static Player findTurn(){
        int t = prevIndex-1;
        if(t >= comps.length){
           prevIndex = 0;
           return null;
        }
        prevIndex += 1;
        return comps[t];
    }
    
    private static void printGraphic(Move move){
        if(move.graphic == null){
            return;
        }
        for(int i = 0; i < move.graphic.frames.length; i++){
            System.out.println(move.graphic.frames[i]);
            Console.waitReal(move.graphic.waitTime);
        }
    }
}
