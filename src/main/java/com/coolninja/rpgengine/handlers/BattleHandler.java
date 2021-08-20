package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.Colors;
import com.coolninja.rpgengine.Cons.*;
import com.coolninja.rpgengine.ConsoleFunc;
import com.coolninja.rpgengine.Engine;
import com.coolninja.rpgengine.MathFunc;
import com.coolninja.rpgengine.Vars;
import static com.coolninja.rpgengine.dev.Macros.*;
import static com.coolninja.rpgengine.enums.LangKeys.*;
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
    private int expVal = 0;

    private Player currentPlayer;
    private String currentStatus;

    public void startBattle(Enemy... en) {
        ens = new Enemy[en.length];
        for (int i = 0; i < ens.length; i++) {
            ens[i] = en[i].clone();
        }
        this.enArchive = ens.clone();
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
            if (checkDie()) {
                break;
            }
            String str = "";
            for (Enemy en : ens) {
                str += Vars.Enemy_Color + en.name + ": " + en.health + "/" + en.maxHealth + Colors.reset() + "\n";
            }
            if (player.health > 0) {
                str += "\n" + Vars.Ally_Color + player.name + ": " + player.health + "/" + player.maxHealth + Colors.reset() + "\n";
            } else {
                str += "\n" + Colors.RED_BACKGROUND + player.name + ": DEAD" + Colors.reset() + "\n";
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
            if (currentPlayer != null) {
                while (currentPlayer != null && currentPlayer.health <= 0) {
                    getTurn(1);
                }
            }
            if (currentPlayer == null) {
                EnemyTurn();
                player.statusEffectTick();
                for (Companion comp : comps) {
                    comp.statusEffectTick();
                }
                for (Enemy en : ens) {
                    en.statusEffectTick();
                }
                continue;
            }

            str += String.format(localize(battle_currentTurn), currentPlayer.name.equalsIgnoreCase("you") ? localize(gen_your) : currentPlayer.name) + "\n";
            currentStatus = str;
            String m = localize(battle_menu);
            String[] menu = m.split(",");
            int selection = Engine.inputHandler.doMenu(menu, str, true);

            switch (selection) {
                case 0:
                    Attack();
                    break;
                case 1:
                    Item();
                    break;
                case 2:
                    ConsoleFunc.dots(String.format(localize(battle_idle), currentPlayer.name), 3, 300);
                    ConsoleFunc.wait(1000);
                    break;
                case 3:
                    Run();
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
        if (!won) {
            System.out.println("[---" + Colors.RED + Colors.BLACK_BACKGROUND + localize(battle_gameover) + Colors.reset() + "---]");
            ConsoleFunc.wait(5000);
            Engine.deathHandler.OnDeath();
        }
        ens = null;
        player = null;
        comps = null;
        currentIndex = 0;
        currentPlayer = null;
        currentStatus = null;

        System.out.println("Battle Ended");
        for (Enemy e : enArchive) {
            Drop d = e.getDrop();
            if (d != null) {
                player.inv.add(d.getItem());
                println(String.format(localize(battle_gotitem), player.name, d.getItem().name));
            }
        }
        Vars.player.levelUp(expVal);
        for (Companion c : Vars.companions) {
            c.levelUp(expVal);
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
        Move[] moves = en.moves;
        Move currentHeighest = new Move("").setDamage(0, 0);
        ArrayList<Player> aliveArr = new ArrayList<>();
        if (player.health > 0) {
            aliveArr.add(player);
        }
        for (int i = 0; i < comps.length; i++) {
            if (comps[i].health > 0) {
                aliveArr.add(comps[i]);
            }
        }
        Player[] t = new Player[aliveArr.size()];
        t = aliveArr.toArray(t);
        int temp = MathFunc.randomInt(0, t.length - 1);
        Player selectedPlayer = t[temp];

        ArrayList<Weakness> weakness = selectedPlayer.weakness;

        //Will default to the first move in moves if none matching the criteria are found.
        int selection = 0;
        switch (en.aiLevel) {
            case Random:
                int index = MathFunc.randomInt(0, moves.length - 1);
                index = index > moves.length - 1 ? moves.length - 1 : index;
                selection = index > 0 ? index : 0;
                break;
            case Damage:
                for (int i = 0; i < moves.length; i++) {
                    if (moves[i].damage > currentHeighest.damage) {
                        currentHeighest = moves[i];
                        selection = i;
                    }
                }
                break;
            case DamageMagic:
                for (int i = 0; i < moves.length; i++) {
                    if (moves[i].mDamage > currentHeighest.mDamage) {
                        currentHeighest = moves[i];
                        selection = i;
                    }
                }
                break;
            case Weakness:
                //TODO: adjust all of these
                for (int i = 0; i < moves.length; i++) {
                    for (Weakness weak : weakness) {
                        if (moves[i].type.equalsIgnoreCase(weak.type)) {
                            selection = i;
                            break;
                        }
                    }
                }
                break;
            case WeaknessDamage:
                int currentDamage = -1;
                int currentWeakness = -1;
                for (int i = 0; i < moves.length; i++) {
                    for (Weakness weak : weakness) {
                        if (weak.type.equalsIgnoreCase(moves[i].type)) {
                            currentWeakness = i;
                        }
                        if (moves[i].damage > currentHeighest.damage) {
                            currentDamage = i;
                            currentHeighest = moves[i];
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
                for (int i = 0; i < moves.length; i++) {
                    for (Weakness weak : weakness) {
                        if (weak.type.equalsIgnoreCase(moves[i].type)) {
                            currentWeakness = i;
                        }
                        if (moves[i].mDamage > currentHeighest.mDamage) {
                            currentDamage = i;
                            currentHeighest = moves[i];
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
        Move selectedMove = moves[selection];
        int d = selectedMove.damage;
        int mD = selectedMove.mDamage;
        if (!MathFunc.checkHit(selectedMove)) {
            println(String.format(localize(battle_missed), en));
            ConsoleFunc.wait(2000);
            return;
        }
        int finalD = ((d - selectedPlayer.defense) >= 0 ? (d - selectedPlayer.defense) : 0)
                + ((mD - selectedPlayer.mDefense) >= 0 ? (mD - selectedPlayer.mDefense) : 0);
        int failAmount = 100 - en.luck;
        int[] chances = new int[]{en.luck, failAmount};
        Object[] obj = new Object[]{1, 0};
        if ((int) MathFunc.hatpull(chances, obj) == 1) {
            println(localize(battle_critical));
            finalD = (finalD * en.luck) / (en.luck - MathFunc.randomInt(0, en.luck > 0 ? en.luck - 2 : 1));
            ConsoleFunc.wait(2000);
        }
        selectedPlayer.health -= finalD;
        Graphic graphic = selectedMove.getGraphic();
        Engine.playSound(selectedMove.sound, 1, 1);
        if (graphic != null) {
            for (String s : graphic.frames) {
                print(s);
                ConsoleFunc.wait(graphic.time);
                ConsoleFunc.clear();
            }
        }
        println(String.format(localize(battle_moveused), en.name, selectedMove.name, selectedPlayer.name, finalD));
        ConsoleFunc.wait(2000);
    }

    private void Attack() {
        Move[] moves = currentPlayer.getMoves();
        String[] mNames = Move.arrToStr(moves);
        int selection = Engine.inputHandler.doMenu(mNames, currentStatus, true);
        Move selectedMove = moves[selection];
        int d = selectedMove.damage + currentPlayer.attack;
        int mD = selectedMove.mDamage + currentPlayer.mAttack;
        int cost = selectedMove.manaCost;
        if (cost > 0 && currentPlayer.mana < cost) {
            String s = currentPlayer.name.equalsIgnoreCase(localize(gen_firstppronoun)) ? localize(battle_missingmana_1stp) : String.format(localize(battle_missingmana), currentPlayer.name);
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
        target = Engine.inputHandler.doMenu(enNames, currentStatus, false);

        if (!MathFunc.checkHit(selectedMove)) {
            println(String.format(localize(battle_missed), currentPlayer.name));
            ConsoleFunc.wait(2000);
            return;
        }

        int finalD = ((d - ens[target].defense) >= 0 ? (d - ens[target].defense) : 0)
                + ((mD - ens[target].mDefense) >= 0 ? (mD - ens[target].mDefense) : 0);
        int failAmount = 100 - currentPlayer.luck;
        int[] chances = new int[]{currentPlayer.luck + 1, failAmount};
        Object[] obj = new Object[]{1, 0};
        if ((int) MathFunc.hatpull(chances, obj) == 1) {
            println(localize(battle_critical));
            finalD = (finalD * currentPlayer.luck) / (currentPlayer.luck - MathFunc.randomInt(0, currentPlayer.luck > 0 ? currentPlayer.luck - 2 : 1));
            ConsoleFunc.wait(2000);
        }
        ens[target].health -= finalD;
        Graphic graphic = selectedMove.getGraphic();
        Engine.playSound(selectedMove.sound, 1, 1);
        if (graphic != null) {
            ConsoleFunc.clear();
            for (String s : graphic.frames) {
                print(s);
                ConsoleFunc.wait(graphic.time);
                ConsoleFunc.clear();
            }
        }
        println(String.format(localize(battle_moveused), currentPlayer.name, selectedMove.name, ens[target].name, finalD));
        selectedMove.Use();
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
        int index = Engine.inputHandler.doMenu(m, currentStatus, true);
        Item selectedItem = player.inv.get(index);
        selectedItem.Use();
        player.inv.remove(selectedItem);
        ConsoleFunc.wait(2000);
    }

    private void Run() {
        println("Run");
        System.exit(0);
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
        Player[] t = new Player[comps.length + 1];
        t[0] = player;
        for (int i = 0; i < comps.length; i++) {
            t[i + 1] = comps[i];
        }
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
            currentPlayer = (currentIndex == 0 ? player : null);
            return;
        }
        Player[] t = new Player[comps.length + 1];
        t[0] = player;
        for (int i = 0; i < comps.length; i++) {
            t[i + 1] = comps[i];
        }
        try {
            currentPlayer = t[currentIndex];
        } catch (ArrayIndexOutOfBoundsException e) {
            currentIndex = 0;
            currentPlayer = null;
        }
    }
}
