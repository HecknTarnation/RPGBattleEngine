package coolninja.rpg;

import java.net.URI;

import coolninja.rpg.Required.LoseBattle;
import coolninja.rpg.Cons.Companion;
import coolninja.rpg.Required.Player;

/**
 * Used for setting variables used in the engine
 * (You can also manual set them by calling Vars.[variable] = [value])
 * @version 1.1
 * @since 1.0
 * @author Ben Ballard
 */
public class SetVars {
    
    /**
     * Sets the main player
     * @param player
     * @since 1.0
     */
    public static void setPlayer(Player player){
        Vars.player = player;
    }
    
    /**
     * Sets the main player's companions
     * @param comps
     * @since 1.0
     */
    public static void setCompainions(Companion[] comps){
        Vars.comps = comps;
    }
    
    /**
     * Sets the text displayed when a battle is won
     * @param text
     * @since 1.0
     */
    public static void setWinText(String text){
        Vars.winText = text;
    }
    
    /**
     * Sets the color code for the companions on battle
     * @param code
     * @since 1.0
     */
    public static void setCompColor(String code){
        Vars.compColorCode = code;
    }
    
    /**
     * Sets the color code for the enemies on battle
     * @param code
     * @since 1.0
     */
    public static void setEnemyColor(String code){
        Vars.enemyColorCode = code;
    }
    
    /**
     * Sets if the text should scroll when leveling up
     * @param ss
     * @since 1.0
     */
    public static void setShouldScroll(boolean ss){
        Vars.shouldScroll = ss;
    }
    
    /**
     * Sets default battle music
     * @param locationToSound
     * @since 1.0
     */
    public static void setBattleMusic(URI locationToSound){
        Vars.defaultBattleSoundLocation = locationToSound;
    }
    
    /**
     * Sets win music
     * @param locationToSound
     * @since 1.0
     */
    public static void setWinMusic(URI locationToSound){
        Vars.winMusic = locationToSound;
    }
    
    /**
     * Sets lose music
     * @param locationToSound
     * @since 1.0
     */
    public static void setLoseMusic(URI locationToSound){
        Vars.loseMusic = locationToSound;
    }
    
    /**
     * Sets lose battle function
     * @param classThatImplements
     * @since 1.0
     */
    public static void setLoseFunction(LoseBattle classThatImplements){
        Vars.loseBattle = classThatImplements;
    }
    
    /**
     * If true, the player's luck state will be used to give extra damage when using a move
     * @since 1.0
     * @param value
     */
    public static void setShoudlUseLuck(boolean value){
        Vars.shouldUseLuckForExtraDamage = value;
    }
    
    /**
     * Mutes/Unmutes audio
     * (Do not set this during a battle! It will cause an NullPointerException)
     * @since 1.1
     * @param shouldMute
     */
    public static void mute(boolean shouldMute){
        Vars.mute = shouldMute;
    }
}
