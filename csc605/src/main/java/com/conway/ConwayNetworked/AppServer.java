package com.conway.ConwayNetworked;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.conway.GameBoard.Board;
import com.conway.GameBoard.GameOfLife;

public class AppServer {
    // Change these as needed
    public static final int DESTINATION_PORT = 9876;
    public static final String DESTINATION_IP = "127.0.0.1";
    public String SERVER_IP = "127.0.0.1";
    public int SERVER_PORT = 8765;

    private DatagramSocket socket;
    private InetAddress clientAddress;
    private int clientPort;
    GameBoardConverter serializer;

    public AppServer() throws SocketException, UnknownHostException {
        serializer = new GameBoardConverter();
        // Create a UDP socket (using any free local port)
        socket = new DatagramSocket();
        // Resolve the destination address and port (where the client is listening)
        clientAddress = InetAddress.getByName(DESTINATION_IP);
        clientPort = DESTINATION_PORT;

        System.out.println("UDP stream server started. Will send board to " 
            + clientAddress.getHostAddress() + ":" + clientPort);
    }

public void startTCPCommandListener(NetBoardController gameController) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                System.out.println("TCP command listener started on port " + SERVER_PORT);

                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                        String command = in.readLine();
                        System.out.println("Received command: " + command);

                        // Process the command
                        switch (command.toUpperCase()) {
                            case "START_GAME":
                                gameController.randomizeBoard();
                                out.println("Game started.");
                                break;
                            case "STOP_GAME":
                                out.println("Game stopped (no-op in this implementation).");
                                break;
                            case "RESET_GAME":
                                // gameController.resetBoard();
                                out.println("Game reset.");
                                break;
                            case "SET_SEED":
                                String seed = in.readLine();
                                gameController.setSeed(seed);
                                out.println("Seed set to: " + seed);
                                break;
                            case "LOAD_SEED":
                                String loadSeed = in.readLine();
                                gameController.loadSeed(loadSeed);
                                out.println("Seed loaded: " + loadSeed);
                                break;
                            default:
                                out.println("Unknown command.");
                                break;
                        }
                    } catch (Exception e) {
                        System.err.println("Error processing command: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void sendData(Board board) throws Exception {

        byte[] data = GameBoardConverter.serialize(board);
        DatagramPacket packet = new DatagramPacket(data, data.length, clientAddress, clientPort);
        socket.send(packet);
        System.out.println("Sent board data (" + data.length + " bytes).");
    }


    public static void main(String[] args) {
        GameOfLife game = new GameOfLife(10, 10);
        game.randomizeBoard();
        
        try {
            AppServer server = new AppServer();
            while(true){
                server.sendData(game.getBoard());
                game.printBoard();
                System.out.println();
                Thread.sleep(800);
                game.updateBoard();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
