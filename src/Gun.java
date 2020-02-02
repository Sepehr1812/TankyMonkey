import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class contains the features of a gun; like image, rotating ability and shooting.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 1.8
 */
public class Gun {

    private BufferedImage gun;
    private static boolean isVolcano = false;
    private ArrayList<Bullet> bullets;
    public static int damageV, damageB;
    private String bigGunImageAddress, volcanoImageAddress;

    Gun() {
        bullets = new ArrayList<>();
        damageB = 50;
        damageV = 25;
        bigGunImageAddress = "BigGun1";
        volcanoImageAddress = "Volcano1";
    }

    /**
     * Rotates the gun.
     *
     * @param state     state of the mouse.
     * @param transform AffineTransform object.
     * @return the image of the gun.
     */
    public BufferedImage rotate(GameState state, AffineTransform transform) {
        setGun();
        transform.setToTranslation(GameState.locXTank + 24, GameState.locYTank + 23);

        double degree = Math.atan2(state.mouseX - GameState.locXTank - 24, state.mouseY - GameState.locYTank - 23);
        transform.rotate(-degree);

        transform.translate(-11, -24);

        return gun;
    }

    /**
     * Sets the image of the gun.
     */
    private void setGun() {
        if (!isVolcano) {
            try {
                gun = ImageIO.read(new File("Images/" + bigGunImageAddress + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                gun = ImageIO.read(new File("Images/" + volcanoImageAddress + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds the bullets to the screen.
     *
     * @param event where mouse clicks.
     */
    public void shooting(MouseEvent event) {
        BufferedImage bulletImage = null;

        if (MainFrame.getTank().isDomeEnabled()) {
            bullets.add(new Dome());
        } else {
            if (!isVolcano) {
                if (MainFrame.getTank().getNumberOfBulletsForBigGun() > 0) {
                    MainFrame.getTank().decreaseNumberOfBulletsForBigGun();

                    try {
                        bulletImage = ImageIO.read(new File("Images/BananaB.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    bullets.add(new Bullet(bulletImage, GameState.locXTank + 13, GameState.locYTank + 24,
                            event.getX() - GameState.locXTank + 13, event.getY() - GameState.locYTank));
                }
            } else {
                if (MainFrame.getTank().getNumberOfBulletsForVolcano() > 0) {
                    MainFrame.getTank().decreaseNumberOfBulletsForVolcano();

                    try {
                        bulletImage = ImageIO.read(new File("Images/BananaV.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    bullets.add(new Bullet(bulletImage, GameState.locXTank + 13, GameState.locYTank + 24,
                            event.getX() - GameState.locXTank + 13, event.getY() - GameState.locYTank));
                }
            }
        }
    }

    /**
     * Sets the gun kind (BigGun or Volcano).
     *
     * @param volcano if {@code true} the gun kind is Volcano.
     */
    public static void setVolcano(boolean volcano) {
        isVolcano = volcano;
    }

    /**
     * Getter for gun kind.
     *
     * @return if {@code true} the gun kind is Volcano.
     */
    public static boolean isVolcano() {
        return isVolcano;
    }

    /**
     * Getter for bullets array.
     *
     * @return bullets array.
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBigGunImageAddress(String bigGunImageAddress) {
        this.bigGunImageAddress = bigGunImageAddress;
    }

    public void setVolcanoImageAddress(String volcanoImageAddress) {
        this.volcanoImageAddress = volcanoImageAddress;
    }

    public String getBigGunImageAddress() {
        return bigGunImageAddress;
    }

    public String getVolcanoImageAddress() {
        return volcanoImageAddress;
    }
}
