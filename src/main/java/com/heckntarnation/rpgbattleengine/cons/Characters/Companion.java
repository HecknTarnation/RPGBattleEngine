package com.heckntarnation.rpgbattleengine.cons.Characters;

import com.heckntarnation.rpgbattleengine.Vars;
import com.heckntarnation.rpgbattleengine.cons.Items.Item;

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
