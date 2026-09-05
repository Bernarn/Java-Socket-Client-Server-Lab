import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class SocketClient {
    private static final int PORT = 9876;
    private static final int REQUEST_COUNT = 5;

    public static void main(String[] args) {
        try {
            InetAddress host = InetAddress.getLocalHost();

            for (int i = 0; i < REQUEST_COUNT; i++) {
                try (Socket socket = new Socket(host.getHostName(), PORT);
                     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                    System.out.println("Sending request to Socket Server");

                    String request = (i == REQUEST_COUNT - 1) ? "exit" : String.valueOf(i);
                    output.writeObject(request);
                    output.flush();

                    String message = (String) input.readObject();
                    System.out.println("Message: " + message);
                }

                Thread.sleep(100);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
