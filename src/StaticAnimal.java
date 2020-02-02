import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * kind on animals which cannot move.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 2.0.0
 */
public class StaticAnimal extends Animal {
    private int x, y, radius;
    private boolean isDone = false;
    private BufferedImage staticAnimalsIsnotDone;
    private BufferedImage staticAnimalImage;
    private BufferedImage staticAnimalIsDone;
    private BufferedImage staticAnimalBulletImage;
    private int health = 0;
    private ArrayList<Bullet> staticAnimalBullets;
    private Random rnd = new Random();
    private int maxHealth;

    StaticAnimal(int xx, int yy, int length, int healthRatio) {
        super(xx, yy, length, healthRatio);
        maxHealth = healthRatio * GameState.difficulty;
        x = xx;
        y = yy;
        radius = length;
        staticAnimalBullets = new ArrayList<>();
        try {
            staticAnimalBulletImage = ImageIO.read(new File("Images/dot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To relocation created animals
     **/
    public void reLocation(int xx, int yy, int radius) {
        isDone = false;
        setStaticAnimalImage(staticAnimalsIsnotDone);
        health = 0;
        x = xx;
        y = yy;
        this.radius = radius;

    }

    /**
     * @return to check is created animal alive
     */
    public boolean isDone() {
        return isDone;
    }

    @Override
    public BufferedImage getAnimal() {
        return super.getAnimal();
    }


    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public BufferedImage getStaticAnimalImage() {
        return staticAnimalImage;
    }

    public void setStaticAnimalImage(BufferedImage staticAnimalImage) {
        this.staticAnimalImage = staticAnimalImage;
    }

    public BufferedImage getStaticAnimalsIsnotDone() {
        return staticAnimalsIsnotDone;
    }

    public void setStaticAnimalsIsnotDone(BufferedImage staticAnimalsIsnotDone) {
        this.staticAnimalsIsnotDone = staticAnimalsIsnotDone;
    }

    public void setStaticAnimalIsDone(BufferedImage staticAnimalIsDone) {
        this.staticAnimalIsDone = staticAnimalIsDone;
    }

    public void settDone(boolean done) {
        isDone = done;
        if (isDone) {
            setStaticAnimalImage(staticAnimalIsDone);
        }
    }

    @Override
    public void increaseHealth(int increased) {
        health = health + increased;
    }

    /**
     * to shoot enemy when its close enough.
     */
    @Override
    public void shoot() {
        if (!isDone) {

            if (Math.abs(GameState.locXTank - getX()) < radius && Math.abs(GameState.locYTank - getY()) < radius) {

                int delay = rnd.nextInt(1000);

                if (delay % 4 == 0) {
                    staticAnimalBullets.add(new Bullet(staticAnimalBulletImage, x + 30, y + 10, GameState.locXTank - x + 27, GameState.locYTank - y + 20));
                }
            }
        }
    }

    public ArrayList<Bullet> getStaticAnimalBullets() {
        return staticAnimalBullets;
    }

    public BufferedImage getStaticAnimalBulletImage() {
        return staticAnimalBulletImage;
    }

    /**
     * @return max health of that animal
     */
    public int getMaxHealth() {
        return maxHealth;
    }
}

class StaticAnimalsForStage {


    private ArrayList<Animal> staticAnimalArrayList;

    StaticAnimalsForStage() {
        staticAnimalArrayList = new ArrayList<>();
    }

    public ArrayList<Animal> getAnimalArrayList() {
        return staticAnimalArrayList;
    }

    public void addAnimalsToArrayList(StaticAnimal animal) {
        staticAnimalArrayList.add(animal);
    }

    /**
     * to remove created animals.
     */
    public void removeAnimals() {
        staticAnimalArrayList.remove(2);
        staticAnimalArrayList.remove(1);
    }
}
