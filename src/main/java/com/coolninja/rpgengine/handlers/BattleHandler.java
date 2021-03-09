package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.Cons.*;
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
            //check if won
            won = checkWon();
        }
    }

    private boolean checkWon() {
        return false;
    }

    private String localize(String key) {
        return Engine.localizationHandler.getLocalizedString(key);
    }

}
