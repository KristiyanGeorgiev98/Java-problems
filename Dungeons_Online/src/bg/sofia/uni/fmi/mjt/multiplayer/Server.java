package bg.sofia.uni.fmi.mjt.multiplayer;

import bg.sofia.uni.fmi.mjt.actors.Player;
import bg.sofia.uni.fmi.mjt.field.MapImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {

    private static final String START = "start";
    private static final int PORT = 8200;
    private static final int MAX_THREADS = 9;
    private int uniqueNumber = 0;
    private MapImpl map = new MapImpl();
    private static ConcurrentMap<Player, Socket> players = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new Server().run();
    }

    public static Map<Player, Socket> getPlayers() {
        return players;
    }

    public static Socket getSocket(Player player) {
        return players.get(player);
    }


    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on localhost:" + PORT);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREADS);

            while (true) {
                Socket socket = serverSocket.accept();
                final String clientConnectedMessage = "A client connected to server " + socket.getInetAddress();
                System.out.println(clientConnectedMessage);

                synchronized (socket) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = reader.readLine();
                    String[] tokens = line.split("\\s+");

                    ClientConnectionRunnable runnable = new ClientConnectionRunnable(uniqueNumber, socket, map);

                    if (tokens[0].equals(START)) {
                        Player player = new Player();
                        player.setUniqueNumber(uniqueNumber);
                        map.putPlayerOnMap(player);
                        map.putMinionOnMap(uniqueNumber);
                        map.putTreasureOnMap(uniqueNumber);

                        players.put(player, socket);

                        final String usernameConnected = "Player " + uniqueNumber + " connected";
                        System.out.println(usernameConnected);

                        ++uniqueNumber;

                        executor.execute(runnable);
                    }
                }
            }
        } catch (IOException e) {
            final String errorMessage = "Perhaps another server is running on port " + PORT;
            System.out.println(errorMessage);
            throw new RuntimeException();
        }
    }

}