package com.conway.ConwayNetworked.ConwayStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPReceiverApp extends Application {

    // UI element to display latest UDP data.
    public Label dataLabel;

    @Override
    public void start(Stage primaryStage) {
        // Set up JavaFX UI
        dataLabel = new Label("Waiting for UDP data...");
        StackPane root = new StackPane(dataLabel);
        Scene scene = new Scene(root, 400, 200);

        primaryStage.setTitle("UDP Receiver Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start UDP receiver in its own thread so it won't block the UI
        setLabel(dataLabel);
        startUDPReceiver();
    }

    public void  setLabel(Label dataLabel){
        this.dataLabel = dataLabel;
    }
    public void startUDPReceiver() {
        // Create a new thread for the UDP receiver
        Thread udpReceiverThread = new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket(9876)) { // Bind to port 9876
                byte[] buffer = new byte[1024];
                while (true) {
                    // Prepare a packet for receiving data
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);  // This will block until data is received

                    // Convert received data to a String
                    String receivedData = new String(packet.getData(), 0, packet.getLength());

                    // Update the UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        dataLabel.setText(receivedData);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Mark the thread as daemon so it does not prevent the application from exiting
        udpReceiverThread.setDaemon(true);
        udpReceiverThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
