package example.basicbattle;

import com.heckntarnation.rpgbattleengine.handlers.DeathHandler;

/**
 *
 * @author Ben
 */
public class MyDeathHandler extends DeathHandler {

    //It is very important that you override the default death handler. I will explain more whym but for now this will do.
    @Override
    public void OnDeath() {
        System.out.println("Oh no!");
    }

}
