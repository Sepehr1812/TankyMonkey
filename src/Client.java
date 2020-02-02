import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class is for client user in Two-Player mode.
 *
 * @author Sepehr Akhoundi & Farbod Rasaei
 * @version 0.0.1
 */
public class Client {

    public static void main(String[] args) {
        try (Socket server = new Socket("127.0.0.1", 12345)) {
            System.out.println("Connected to server.");
            OutputStream out = server.getOutputStream();
            InputStream in = server.getInputStream();
            byte[] buffer = new byte[2048];
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            System.out.print("All messages sent.\nClosing ... ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("done.");
    }
}
