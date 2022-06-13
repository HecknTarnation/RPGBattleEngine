package example;

import com.heckntarnation.rpgbattleengine.BattleEngine;

/**
 *
 * @author Ben
 */
public class examples {

    public static void main(String[] args) {
        BattleEngine.init();
        System.out.println("Select example to run.");
        String[] menu = new String[]{
            "Basic Battle",
            "empty"
        };
        int index = BattleEngine.inputHandler.doMenu(menu, "Select example to run.", true);
        switch (index) {
            case 0: {
                example.basicbattle.Main.main(null);
            }
        }

        System.exit(0);
    }

}
