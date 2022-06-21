package example.basicbattle;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.Vars;
import com.heckntarnation.rpgbattleengine.arrays.StatusArray;
import com.heckntarnation.rpgbattleengine.cons.Battle.Move;
import com.heckntarnation.rpgbattleengine.cons.Characters.Enemy;
import com.heckntarnation.rpgbattleengine.enums.StatusArrayPosition;

/**
 *
 * @author Ben
 */
public class BasicBattleMain {

    /*
    There are a few steps that must be taken before you can begin a battle.
    First you must create a 'player' object and set Vars.player equal to it. This object will represent the player.
     */
    public static void main(String[] args) {
        //We need to initilize the battle engine by calling BattleEngine.init()
        //This step is skipped here because it has already been called in the 'examples' class.

        //We also need to set the current language. In this case, American English (en_us).
        BattleEngine.localizationHandler.changLang("en_us");

        //Let's also override the dealth handler by calling BattleEngine.init().
        BattleEngine.init(null, null, null, null, new MyDeathHandler(), null, null);

        MyPlayer player = new MyPlayer("Ben", 10, 1);
        Vars.player = player;

        //Next we need to actually create enemies for the player to fight.
        MyEnemy enemy = new MyEnemy("Enemy", 5, 10);

        //Now the next things to do is to set the enemies stats. We do this using a StatusArray.
        StatusArray stats = new StatusArray();
        //You can sets stats using the 'put' method. It will take a StatusArrayPosition and an object to put.
        stats.put(StatusArrayPosition.ATK, 1);
        stats.put(StatusArrayPosition.DEF, 2);

        //Now let's set the stats
        enemy.setStats(stats);

        //Our enemy now has an attack of 1 and a defense of 2. All stats start at 0, which means the enemy currently has
        //a magical attack and defense of 0.
        //The player, and companions, also use StatusArrays for their stats. Let's just use the same one as the enemy for now.
        player.setStats(stats);

        //The start battle method expects an enemy array, so let's make one.
        Enemy[] ens = new Enemy[]{enemy};

        //Now that is it. We can start a battle by calling the following method.
        //The boolean (canRun) determens if the prompt for running is available to the player.
        BattleEngine.battleHandler.startBattle(true, ens);

        //Now if you run this, you will realize it will cause an instant game over. This is because the player's health defaults to 0.
        //You can fix this by putting a health value into the status array.
        stats.put(StatusArrayPosition.Health, 10);
        stats.put(StatusArrayPosition.MaxHealth, 10);

        //We also need to create a move for the player to use.
        Move move = new Move("Smack");
        //and sets the damage, this move will do 10 normal damage and 0 magic damage.
        move.setDamage(10, 0);

        player.moves.add(move);

        player.setStats(stats);

        //Now let's try again.
        BattleEngine.battleHandler.startBattle(true, ens);
    }

}
