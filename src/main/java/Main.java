import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.err.println("Logs from your program will appear here!");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 9092;
        try {
            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            // Wait for connection from client.
            clientSocket = serverSocket.accept();
            InputStream inputStream = clientSocket.getInputStream();

            byte[] messageSize = new byte[4];
            inputStream.read(messageSize);

            byte[] requestApiKey = new byte[2];
            inputStream.read(requestApiKey);

            byte[] requestApiKeyVersion = new byte[2];
            inputStream.read(requestApiKeyVersion);

            byte[] correlationId = new byte[4];
            inputStream.read(correlationId);

            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(messageSize);
            outputStream.write(correlationId);
            outputStream.write(35);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
