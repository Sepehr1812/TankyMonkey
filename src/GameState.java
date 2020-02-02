import javax.swing.*;
import java.awt.event.*;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 1.0.1
 */
public class GameState {
    /**
     * locTank is location for our tank and key booleans indicate which keys are pressed.
     */
    public static int locXTank, locYTank, diam;
    public static int difficulty = 2;
    private static boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    public int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private boolean isPaused;

    /**
     * starting point
     */
    GameState() {
        locXTank = Map.xOfStart;
        locYTank = Map.yOfStart;
        diam = 50;

        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;

        mouseX = 0;
        mouseY = 0;

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    }

    /**
     * The method which updates the game state for changes.
     */
    public void update() {
        if (keyUP && Map.isIntoTreesOrLeaves(locXTank, locYTank - 8 - 5))
            locYTank -= 8;
        if (keyDOWN && Map.isIntoTreesOrLeaves(locXTank, locYTank + 48))
            locYTank += 8;
        if (keyLEFT && Map.isIntoTreesOrLeaves(locXTank - 8 - 5, locYTank))
            locXTank -= 8;
        if (keyRIGHT && Map.isIntoTreesOrLeaves(locXTank + 48, locYTank))
            locXTank += 8;

        locXTank = Math.max(locXTank, 0);
        locXTank = Math.min(locXTank, MainFrame.GAME_WIDTH);
        locYTank = Math.max(locYTank, 0);
        locYTank = Math.min(locYTank, MainFrame.GAME_HEIGHT);

        Map.locating(locXTank + 23, locYTank + 24);
    }


    /**
     * @return KeyListener for gamer.
     */
    public KeyListener getKeyListener() {
        return keyHandler;
    }

    /**
     * @return MouseListener for gamer.
     */
    public MouseListener getMouseListener() {
        return mouseHandler;
    }

    /**
     * @return MouseMotionListener for gamer.
     */
    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public static void setMovingFalse() {
        keyDOWN = false;
        keyUP = false;
        keyRIGHT = false;
        keyLEFT = false;
    }

    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter {

        /**
         * KeyHandler for pressing a key.
         *
         * @param e the key pressed.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = true;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = true;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = true;
                    break;
                // case KeyEvent.VK_Z:
                //    Tank t = MainFrame.getTank();
                //  t.setMaxHealths();
                //break;
                /*
                  these are cheat codes to maximize health or bullet.
                 */
                case KeyEvent.VK_SPACE:
                    String str = JOptionPane.showInputDialog("Enter your code");

                    Tank t = MainFrame.getTank();
                    switch (str) {
                        case "hesoyam":
                            t.setMaxHealths(5);
                            break;
                        case "rashid":
                            t.setBullets();
                            break;
                        case "siktir":
                            Map.setCanGoToNextStep(true);
                            break;
                    }

                    break;
            }
        }

        /**
         * KeyHandler for releasing a key.
         *
         * @param e the key released.
         */
        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = false;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    break;

                case KeyEvent.VK_ESCAPE:
                    isPaused = !isPaused;
            }
        }
    }

    /**
     * The mouse handler.
     */
    class MouseHandler extends MouseAdapter {

        /**
         * MouseHandler for pressing a mouse button.
         *
         * @param e a mouse button pressed.
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e))
                Gun.setVolcano(!Gun.isVolcano());
            else if (SwingUtilities.isLeftMouseButton(e))
                MainFrame.getGun().shooting(e);
        }

        /**
         * MouseHandler for moving the mouse.
         *
         * @param e moving the mouse.
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }
}

