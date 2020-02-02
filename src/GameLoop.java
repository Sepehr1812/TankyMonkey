import javax.swing.*;

/**
 * This class specifies the loop of the game.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 1.0
 */
public class GameLoop implements Runnable {

    //Frame per Second.
    private static final int FPS = 30;

    public boolean gameOver = false;
    private MainFrame canvas;
    private GameState state;

    GameLoop(MainFrame frame) {
        canvas = frame;
    }

    /**
     * Initialing the initial game states.
     */
    public void init() {
        state = new GameState();
        canvas.addKeyListener(state.getKeyListener());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());
    }

    /**
     * run method for Runnable Interface.
     * boolean gameOver indicates if is game over or not!
     */
    @Override
    public void run() {
        while (!gameOver) {
            try {
                long start = System.currentTimeMillis();
                //
                state.update();
                canvas.render(state);
                /*
                  This if and else are for pause, and saving game for next time.
                 */
                //Pausing and Saving
                if (state.isPaused()) {
                    int answer = JOptionPane.showConfirmDialog(canvas, "You are going to exit the game.\n" +
                            "Do you want to save your game?", "Game Paused", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.CLOSED_OPTION)
                        state.setPaused(false);
                    else if (answer == JOptionPane.YES_OPTION) {
                        //Saving and back to menu

                        Menu.writeResumeFile();
                        state.setPaused(false);
                        System.exit(0);
                    } else if (answer == JOptionPane.NO_OPTION) {
                        state.setPaused(false);
                        System.exit(0);
                    }
                }

                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        canvas.render(state);
    }
}
