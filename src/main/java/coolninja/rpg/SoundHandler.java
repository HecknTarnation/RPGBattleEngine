package coolninja.rpg;

import coolninja.rpg.Console.Console;
import java.io.File;
import java.io.IOException;
import java.net.URI;
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

        if (shouldLoop) {
            audio.loop(Clip.LOOP_CONTINUOUSLY);
        }

        try {
            audio.open(input);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        audio.start();
        while (audio.isRunning() && !this.isInterrupted()) {
            try {
                this.wait(1);
            } catch (InterruptedException e) {
            }
        }

    }

    public void end() {
        if (Vars.mute) {
            return;
        }
        if (audio.isOpen()) {
            audio.close();
        }

        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.interrupt();
    }
}
