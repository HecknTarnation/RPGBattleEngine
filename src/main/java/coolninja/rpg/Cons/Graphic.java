package coolninja.rpg.Cons;

/**
 * The graphic class
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class Graphic {
    
    public String[] frames;
    public int waitTime;
    
    /**
     * Creates a new Graphic object
     * @param frames
     * @param waitTime
     */
    public Graphic(String[] frames, int waitTime){
        this.frames = frames;
        this.waitTime = waitTime;
    }
    
}
