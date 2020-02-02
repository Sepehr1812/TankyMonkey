import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains Tanks are controlled by AI.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 2.0.0
 */
public class Animal {

    /*
     * animalBullet is animals bullet gui
     * maxR indicates most distance our animal can go
     * isDone Shows if is our animal healed or not
     * isGoingRight indicates how this animal move
     */
    private BufferedImage animalBullet;
    private int maxR;
    private int minR;
    private boolean isDone = false;
    private int health = 0;
    private int maxHealth;
    private boolean isGoingRight = true;
    private int x, y;
    private BufferedImage Animal;
    private ArrayList<Bullet> bullets;
    private Random random = new Random();
    private BufferedImage animalsIsGoingRight;
    private BufferedImage animalsIsGoingLeft;
    private BufferedImage animalDone;
    private boolean isVertical;

    @SuppressWarnings("Duplicates")
    Animal(int xx, int yy, int length, int healthRatio) {
        x = xx;
        y = yy;
        minR = x;
        maxR = x + length;
        maxHealth = healthRatio * GameState.difficulty;
        bullets = new ArrayList<>();
        try {
            animalBullet = ImageIO.read(new File("Images/dot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reLocation(int xx, int yy, int length) {
        isDone = false;
        health = 0;
        x = xx;
        y = yy;
        minR = x;
        maxR = x + length;
    }

    /**
     * To Change move direction.
     */
    public void setVertical(boolean isV) {
        isVertical = isV;
        if (isV) {

            try {
                setAnimalsIsGoingRight(ImageIO.read(new File(("Images/Animals/Crocodile4.png"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                setAnimalsIsGoingLeft(ImageIO.read(new File(("Images/Animals/Crocodile.png"))));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void setAnimal(BufferedImage animal) {
        Animal = animal;
    }

    public BufferedImage getAnimal() {
        return Animal;
    }

    public int getX() {
        if (!isVertical) {
            if (isDone) {
                setAnimal(animalDone);
                return x;
            } else {
                if (x > maxR) {
                    isGoingRight = false;
                }
                if (x < minR) {
                    isGoingRight = true;
                }

                if (isGoingRight) {
                    setAnimal(animalsIsGoingRight);
                }
                if (!isGoingRight) {
                    setAnimal(animalsIsGoingLeft);
                }
                if (isGoingRight) {
                    return x += 2;
                } else {
                    return x -= 2;
                }
            }

        } else return x;
    }

    public int getY() {
        if (!isVertical) {
            return y;
        } else {

            if (isDone) {
                try {
                    setAnimal(animalDone);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return y;
            } else {
                if (y > maxR) {
                    isGoingRight = false;
                }
                if (y < minR) {
                    isGoingRight = true;
                }

                if (isGoingRight) {
                    try {
                        setAnimal(animalsIsGoingRight);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!isGoingRight) {
                    try {
                        setAnimal(animalsIsGoingLeft);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (isGoingRight) {
                    return y += 2;
                } else {
                    return y -= 2;
                }
            }
        }
    }

    public void increaseHealth(int increased) {
        health += increased;
    }

    public int getHealth() {
        return health;
    }

    public void setDone(boolean done) {
        isDone = done;

    }

    public void shoot() {
        if (!isVertical) {
            if (!isDone) {
                int delay = random.nextInt(1000);

                if (Math.abs(y - GameState.locYTank) < 20) {

                    if (x > GameState.locXTank)
                        isGoingRight = false;

                    if (x < GameState.locXTank)
                        isGoingRight = true;

                    if (delay % 5 == 0) {
                        bullets.add(new Bullet(animalBullet, x + 3, y + 7,
                                GameState.locXTank - x + 20, GameState.locYTank - y + 20));
                    }
                }
            }
        } else {
            if (!isDone) {
                int delay = random.nextInt(1000);

                if (Math.abs(x - GameState.locXTank) < 20) {

                    if (y > GameState.locYTank)
                        isGoingRight = false;

                    if (x < GameState.locYTank)
                        isGoingRight = true;

                    if (delay % 5 == 0) {
                        bullets.add(new Bullet(animalBullet, x + 3, y + 7,
                                GameState.locXTank - x + 20, GameState.locYTank - y + 20));
                    }
                }
            }


        }
    }

    public BufferedImage getAnimalBullet() {
        return animalBullet;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setAnimalsIsGoingLeft(BufferedImage animalsIsGoingLeft) {
        this.animalsIsGoingLeft = animalsIsGoingLeft;
    }

    public void setAnimalsIsGoingRight(BufferedImage animalsIsGoingRight) {
        this.animalsIsGoingRight = animalsIsGoingRight;
    }

    public void setAnimalDone(BufferedImage animalDone) {
        this.animalDone = animalDone;
    }
}

class AnimalsForStage {

    private ArrayList<Animal> animalArrayList;

    AnimalsForStage() {
        animalArrayList = new ArrayList<>();
    }

    public ArrayList<Animal> getAnimalArrayList() {
        return animalArrayList;
    }

    public void addAnimalsToArrayList(Animal animal) {
        animalArrayList.add(animal);
    }

    /**
     * remove created animals.
     */
    public void removeAnimals() {
        animalArrayList.remove(1);
//        animalArrayList.remove(2);
    }
}

