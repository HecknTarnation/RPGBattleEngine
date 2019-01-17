package coolninja.rpg;

import java.net.URI;

import coolninja.rpg.Battle.LoseBattle;
import coolninja.rpg.Cons.Companion;
import coolninja.rpg.Required.Player;

/**
 * Used for setting varibles used in the engine
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class SetVars {
    
    /**
     * Sets the main player
     * @since 1.0
     */
    public static void setPlayer(Player player){
        Vars.player = player;
    }
    
    /**
     * Sets the main player's compainions
     * @since 1.0
     */
    public static void setCompainions(Companion[] comps){
        Vars.comps = comps;
    }
    
    /**
     * Sets the text displayed when a battle is won
     * @since 1.0
     */
    public static void setWinText(String text){
        Vars.winText = text;
    }
    
    /**
     * Sets the color code for the compainions on battle
     * @since 1.0
     */
    public static void setCompColor(String code){
        Vars.compColorCode = code;
    }
    
    /**
     * Sets the color code for the enemies on battle
     * @since 1.0
     */
    public static void setEnemyColor(String code){
        Vars.enemyColorCode = code;
    }
    
    /**
     * Sets if the text should scroll when leveling up
     * @since 1.0
     */
    public static void setShouldScroll(boolean ss){
        Vars.shouldScroll = ss;
    }
    
    /**
     * Sets default battle music
     * @since 1.0
     */
    public static void setBattleMusic(URI locationToSound){
        Vars.defaultBattleSoundLocation = locationToSound;
    }
    
    /**
     * Sets win music
     * @since 1.0
     */
    public static void setWinMusic(URI locationToSound){
        Vars.winMusic = locationToSound;
    }
    
    /**
     * Sets lose music
     * @since 1.0
     */
    public static void setLoseMusic(URI locationToSound){
        Vars.loseMusic = locationToSound;
    }
    
    /**
     * Sets lose battle function
     * @since 1.0
     */
    public static void setLoseFunction(LoseBattle classThatImplements){
        Vars.loseBattle = classThatImplements;
    }
}
