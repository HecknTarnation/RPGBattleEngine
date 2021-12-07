package com.heckntarnation.rpgbattleengine.cons;

import com.heckntarnation.rpgbattleengine.Vars;

/**
 *
 * @author Ben
 */
public class Companion extends Player {
    
    public Companion(String name, int baseExp, int expMod) {
        super(name, baseExp, expMod);
    }
    
    @Override
    public void addItemToInv(Item item) {
        Vars.player.addItemToInv(item);
    }
}
