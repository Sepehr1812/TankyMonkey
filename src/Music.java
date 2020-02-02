import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;


/**
 * playing music for game.
 *
 * @author Sepeh Akhoundi & Farbod Rasaei
 * @version 1.0.0
 */
public class Music implements Serializable {
    @SuppressWarnings("Duplicates")
    private

    AudioInputStream audio5;
    private Clip clip5;

    public void start() {
        try {
            audio5 = AudioSystem.getAudioInputStream(new File("Music/1.wav"));

            clip5 = AudioSystem.getClip();
            clip5.open(audio5);
            clip5.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void toBakhti() {
        try {
            audio5 = AudioSystem.getAudioInputStream(new File("Music/toBakhti.wav"));

            clip5 = AudioSystem.getClip();
            clip5.open(audio5);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void damet() {
        try {
            audio5 = AudioSystem.getAudioInputStream(new File("Music/dametGarm.wav"));

            clip5 = AudioSystem.getClip();
            clip5.open(audio5);
            clip5.start();
            try {
                Thread.sleep(10001);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clip5.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

    }
}
