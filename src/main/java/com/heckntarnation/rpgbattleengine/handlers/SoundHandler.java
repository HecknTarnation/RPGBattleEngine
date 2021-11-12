package com.heckntarnation.rpgbattleengine.handlers;

import com.heckntarnation.rpgbattleengine.Vars;
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
     * @param repeatTime (1 = repeat once, and -1 = play until stopSound() is
     * called).
     */
    public void playSound(URI uri, int repeatTime) {
        if (uri == null) {
            return;
        }
        playSound(new File(uri.toString()), repeatTime);
    }

    public void playSound(File file, int repeatTime) {
        if (file == null) {
            return;
        }
        try {
            SoundThread t = new SoundThread(file, repeatTime);
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
        threads.forEach(s -> {
            s.mute();
        });
    }

    /**
     * Mutes the sound of a specific thread
     *
     * @param threadIndex
     */
    public void mute(int threadIndex) {
        threads.get(threadIndex).mute();
    }

    /**
     * Unmutes the sound.
     */
    public void unmute() {
        threads.forEach(s -> {
            s.unmute();
        });
    }

    /**
     * Unmutes the sound of a specific thread.
     *
     * @param threadIndex
     */
    public void unmute(int threadIndex) {
        threads.get(threadIndex).unmute();
    }

    public class SoundThread extends Thread {

        public File file;
        public int repeatTime;
        public boolean muted;

        private Clip audio;
        private AudioInputStream input;

        public SoundThread(File file, int repeatTime) throws UnsupportedAudioFileException, IOException {
            super("SoundThread");
            this.input = AudioSystem.getAudioInputStream(file);
            this.file = file;
            this.repeatTime = repeatTime;
        }

        @Override
        public void run() {
            if (Vars.disableAudio) {
                return;
            }

            input = null;
            try {
                input = AudioSystem.getAudioInputStream(file);
            } catch (UnsupportedAudioFileException | IOException e) {
            }
            AudioFormat format = input.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            audio = null;
            try {
                audio = (Clip) AudioSystem.getLine(info);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return;
            }

            try {
                audio.open(input);
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            audio.loop(repeatTime);
            audio.start();
            while (audio.isRunning() && !this.isInterrupted()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
        }

        public void play() throws LineUnavailableException, IOException {
            this.start();
        }

        public void end() {
            this.interrupt();
        }

        public void mute() {
            this.muted = true;
            audio.stop();
        }

        public void unmute() {
            this.muted = false;
            audio.start();
        }

    }
}
