package example.statuseffects;

import com.heckntarnation.rpgbattleengine.MathFunc;
import com.heckntarnation.rpgbattleengine.cons.Battle.Move;
import com.heckntarnation.rpgbattleengine.cons.Characters.Enemy;

/**
 *
 * @author Ben
 */
public class MyMove extends Move {

    //damage, then duration (turns)
    MyStatusEffect effect = new MyStatusEffect(10, 2);
    
    public MyMove(String name) {
        super(name);
        this.setTargetAlly(false);
    }
    
    @Override
    public void Use(Object target) {
        Enemy en = (Enemy) target;
        int[] amountOfEach = {9, 1};
        Object[] elements = {true, false};
        boolean result = (boolean) MathFunc.hatpull(amountOfEach, elements);
        if (result) {
            System.out.println("Success");
            en.addStatusEffect(effect);
        } else {
            System.out.println("Failed");
        }
    }
    
}
