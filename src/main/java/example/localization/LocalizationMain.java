package example.localization;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.exceptions.StringAlreadyDefinedException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class LocalizationMain {

    //This example will teach you how to use the localization system for this engine.
    //By default, this engine supports American English (en_us).
    public static void main(String[] args) {
        BattleEngine.init();

        //To switch the languague
        BattleEngine.switchLang("en_us");
        //Or
        BattleEngine.localizationHandler.changLang("en_us");

        //You can get a localized string by running the following.
        BattleEngine.localizationHandler.getLocalizedString("string.key");

        try {
            //You can add new strings.
            BattleEngine.localizationHandler.addString("string.key", "Hello :)");
            //An exception can be thrown if the key is already in use.
        } catch (StringAlreadyDefinedException ex) {
            Logger.getLogger(LocalizationMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        //You can more languages.
        HashMap<String, String> myLang = new HashMap<>();
        myLang.put("string.key", "Hello :)");

        BattleEngine.localizationHandler.addLang("key", myLang);
        BattleEngine.switchLang("key");
        System.out.println(BattleEngine.localizationHandler.getLocalizedString("string.key"));
    }

}
