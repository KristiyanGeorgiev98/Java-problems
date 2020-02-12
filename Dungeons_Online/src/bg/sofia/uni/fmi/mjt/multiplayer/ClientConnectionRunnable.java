package bg.sofia.uni.fmi.mjt.multiplayer;

import bg.sofia.uni.fmi.mjt.actors.Minion;
import bg.sofia.uni.fmi.mjt.actors.Player;
import bg.sofia.uni.fmi.mjt.field.MapImpl;
import bg.sofia.uni.fmi.mjt.field.Treasure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientConnectionRunnable implements Runnable {
    private static final String MOVE = "move";
    private static final String FIGHT = "fight";
    private static final String ATTACK = "attack";
    private static final String PICK = "pick";
    private static final String EXCHANGE = "exchange";
    private static final String CHECK = "check";
    private static final int XP_MULTIPLIER = 30;

    private int uniqueNumber;
    private Socket socket;
    private MapImpl map;

    public ClientConnectionRunnable(int uniqueNumber, Socket socket, MapImpl map) {
        this.uniqueNumber = uniqueNumber;
        this.socket = socket;
        this.map = map;
    }

    public Player getCurrentPlayer() {
        Map<Player, Socket> playersMap = Server.getPlayers();
        for (Player player : playersMap.keySet()) {
            if (player.getUniqueNumber() == uniqueNumber) {
                return player;
            }
        }
        return null;
    }

    public void sendSeparately(Player player, String message, PrintWriter writer) throws IOException {
        Socket toSocket = Server.getSocket(player);
        if (toSocket == null) {
            return;
        }
        PrintWriter toWriter = new PrintWriter(toSocket.getOutputStream(), true);
        toWriter.println(message);
    }

    private Player findAnotherPlayerInTheCell() {
        Player currentPlayer = getCurrentPlayer();
        Map<Player, Socket> playersMap = Server.getPlayers();
        for (Player otherPlayer : playersMap.keySet()) {
            if (currentPlayer.getRawPosition() == otherPlayer.getRawPosition()
                    && currentPlayer.getColumnPosition() == otherPlayer.getColumnPosition()) {
                if (currentPlayer != otherPlayer) {
                    return otherPlayer;
                }
            }
        }
        return null;
    }

    //Player should write "quit" after the info that he is dead
    private void die(Player player) throws IOException {
        Server.getPlayers().remove(player);
        map.getMapArray()[player.getRawPosition()][player.getColumnPosition()].setSign('.');

    }

    private void commandMove(String[] tokens, boolean isMinion, boolean isAnotherPlayer, Player currentPlayer) {
        final int index = 1;
        if (tokens.length == 2) {
            int direction = tokens[index].charAt(0) - '0';
            Map<Player, Socket> playersMap = Server.getPlayers();
            for (Player player : playersMap.keySet()) {
                if (player.getUniqueNumber() == uniqueNumber) {
                    map.move(direction, player);
                }
            }
        }
    }

    private void commandFightMinion(Minion minion, Player currentPlayer, PrintWriter writer)
            throws IOException {
        if (currentPlayer.getLevel() < minion.getLevel()) {
            writer.println("You don't have level to fight this minion!");
        } else {
            int minionLevel = minion.getLevel();
            while (minion.isAlive() && currentPlayer.isAlive()) {
                minion.setHealth(minion.getHealth() - currentPlayer.getAttack() + minion.getDefense() / 10);
                currentPlayer.setHealth(currentPlayer.getHealth() - minion.getAttack() + currentPlayer.getDefense() / 10);
            }

            if (currentPlayer.isAlive()) {
                writer.println("Congrats!You killed a minion!");
                writer.println("+ " + minionLevel * XP_MULTIPLIER + " experience");
                currentPlayer.setExperience(minionLevel * XP_MULTIPLIER);
                currentPlayer.levelUp();
                int rawPosition = minion.getRawPosition();
                int columnPosition = minion.getColumnPosition();
                map.getMapArray()[rawPosition][columnPosition].setMinionInTheCell(false);
                map.putMinionOnMap(uniqueNumber + 1);
            } else {
                writer.println("You died!!!");
                die(currentPlayer);


            }
        }

    }

    private void commandCheck(Player currentPlayer, PrintWriter writer) {
        if (!currentPlayer.getPlayerBackpack().isEmpty()) {
            for (int i = 0; i < getCurrentPlayer().getPlayerBackpack().getCapacity(); ++i) {
                if (currentPlayer.getPlayerBackpack().getBackpack()[i] != null) {
                    writer.println(currentPlayer.getPlayerBackpack().getBackpack()[i].getType());
                } else continue;
            }
        } else {
            writer.println("The backpack is empty!");
        }
    }

    private void commandPickTreasure(Player currentPlayer, Treasure treasure, PrintWriter writer) {
        //currentPlayer.setTreasureInPlayerBackpack(treasure);
        //Weapon
        if (treasure.getAttack() > 0
                && treasure.getManaCost() == 0) {
            writer.println("You picked a weapon!");

            //if hero has enough level
            if (treasure.getLevel() >= treasure.getLevel()) {
                writer.println("You equiped the weapon!");
                currentPlayer.setAttack(currentPlayer.getAttack() + treasure.getAttack());
            }
            //if hero doesn't have enough level -> pick the weapon
            else {
                currentPlayer.setTreasureInPlayerBackpack(treasure);
            }

            //Spell
        } else if (currentPlayer.getLevel() >= treasure.getLevel()
                && treasure.getManaCost() > 0) {
            writer.println("You picked a spell!");
            //if hero has enough level and mana
            if (treasure.getLevel() >= treasure.getLevel()
                    && currentPlayer.getMana() > treasure.getManaCost()) {
                writer.println("You used the spell!");
                currentPlayer.setAttack(currentPlayer.getAttack() + treasure.getAttack());
            }
            //if hero doesn't have enough level -> pick the spell
            else {
                currentPlayer.setTreasureInPlayerBackpack(treasure);
            }
        }
        //mana potion
        else if (treasure.getMana() > 0) {
            writer.println("You picked a mana potion!");
            currentPlayer.setTreasureInPlayerBackpack(treasure);

        }
        //health potion
        else if (treasure.getHealth() > 0) {
            writer.println("You picked a health potion!");
            currentPlayer.setTreasureInPlayerBackpack(treasure);

        }
    }

    private void commandFightEnemy(Player otherPlayer, Player currentPlayer, PrintWriter writer)
            throws IOException {
        int enemyLevel = otherPlayer.getLevel();

        if (currentPlayer.getAttack() == otherPlayer.getDefense()
                && currentPlayer.getDefense() == otherPlayer.getAttack()) {

            writer.println("You died!!!");
            sendSeparately(otherPlayer, "You died!!!", writer);

            die(currentPlayer);
            die(otherPlayer);
        }
        while (otherPlayer.isAlive() && currentPlayer.isAlive()) {
            otherPlayer.setHealth(otherPlayer.getHealth() - currentPlayer.getAttack() + otherPlayer.getDefense());
            currentPlayer.setHealth(currentPlayer.getHealth() - otherPlayer.getAttack() + currentPlayer.getDefense());
        }

        if (currentPlayer.isAlive()) {
            writer.println("Congrats!You killed another player!");
            writer.println("+ " + enemyLevel * XP_MULTIPLIER + " experience");
            currentPlayer.setExperience(enemyLevel * XP_MULTIPLIER);
            currentPlayer.levelUp();
            die(otherPlayer);

        } else {
            writer.println("You died!!!");
            die(currentPlayer);
        }
    }

    private void commandExchange(Player currentPlayer, Player otherPlayer, PrintWriter writer)
            throws IOException {
        if (!currentPlayer.getPlayerBackpack().isEmpty()
                && !otherPlayer.getPlayerBackpack().isEmpty()) {
            Treasure currentPlayerTreasure = currentPlayer.getPlayerBackpack().getBackpack()[0];
            Treasure otherPlayerTreasure = currentPlayer.getPlayerBackpack().getBackpack()[0];

            currentPlayer.setTreasureInPlayerBackpack(otherPlayerTreasure);
            otherPlayer.setTreasureInPlayerBackpack(currentPlayerTreasure);
            writer.println("You exchanged treasures with other player!");
        } else {
            writer.println("There is nothing to exchange!");
        }
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            while (true) {

                map.printMap();
                writer.println(map.convertToStringBuilder());
                String commandInput = reader.readLine();

                Player currentPlayer = getCurrentPlayer();
                int rawPosition = currentPlayer.getRawPosition();
                int columnPosition = currentPlayer.getColumnPosition();

                boolean isMinion = map.getMapArray()[rawPosition][columnPosition].isMinionInTheCell();
                boolean isTreasure = map.getMapArray()[rawPosition][columnPosition].isTreasureInTheCell();
                boolean isAnotherPlayer;
                if (findAnotherPlayerInTheCell() == null) {
                    isAnotherPlayer = false;
                } else {
                    isAnotherPlayer = true;
                }

                if (commandInput != null) {

                    final String whitespace = "\\s+";
                    String[] tokens = commandInput.split(whitespace);
                    final int commandIndex = 0;
                    String command = tokens[commandIndex];

                    //Player move on the map
                    if (command.equals(MOVE)) {
                        commandMove(tokens, isMinion, isAnotherPlayer, currentPlayer);

                        //fight with a Minion
                    } else if (command.equals(FIGHT) && isMinion) {

                        Minion minion = map.getMinion(rawPosition, columnPosition);
                        commandFightMinion(minion, currentPlayer, writer);

                        //pick a Treasure
                    } else if (command.equals(PICK) && isTreasure) {

                        Treasure treasure = map.getTreasure(rawPosition, columnPosition);
                        commandPickTreasure(currentPlayer, treasure, writer);
                        map.getMapArray()[rawPosition][columnPosition].setTreasureInTheCell(false);

                        //fight with another player

                    } else if (command.equals(ATTACK) && isAnotherPlayer) {

                        Player otherPlayer = findAnotherPlayerInTheCell();
                        commandFightEnemy(otherPlayer, currentPlayer, writer);

                        //exchange the first elements of the backpacks
                    } else if (command.equals(EXCHANGE) && isAnotherPlayer) {
                        Player otherPlayer = findAnotherPlayerInTheCell();
                        commandExchange(currentPlayer, otherPlayer, writer);

                        //show the treasures in the bag
                    } else if (command.equals(CHECK)) {
                        commandCheck(currentPlayer, writer);
                    }
                }
                map.update();
                Map<Player, Socket> allUsers = Server.getPlayers();
                for (Player player : allUsers.keySet()) {
                    if (player == currentPlayer) {
                        continue;
                    }
                    sendSeparately(player, map.convertToStringBuilder().toString(), writer);
                }
            }
        } catch (IOException e) {
            System.out.println("Socket is closed!");
            e.addSuppressed(new RuntimeException());
        }
    }
}