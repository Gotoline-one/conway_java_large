package com.conway.NetC2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.conway.GameBoard.Board;
import com.conway.GameBoard.GameOfLife;

public class AppServer {
    // Change these as needed
    public static final int DESTINATION_PORT = 9876;
    public static final String DESTINATION_IP = "127.0.0.1";

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
