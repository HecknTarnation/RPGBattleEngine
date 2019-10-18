package coolninja.rpg.Cons;

import java.lang.reflect.Field;

import coolninja.rpg.Battle.BattleHandler;

/**
 * A class that gets all fields from the BattleHandler class <br>
 * All Fields: <br>
 * private static Enemy[] enemies;
 * private static Player player;
 * private static Companion[] comps;
 * private static Player currentPlayer;
 * private static byte prevIndex;
 * private static int expVal = 0;
 * private static Item[] eDrops;
 * private static Enemy[] enArchive;
 * @since 1.0
 * @version 1.0
 * @author Ben Ballard
 *///extends BattleHandler so that it may access the protected variables
public class BattleState extends BattleHandler{
    
    public Field[] fields;
    /**
     * The BattleState constructor
     */
    public BattleState(){
        fields = BattleHandler.class.getClass().getFields();
        System.out.println(fields.length);
    }
    
}
