package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.Colors;
import com.coolninja.rpgengine.Cons.*;
import com.coolninja.rpgengine.ConsoleFunc;
import com.coolninja.rpgengine.Engine;
import com.coolninja.rpgengine.Vars;

/**
 *
 * @author Ben
 */
public class BattleHandler {

    public Enemy[] ens;
    public Player player;
    public Companion[] comps;
    public Enemy[] enArchive;

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

            //TODO: battle
            for (Enemy en : ens) {
                println(Vars.Enemy_Color + en.name + ": " + en.health + "/" + en.maxHealth + Colors.reset());
            }
            println(Vars.Ally_Color + player.name + ": " + player.health + "/" + player.maxHealth + Colors.reset());
            if (Vars.companions != null) {
                for (Companion com : Vars.companions) {
                    println(Vars.Ally_Color + com.name + ": " + com.health + "/" + com.maxHealth + Colors.reset());
                }
            }

            ConsoleFunc.wait(5000);

            //check if won
            won = checkWon();
            ConsoleFunc.clear();
        }
    }

    private boolean checkWon() {
        return false;
    }

    //some macros for me
    private String localize(String key) {
        return Engine.localizationHandler.getLocalizedString(key);
    }

    private void print(String str) {
        System.out.print(str);
    }

    private void println(String str) {
        System.out.println(str);
    }

}
