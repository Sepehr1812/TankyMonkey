import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * This class contains the features of the Tank; like image, rotating ability, number of remaining bullets, health, and so on.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 1.1.0
 */
public class Tank implements Serializable {

    private transient BufferedImage tank;
    private double theta = 0;
    private int health = 100;
    private int maxHealths = 3;
    private int numberOfBulletsForVolcano = 15;
    private int numberOfBulletsForBigGun = 10;
    private boolean isShieldEnabled = false, isDomeEnabled = false;
    private Music m1;
    /**
     * Rotates the tank continuously.
     *
     * @param transform AffineTransform object.
     * @return the image of the tank.
     */
    public BufferedImage rotate(AffineTransform transform) {
        transform.setToTranslation(GameState.locXTank + 23, GameState.locYTank + 24);

        transform.rotate(theta += 0.06);

        if (isShieldEnabled)
            transform.translate(-42, -42);
        else if (isDomeEnabled)
            transform.translate(-75, -75);
        else
            transform.translate(-23, -24);

        return tank;
    }

    Tank(){
        m1 = new Music();
    }
    /**
     * Sets the image of the tank.
     *
     * @param tank image of the tank.
     */
    public void setTank(BufferedImage tank) {
        this.tank = tank;
    }

    public void decreaseHealth(int dec) {
        if (!isShieldEnabled)
            health -= dec;
    }

    /**
     * main changes in our tank in bullets or health or etc.
     */
    public int getHealth() {
        return health;
    }

    public int getNumberOfBulletsForBigGun() {
        return numberOfBulletsForBigGun;
    }

    public int getNumberOfBulletsForVolcano() {
        return numberOfBulletsForVolcano;
    }

    public void decreaseNumberOfBulletsForBigGun() {
        numberOfBulletsForBigGun--;
    }

    public void decreaseNumberOfBulletsForVolcano() {
        numberOfBulletsForVolcano--;
    }

    public void decreaseMaxHealth() {
        maxHealths--;
    }

    public int getMaxHealths() {
        return maxHealths;
    }

    /**
     * respawn our tank after we die.
     */
    public void respawn() {
        health = 100;
        GameState.locXTank = Map.xOfStart;
        GameState.locYTank = Map.yOfStart;
        numberOfBulletsForBigGun = 10;
        numberOfBulletsForVolcano = 15;
    }

    public void repairHealth() {
        health = 100;
    }

    public void setShieldEnabled(boolean shieldEnabled) {
        isShieldEnabled = shieldEnabled;
    }

    public void repairNumberOfBullets() {
        numberOfBulletsForBigGun = 10;
        numberOfBulletsForVolcano = 15;
    }

    public void setNumberOfBulletsForVolcano(int numberOfBulletsForVolcano) {
        this.numberOfBulletsForVolcano = numberOfBulletsForVolcano;
    }

    public void setNumberOfBulletsForBigGun(int numberOfBulletsForBigGun) {
        this.numberOfBulletsForBigGun = numberOfBulletsForBigGun;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealths(int maxHealths) {
        this.maxHealths = maxHealths;
    }

    void setBullets() {
        numberOfBulletsForBigGun = 30;
        numberOfBulletsForVolcano = 30;
    }

    /**
     *To enable shield
     */
    public boolean isShieldEnabled() {
        return isShieldEnabled;
    }

    public boolean isDomeEnabled() {
        return isDomeEnabled;
    }

    public void setDomeEnabled(boolean domeEnabled) {
        isDomeEnabled = domeEnabled;
    }
}
