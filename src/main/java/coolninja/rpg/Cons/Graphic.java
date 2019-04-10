package coolninja.rpg.Cons;

import java.io.Serializable;

/**
 * The graphic class
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class Graphic implements Serializable{
    
    static final long serialVersionUID = 5;
    
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
