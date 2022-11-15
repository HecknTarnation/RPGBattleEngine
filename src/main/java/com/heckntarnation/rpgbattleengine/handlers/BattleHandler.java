package com.heckntarnation.rpgbattleengine.handlers;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.Colors;
import com.heckntarnation.rpgbattleengine.ConsoleFunc;
import com.heckntarnation.rpgbattleengine.MathFunc;
import com.heckntarnation.rpgbattleengine.Vars;
import com.heckntarnation.rpgbattleengine.cons.Battle.Graphic;
import com.heckntarnation.rpgbattleengine.cons.Battle.Move;
import com.heckntarnation.rpgbattleengine.cons.Battle.Weakness;
import com.heckntarnation.rpgbattleengine.cons.Characters.Companion;
import com.heckntarnation.rpgbattleengine.cons.Characters.Enemy;
import com.heckntarnation.rpgbattleengine.cons.Characters.Player;
import com.heckntarnation.rpgbattleengine.cons.Items.Drop;
import com.heckntarnation.rpgbattleengine.cons.Items.Item;
import static com.heckntarnation.rpgbattleengine.dev.Macros.*;
import static com.heckntarnation.rpgbattleengine.enums.LangKeys.*;
import com.heckntarnation.rpgbattleengine.enums.StatusArrayPosition;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class BattleHandler {

    public Enemy[] ens;
    public Player player;
    public Companion[] comps;
    public Enemy[] enArchive;
    public boolean canRun;
    private int expVal = 0;

    private Player currentCharacter;
    private String currentStatus;

    public void startBattle(boolean canRun, Enemy... en) {
        this.canRun = canRun;
        ens = new Enemy[en.length];
        for (int i = 0; i < ens.length; i++) {
            ens[i] = en[i].clone();
            expVal += en[i].expVal;
        }
        this.enArchive = ens.clone();
        this.player = Vars.player;
        this.comps = Vars.companions;
        battleLoop();
    }

    private void battleLoop() {
        boolean won = false;
        exit:
        while (!won) {
            if (checkDie()) {
                break;
            }
            String str = "";
            for (Enemy en : ens) {
                str += Vars.Enemy_Color + en.name + ": " + en.health + "/" + en.maxHealth + Colors.reset() + "\n";
            }
            String pName = player.name.equalsIgnoreCase(BattleEngine.localizationHandler.SECOND_PERSON_STRING) ? "You" : player.name;
            if (player.health > 0) {
                str += "\n" + Vars.Ally_Color + pName + ": " + player.health + "/" + player.maxHealth + Colors.reset() + "\n";
            } else {
                str += "\n" + Colors.RED_BACKGROUND + pName + ": DEAD" + Colors.reset() + "\n";
            }
            if (comps != null) {
                for (Companion com : Vars.companions) {
                    if (com.health > 0) {
                        str += Vars.Ally_Color + com.name + ": " + com.health + "/" + com.maxHealth + Colors.reset() + "\n";
                    } else {
                        str += Colors.RED_BACKGROUND + com.name + ": DEAD" + Colors.reset() + "\n";
                    }
                }
            }

            getTurn();
            if (currentCharacter != null) {
                while (currentCharacter != null && currentCharacter.health <= 0) {
                    getTurn(1);
                }
            }
            if (currentCharacter == null) {
                EnemyTurn();
                player.statusEffectTick();
                if (comps != null) {
                    for (Companion comp : comps) {
                        comp.statusEffectTick();
                    }
                }
                for (Enemy en : ens) {
                    en.statusEffectTick();
                }
                currentIndex = 0;
                continue;
            }

            str += currentCharacter.name.equalsIgnoreCase(BattleEngine.localizationHandler.SECOND_PERSON_STRING) ? String.format(localize(battle_currentTurn), "your") : String.format(localize(battle_currentTurn), currentCharacter.name);
            currentStatus = str;
            String m = localize(battle_menu);
            String[] menu = m.split(",");
            if (!canRun) {
                menu[3] = null;
            }
            int selection = BattleEngine.inputHandler.doMenu(menu, str, true, 0);

            switch (selection) {
                case 0:
                    Attack();
                    break;
                case 1:
                    Item();
                    break;
                case 2:
                    ConsoleFunc.dots(String.format(localize(battle_idle), currentCharacter.name), 3, 300);
                    ConsoleFunc.wait(1000);
                    break;
                case 3:
                    if (Run()) {
                        won = false;
                        break exit;
                    }
                    break;
            }

            //check if won
            won = checkWon();
            ConsoleFunc.clear();
            currentIndex++;
        }
        BattleEnd(won);
    }

    private void BattleEnd(boolean won) {
        if (!won && player.health <= 0) {
            System.out.println("[---" + Colors.RED + Colors.BLACK_BACKGROUND + localize(battle_gameover) + Colors.reset() + "---]");
            ConsoleFunc.wait(5000);
            BattleEngine.deathHandler.OnDeath();
            return;
        } else if (!won) {
            return;
        }
        ens = null;
        player = null;
        comps = null;
        currentIndex = 0;
        currentCharacter = null;
        currentStatus = null;

        for (Enemy e : enArchive) {
            Drop d = e.getDrop();
            if (d != null) {
                player.inv.add(d.getItem());
                println(String.format(localize(battle_gotitem), player.name, d.getItem().name));
            }
        }
        Vars.player.levelUp(expVal);
        if (Vars.companions != null) {
            for (Companion c : Vars.companions) {
                c.levelUp(expVal);
            }
        }
        enArchive = null;
        expVal = 0;
        System.gc();
    }

    private void EnemyTurn() {
        for (Enemy en : ens) {
            EnemyTurnSub(en);
        }
    }

    private void EnemyTurnSub(Enemy en) {
        ArrayList<Move> moves = en.moves;
        Move currentHeighest = new Move("").setDamage(0, 0);
        ArrayList<Player> aliveArr = new ArrayList<>();
        if (player.health > 0) {
            aliveArr.add(player);
        }
        if (comps != null) {
            for (int i = 0; i < comps.length; i++) {
                if (comps[i].health > 0) {
                    aliveArr.add(comps[i]);
                }
            }
        }
        Player[] t = new Player[aliveArr.size()];
        t = aliveArr.toArray(t);
        int temp = MathFunc.randomInt(0, t.length - 1);
        Player selectedPlayer = t[temp];

        ArrayList<Weakness> weakness = selectedPlayer.weaknesses;

        //Will default to the first move in moves if none matching the criteria are found.
        int selection = 0;
        switch (en.aiLevel) {
            case Random:
                int index = MathFunc.randomInt(0, moves.size() - 1);
                index = index > moves.size() - 1 ? moves.size() - 1 : index;
                selection = index > 0 ? index : 0;
                break;
            case Damage:
                for (int i = 0; i < moves.size(); i++) {
                    if (moves.get(i).damage > currentHeighest.damage) {
                        currentHeighest = moves.get(i);
                        selection = i;
                    }
                }
                break;
            case DamageMagic:
                for (int i = 0; i < moves.size(); i++) {
                    if (moves.get(i).mDamage > currentHeighest.mDamage) {
                        currentHeighest = moves.get(i);
                        selection = i;
                    }
                }
                break;
            case Weakness:
                for (int i = 0; i < moves.size(); i++) {
                    for (Weakness weak : weakness) {
                        if (moves.get(i).type.type.equalsIgnoreCase(weak.type)) {
                            selection = i;
                            break;
                        }
                    }
                }
                break;
            case WeaknessDamage:
                int currentDamage = -1;
                int currentWeakness = -1;
                for (int i = 0; i < moves.size(); i++) {
                    for (Weakness weak : weakness) {
                        if (weak.type.equalsIgnoreCase(moves.get(i).type.type)) {
                            currentWeakness = i;
                        }
                        if (moves.get(i).damage > currentHeighest.damage) {
                            currentDamage = i;
                            currentHeighest = moves.get(i);
                        }
                    }
                }
                if (currentDamage == currentWeakness && currentDamage != -1) {
                    selection = currentDamage;
                } else {
                    if (MathFunc.randomInt(0, 1) == 0) {
                        selection = currentDamage;
                    } else {
                        if (currentWeakness != -1) {
                            selection = currentWeakness;
                        }
                    }

                }
                break;
            case WeaknessDamageMagic:
                currentDamage = -1;
                currentWeakness = -1;
                for (int i = 0; i < moves.size(); i++) {
                    for (Weakness weak : weakness) {
                        if (weak.type.equalsIgnoreCase(moves.get(i).type.type)) {
                            currentWeakness = i;
                        }
                        if (moves.get(i).mDamage > currentHeighest.mDamage) {
                            currentDamage = i;
                            currentHeighest = moves.get(i);
                        }
                    }
                }
                if (currentDamage == currentWeakness && currentDamage != -1) {
                    selection = currentDamage;
                } else {
                    if (MathFunc.randomInt(0, 1) == 0) {
                        selection = currentDamage;
                    } else {
                        if (currentWeakness != -1) {
                            selection = currentWeakness;
                        }
                    }
                }
                break;
        }
        Move selectedMove = moves.get(selection);
        int d = selectedMove.damage;
        int mD = selectedMove.mDamage;
        if (!MathFunc.checkHit(selectedMove, selectedPlayer.evasion)) {
            println(String.format(localize(battle_missed), en));
            ConsoleFunc.wait(2000);
            return;
        }
        int finalD = ((d - selectedPlayer.defense) >= 0 ? (d - selectedPlayer.defense) : 0)
                + ((mD - selectedPlayer.mDefense) >= 0 ? (mD - selectedPlayer.mDefense) : 0);
        if (Weakness.isWeak(selectedPlayer.weaknesses, selectedMove)) {
            finalD *= selectedMove.type.effectiveness;
        }
        int failAmount = 100 - en.luck;
        int[] chances = new int[]{en.luck, failAmount};
        Object[] obj = new Object[]{1, 0};
        if ((int) MathFunc.hatpull(chances, obj) == 1) {
            println(localize(battle_critical));
            finalD += MathFunc.getPercentageOf(en.critDMG, finalD);
            ConsoleFunc.wait(2000);
        }
        selectedPlayer.health -= finalD;
        Graphic graphic = selectedMove.getGraphic();
        BattleEngine.playSound(selectedMove.sound, 1);
        if (graphic != null) {
            for (String s : graphic.frames) {
                print(s);
                ConsoleFunc.wait(graphic.time);
                ConsoleFunc.clear();
            }
        }
        playGraphic(selectedMove);
        println(String.format(localize(battle_moveused), en.name, selectedMove.name, selectedPlayer.name, finalD));
        ConsoleFunc.wait(2000);
    }

    private void Attack() {
        Move[] moves = currentCharacter.getMoves();
        String[] mNames = Move.arrToStr(moves);
        int selection = BattleEngine.inputHandler.doMenu(mNames, currentStatus, true, 0);
        Move selectedMove = moves[selection];
        int d = selectedMove.damage + currentCharacter.attack;
        int mD = selectedMove.mDamage + currentCharacter.mAttack;
        int cost = selectedMove.manaCost;
        if (cost > 0 && currentCharacter.mana < cost) {
            String s = currentCharacter.name.equalsIgnoreCase(BattleEngine.localizationHandler.SECOND_PERSON_STRING) ? localize(battle_missingmana_2ndp) : String.format(localize(battle_missingmana), currentCharacter.name);
            ConsoleFunc.dots(s, 3, 250);
            ConsoleFunc.wait(1500);
            Attack();
        }
        currentCharacter.mana -= cost;
        int target;
        int finalD = 0;
        if (!selectedMove.targetAlly) {
            String[] enNames = new String[ens.length];
            for (int i = 0; i < ens.length; i++) {
                enNames[i] = ens[i].name;
            }
            target = BattleEngine.inputHandler.doMenu(enNames, currentStatus, false, 0);

            if (!MathFunc.checkHit(selectedMove, ens[target].evasion)) {
                println(String.format(localize(battle_missed), currentCharacter.name));
                ConsoleFunc.wait(2000);
                return;
            }

            finalD = ((d - ens[target].defense) >= 0 ? (d - ens[target].defense) : 0)
                    + ((mD - ens[target].mDefense) >= 0 ? (mD - ens[target].mDefense) : 0);
            if (Weakness.isWeak(ens[target].weaknesses, selectedMove)) {
                finalD = (int) (finalD * selectedMove.type.effectiveness);
            }
            int failAmount = 100 - currentCharacter.luck + 1;
            int[] chances = new int[]{currentCharacter.luck + 1, failAmount};
            Object[] obj = new Object[]{true, false};
            if ((boolean) MathFunc.hatpull(chances, obj)) {
                println(localize(battle_critical));
                finalD += MathFunc.getPercentageOf(currentCharacter.critDMG, finalD);
                ConsoleFunc.wait(2000);
            }
            ens[target].health -= finalD;
            playGraphic(selectedMove);
            String name = currentCharacter.name.equalsIgnoreCase(BattleEngine.localizationHandler.SECOND_PERSON_STRING) ? "You" : currentCharacter.name;
            println(String.format(localize(battle_moveused), name, selectedMove.name, ens[target].name, finalD));
            selectedMove.Use(ens[target]);
        } else {
            String[] allyNames = new String[comps.length + 1];
            allyNames[0] = currentCharacter.name.equalsIgnoreCase(BattleEngine.localizationHandler.SECOND_PERSON_STRING) ? "You" : currentCharacter.name;
            for (int i = 1; i < comps.length; i++) {
                allyNames[i] = comps[i].name;
            }
            target = BattleEngine.inputHandler.doMenu(allyNames, currentStatus, false, 0);
            String name = currentCharacter.name.equalsIgnoreCase(BattleEngine.localizationHandler.SECOND_PERSON_STRING) ? "You" : currentCharacter.name;
            println(String.format(localize(battle_moveusedAlly), name, selectedMove.name, ens[target].name, finalD));
            if (target == 0) {
                selectedMove.Use(player);
            } else {
                selectedMove.Use(comps[target]);
            }
        }

        ConsoleFunc.wait(2000);
    }

    private void Item() {
        println("Item");
        if (player.inv.isEmpty()) {
            println(String.format(localize(inv_noitems), player.name));
            ConsoleFunc.wait(2000);
            return;
        }
        String[] m = player.invToStringArr();
        int index = BattleEngine.inputHandler.doMenu(m, currentStatus, true, 0);
        Item selectedItem = player.inv.get(index);
        selectedItem.Use();
        player.inv.remove(selectedItem);
        ConsoleFunc.wait(2000);
    }

    private boolean Run() {
        int amount = ((int) MathFunc.addStatFromArray(ens, StatusArrayPosition.Luck) + 10) - currentCharacter.luck;
        if (amount < 0) {
            amount = 0;
        }
        int[] a = {currentCharacter.luck + 5, amount};
        String[] b = {"run", "failed"};
        String result = (String) MathFunc.hatpull(a, b);
        if (result.equalsIgnoreCase("run")) {
            println(String.format(localize(battle_ran), currentCharacter.name));
        } else {
            println(Colors.RED_BACKGROUND + String.format(localize(battle_failedToRun), currentCharacter.name) + Colors.reset());
            ConsoleFunc.wait(3000);
            return false;
        }
        ConsoleFunc.wait(3000);
        return true;
    }

    private void playGraphic(Move move) {
        BattleEngine.playSound(move.sound, 1);
        Graphic graphic = move.getGraphic();
        if (graphic != null) {
            ConsoleFunc.clear();
            for (String s : graphic.frames) {
                print(s);
                ConsoleFunc.wait(graphic.time);
                ConsoleFunc.clear();
            }
        }
    }

    private boolean checkWon() {
        ArrayList<Enemy> z = new ArrayList<>();

        for (Enemy enemy : ens) {
            if (enemy.health > 0) {
                z.add(enemy);
            } else {
                enemy.onDeath();
            }
        }

        ens = new Enemy[z.size()];
        z.toArray(ens);
        return ens.length == 0;
    }

    private boolean checkDie() {
        if (comps == null) {
            return player.health <= 0;
        }
        Player[] t = new Player[comps.length + 1];
        t[0] = player;
        System.arraycopy(comps, 0, t, 1, comps.length);
        int dead = 0;
        for (Player p : t) {
            if (p.health <= 0) {
                dead++;
            }
        }
        return dead == t.length;
    }

    private int currentIndex = 0;

    private void getTurn() {
        getTurn(0);
    }

    private void getTurn(int amountToInc) {
        currentIndex += amountToInc;
        if (comps == null) {
            currentCharacter = (currentIndex == 0 ? player : null);
            return;
        }
        Player[] t = new Player[comps.length + 1];
        t[0] = player;
        System.arraycopy(comps, 0, t, 1, comps.length);
        try {
            currentCharacter = t[currentIndex];
        } catch (ArrayIndexOutOfBoundsException e) {
            currentIndex = 0;
            currentCharacter = null;
        }
    }
}
