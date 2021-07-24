package com.coolninja.rpgengine.Cons;

/**
 *
 * @author Ben
 */
public class Graphic {

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
