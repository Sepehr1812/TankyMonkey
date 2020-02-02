import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * This Class Contains every Stage information including start and end point, power ups or trees and etc.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 3.0.2
 */
public class Map {

    private static char[][] objects;
    private static boolean canGoToNextStep;
    private static int numberOfStep = 0;
    public static int xOfStart, yOfStart;
    private static int[] numberOfAnimals = {9, 21, 2};
    public static boolean isMapGenerated = false;

    public static void setObjects() {
        objects = new char[15][26];
        /*
          Reads every map from txt file and create it based on images given.
         */
        File map = new File(Objects.requireNonNull(getMapFile()));

        try (BufferedReader reader = new BufferedReader(new FileReader(map))) {
            for (int i = 0; reader.ready(); i++) {
                String line = reader.readLine();
                objects[i] = line.toCharArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createMap() throws IOException {
        BufferedImage mapImage = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = mapImage.createGraphics();

        /*
          every signature means different thing and this 2d for create our 2d map
         */
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                switch (objects[i][j]) {
                    case ' ':
                        graphics2D.drawImage(ImageIO.read(new File("Images/Land.png")), j * 50, i * 50, null);
                        break;

                    case '#':
                        graphics2D.drawImage(ImageIO.read(new File("Images/Tree.jpg")), j * 50, i * 50, null);
                        break;

                    case 'S':
                        graphics2D.drawImage(ImageIO.read(new File("Images/Start.jpg")), j * 50, i * 50, null);
                        xOfStart = j * 50;
                        yOfStart = i * 50 - 50;
                        break;

                    case 'B':
                        xOfStart = j * 50;
                        yOfStart = i * 50 - 50;
                    case 'N':
                        graphics2D.drawImage(ImageIO.read(new File("Images/NextY.jpg")), j * 50, i * 50, null);
                        break;

                    case 'F':
                        if (numberOfStep == 0)
                            graphics2D.drawImage(ImageIO.read(new File("Images/Finish.jpg")), j * 50, i * 50, null);
                        else {
                            if (!canGoToNextStep)
                                graphics2D.drawImage(ImageIO.read(new File("Images/NextN.jpg")), j * 50, i * 50, null);
                            else
                                graphics2D.drawImage(ImageIO.read(new File("Images/Finish.jpg")), j * 50, i * 50, null);
                        }

                        break;

                    case ')':
                    case '!':
                    case '$':
                    case '^':
                    case '*':
                    case '~':
                        graphics2D.drawImage(ImageIO.read(new File("Images/Leaf.png")), j * 50, i * 50, null);
                        break;

                    case '(':
                    case '@':
                    case '%':
                    case '&':
                    case '-':
                    case '+':
                        graphics2D.drawImage(ImageIO.read(new File("Images/BriefLeaf.png")), j * 50, i * 50, null);
                        break;

                    case '1':
                        graphics2D.drawImage(ImageIO.read(new File("Images/Upgrade.jpg")), j * 50, i * 50, null);
                        break;
                    case '2':
                        graphics2D.drawImage(ImageIO.read(new File("Images/Health.jpg")), j * 50, i * 50, null);
                        break;
                    case '3':
                        graphics2D.drawImage(ImageIO.read(new File("Images/Shield.jpg")), j * 50, i * 50, null);
                        break;
                    case '4':
                        graphics2D.drawImage(ImageIO.read(new File("Images/BulletPack.jpg")), j * 50, i * 50, null);
                        break;
                    case '5':
                        graphics2D.drawImage(ImageIO.read(new File("Images/Dome.jpg")), j * 50, i * 50, null);
                        break;
                }
            }
        }

        // Disposes of this graphics context and releases any system resources that it is using.
        graphics2D.dispose();

        // Save as PNG
        /*
          we need to save our file as png.
         */
        File file = new File(Objects.requireNonNull(getMapImage()));
        ImageIO.write(mapImage, "png", file);
    }

    public static boolean isIntoTreesOrLeaves(int x, int y) {
        int j = x / 50, i = y / 50;
        try {
            return (objects[i][j] == ' ' || objects[i][j] == '1' || objects[i][j] == '2' || objects[i][j] == '3'
                    || objects[i][j] == '4' || objects[i][j] == '5' || objects[i][j] == 'N' || objects[i][j] == 'F');
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * @return objects for map
     */
    public static char[][] getObjects() {
        return objects;
    }

    private static String getMapFile() {
        switch (numberOfStep) {
            //Introduction
            case 0:
                return "Maps/0.txt";

            //Step 1
            case 1:
                return "Maps/11.txt";
            case 2:
                return "Maps/12.txt";
            case 3:
                return "Maps/13.txt";

            //Step 2
            case 4:
                return "Maps/21.txt";
            case 5:
                return "Maps/22.txt";
            case 6:
                return "Maps/23.txt";
            case 7:
                return "Maps/24.txt";

            //Step 3
            case 8:
                return "Maps/3.txt";
                
            //Map Generating
            case 9:
                return "Maps/000.txt";
        }

        return null;
    }

    public static String getMapImage() {
        switch (numberOfStep) {
            //Introduction
            case 0:
                return "Maps/0.png";

            //Step 1
            case 1:
                return "Maps/11.png";
            case 2:
                return "Maps/12.png";
            case 3:
                return "Maps/13.png";

            //Step 2
            case 4:
                return "Maps/21.png";
            case 5:
                return "Maps/22.png";
            case 6:
                return "Maps/23.png";
            case 7:
                return "Maps/24.png";

            //Step 3
            case 8:
                return "Maps/3.png";

            //Map Generating
            case 9:
                return "Maps/000.png";
        }

        return null;
    }

    /**
     * This method locate our guns shape and damage based on which gun we earned.
     */
    public static void locating(int x, int y) {
        int j = x / 50, i = y / 50;

        switch (Map.getObjects()[i][j]) {
            case '1':
                Map.getObjects()[i][j] = ' ';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Gun.isVolcano()) {
                    if (MainFrame.getGun().getVolcanoImageAddress().equals("Volcano1")) {
                        MainFrame.getGun().setVolcanoImageAddress("Volcano2");
                        Gun.damageV = 50;
                    } else {
                        MainFrame.getGun().setVolcanoImageAddress("Volcano3");
                        Gun.damageV = 75;
                    }
                } else {
                    if (MainFrame.getGun().getBigGunImageAddress().equals("BigGun1")) {
                        MainFrame.getGun().setBigGunImageAddress("BigGun2");
                        Gun.damageB = 125;
                    } else {
                        MainFrame.getGun().setBigGunImageAddress("BigGun3");
                        Gun.damageB = 150;
                    }
                }

                break;
            case '2':
                Map.getObjects()[i][j] = ' ';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MainFrame.getTank().repairHealth();

                break;
            case '3':
                Map.getObjects()[i][j] = ' ';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MainFrame.getTank().setShieldEnabled(true);
                try {
                    MainFrame.getTank().setTank(ImageIO.read(new File("Images/BaseTankShield.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case '4':
                Map.getObjects()[i][j] = ' ';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MainFrame.getTank().repairNumberOfBullets();

                break;
            case '5':
                Map.getObjects()[i][j] = ' ';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MainFrame.getTank().setDomeEnabled(true);
                try {
                    MainFrame.getTank().setTank(ImageIO.read(new File("Images/DomeTank.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case 'N':
                numberOfStep++;
                setObjects();
                GameState.locYTank += 670;
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case 'B':
                numberOfStep--;
                setObjects();
                GameState.locYTank -= 670;
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case 'F':
                if (canGoToNextStep || numberOfStep == 0) {
                    if (numberOfStep < 8) {
                        JOptionPane.showConfirmDialog(null, "Congratulations! You passed this step!", "Congratulations!",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Images/Finish1.gif"));
                        GameState.setMovingFalse();

                        canGoToNextStep = false;

                        numberOfStep++;
                        setObjects();
                        try {
                            Map.createMap();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        GameState.locXTank = xOfStart;
                        GameState.locYTank = yOfStart;
                    } else {
                        JOptionPane.showMessageDialog(null, "Congratulations! You finished the game!",
                                "Congratulations!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Images/Finish2.gif"));
                        System.exit(1);
                    }
                }

                break;
        }
    }

    public static void setNumberOfStep(int numberOfStep) {
        Map.numberOfStep = numberOfStep;
    }

    public static int getNumberOfStep() {
        return numberOfStep;
    }

    /**
     * we need to count animals to see if its possible to go to next step.
     */
    public static void decreaseNumberOfAnimal() {
        canGoToNextStep = true;
        switch (numberOfStep) {
            case 1:
            case 2:
            case 3:
                numberOfAnimals[0]--;
                if (numberOfAnimals[0] == 0) {
                    canGoToNextStep = true;
                    try {
                        createMap();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 4:
            case 5:
            case 6:
            case 7:
                numberOfAnimals[1]--;
                if (numberOfAnimals[1] == 0) {
                    canGoToNextStep = true;
                    try {
                        createMap();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 8:
                numberOfAnimals[2]--;
                if (numberOfAnimals[2] == 0) {
                    canGoToNextStep = true;
                    try {
                        createMap();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * @param canGoToNextStep indicates that can we go to next step.
     */
    public static void setCanGoToNextStep(boolean canGoToNextStep) {
        Map.canGoToNextStep = canGoToNextStep;
    }

    /**
     * For map generating.
     */
    public static void mapGenerating() {
        JOptionPane.showMessageDialog(null, "Land: (space)\nTree: #\n" +
                "Leaves: )\nBullet Pack: 4\nHealth: 2\nShield: 3\nUpgrade Gun: 1\nDome: 5\nStart Point: S" +
                "", "Map Generator tips", JOptionPane.INFORMATION_MESSAGE);

        isMapGenerated = true;

        Desktop desktop = null;
        if (Desktop.isDesktopSupported())
            desktop = Desktop.getDesktop();

        try {
            Objects.requireNonNull(desktop).open(new File("Maps/000.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
