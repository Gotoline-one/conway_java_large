package com.conway.ConwayNetworked.ConwayStream;

import java.net.InetAddress;

import com.conway.ConwayNetworked.AppClient;
import com.conway.ConwayNetworked.NetBoardController;
import com.conway.GameBoard.Board;
import com.conway.GameBoard.GameOfLife;



public class StreamRecvr {
    int PORT = 9876;
    InetAddress address;
    GameOfLife game;
    Board board;
    AppClient client;
    UDPReceiverApp udpReceiverApp;

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
    
    public void setController(NetBoardController gameController){
        udpReceiverApp.setStreamController((NetBoardController)gameController);
    }

    public boolean start(){
        if (udpReceiverApp == null){ return false;}
        System.out.println("Started");
        // udpReceiverApp.startUDPReceiver();
        return true;

    }
    public void stop(){
        client.close();
    }
    
}

