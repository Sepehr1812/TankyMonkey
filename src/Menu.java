import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * This Class contains details for menu and every button or anything we click on.
 */
public class Menu {

    private static JFrame canvas;

    private static final boolean[] isGameStarted = {false};
    public static boolean isResume = false;

    public static void showMainMenu() {
        canvas = new JFrame("TankyMonkey!");
        canvas.setIconImage(new ImageIcon("Images/Icon.png").getImage());

        try {
            canvas.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Images/Menu.JPEG")))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /*
                  To set game options
                 */
                if (e.getX() > 385 && e.getX() < 594 && e.getY() > 114 && e.getY() < 222) {
                    GameState.difficulty = 1;
                    isGameStarted[0] = true;
                    canvas.dispatchEvent(new WindowEvent(canvas, WindowEvent.WINDOW_CLOSING));
                } else if (e.getX() > 640 && e.getX() < 837 && e.getY() > 114 && e.getY() < 222) {
                    GameState.difficulty = 2;
                    isGameStarted[0] = true;
                    canvas.dispatchEvent(new WindowEvent(canvas, WindowEvent.WINDOW_CLOSING));
                } else if (e.getX() > 900 && e.getX() < 1100 && e.getY() > 114 && e.getY() < 222) {
                    GameState.difficulty = 3;
                    isGameStarted[0] = true;
                    canvas.dispatchEvent(new WindowEvent(canvas, WindowEvent.WINDOW_CLOSING));
                } else if (e.getX() > 30 && e.getX() < 460 && e.getY() > 390 && e.getY() < 450) {
                    isResume = true;

                    isGameStarted[0] = true;
                    canvas.dispatchEvent(new WindowEvent(canvas, WindowEvent.WINDOW_CLOSING));
                } else if (e.getX() > 30 && e.getX() < 460 && e.getY() > 500 && e.getY() < 580)
                    Map.mapGenerating();
                else if (e.getX() > 60 && e.getX() < 440 && e.getY() > 680 && e.getY() < 800)
                    System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };

        canvas.setSize(1125, 841);
        canvas.setLocationRelativeTo(null);
        canvas.addMouseListener(mouseListener);
        canvas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        canvas.setResizable(false);
        canvas.setVisible(true);

        while (!isGameStarted[0]) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method write files to resume later
     */
    public static void writeResumeFile() {
        try (ObjectOutputStream tank = new ObjectOutputStream(new FileOutputStream("Data/Tank.tm", false));
             ObjectOutputStream numberOfStep = new ObjectOutputStream(new FileOutputStream("Data/Step.tm", false));
             ObjectOutputStream gameDifficulty = new ObjectOutputStream(new FileOutputStream("Data/Difficulty.tm", false))) {

            tank.writeObject(MainFrame.getTank());
            numberOfStep.writeObject(Map.getNumberOfStep());
            gameDifficulty.writeObject(GameState.difficulty);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "A problem occurred.", "Problem Occurred", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * we need to read files and resume game.
     */
    public static void readResumeFile() {
        try (ObjectInputStream tank = new ObjectInputStream(new FileInputStream("Data/Tank.tm"));
             ObjectInputStream numberOfStep = new ObjectInputStream(new FileInputStream("Data/Step.tm"));
             ObjectInputStream gameDifficulty = new ObjectInputStream(new FileInputStream("Data/Difficulty.tm"))) {

            Tank buffTank = (Tank) tank.readObject();
            MainFrame.getTank().setHealth(buffTank.getHealth());
            MainFrame.getTank().setShieldEnabled(buffTank.isShieldEnabled());
            MainFrame.getTank().setMaxHealths(buffTank.getMaxHealths());
            MainFrame.getTank().setNumberOfBulletsForBigGun(buffTank.getNumberOfBulletsForBigGun());
            MainFrame.getTank().setNumberOfBulletsForVolcano(buffTank.getNumberOfBulletsForVolcano());

            Map.setNumberOfStep((Integer) numberOfStep.readObject());
            GameState.difficulty = (int) gameDifficulty.readObject();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "A problem occurred.", "Problem Occurred", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
