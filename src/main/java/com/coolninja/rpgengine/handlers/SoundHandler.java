package com.coolninja.rpgengine.handlers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

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
     * @param uri
     * @param volume
     * @param repeatTime (1 = play once, and 0 = play until stopSound() is
     * called).
     */
    public void playSound(URI uri, int volume, int repeatTime) {
        if (uri == null) {
            return;
        }
        playSound(new File(uri.toString()), volume, repeatTime);
    }

    public void playSound(File file, int volume, int repeatTime) {
        if (file == null) {
            return;
        }
        try {
            SoundThread t = new SoundThread(file, repeatTime).setVolume(volume);
            if (threads.size() > maxThreads) {
                threads.get(0).end();
            }
            t.play();
            threads.add(t);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(SoundHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * Mutes the sound of a specific thread
     *
     * @param threadIndex
     */
    public void mute(int threadIndex) {
        threads.get(threadIndex).prevVolume = threads.get(threadIndex).volume;
        threads.get(threadIndex).volume = 0;
    }

    /**
     * Unmutes the sound.
     */
    public void unmute() {
        for (SoundThread s : threads) {
            s.volume = s.prevVolume;
        }
    }

    /**
     * Unmutes the sound of a specific thread.
     *
     * @param threadIndex
     */
    public void unmute(int threadIndex) {
        threads.get(threadIndex).volume = threads.get(threadIndex).prevVolume;
    }

    public class SoundThread extends Thread {

        public File file;
        public int repeatTime;
        public int volume;
        protected int prevVolume;

        private Clip clip;
        private AudioInputStream audioStream;

        public SoundThread(File file, int repeatTime) throws UnsupportedAudioFileException, IOException {
            super("SoundThread");
            this.audioStream = AudioSystem.getAudioInputStream(file);
            this.file = file;
            this.repeatTime = repeatTime;
        }

        @Override
        public void run() {

        }

        public void play() throws LineUnavailableException, IOException {
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
