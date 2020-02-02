import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class contains the features of the bullets; like image, the coordinates of the bullet, amount of moving in the x's and y's axises.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 1.5
 */
public class Bullet {

    private BufferedImage image;
    private int x, y;
    private double xIncrease, yIncrease;

    Bullet() {

    }

    /**
     * Constructor for bullet.
     *
     * @param image       the image of the bullet.
     * @param x           the x parameter of the bullet.
     * @param y           the y parameter of the bullet.
     * @param xDifference the difference of the tank location and mouse click (x parameter).
     * @param yDifference the difference of the tank location and mouse click (y parameter).
     */
    Bullet(BufferedImage image, int x, int y, int xDifference, int yDifference) {
        this.image = image;
        this.x = x;
        this.y = y;

        int originalXDifference = xDifference, originalYDifference = yDifference;

        xDifference = Math.abs(xDifference);
        yDifference = Math.abs(yDifference);

        // Calculating the rate of the differences and increase it for 10 times.
        if (xDifference > yDifference) {
            this.xIncrease = 1.0 * 10;
            this.yIncrease = ((double) yDifference / (double) xDifference) * 10;
        } else {
            this.yIncrease = 1.0 * 10;
            this.xIncrease = ((double) xDifference / (double) yDifference) * 10;
        }

        if (originalXDifference < 0)
            xIncrease *= -1;
        if (originalYDifference < 0)
            yIncrease *= -1;
    }

    /**
     * Specify the new location of the bullet.
     *
     * @return if {@code false} the bullet crosses the game screen.
     */
    public boolean increasing() {
        if ((x > 1280 || y > 720 || x < 0 || y < 0) && MainFrame.getGun().getBullets().size() > 1) {
            // We must NOT remove the bullet directly; because it makes the thread throw java.util.ConcurrentModificationException.
//            MainFrame.getGun().getBullets().remove(this);
            return false;
        } else {
            x += xIncrease;
            y += yIncrease;

            return true;
        }
    }

    /**
     * Getter for x parameter of the bullet location.
     *
     * @return x parameter of the bullet location.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y parameter of the bullet location.
     *
     * @return y parameter of the bullet location.
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for bullet's image.
     *
     * @return bullet's image.
     */
    public BufferedImage getImage() {
        return image;
    }

    public boolean isHit(Animal animal) {
        return (Math.abs(x - animal.getX()) < 30 && (Math.abs(y - animal.getY()) < 30));
    }

    public boolean isTankHit() {
        return (Math.abs(x - GameState.locXTank) < 30 && (Math.abs(y - GameState.locYTank) < 30));
    }

    public boolean isShotToTrees(int x, int y) {
        return (x > 1280 || y > 720 || x < 0 || y < 0 || Map.getObjects()[y / 50][x / 50] == '#');
    }

    public boolean isShotToLeaves(int x, int y) {
        int j = x / 50, i = y / 50;

        if (x > 1280 || y > 720 || x < 0 || y < 0)
            return true;

        switch (Map.getObjects()[i][j]) {
            case ')':
                Map.getObjects()[i][j] = '(';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '!':
                Map.getObjects()[i][j] = '@';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '$':
                Map.getObjects()[i][j] = '%';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '^':
                Map.getObjects()[i][j] = '&';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '*':
                Map.getObjects()[i][j] = '-';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '~':
                Map.getObjects()[i][j] = '+';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case '(':
                Map.getObjects()[i][j] = ' ';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '@':
                Map.getObjects()[i][j] = '1';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '%':
                Map.getObjects()[i][j] = '2';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '&':
                Map.getObjects()[i][j] = '3';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '-':
                Map.getObjects()[i][j] = '4';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '+':
                Map.getObjects()[i][j] = '5';
                try {
                    Map.createMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                return false;
        }

        return true;
    }

    public boolean isShotToLeavesOrTreesForAnimals(int x, int y) {
        int j = x / 50, i = y / 50;

        if (x > 1280 || y > 720 || x < 0 || y < 0)
            return true;

        switch (Map.getObjects()[i][j]) {
            case '#':
            case ')':
            case '!':
            case '$':
            case '^':
            case '*':
            case '~':
            case '(':
            case '@':
            case '%':
            case '&':
            case '-':
            case '+':
                return true;
            default:
                return false;
        }
    }
}

/**
 * This class give us upgraded weapon 3rd one.
 */
class Dome extends Bullet {

    public void shooting(ArrayList<Animal> animals) {
        for (Animal animal : animals) {
            if (Math.abs(animal.getX() - GameState.locXTank) < 150 && Math.abs(animal.getX() - GameState.locXTank) < 150) {
                animal.increaseHealth(200);

                if (animal.getHealth() >= animal.getMaxHealth()) {
                    animal.setDone(true);
                    Map.decreaseNumberOfAnimal();
                }
            }
        }
    }
}
