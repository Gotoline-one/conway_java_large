package com.conway.ConwayNetworked.ConwayStream;

import com.conway.AppOptions.AppOptions;
import com.conway.ConwayNetworked.AppServer;
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

    public StreamSender(AppOptions options ){
        System.out.println("Start Server");
        if(options.flags.width && options.flags.height){
            game = new GameOfLife(options.height,options.width);
        }else{
            game = new GameOfLife(10,10);

        }
        game.randomizeBoard();
    }

    public void startTest(Label test){
        UDPReceiverApp udpRcvApp = new UDPReceiverApp();
        udpRcvApp.setLabel(test);
        udpRcvApp.startUDPReceiver();



    }
    public void start(){
        
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
