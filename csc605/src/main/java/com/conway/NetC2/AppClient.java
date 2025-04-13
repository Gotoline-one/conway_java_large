package com.conway.NetC2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import com.conway.GameBoard.Board;

public class AppClient {
    public static final int PORT = 9876;
    // The IP "0.0.0.0" is usually used for binding a server socket;
    // for a client that listens, you may simply bind to the port.
    
    private DatagramSocket socket;
    GameBoardConverter serializer;
    // You can maintain a persistent buffer, or allocate it per receive call.
    byte[] buffer;

    public AppClient() throws SocketException{
        serializer = new GameBoardConverter();
        // Bind the socket to a specific port if needed.
        // If you want the client to listen on PORT, bind it directly:
        socket = new DatagramSocket(PORT);
        // buffer = new byte[210];
        buffer = new byte[65535];

    }

    public Board receiveData() {
        try {
            System.out.println("UDP client listening on port " + PORT);
            
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            
            // Use only the valid received data
            ByteBuffer byteBuffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength());
            // buffer = packet.getData();
            // System.out.printf("buffer: \n%b\n",buffer);

            // Deserialize using ByteBuffer from your converter/adapter class
            Board board = GameBoardConverter.deserialize(byteBuffer);
            // Board board = GameBoardConverter.deserialize(buffer);

            return board;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Optionally, add a close method to clean up resources:
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
