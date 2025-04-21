package com.conway.ConwayNetworked.ConwayStream;

import javafx.application.Platform;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

import com.conway.ConwayNetworked.*;
import com.conway.GameBoard.*;
import com.google.protobuf.*;

// public class UDPReceiverApp extends Application {
public class UDPReceiverApp {

    public static Board latestBoard; 
    NetBoardController streamBoardController;
    StreamEvent onBoardUpdate;

    // public int streamPort =9876;

    public UDPReceiverApp(NetBoardController streamBoardController){
        this.streamBoardController = streamBoardController;
    }

    public void setStreamController(NetBoardController streamBoardController){
        this.streamBoardController = streamBoardController;
    }

    public void startUDPReceiver(int streamPort) {
        // Create a new thread for the UDP receiver
        Thread udpReceiverThread = new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket(streamPort)) { 
                byte[] buffer = new byte[1024];
                System.out.print("trying ");

                while (true) {
                    System.out.print(". ");
                    // Prepare a packet for receiving data
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);  // This will block until data is received

                    // Convert received data to a String
                    // String receivedData = new String(packet.getData(), 0, packet.getLength());
                    ByteBuffer byteBuffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength());
                    try{
                        latestBoard = GameBoardConverter.deserialize(byteBuffer);
                        // Update the UI on the JavaFX Application Thread
                        Platform.runLater(() -> {
                            System.out.print("+ ");
                                boardUpdate();
                        });
                    }catch (InvalidProtocolBufferException e){
                        System.out.println("Recieved bad protocol on port. Is someone stepping on us?");
                    }

                }
            } catch (Exception e) {

                System.out.println("\n\nEXCEPTION \n\n");
                e.printStackTrace();
            }
        });

        // Mark the thread as daemon so it does not prevent the application from exiting
        udpReceiverThread.setDaemon(true);
        udpReceiverThread.start();
    }

    public void setOnBoardUpdate(StreamEvent event){//getBoardUpdate
        this.onBoardUpdate = event;
    }

    public void boardUpdate(){
        if(onBoardUpdate !=null){
            onBoardUpdate.execute();
        }else{
            System.out.print("--");
        }
    }

}
