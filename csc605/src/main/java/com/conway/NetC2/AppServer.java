package com.conway.NetC2;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

// import com.google.protobuf.InvalidProtocolBufferException;

// import javafx.application.Platform;

// import java.nio.*;
// import java.nio.channels.*;
// import java.net.*;
// import java.util.*;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
public class AppServer {
    public static int DEFAULT_PORT = 19;
    
    public AppServer(){
           try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("TCP Server listening on port 12345");
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    InputStream in = clientSocket.getInputStream();

                    // Read the 4-byte length prefix
                    byte[] lengthBuffer = new byte[4];
                    in.read(lengthBuffer);
                    int dataLength = ByteBuffer.wrap(lengthBuffer).getInt();

                    // Read the rest of the bytes based on the length
                    byte[] data = new byte[dataLength];
                    int bytesRead = 0;
                    while (bytesRead < dataLength) {
                        int read = in.read(data, bytesRead, dataLength - bytesRead);
                        if (read < 0) {
                            throw new Exception("Unexpected end of stream");
                        }
                        bytesRead += read;
                    }
                    
                    // Deserialize the protobuf message
                    BasicCommands.Command command = BasicCommands.Command.parseFrom(data);
                    System.out.println("Received Command: " + command.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
}