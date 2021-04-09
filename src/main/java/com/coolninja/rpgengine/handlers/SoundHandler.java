package com.coolninja.rpgengine.handlers;

import java.net.URI;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class SoundHandler {

    /**
     * An array of all sound threads
     */
    public ArrayList<SoundThread> threads;

    /**
     * Maximum Threads playing at once (if this is reached, and a new one is
     * created, it will destroy the oldest one (the first one in the list).
     */
    public int maxThreads = 3;

    public void init() {
        threads = new ArrayList<>();
    }

    /**
     * Sets the maximum amount of threads. Make sure to be careful of the
     * dreaded OutOfMemory error that can occur if too many threads exist.
     *
     * @param max
     * @return
     */
    public SoundHandler setMaxThreads(int max) {
        this.maxThreads = max;
        return this;
    }

    /**
     * Starts a thread to play a sound, provided in the passed file, at the
     * specified volume for the specified amount of time.
     *
     * @param file
     * @param volume
     * @param repeatTime (1 = play once, and 0 = play until stopSound() is
     * called).
     */
    public void playSound(URI file, int volume, int repeatTime) {
        SoundThread t = new SoundThread(file, repeatTime).setVolume(volume);
        if (threads.size() > maxThreads) {
            threads.get(0).end();
        }
        t.play();
        threads.add(t);
    }

    /**
     * Stops a sound from playing.
     *
     * @param index (the index in the "threads" array)
     */
    public void stopSound(int index) {
        threads.get(index).end();
    }

    /**
     * Stops a sound from playing
     *
     * @param thread (a copy of the thread to stop)
     */
    public void stopSound(SoundThread thread) {
        threads.get(threads.indexOf(thread)).end();
    }

    /**
     * Mutes the sound
     */
    public void mute() {
        for (SoundThread s : threads) {
            s.prevVolume = s.volume;
            s.volume = 0;
        }
    }

    /**
     * Unmutes the sound.
     */
    public void unmute() {
        for (SoundThread s : threads) {
            s.volume = s.prevVolume;
        }
    }

    public class SoundThread extends Thread {

        public URI file;
        public int repeatTime;
        public int volume;
        protected int prevVolume;

        public SoundThread(URI file, int repeatTime) {
            super("SoundThread");
            this.file = file;
            this.repeatTime = repeatTime;
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                //TODO: sound code
            }
        }

        public void play() {
            //TODO: set up sound variables
            this.start();
        }

        public void end() {
            this.interrupt();
        }

        public SoundThread setVolume(int vol) {
            this.volume = vol;
            return this;
        }

    }
}
