package com.conway.ConwayNetworked.ConwayStream;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.conway.AppOptions.AppOptions;
import com.conway.ConwayNetworked.AppServer;
import com.conway.ConwayNetworked.GameBoardConverter;
import com.conway.GameBoard.Board;
import com.conway.GameBoard.GameOfLife;
import javafx.scene.control.Label;


// import javafx.scene.control.Label;

/**
 * in the future this will start a TCP server that sits and waits, and will:
 * 1. wait for multiple connections from clients to send board data to
 * 2. when a control-client connects, it will setup the board per instructions
 *      a. start board stream &/or game
 *      b. stop board steam   &/or game
 *      c. pause board stream &/or game
 * 3. when a view-client connects, it will let it know over tcp info of board 
 * 
 */
public class StreamSender {
    GameOfLife game;
    AppOptions options;
    InetAddress streamAddress;
    private DatagramSocket socket;
    public StreamSender(AppOptions options ){
        this.options = options;
        System.out.println("Start Server");
        if(options.flags.width && options.flags.height){
            game = new GameOfLife(options.height,options.width);
        }else{
            game = new GameOfLife(10,10);
        }
        
        game.randomizeBoard();
    }

    public void start() throws UnknownHostException, SocketException{
        System.out.println("Starting Stream Sender");
        socket = new DatagramSocket();
        streamAddress = InetAddress.getByName(options.streamAddressString);

        try {
            
            while(true){
                this.sendData(game.getBoard());
                game.printBoard();
                System.out.println();
                Thread.sleep(800);
                game.updateBoard();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void sendData(Board board) throws Exception {

        byte[] data = GameBoardConverter.serialize(board);
        DatagramPacket packet = new DatagramPacket(data, data.length, this.streamAddress, options.streamPort);
        socket.send(packet);
        System.out.println("Sent board data (" + data.length + " bytes).");
    }

}
