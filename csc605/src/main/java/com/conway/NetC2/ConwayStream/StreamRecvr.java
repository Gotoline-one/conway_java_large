// package com.conway.NetC2.ConwayStream;
package com.conway.NetC2.ConwayStream;

import java.net.InetAddress;
import com.conway.GameBoard.Board;
import com.conway.GameBoard.GameOfLife;
import com.conway.NetC2.AppClient;

import javafx.application.Platform;


public class StreamRecvr {
    int PORT = 9876;
    InetAddress address;
    GameOfLife game;
    Board board;
    AppClient client;
    /** in the future this will 
     * 1. setup a TCP connection to the server
     * 2. start the stream from the server to here 
     * 2a. open up the udp stream recieve to get info
    */

    public StreamRecvr( GameOfLife game){
        System.out.println("ConwayAppController: starting Client");
        this.game = game;
        board= game.getBoard(); 

    }
    
    void start(){
     try {
            client = new AppClient();
            while(true){
                board.setBoard( client.receiveData());
                game.printBoard();
                Thread.sleep(800);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    void stop(){
        client.close();
    }
    
}

