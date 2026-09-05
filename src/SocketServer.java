import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer {
    private static final int PORT = 9876;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("Waiting for the client request");

                try (Socket socket = server.accept();
                     ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

                    String message = (String) input.readObject();
                    System.out.println("Message Received: " + message);

                    output.writeObject("Hi Client " + message);
                    output.flush();

                    if (message.equalsIgnoreCase("exit")) {
                        break;
                    }
                }
            }

            System.out.println("Shutting down Socket server!!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
