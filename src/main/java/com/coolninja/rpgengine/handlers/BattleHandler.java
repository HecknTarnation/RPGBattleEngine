package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.Colors;
import com.coolninja.rpgengine.Cons.*;
import com.coolninja.rpgengine.ConsoleFunc;
import com.coolninja.rpgengine.Engine;
import com.coolninja.rpgengine.Vars;
import com.coolninja.rpgengine.enums.EN_USKeys;
import static com.coolninja.rpgengine.enums.EN_USKeys.*;

/**
 *
 * @author Ben
 */
public class BattleHandler {

    public Enemy[] ens;
    public Player player;
    public Companion[] comps;
    public Enemy[] enArchive;
    private int expVal = 0;

    private Player currentPlayer;
    private String currentStatus;

    public void startBattle(Enemy[] en) {
        this.ens = en;
        this.enArchive = en;
        this.player = Vars.player;
        this.comps = Vars.companions;
        for (Enemy e : en) {
            expVal += e.expVal;
        }
        battleLoop();
    }

    private void battleLoop() {
        boolean won = false;
        while (!won) {

            String str = "";
            for (Enemy en : ens) {
                str += Vars.Enemy_Color + en.name + ": " + en.health + "/" + en.maxHealth + Colors.reset() + "\n";
            }
            str += "\n" + Vars.Ally_Color + player.name + ": " + player.health + "/" + player.maxHealth + Colors.reset() + "\n";
            if (Vars.companions != null) {
                for (Companion com : Vars.companions) {
                    str += Vars.Ally_Color + com.name + ": " + com.health + "/" + com.maxHealth + Colors.reset() + "\n";
                }
            }

            getTurn();
            if (currentPlayer == null) {
                EnemyTurn();
                continue;
            }

            str += String.format(localize(battle_currentTurn), currentPlayer.name.equalsIgnoreCase("you") ? localize(gen_your) : currentPlayer.name) + "\n";
            currentStatus = str;
            String m = localize(battle_menu);
            String[] menu = m.split(",");
            int selection = Engine.inputHandler.doMenu(menu, str);

            switch (selection) {
                case 0:
                    Attack();
                    break;
                case 1:
                    Item();
                    break;
                case 2:
                    ConsoleFunc.dots(player.name + localize(battle_idle), 3, 300);
                    ConsoleFunc.wait(1000);
                    break;
                case 3:
                    Run();
                    break;
            }

            //check if won
            won = checkWon();
            ConsoleFunc.clear();
            getTurn(1);
        }
    }

    private void EnemyTurn() {
        for (Enemy en : ens) {
            EnemyTurnSub(en);
        }
    }

    private void EnemyTurnSub(Enemy en) {

    }

    private void Attack() {
        Move[] moves = currentPlayer.getMoves();
        String[] mNames = Move.arrToStr(moves);
        int selection = Engine.inputHandler.doMenu(mNames, currentStatus);
        Move selectedMove = moves[selection];
        int d = selectedMove.damage + currentPlayer.attack;
        int mD = selectedMove.mDamage + currentPlayer.mAttack;
        int cost = selectedMove.manaCost;
        if (cost > 0 && currentPlayer.mana < cost) {
            //TODO: localize
            String p1 = currentPlayer.name.equalsIgnoreCase("you") ? "You don't" : currentPlayer.name + " doesnt't";
            String s = String.format(localize(battle_missingmana), p1);
            ConsoleFunc.dots(s, 3, 250);
            ConsoleFunc.wait(1500);
            Attack();
        }
        currentPlayer.mana -= cost;
        int target;
        String[] enNames = new String[ens.length];
        for (int i = 0; i < ens.length; i++) {
            enNames[i] = ens[i].name;
        }
        target = Engine.inputHandler.doMenu(enNames, currentStatus);

        int finalD = ((d - ens[target].defense) >= 0 ? (d - ens[target].defense) : 0)
                + ((mD - ens[target].mDefense) >= 0 ? (mD - ens[target].mDefense) : 0);

        ens[target].health -= finalD;
        println(String.format(localize(battle_moveused), currentPlayer.name, selectedMove.name, ens[target].name, finalD));
        selectedMove.Use();
        ConsoleFunc.wait(2000);
    }

    private void Item() {
        println("Item");
        System.exit(0);
    }

    private void Run() {
        println("Run");
        System.exit(0);
    }

    private boolean checkWon() {
        return false;
    }

    //some macros for me
    private String localize(String key) {
        return Engine.localizationHandler.getLocalizedString(key);
    }

    private String localize(EN_USKeys key) {
        return localize(key.key);
    }

    private void print(String str) {
        System.out.print(str);
    }

    private void println(String str) {
        System.out.println(str);
    }
    //end macros

    private int currentIndex = 0;

    private void getTurn() {
        getTurn(0);
    }

    private void getTurn(int amountToInc) {
        if (comps == null) {
            currentPlayer = (currentIndex == 0 ? player : null);
            return;
        }
        Player[] t = new Player[comps.length + 1];
        t[0] = player;
        for (int i = 1; i < comps.length; i++) {
            t[i] = comps[i - 1];
        }
        currentIndex += amountToInc;
        try {
            currentPlayer = t[currentIndex];
        } catch (ArrayIndexOutOfBoundsException e) {
            currentIndex = 0;
            currentPlayer = null;
        }
    }
}
