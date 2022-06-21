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
            "Configs"
        };
        int index = BattleEngine.inputHandler.doMenu(menu, "Select example to run.", true);
        switch (index) {
            case 0: {
                example.basicbattle.BasicBattleMain.main(args);
            }
            case 1: {
                example.configs.ConfigsMain.main(args);
            }
            case 2: {
                example.localization.LocalizationMain.main(args);
            }
        }

        System.exit(0);
    }

}
