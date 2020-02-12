package bg.sofia.uni.fmi.mjt.multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientRunnable implements Runnable {

    private Socket socket;

    public ClientRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while (true) {
                if (this.socket.isClosed()) { // socket is closed
                    System.out.println("Client socket is closed, stop waiting for server messages. ");
                    return;
                } else {
                    System.out.println(reader.readLine());

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}