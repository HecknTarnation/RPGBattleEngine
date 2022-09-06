package example.sounds;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.ConsoleFunc;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 *
 * @author Ben
 */
public class SoundsMain {

    //This tutotial will show you how to do sounds.
    public static void main(String[] args) throws URISyntaxException, IOException {
        BattleEngine.init();

        //File to play
        InputStream stream = new BufferedInputStream(SoundsMain.class.getClassLoader().getResourceAsStream("example/sounds/test.wav"));

        //You can also pass a file object into the playSound method
        //The first argument is a URI/File/InputStream that points to the file you wish to play.
        //The second one is the amount of times to play the audio. -1 = until stopSound is played.
        BattleEngine.playSound(stream, 0);
        System.out.println("Playing...");
        ConsoleFunc.wait(10000);
    }

}
