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

    private Player currentPlayer;

    public void startBattle(Enemy[] en) {
        this.ens = en;
        this.enArchive = en;
        this.player = Vars.player;
        this.comps = Vars.companions;
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

            str += String.format(localize(battle_currentTurn), currentPlayer.name.equalsIgnoreCase("you") ? "your" : currentPlayer.name) + "\n";
            String m = localize(battle_menu);
            String[] menu = m.split(",");
            int selection = Engine.inputHandler.doMenu(menu, str);

            switch (selection) {
                case 0:
                    Attack();
                case 1:
                    Item();
                case 2:
                    if (currentPlayer == player) {
                        ConsoleFunc.dots(player.name.equalsIgnoreCase("you") ? player.name + localize(battle_compidle) : localize(battle_plridle), 3, 300);
                    } else {
                        ConsoleFunc.dots(currentPlayer.name + localize(battle_compidle), 3, 300);
                    }
                    ConsoleFunc.wait(1000);
                    break;
                case 3:
                    Run();
            }

            //check if won
            won = checkWon();
            ConsoleFunc.clear();
            getTurn(1);
        }
    }

    private void EnemyTurn() {

    }

    private void Attack() {
        System.exit(1);
    }

    private void Item() {
        System.exit(1);
    }

    private void Run() {
        System.exit(1);
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

    private int currentIndex = -1;

    private void getTurn() {
        getTurn(0);
    }

    private void getTurn(int amountToInc) {
        currentIndex += amountToInc;
        if (currentIndex == -1) {
            currentPlayer = player;
        } else {
            try {
                currentPlayer = comps[currentIndex];
            } catch (ArrayIndexOutOfBoundsException e) {
                currentIndex = -1;
                currentPlayer = null;
            }
        }
    }

}
