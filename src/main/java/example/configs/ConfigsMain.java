package example.configs;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.Colors;
import com.heckntarnation.rpgbattleengine.Vars;
import java.util.HashMap;

/**
 *
 * @author Ben
 */
public class ConfigsMain {

    //This example will show you the different settings you can change.
    public static void main(String[] args) {
        BattleEngine.init();

        //Many of the engine's settings are stored in the 'Vars' class which you can freely edit at any time.
        //Controls
        //You can edit the keyboard controls by setting 'Vars.Controls'.
        HashMap<Vars.ControlMapping, Integer> controls = new HashMap<>();
        //This sets the 'up' button to the up arrow.
        controls.put(Vars.ControlMapping.Up, NativeKeyEvent.VC_UP);
        //This sets the 'select' button to the backslash key.
        controls.put(Vars.ControlMapping.Submit, NativeKeyEvent.VC_BACK_SLASH);
        Vars.Controls = controls;

        //Text colors
        //You can change the colors used for text.
        //This will change the 'Selected_Color' (which is used when a menu option is selected) to 'hey', which isn't a valid color.
        //This engine uses ANSI colors. The 'Colors' class has some pre-definded colors that you can use.
        Vars.Selected_Color = "hey";

        Vars.Selected_Color = Colors.BLACK;

        //You can also set 'enemy' and 'ally' colors, which will determine how the names will show up in battles.
        //Characters
        //You can set the player object and the companions here as well.
        //You can also set how many skillpoints a character gets on level up, which can be used to upgrade any stat by 1. 
        //Setting it to 0 will disable skillpoints entirely.
        Vars.skillPointsOnLevel = 0;
        //You can also set the max level for characters/
        Vars.maxLevel = 10;

        //Audio
        //You can disable audio by setting the 'disableAudio' boolean to true.
        Vars.disableAudio = true;

        //You can change the amount of threads the, built-in, sound handler can use. Each thread equals one audio track that can play simultaneously. The default is 3.
        Vars.maxThreads = 100;
        //Beware, each thread consumes a decent amount of memory.
        //Don't use too many or you might run out of memory, if this is an issue you could always dedicated more memory to the JVM.
        //Of course, every computer has a different amount of memory/ram, make sure to be careful.
    }

}
