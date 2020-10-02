package coolninja.rpg;

import coolninja.rpg.Console.Console;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 * Handles playing sound
 *
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class SoundHandler extends Thread {

    public URI pathToSound;
    public boolean shouldLoop;

    public Clip audio;
    private AudioInputStream input;

    public static ArrayList<SoundHandler> handlers = new ArrayList<>();

    /**
     * Sets up thread
     *
     * @since 1.0
     * @param pathToSound
     * @param shouldLoop
     */
    public SoundHandler(URI pathToSound, boolean shouldLoop) {
        if (pathToSound == null) {
            return;
        }
        this.pathToSound = pathToSound;
        this.shouldLoop = shouldLoop;
        SoundHandler.handlers.add(this);
    }

    /**
     * Starts playing sound
     *
     * @since 1.0
     */
    @Override
    public void run() {
        if (Vars.mute) {
            return;
        }
        File file = new File(pathToSound.getPath());

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
            Console.printError("System couldn't play audio.", 1);
            return;
        }

        try {
            audio.open(input);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        if (shouldLoop) {
            audio.loop(Clip.LOOP_CONTINUOUSLY);
        }
        audio.start();
        while (audio.isRunning() && !this.isInterrupted()) {
            try {
                this.wait(1);
            } catch (InterruptedException e) {
            }
        }

    }

    public void end() throws InterruptedException {
        if (Vars.mute) {
            return;
        }
        handlers.remove(this);
        try {
            if (audio.isOpen()) {
                audio.close();
            }
        } catch (Exception e) {
            this.join();
        }
        this.interrupt();
    }

    /**
     * Ends all currently running sounds
     *
     * @since 1.0
     */
    public static void endAll() {
        handlers.forEach((handler) -> {
            try {
                handler.end();
                handlers.remove(handler);
            } catch (InterruptedException ex) {
                Logger.getLogger(SoundHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
