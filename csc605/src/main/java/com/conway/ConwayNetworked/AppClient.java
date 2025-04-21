package com.conway.ConwayNetworked;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;

import com.conway.ConwayNetworked.ConwayStream.UDPReceiverApp;
import com.conway.GameBoard.GameEvent;

public class AppClient {
    // public String SERVER_IP = "192.168.100.136";
    public int SERVER_PORT = 8765;
    NetBoardController netBoardController;
    // public static final int PORT = 9876;
    private int clientPort =9876;
    UDPReceiverApp udpReceiverApp;

    // The IP "0.0.0.0" is usually used for binding a server socket;
    // for a client that listens, you may simply bind to the port.
    
    private DatagramSocket socket;
    GameBoardConverter serializer;
    // You can maintain a persistent buffer, or allocate it per receive call.
    byte[] buffer;

    public AppClient() {
    }

    public void startStreamCatcher(){
        System.out.println("\n\npublic void startStreamCatcher()\n\n");
        udpReceiverApp = new UDPReceiverApp(netBoardController);
        udpReceiverApp.setOnBoardUpdate(() -> {
            System.out.println("Recieved new board " + System.currentTimeMillis());
            netBoardController.updateGameboard();
        });

        udpReceiverApp.startUDPReceiver(clientPort);
    }

    
    // Optionally, add a close method to clean up resources:
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public void startTCPControl(NetBoardController gameController) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void setOnBoardUpdate(GameEvent event) {
        
    }
    public void setController(NetBoardController netBoardController) {
        this.netBoardController = netBoardController;
    }

}
