package example.statuseffects;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.Vars;
import com.heckntarnation.rpgbattleengine.arrays.StatusArray;
import com.heckntarnation.rpgbattleengine.cons.Battle.Move;
import com.heckntarnation.rpgbattleengine.cons.Characters.Enemy;
import com.heckntarnation.rpgbattleengine.enums.AILevel;
import com.heckntarnation.rpgbattleengine.enums.StatusArrayPosition;
import example.basicbattle.MyDeathHandler;
import example.basicbattle.MyEnemy;
import example.basicbattle.MyPlayer;

/**
 *
 * @author Ben
 */
public class StatusEffectMain {

    //This will example shows how to set up and use status effects.
    public static void main(String[] args) {
        //Copying the code from basicbattle.
        BattleEngine.localizationHandler.changLang("en_us");
        BattleEngine.init(null, null, null, null, new MyDeathHandler());

        MyPlayer player = new MyPlayer("Ben", 10, 1);
        Vars.player = player;
        MyEnemy enemy = new MyEnemy("Enemy", 5, 10);
        StatusArray stats = new StatusArray();
        stats.put(StatusArrayPosition.ATK, 1);
        stats.put(StatusArrayPosition.DEF, 2);
        enemy.setStats(stats);
        enemy.aiLevel = AILevel.Random;
        player.setStats(stats);
        Enemy[] ens = new Enemy[]{enemy};
        stats.put(StatusArrayPosition.Health, 10);
        stats.put(StatusArrayPosition.MaxHealth, 10);
        Move move = new Move("Smack");
        move.setDamage(10, 0);

        player.addMove(move);
        enemy.addMove(move);

        player.setStats(stats);

        //Creating a custom move class, so that we can override the "Use" method.
        MyMove move2 = new MyMove("Status Effect");
        move2.setDamage(0, 0);
        player.moves.add(move2);

        BattleEngine.battleHandler.startBattle(true, ens);
    }

}
