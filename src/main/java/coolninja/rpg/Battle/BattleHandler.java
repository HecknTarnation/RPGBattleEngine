package coolninja.rpg.Battle;

import coolninja.rpg.*;
import coolninja.rpg.Cons.*;
import coolninja.rpg.Console.Colors;
import coolninja.rpg.Console.Console;
import coolninja.rpg.Exception.NoPlayerSetException;
import coolninja.rpg.Required.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * The battle handler class
 *
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
     *
     * @since 1.0
     * @param en The enemy (as an object or array)
     * @param canRun if the player can run or not
     */
    public static void StartBattle(Enemy en, boolean canRun) {
        Colors.RESET();
        enemies = new Enemy[1];
        enemies[0] = en.clone();

        try {
            BattleLoop(canRun);
        } catch (NoPlayerSetException e) {
            e.printStackTrace();
        }
    }

    public static void StartBattle(Enemy[] ens, boolean canRun) {

        enemies = new Enemy[ens.length];

        for (int i = 0; i < ens.length; i++) {
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
     *
     * @since 1.0
     * @param index the enemy's index in the array
     * @param damage the amount of damage
     */
    public static void dealEnemyDamage(int index, int damage) {
        enemies[index].health -= damage;
    }

    private static void BattleLoop(boolean canRun) throws NoPlayerSetException {

        player = Vars.player;

        comps = Vars.comps;

        enArchive = enemies.clone();

        //starts battle music
        SoundHandler handler = new SoundHandler(Vars.defaultBattleSoundLocation, true);
        handler.run();

        //finds out hows much exp this battle is worth
        for (Enemy enemy : enemies) {
            expVal += enemy.expValue;
        }

        //creates array that contains enemy drops
        eDrops = new Item[enemies.length];
        for (int i = 0; i < enemies.length; i++) {
            eDrops[i] = enemies[i].drop;
        }

        //throws exception if player is null
        if (player == null) {
            throw new NoPlayerSetException("No player was set");
        }

        //main battle loop
        //runs until enemies array is empty/null
        while (enemies != null) {

            //ends battle if player and all companions are dead
            if (didLose()) {
                SoundHandler hand = new SoundHandler(Vars.loseMusic, false);
                hand.run();
                Vars.loseBattle.BattleLost(player, comps);
                hand.end();
                return;
            }

            //ends battle if all enemies are dead
            if (CheckIfShouldFinish()) {
                enemies = null;
                break;
            }

            //sets currentPlayer to next character
            currentPlayer = findTurn();

            //checks if the currentPlayer is the player and if their health <= 0
            //if so, it rerolls player
            if (currentPlayer == player && player.health <= 0) {
                currentPlayer = findTurn();
            }

            //if currentPlayer is null, have enemies go
            if (currentPlayer == null) {
                currentPlayer = player;
                prevIndex = 1;
                EnemyTurns();
            }

            //clears console to prepare for printing
            Console.clear();

            //prints top if player is healthy, else bottom
            if (player.health > 0) {
                System.out.println("\n -Health: " + player.health + "/" + player.maxHealth + "\n -Mana: " + player.mana + "/" + player.maxMana + "\n");
            } else {
                System.out.println("\n" + Colors.RED_BACKGROUND + "(Dead!) -Health: " + player.health + "/" + player.maxHealth + "\n -Mana: " + player.mana + "/" + player.maxMana + Colors.reset() + "\n");
            }

            //checks if companions are null, if not, then print using the same rules as above
            if (comps != null) {
                for (Companion comp : comps) {
                    if (comp.health > 0) {
                        System.out.println(Vars.compColorCode + " -" + comp.name + ": " + comp.health + "/" + comp.maxHealth + " | " + comp.mana + "/" + comp.maxMana);
                    } else {
                        System.out.println(Vars.compColorCode + " (Dead!) -" + comp.name + ": " + comp.health + "/" + comp.maxHealth + " | " + comp.mana + "/" + comp.maxMana + Colors.reset());
                    }
                }
            }

            //prints new line
            System.out.print("\n");

            //prints out enemies's stats
            for (Enemy enemy : enemies) {
                System.out.println(Vars.enemyColorCode + " -" + enemy.name + ": " + enemy.health);
            }

            //resets colors
            Colors.RESET();

            System.out.print("\n");

            //prints out currentPlayer's name
            if (currentPlayer.name.equalsIgnoreCase("you")) {
                System.out.println("It's Your Turn");
            } else {
                System.out.println("It's " + currentPlayer.name + "'s Turn");
            }

            //prints out player's options
            System.out.print(" -Attack\n -Item\n -Stats\n -Idle\n");
            if (canRun) {
                System.out.println(" -Run");
            }

            String input = InputHandler.getInput();

            //sees which option the player picked
            switch (input.toLowerCase()) {
                case "attack":
                    Attack();
                    break;
                case "item":
                    Item();
                    break;
                case "idle":
                    Idle();
                    break;
                case "stats":
                    Console.clear();
                    currentPlayer.printStats();
                    InputHandler.pressEnter();
                    Attack();
                case "run":
                    Run(canRun);
                    break;
                default:
                    Console.printError("Not a Command", 1000);
            }

            //checks if player lost
            if (didLose()) {
                Vars.loseBattle.BattleLost(player, comps);
                return;
            }
            
            currentPlayer.checkTemStats();

        }

        //once loop has ended, stop music and run BattleFinished
        handler.end();
        BattleFinished();
    }

    //lets player pick move
    private static void Attack() {

        //exits if player doesn't have at least 1 hp
        if (currentPlayer.health <= 0) {
            return;
        }

        //prints player's move
        currentPlayer.printMoves();

        ArrayList<Move> m = currentPlayer.getMoves();

        Move move = null;

        String input = InputHandler.getInput();

        for (int i = 0; i < m.size(); i++) {
            if (m.get(i).name.equalsIgnoreCase(input)) {
                move = m.get(i);
            }
        }

        while (move == null) {
            Console.printError("Not A Move", 1050);
            Console.clear();
            player.printMoves();
            move = PickMove();
        }

        //has player pick the target
        System.out.println("Target?");
        for (Enemy enemy : enemies) {
            System.out.println(Colors.BLACK);
            System.out.println("  -" + Colors.WHITE_BACKGROUND + enemy.name + Colors.reset());
        }

        String target = InputHandler.getInput();

        for (int i = 0; i < enemies.length; i++) {
            if (target.equalsIgnoreCase(enemies[i].name)) {
                EnemyTakeDamage(i, move);
                return;
            }
        }

        Console.printError("Not A Valid Target!", 1000);

        //loops if target was invalid
        Attack();

    }

    /**
     * Uses an item
     *
     * @since 1.0
     */
    private static void Item() {

        if (currentPlayer.health <= 0) {
            return;
        }

        player.printInv();

        String input = InputHandler.getInput();

        for (int i = 0; i < player.getInv().size(); i++) {
            if (input.equalsIgnoreCase(player.getInv().get(i).name)) {
                player.getInv().get(i).Use(new BattleState());
                player.getInv().remove(i);
            }
        }

    }

    //allows player to skip their turn
    private static void Idle() {

        if (currentPlayer.health <= 0) {
            return;
        }

        if (currentPlayer.name.equalsIgnoreCase("you")) {
            System.out.print("You did nothing");
        } else {
            System.out.print(currentPlayer.name + " did nothing");
        }
        Console.Dots(3, 300);
    }

    //allows player to run from battle
    private static void Run(boolean canRun) {

        if (currentPlayer.health <= 0) {
            return;
        }

        System.out.print(currentPlayer.name + " started to run");
        Console.Dots(3, 400);

        //if the battle cannot be ran from, print this message and exit function
        if (!canRun) {
            System.out.println(currentPlayer.name + " can't run from this battle!");
            return;
        }
        Player[] t = new Player[comps.length + 1];
        for (int i = 0; i < t.length - 1; i++) {
            t[i] = comps[i];
        }
        t[t.length - 1] = player;
        int a = MathFunc.addPlayerLuck(t);
        boolean can = MathFunc.canRun(a, MathFunc.addLuck(enemies));
        if (can) {
            System.out.println("and " + currentPlayer.name + " got away!");
            enemies = null;
            expVal = 0;
        } else {
            System.out.println("and " + currentPlayer.name + " couldn't get away!");
        }

        Console.waitFull(1);
    }

    //used to damage the enemy
    private static void EnemyTakeDamage(int enemyIndex, Move move) {
        Console.clear();

        //checks if move costs mana
        if (move.manaCost > 0) {
            currentPlayer.mana -= move.manaCost;
        }

        int t;

        int a = (move.damage + currentPlayer.attack) - enemies[enemyIndex].defense;
        int mA = (move.mDamage + currentPlayer.mAttack) - enemies[enemyIndex].mDefense;

        t = a + mA;

        //checks if damage is less than 0, if so, set it to 0
        if (t < 0) {
            t = 0;
        }

        //plays sound and prints graphic
        printGraphic(move, playSound(move));
        Colors.RESET();
        move.Use(t, currentPlayer, enemies[enemyIndex], comps);

        //checks if move is super effective
        if (enemies[enemyIndex].weakness != null) {
            if (enemies[enemyIndex].weakness.type == move.type) {
                t *= enemies[enemyIndex].weakness.effectiveness;
                if (enemies[enemyIndex].weakness.effectiveness > 1) {
                    System.out.println("Move is super effective!");
                } else {
                    System.out.println("Move was not very effective!");
                }
            }
        }

        if (Vars.shouldUseLuckForExtraDamage) {
            t += MathFunc.luckDamageCalc(currentPlayer.luck);
        }

        //checks if move is a critical hit
        if (MathFunc.random(0) * 100 < currentPlayer.luck) {
            if (!MathFunc.accHitCalc(move.accuracy)) {
                System.out.println(currentPlayer.name + " missed!");
                Console.waitFull(1);
                return;
            }
            System.out.println(Colors.BLUE + "C" + Colors.CYAN + " R" + Colors.GREEN + " I" + Colors.YELLOW + " T" + Colors.WHITE + " I" + Colors.RED + " C" + Colors.PURPLE + " A" + Colors.GREEN + " L!" + Colors.reset());
            t *= (currentPlayer.specialAttack / 2 + 1.1) + 1;
            Console.Dots(3, 300);
            System.out.println(currentPlayer.name + move.text + enemies[enemyIndex].name + " for " + t + " damage");
            enemies[enemyIndex].health -= t;
            Console.waitFull(1);
            return;
        }

        //checks if move missed
        if (!MathFunc.accHitCalc(move.accuracy)) {
            System.out.println(currentPlayer.name + " missed!");
            return;
        }

        //damages the enemy
        enemies[enemyIndex].health -= t;

        Console.Dots(3, 300);

        //prints top if damsge is greater than 0, else bottom
        if (t > 0) {
            System.out.println(currentPlayer.name + move.text + enemies[enemyIndex].name + " for " + t + " damage");
        } else {
            System.out.println(enemies[enemyIndex].name + " took no damage");
        }

        //waits one second
        Console.waitFull(1);

    }

    private static void EnemyTurns() {

        for (Enemy enemie : enemies) {
            Player p = pick();
            Enemy e = enemie;
            Move m = pickEnemyMove(e, p);
            PlayerTakeDamage(p, m, e);
        }

    }

    //pciks the best move to use
    private static Move pickEnemyMove(Enemy e, Player p) {

        switch (e.AIID) {
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

        try {
            return e.moves[MathFunc.random(e.moves.length) - 1];
        } catch (IndexOutOfBoundsException x) {
            return e.moves[0];
        }

    }

    //picks a random character
    private static Player pick() {
        int t = MathFunc.random(comps.length);
        Player p;
        if (t <= 0) {
            p = player;
        } else {
            p = comps[t - 1];
        }
        if (p.health <= 0) {
            return pick();
        } else {
            return p;
        }
    }

    //same as enemy take damage, but for the player's team
    private static void PlayerTakeDamage(Player p, Move move, Enemy enemy) {
        Console.clear();

        int t;

        int a = (move.damage + enemy.attack) - p.defense;
        int mA = (move.mDamage + enemy.mAttack) - p.mDefense;

        t = a + mA;

        if (t < 0) {
            t = 1;
        }

        printGraphic(move, playSound(move));
        Colors.RESET();

        if (p.currentWeakness != null && move.type == p.currentWeakness.type) {
            t *= p.currentWeakness.effectiveness;
            if (p.currentWeakness.effectiveness > 1) {
                System.out.println("Enemy's move is super effective!");
            } else {
                System.out.println("Enemy's move was not very effective!");
            }
        }

        if (!MathFunc.accHitCalc(move.accuracy)) {
            System.out.println(enemy.name + " missed!");
            Console.waitFull(1);
            return;
        }

        p.health -= t;

        Console.Dots(3, 300);

        if (t >= 0) {
            System.out.println(enemy.name + move.text + p.name + " for " + t + " damage");
        } else {
            System.out.println(p.name + " took no damage");
        }

        Console.waitFull(1);
    }

    //checks what items shou;d drop
    private static void DropItem() {

        for (int i = 0; i < eDrops.length; i++) {
            if (eDrops[i] != null && MathFunc.shouldDrop(enArchive[i], eDrops[i])) {
                System.out.println("You got " + eDrops[i].name);
                player.addItemToInv(eDrops[i]);
                Console.waitFull(1);
            }
        }

    }

    //ends battle and awards items/exp
    private static void BattleFinished() {

        if (expVal > 0) {
            SoundHandler handler = new SoundHandler(Vars.winMusic, false);

            System.out.println(Vars.winText);

            int tempEXP = (int) (expVal + (3 * Math.sin(MathFunc.random(0))));

            System.out.println("You earned " + tempEXP + " exp");

            player.increaseEXP(tempEXP);

            if (comps != null) {
                for (Companion comp : comps) {
                    comp.increaseEXP(tempEXP);
                }
            }

            DropItem();

            //do nothing until win music has stopped playing
            if (handler != null) {
                try {
                    while (handler.audio.isRunning()) {
                    }
                } catch (NullPointerException e) {
                }
            }

            //ends sound and waits for player to press enter
            handler.end();
            InputHandler.pressEnter();

            //checks if player/companions should level up
            player.levelUp();

            if (comps != null) {
                for (Companion comp : comps) {
                    comp.levelUp();
                }
            }

            //runs the boss's "onDeath" method
            if (enArchive[0] instanceof Boss) {
                enArchive[0].onDeath();
            }

        } else {
            //skips these steps if there was no exp to be won
        }

        //prepares for next battle
        currentPlayer = null;

        prevIndex = 0;
    }

    private static boolean CheckIfShouldFinish() {

        updateArray();

        //returns true if length is 0, false otherwise
        return enemies.length == 0;

    }

    //checks if player lost the battle
    private static boolean didLose() {
        int s = 0;
        if (player.health <= 0) {
            player.health = 0;
            s += 1;
        }
        if (comps != null) {
            for (Companion comp : comps) {
                if (comp.health <= 0) {
                    comp.health = 0;
                    s += 1;
                }
            }
            return s == comps.length + 1;
        } else {
            return s == 1;
        }
    }

    //removes dead enemies from the array
    private static void updateArray() {
        List<Enemy> z = new ArrayList<>();

        for (Enemy enemy : enemies) {
            if (enemy.health > 0) {
                z.add(enemy);
            } else {
                enemy.onDeath();
            }
        }

        enemies = new Enemy[z.size()];
        z.toArray(enemies);
    }

    //allows player to pick move from move list
    private static Move PickMove() {
        String input = InputHandler.getInput();

        for (int i = 0; i < currentPlayer.getMoves().size(); i++) {
            if (input.equalsIgnoreCase(currentPlayer.getMoves().get(i).name)) {
                return currentPlayer.getMoves().get(i);
            }
        }

        //returns null if input doesn't match any moves
        return null;
    }

    //finds the current characters turn
    private static Player findTurn() {
        if (prevIndex == 0 && player.health > 0) {
            prevIndex += 1;
            return player;
        } else if (prevIndex == 0) {
            prevIndex += 1;
            return findTurn();
        } else {
            try {
                if (comps[prevIndex - 1].health > 0) {
                    byte t = (byte) (prevIndex - 1);
                    prevIndex += 1;
                    return comps[t];
                } else {
                    prevIndex += 1;
                    return findTurn();
                }
            } catch (IndexOutOfBoundsException e) {
                //returns null if there is a problem
                return null;
            }
        }
    }

    //plays move sound
    private static SoundHandler playSound(Move move) {
        if (move.sound == null) {
            return null;
        }

        SoundHandler handler = new SoundHandler(move.sound, false);
        handler.run();
        return handler;
    }

    //prints move graphic
    private static void printGraphic(Move move, SoundHandler handler) {
        if (move.graphic == null) {
            return;
        }

        for (int i = 0; i < move.graphic.frames.length; i++) {
            Colors.RESET();
            //prints top if player is healthy, else bottom
            if (player.health > 0) {
                System.out.println("\n -Health: " + player.health + "/" + player.maxHealth + "\n -Mana: " + player.mana + "/" + player.maxMana + "\n");
            } else {
                System.out.println("\n" + Colors.RED_BACKGROUND + "(Dead!) -Health: " + player.health + "/" + player.maxHealth + "\n -Mana: " + player.mana + "/" + player.maxMana + Colors.reset() + "\n");
            }

            //checks if companions are null, if not, then print using the same rules as above
            if (comps != null) {
                for (Companion comp : comps) {
                    if (comp.health > 0) {
                        System.out.println(Vars.compColorCode + " -" + comp.name + ": " + comp.health + "/" + comp.maxHealth + " | " + comp.mana + "/" + comp.maxMana);
                    } else {
                        System.out.println(Vars.compColorCode + " (Dead!) -" + comp.name + ": " + comp.health + "/" + comp.maxHealth + " | " + comp.mana + "/" + comp.maxMana + Colors.reset());
                    }
                }
            }
            Colors.RESET();
            for (Enemy en : enemies) {
                System.out.println("\n" + en.name + " HP: " + en.health);
            }
            System.out.println("--------------" + move.name + "----------------");
            System.out.print(move.graphic.frames[i]);
            Console.waitReal(move.graphic.waitTime);
            Console.clear();
        }
        Colors.RESET();
        if(handler != null){
            while(handler.audio.isRunning()){}
        }
    }
}
