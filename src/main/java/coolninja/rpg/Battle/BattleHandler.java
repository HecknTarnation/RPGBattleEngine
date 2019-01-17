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
    private static byte prevIndex;
    private static int expVal = 0;
    private static Item[] eDrops;
    private static Enemy[] enArchive;
    
    /**
    * Starts battle with the given enemy/enemies
    * @since 1.0
    * @param en
    * @param canRun
    */
    public static void StartBattle(Enemy en, boolean canRun){
        enemies = new Enemy[1];
        
        enemies[0] = en.clone();
        
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
            enemies[i] = t.clone();
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
     * @param index
     * @param damage
     */
    public static void dealEnemyDamage(int index, int damage){
        enemies[index].health -= damage;
    }
    
    //private methods
    
    private static void BattleLoop(boolean canRun) throws NoPlayerSetException{
        
        player = Vars.player;
        
        comps = Vars.comps;
        
        enArchive = enemies;
        
        SoundHandler handler = new SoundHandler(Vars.defaultBattleSoundLocation, true);
        handler.run();
        
        for (Enemy enemy : enemies) {
            expVal += enemy.expValue;
        }
        
        eDrops = new Item[enemies.length];
        for(int i = 0; i < enemies.length; i++){
            eDrops[i] = enemies[i].drop;
        }
        
        if(player == null){
            throw new NoPlayerSetException("No player was set");
        }
        
        //main battle loop
        while(enemies != null){
            
            if(didLose()){
                SoundHandler hand = new SoundHandler(Vars.loseMusic, false);
                hand.run();
                Vars.loseBattle.BattleLost(player, comps);
                hand.end();
                return;
            }
            
            if(CheckIfShouldFinish()){
                enemies = null;
                break;
            }

            currentPlayer = findTurn();
            
            if(currentPlayer == player && player.health <= 0){
                currentPlayer = findTurn();
            }
            
            if(currentPlayer == null){
                currentPlayer = player;
                prevIndex = 1;
                EnemyTurns();
            }
            
            Console.clear();
            
            if(player.health > 0){
                System.out.println("\n -Health: " + player.health + "/" + player.maxHealth + "\n -Mana: " + player.mana + "/" + player.maxMana + "\n");
            }else{
                System.out.println("\n" + Colors.RED_BACKGROUND + "(Dead!) -Health: " + player.health + "/" + player.maxHealth + "\n -Mana: " + player.mana + "/" + player.maxMana + Colors.reset() + "\n");
            }

            if(comps != null){
                for (Companion comp : comps) {
                    if (comp.health > 0) {
                        System.out.println(Vars.compColorCode+" -" + comp.name + ": " + comp.health + "/" + comp.maxHealth + " | " + comp.mana + "/" + comp.maxMana);
                    } else {
                        System.out.println(Vars.compColorCode+" (Dead!) -" + comp.name + ": " + comp.health + "/" + comp.maxHealth + " | " + comp.mana + "/" + comp.maxMana + Colors.reset());
                    }
                }   
            }
            
            System.out.print("\n");
            
            for (Enemy enemy : enemies) {
                System.out.println(Vars.enemyColorCode + " -" + enemy.name + ": " + enemy.health);
            }
            
            Colors.RESET();
            
            System.out.print("\n");
            
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
            
            switch(input.toLowerCase()){
                case "attack":
                    Attack();
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
            
            if(didLose()){
                Vars.loseBattle.BattleLost(player, comps);
                return;
            }
            
        }
        
        handler.end();
        BattleFinished();
        
        
    }
    
    public static void Attack(){
        
        if(currentPlayer.health <= 0){
            return;
        }
        
        currentPlayer.printMoves();
        
        ArrayList<Move> m = currentPlayer.getMoves();
        
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
        for (Enemy enemie : enemies) {
            System.out.println(Colors.BLACK);
            System.out.println("  -" + Colors.WHITE_BACKGROUND + enemie.name + Colors.reset());
        }
        
        String target = InputHandler.getInput();
        
        for(int i = 0; i < enemies.length; i++){
            if(target.equalsIgnoreCase(enemies[i].name)){
                EnemyTakeDamage(i, move);
                return;
            }
        }
        
        Console.printError("Not A Valid Target!", 1000);
        
        Attack();
        
    }
    
    /**
     * Uses an item
     * @since 1.0
     */
    public static void Item(){
        
        if(currentPlayer.health <= 0){
            return;
        }
        
        player.printInv();
        
        String input = InputHandler.getInput();
        
        System.out.println("Target?");
        for (Enemy enemie : enemies) {
            System.out.println(Colors.BLACK);
            System.out.println("  -" + Colors.WHITE_BACKGROUND + enemie.name + Colors.reset());
        }
        
        String target = InputHandler.getInput();
        Enemy t = null;
        for (Enemy enemie : enemies) {
            if (target.equalsIgnoreCase(enemie.name)) {
                t = enemie;
                break;
            }
        }
        
        if(t == null){
            Console.printError("Not A Valid Target!", 1000);
            Item();
            return;
        }
        
        for(int i = 0; i < player.getInv().size(); i++){
            if(input.equalsIgnoreCase(player.getInv().get(i).name)){
                player.getInv().get(i).Use(currentPlayer, t);
            }
        }
        
    }
    
    private static void Idle(){
        
        if(currentPlayer.health <= 0){
            return;
        }
        
        if(currentPlayer.name.equalsIgnoreCase("you")){
            System.out.print("You did nothing");
        }else{
            System.out.print(currentPlayer.name + " did nothing");
        }
        Console.Dots(3, 300);
    }
    
    private static void Run(boolean canRun){
        
        if(currentPlayer.health <= 0){
            return;
        }
        
        System.out.print(currentPlayer.name + " started to run");
        Console.Dots(3, 400);
        
        if(!canRun){
            System.out.println(currentPlayer.name + " can't run from this battle!");
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
            System.out.println("and " + currentPlayer.name + " got away!");
            enemies = null;
            expVal = 0;
        }else{
            System.out.println("and " + currentPlayer.name + " couldn't get away!");
        }
        
        Console.waitFull(1);
    }
    
    private static void EnemyTakeDamage(int enemyIndex, Move move){
        Console.clear();
        
        if(move.manaCost > 0){
            currentPlayer.mana -= move.manaCost;
        }
        
        int t;
        
        int a = (move.damage + currentPlayer.attack)-enemies[enemyIndex].defense;
        int mA = (move.mDamage + currentPlayer.mAttack)-enemies[enemyIndex].mDefense;
        
        t = a + mA;
        
        if(t < 0){
            t = 0;
        }
        
        if(enemies[enemyIndex].weakness != null){
            if(enemies[enemyIndex].weakness.type == move.type){
                t *= enemies[enemyIndex].weakness.effectiveness;
            }
        }
        
        playSound(move);
        printGraphic(move);
        
        if(MathFunc.random(0)*100 < currentPlayer.luck){
            if(!MathFunc.accHitCalc(move.accuracy)){
                System.out.println(currentPlayer.name + " missed!");
                return;
            }
            System.out.println(Colors.BLUE+"C"+Colors.CYAN+" R"+Colors.GREEN+" I"+Colors.YELLOW+" T"+Colors.WHITE+" I"+Colors.RED+" C"+Colors.PURPLE+" A"+Colors.GREEN+" L!"+Colors.reset());
            t *= (currentPlayer.specialAttack/2 + 1.1)+1;
            Console.Dots(3, 300);
            System.out.println(currentPlayer.name + move.text + enemies[enemyIndex].name + " for " + t + " damage");
            enemies[enemyIndex].health -= t;
            Console.waitFull(1);
            return;
        }
        
        if(!MathFunc.accHitCalc(move.accuracy)){
            System.out.println(currentPlayer.name + " missed!");
            return;
        }
        
        enemies[enemyIndex].health -= t;
        
        Console.Dots(3, 300);
        
        if(t != 0){
            System.out.println(currentPlayer.name + move.text + enemies[enemyIndex].name + " for " + t + " damage");
        }else{
            System.out.println(enemies[enemyIndex].name + " took no damage");
        }
        
        Console.waitFull(1);
        
        
    }
    
    private static void EnemyTurns(){
        
        for (Enemy enemie : enemies) {
            Player p = pick();
            Enemy e = enemie;
            Move m = pickEnemyMove(e, p);
            PlayerTakeDamage(p, m, e);
        }
        
    }
    
    private static Move pickEnemyMove(Enemy e, Player p){
        
        switch(e.AIID){
            case 1:
                for (Move move : e.moves) {
                    if (p.currentWeakness != null && move.type == p.currentWeakness.type) {
                        return move;
                    }
                }
                break;
            case 2:
                for (Move move : e.moves) {
                    if (move.damage >= p.health) {
                        return move;
                    }
                }
                break;                        
        }
        
        try{
            return e.moves[MathFunc.random(e.moves.length)-1];
        }catch(IndexOutOfBoundsException x){
            return e.moves[0];
        }

    }
    
    private static Player pick(){
        int t = MathFunc.random(comps.length);
        Player p;
        if(t <= 0){
            p = player;
        }else{
            p = comps[t-1];
        }
        if(p.health <= 0){
            return pick();
        }else{
            return p;
        }
    }
    
    private static void PlayerTakeDamage(Player p, Move move, Enemy enemy){
        Console.clear();
        
        int t;
        
        int a = (move.damage + enemy.attack)-p.defense;
        int mA = (move.mDamage + enemy.mAttack)-p.mDefense;
        
        t = a + mA;
        
        if(t < 0){
            t = 1;
        }
        
        playSound(move);
        printGraphic(move);
        
        if(p.currentWeakness != null && move.type == p.currentWeakness.type){
            t *= p.currentWeakness.effectiveness;
        }
        
        if(!MathFunc.accHitCalc(move.accuracy)){
            System.out.println(enemy.name + " missed!");
            return;
        }
        
        p.health -= t;
        
        Console.Dots(3, 300);
        
        if(t >= 0){
            System.out.println(p.name + move.text + "for " + t + " damage");
        }else{
            System.out.println(p.name + " took no damage");
        }
        
        Console.waitFull(1);
    }
    
    private static void DropItem(){
        
        for(int i = 0; i < eDrops.length; i++){
            if(eDrops[i] != null && MathFunc.shouldDrop(enArchive[i], eDrops[i])){
                System.out.println("You got " + eDrops[i].name);
                player.addItemToInv(eDrops[i]);
                Console.waitFull(1);
            }
        }
        
    }
    
    private static void BattleFinished(){
        
        if(expVal > 0){
            SoundHandler handler = new SoundHandler(Vars.winMusic, false);
            
            System.out.println(Vars.winText);
        
            System.out.println("You earned " + expVal + " exp");
            
            
            player.increaseEXP(expVal);
        
            for (Companion comp : comps) {
                comp.increaseEXP(expVal);
            }
            
            DropItem();
            
            while (handler.audio.isRunning()){}
            
            handler.end();
            InputHandler.pressEnter();
            
            player.levelUp();
        
            for (Companion comp : comps) {
                comp.levelUp();
            }
        }else{
            
        }
        
        currentPlayer = null;
        
        prevIndex = 0;
    }
    
    private static boolean CheckIfShouldFinish(){
        
        updateArray();
        
        return enemies.length == 0;
        
    }
    
    private static boolean didLose(){
        int s = 0;
        if(player.health <= 0){
            player.health = 0;
            s += 1;
        }
        for (Companion comp : comps) {
            if (comp.health <= 0) {
                comp.health = 0;
                s += 1;
            }
        }
        return s == comps.length+1;
    }
    
    private static void updateArray(){
        List<Enemy> z = new ArrayList<>();
        
        for (Enemy enemie : enemies) {
            if (enemie.health > 0) {
                z.add(enemie);
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
        if (prevIndex == 0 && player.health > 0){
            prevIndex += 1;
            return player;
        }else if(prevIndex == 0){
            prevIndex += 1;
            return findTurn();
        }else{
            try{
                if(comps[prevIndex-1].health > 0){
                    byte t = (byte)(prevIndex-1);
                    prevIndex += 1;
                    return comps[t];
                }else{
                    prevIndex += 1;
                    return findTurn();
                }
            }catch(IndexOutOfBoundsException e){
                    return null;
            }
        }
    }
    
    private static void playSound(Move move){
        if(move.sound == null){return;}
        
        SoundHandler handler = new SoundHandler(move.sound, false);
        handler.run();
        while (handler.audio.isRunning()){}
        handler.end();
    }
    
    private static void printGraphic(Move move){
        if(move.graphic == null){return;}
        
        for (String frame : move.graphic.frames) {
            System.out.println(frame);
            Console.waitReal(move.graphic.waitTime);
        }
    }
}
