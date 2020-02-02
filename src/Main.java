import javax.swing.*;
import java.awt.*;

/**
 * Main class. The game happens here.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        //Plays staring music and create starting menu.
        Music music = new Music();
        music.start();
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...
        Menu.showMainMenu();

        // After the player clicks 'PLAY' ...

        EventQueue.invokeLater(() -> {

            //Create main frame that game run.
            MainFrame frame = new MainFrame("TankyMonkey!");
            frame.setIconImage(new ImageIcon("Images/Icon.png").getImage());
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.initBufferStrategy();
            // Create and execute the game-loop
            GameLoop game = new GameLoop(frame);
            game.init();
            ThreadPool.execute(game);
            // and the game starts ...
        });
    }
}
