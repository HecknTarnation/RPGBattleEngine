package com.heckntarnation.rpgbattleengine.Cons;

/**
 *
 * @author Ben
 */
public class Graphic {

    /**
     * For JSON loading
     */
    public String namespace, id;

    public String[] frames;
    public short time;

    public Graphic() {

    }

    public Graphic(String[] frames, short time) {
        this.setFrames(frames);
        this.setTime(time);
    }

    public Graphic setFrames(String[] frames) {
        this.frames = frames;
        return this;
    }

    public Graphic setTime(short time) {
        this.time = time;
        return this;
    }

    public Graphic setTime(int time) {
        this.time = (short) time;
        return this;
    }

}
