package bg.sofia.uni.fmi.mjt.multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Client {
    private static final int PORT = 8200;
    private static final String HOST = "localhost";
    private static final String QUIT = "quit";
    private static final int MAX_THREADS = 9;

    public static void main(String[] args) throws IOException {

        new Client().run();
    }

    public void run() throws IOException {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            ClientRunnable clientRunnable = new ClientRunnable(socket);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREADS);
            executor.execute(clientRunnable);

            System.out.println("Connected to the server.");
            while (true) {
                String message = scanner.nextLine();
                if (message.equals(QUIT)) {
                    break;
                }

                writer.println(message);
            }
        } catch (IOException e) {
            System.out.println("Cannot connect to the server!");
            e.printStackTrace();
        }
    }
}