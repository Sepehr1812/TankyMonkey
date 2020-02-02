import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

/**
 * The main frame of the game.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 1.0.1
 */
public class MainFrame extends JFrame {
    /*
     * This is main frame that render every frame our game and what we see is come from this class
     * Animal are creatures controlled by cpu and static animals are that cannot move
     * forStage Class is arrayList that contain all animals exist in one stage
     */
    // 720p game resolution
    public static final int GAME_HEIGHT = 720;
    public static final int GAME_WIDTH = 1280;
    private int thisStep = 0;
    private static Tank tank;
    private static Gun gun;
    private Animal animal;
    private Animal animal2;
    private Animal animal3;
    private StaticAnimal staticAnimal;
    private StaticAnimal staticAnimal2;
    private StaticAnimal staticAnimal3;
    private BufferStrategy bufferStrategy;
    private AnimalsForStage animalsForStage;
    private StaticAnimalsForStage staticAnimalsForStage;
    private static boolean isGameOver = false;
    private boolean isSaid = false;
    private Music music1;

    MainFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);

        /*
         * set mouse cursor to some shape that looks best in this game.
         */
        //Set the blank cursor to the JFrame.
        BufferedImage cursorImg = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        getContentPane().setCursor(blankCursor);

        /*
          create music class to play sounds and our main tank
         */
        music1 = new Music();
        tank = new Tank();

        if (Menu.isResume) {
            Menu.readResumeFile();
        }

        gun = new Gun();

        if (Map.isMapGenerated)
            Map.setNumberOfStep(9);

        Map.setObjects();
        try {
            Map.createMap();
        } catch (IOException e) {
            e.printStackTrace();
        }


        animalsForStage = new AnimalsForStage();
        staticAnimalsForStage = new StaticAnimalsForStage();

        try {
            tank.setTank(ImageIO.read(new File("Images/BaseTank.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initials BufferStrategy of main frame.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {
        if (!isGameOver) {
            if (thisStep != Map.getNumberOfStep()) {

                thisStep = Map.getNumberOfStep();

                //stage 1 or map generated
                /*
                 * this create and relocate animals based on which stage are we.
                 */
                if (thisStep == 1 || thisStep == 9) {
                    animal = new Animal(120, 420, 410, 100);
                    try {
                        animal.setAnimalDone(ImageIO.read(new File("Images/Animals/Crocodile5.png")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        animal.setAnimalsIsGoingLeft(ImageIO.read(new File("Images/Animals/Crocodile2.png")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        animal.setAnimalsIsGoingRight(ImageIO.read(new File(("Images/Animals/Crocodile1.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    animalsForStage.addAnimalsToArrayList(animal);


                    staticAnimal = new StaticAnimal(720, 60, 320, 100);
                    try {
                        staticAnimal.setStaticAnimalsIsnotDone(ImageIO.read(new File(("Images/Animals/Tiger3.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        staticAnimal.setStaticAnimalIsDone(ImageIO.read(new File(("Images/Animals/Tiger5.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    staticAnimal.setStaticAnimalImage(staticAnimal.getStaticAnimalsIsnotDone());
                    staticAnimalsForStage.addAnimalsToArrayList(staticAnimal);

                }

                //stage 2
                if (thisStep == 2) {
                    animal.reLocation(150, 350, 250);
                    staticAnimal.reLocation(1106, 600, 450);
                    animal2 = new Animal(170, 480, 200, 70);
                    try {
                        animal2.setAnimalsIsGoingRight(ImageIO.read(new File(("Images/Animals/Elephant1.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        animal2.setAnimalsIsGoingLeft(ImageIO.read(new File(("Images/Animals/Elephant2.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        animal2.setAnimalDone(ImageIO.read(new File(("Images/Animals/Elephant5.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    animalsForStage.addAnimalsToArrayList(animal2);
                }

                //stage 3
                if (thisStep == 3) {
                    staticAnimal.reLocation(280, 140, 365);
                    staticAnimal2 = new StaticAnimal(1000, 150, 300, 100);
                    try {
                        staticAnimal2.setStaticAnimalsIsnotDone(ImageIO.read(new File(("Images/Animals/Turtle3.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        staticAnimal2.setStaticAnimalIsDone(ImageIO.read(new File(("Images/Animals/Turtle5.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    staticAnimal2.setStaticAnimalImage(staticAnimal2.getStaticAnimalsIsnotDone());
                    staticAnimalsForStage.addAnimalsToArrayList(staticAnimal2);
                    animal.reLocation(350, 550, 400);
                    animal2.reLocation(400, 350, 800);
                }

                //stage 4
                if (thisStep == 4) {
                    staticAnimal.reLocation(1200, 500, 300);
                    staticAnimal2.reLocation(100, 100, 275);
                    animal.reLocation(650, 250, 600);
                    animal2.reLocation(100, 450, 780);

                }

                //stage 5
                if (thisStep == 5) {
                    animal.reLocation(700, 100, 500);
                    animal2.reLocation(150, 650, 650);
                    staticAnimal.reLocation(750, 350, 175);
                    staticAnimal2.reLocation(250, 200, 320);

                }

                //stage 6
                if (thisStep == 6) {
                    animal.reLocation(75, 575, 800);
                    animal2.reLocation(80, 320, 1000);
                    staticAnimal.reLocation(800, 100, 350);
                    staticAnimal2.reLocation(100, 100, 220);
                    animal3 = new Animal(100, 200, 300, 100);
                    try {
                        animal3.setAnimalDone(ImageIO.read(new File(("Images/Animals/Tiger5.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        animal3.setAnimalsIsGoingLeft(ImageIO.read(new File(("Images/Animals/Tiger2.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        animal3.setAnimalsIsGoingRight(ImageIO.read(new File(("Images/Animals/Tiger1.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    animalsForStage.addAnimalsToArrayList(animal3);


                }

                //stage 7
                if (thisStep == 7) {
                    animal.reLocation(300, 600, 700);
                    animal2.reLocation(300, 300, 500);
                    animal3.reLocation(300, 150, 200);
                    staticAnimal.reLocation(150, 250, 350);
                    staticAnimal2.reLocation(850, 100, 430);
                    staticAnimal3 = new StaticAnimal(1184, 327, 450, 25);
                    try {
                        staticAnimal3.setStaticAnimalIsDone(ImageIO.read(new File(("Images/Animals/Elephant5.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        staticAnimal3.setStaticAnimalsIsnotDone(ImageIO.read(new File(("Images/Animals/Elephant3.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    staticAnimal3.setStaticAnimalImage(staticAnimal3.getStaticAnimalsIsnotDone());
                    staticAnimalsForStage.addAnimalsToArrayList(staticAnimal3);
                }

                //stage 8
                if (thisStep == 8) {
                    animalsForStage.removeAnimals();
                    staticAnimalsForStage.removeAnimals();
                    try {
                        staticAnimal.setStaticAnimalsIsnotDone(ImageIO.read(new File(("Images/Animals/Dragon.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        staticAnimal.setStaticAnimalIsDone(ImageIO.read(new File(("Images/dot1.png"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    staticAnimal.reLocation(1148, 545, 1200);
                    animal.reLocation(150, 350, 100);
                    animal.setVertical(true);
                }
            }

            /*
              This part draw maps and other objects exist on map.
             */
            // Draw Map
            g2d.setColor(Color.ORANGE);
            try {
                g2d.drawImage(ImageIO.read(new File(Objects.requireNonNull(Map.getMapImage()))), 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Draw Tank
            AffineTransform tankTransform = new AffineTransform();
            g2d.drawImage(tank.rotate(tankTransform), tankTransform, null);

            // Draw Gun
            AffineTransform gunTransform = new AffineTransform();
            g2d.drawImage(gun.rotate(state, gunTransform), gunTransform, null);

            // Draw Animals
            /*
              drawing animals from arrayList.
             */
            Iterator animalsItr = animalsForStage.getAnimalArrayList().iterator();
            while (animalsItr.hasNext()) {
                Animal buffAnimal = (Animal) animalsItr.next();

                g2d.drawImage(buffAnimal.getAnimal(), buffAnimal.getX(), buffAnimal.getY(), null);

                g2d.drawString(Math.min(buffAnimal.getHealth(), buffAnimal.getMaxHealth()) + "/" + buffAnimal.getMaxHealth(), buffAnimal.getX() + 10, buffAnimal.getY() - 15);
                // Draw Animals' bullets
                buffAnimal.shoot();
                Iterator itr = buffAnimal.getBullets().iterator();
                while (itr.hasNext()) {
                    Bullet buffAnimalBullet = (Bullet) itr.next();
                    g2d.drawImage(buffAnimal.getAnimalBullet(), buffAnimalBullet.getX(), buffAnimalBullet.getY(), null);

                    if (buffAnimalBullet.isShotToLeavesOrTreesForAnimals(buffAnimalBullet.getX(), buffAnimalBullet.getY())) {
                        itr.remove();
                        continue;
                    }

                    if (buffAnimalBullet.isTankHit()) {
                        itr.remove();

                        tank.decreaseHealth(20);
                        /*
                          to check is any health remained or not.
                         */
                        if (tank.getHealth() < 0) {
                            tank.decreaseMaxHealth();
                            if (tank.getMaxHealths() < 0) {
                                //Game Over
                                isGameOver = true;
                            }
                            tank.respawn();
                        }

                        continue;
                    }

                    if (!buffAnimalBullet.increasing()) itr.remove();
                }

            }

            // Draw Static Animals
            /*
              Draw animals which cannot move
             */
            Iterator staticAnimalItr = staticAnimalsForStage.getAnimalArrayList().iterator();
            while (staticAnimalItr.hasNext()) {
                StaticAnimal buffStaticAnimal = (StaticAnimal) staticAnimalItr.next();
                g2d.drawImage(buffStaticAnimal.getStaticAnimalImage(), buffStaticAnimal.getX(), buffStaticAnimal.getY(), null);
                g2d.drawString(Math.min(buffStaticAnimal.getHealth(), buffStaticAnimal.getMaxHealth()) + "/" + buffStaticAnimal.getMaxHealth(), buffStaticAnimal.getX() + 25, buffStaticAnimal.getY() - 10);


                //Draw StaticAnimals Bullet
                buffStaticAnimal.shoot();
                Iterator itr1 = buffStaticAnimal.getStaticAnimalBullets().iterator();
                while (itr1.hasNext()) {
                    Bullet buffStaticAnimalBullet = (Bullet) itr1.next();
                    g2d.drawImage(buffStaticAnimal.getStaticAnimalBulletImage(), buffStaticAnimalBullet.getX(), buffStaticAnimalBullet.getY(), null);
                    if (buffStaticAnimalBullet.isTankHit()) {
                        itr1.remove();
                        tank.decreaseHealth(15);

                        if (tank.getHealth() < 0) {
                            tank.decreaseMaxHealth();
                            if (tank.getMaxHealths() <= 0) {
                                isGameOver = true;
                            } else {
                                tank.respawn();
                            }
                        }

                        continue;
                    }
                    if (!buffStaticAnimalBullet.increasing()) {
                        itr1.remove();
                    }
                }
            }

            // Draw bullets
            try {
                Iterator iterator = gun.getBullets().iterator();

                while (iterator.hasNext()) {

                    Bullet buffBullet = (Bullet) iterator.next();

                    if (buffBullet instanceof Dome) {
                        ((Dome) buffBullet).shooting(animalsForStage.getAnimalArrayList());
                        ((Dome) buffBullet).shooting(staticAnimalsForStage.getAnimalArrayList());
                        iterator.remove();

                        tank.setDomeEnabled(false);
                        try {
                            tank.setTank(ImageIO.read(new File("Images/BaseTank.png")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        g2d.drawImage(buffBullet.getImage(), buffBullet.getX(), buffBullet.getY(), null);
                        Iterator itt = animalsForStage.getAnimalArrayList().iterator();
                        while (itt.hasNext()) {
                            Animal buffAnimal = (Animal) itt.next();
                            if (buffBullet.isHit(buffAnimal)) {
                                iterator.remove();

                                if (Gun.isVolcano()) {
                                    buffAnimal.increaseHealth(Gun.damageV);
                                } else {
                                    buffAnimal.increaseHealth(Gun.damageB);
                                }

                                if (buffAnimal.getHealth() >= buffAnimal.getMaxHealth()) {
                                    buffAnimal.setDone(true);
                                    Map.decreaseNumberOfAnimal();
                                }
                            }
                        }

                        Iterator staticItr = staticAnimalsForStage.getAnimalArrayList().iterator();
                        while (staticItr.hasNext()) {
                            StaticAnimal buffStaticAnimal = (StaticAnimal) staticItr.next();
                            if (buffBullet.isHit(buffStaticAnimal)) {

                                iterator.remove();
                                if (Gun.isVolcano()) {
                                    buffStaticAnimal.increaseHealth(Gun.damageV);
                                } else {
                                    buffStaticAnimal.increaseHealth(Gun.damageB);
                                }

                                if (buffStaticAnimal.getHealth() >= buffStaticAnimal.getMaxHealth()) {
                                    buffStaticAnimal.settDone(true);
                                    Map.decreaseNumberOfAnimal();

                                    //Rewarding
                                    Map.getObjects()[buffStaticAnimal.getY() / 50 + 1][buffStaticAnimal.getX() / 50 - 1] = '4';
                                    try {
                                        Map.createMap();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        if (buffBullet.isShotToTrees(buffBullet.getX(), buffBullet.getY())) {
                            iterator.remove();
                            continue;
                        }

                        if (buffBullet.isShotToLeaves(buffBullet.getX(), buffBullet.getY())) {
                            iterator.remove();
                            continue;
                        }

                        if (!buffBullet.increasing())
                            iterator.remove(); //We must remove iterator because not do it makes the thread shutdown.
                    }
                }
            } catch (ConcurrentModificationException | IllegalStateException e) {
//            e.printStackTrace();
            }

            // Draw Target
            try {
                g2d.drawImage(ImageIO.read(new File("Images/Target.png")), getMousePosition().x - 15, getMousePosition().y - 15, null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
//            e.printStackTrace();
            }

            // Draw details
            // Show remained health and bullets for our tank.
            /*
              Draw details for our tank including health and remained bullets.
             */
            g2d.drawString("Health: " + tank.getHealth(), 1050, 70);
            g2d.drawString("Remained Bullets for BigGun: " + tank.getNumberOfBulletsForBigGun(), 1050, 85);
            g2d.drawString("Remained Bullets for Volcano: " + tank.getNumberOfBulletsForVolcano(), 1050, 100);
            g2d.drawString("Max Health: " + tank.getMaxHealths(), 1050, 115);
        }
        /*
          Draw Game Over scene
         */
        // Draw GAME OVER
        if (isGameOver) {
            if (!isSaid) {
                music1.toBakhti();
                isSaid = false;
            }
            String str = "GAME OVER";
            g2d.setColor(Color.black);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
            if (tank.getMaxHealths() > 0) {
                isGameOver = false;
                tank.respawn();
            }

        }
    }

    /**
     * Getter for the Gun object.
     *
     * @return the Gun object.
     */
    public static Gun getGun() {
        return gun;
    }

    public static Tank getTank() {
        return tank;
    }
}
