package com.conway;

import java.net.SocketException;
import java.net.UnknownHostException;

// import java.util.Arrays;

import com.conway.AppOptions.AppOptions;
import com.conway.ConwayApp.*;
import com.conway.ConwayNetworked.AppClient;
import com.conway.ConwayNetworked.AppServer;
import com.conway.ConwayNetworked.NetBoardController;
import com.conway.ConwayNetworked.ConwayStream.StreamRecvr;
import com.conway.ConwayNetworked.ConwayStream.StreamSender;
import com.conway.ConwayNetworked.ConwayStream.UDPReceiverApp;
import com.conway.GameBoard.*;
import com.conway.Utilities.CommandLineParser;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConwayApplication extends Application {
    GameBoardView gameView;
    GameOfLife gameLogic;
    public GameController gameController;
    public static int WIDTH = 20, HEIGHT = 20;
    public static double TIME_LIMIT_SEC = -1;
    public static AppOptions options;
    public ConwayAppView view;
    public ConwayAppController appController;

    AppClient appClient;
    AppServer appServer;
    public static void setWidth(int width){ WIDTH = width;}
    public static void setHeight(int height){ HEIGHT = height;}
    
    @Override
    public void start(Stage primaryStage) throws SocketException {

        // right now this is hard coded, will controlled programically in future
        // settting up as control client first 

        if (options.flags.height) {
            HEIGHT = options.height;
        }
        if (options.flags.width) {
            WIDTH = options.width;
        }
        if (options.flags.timeInSeconds) {
            TIME_LIMIT_SEC = options.timeInSeconds;
        }

        // Create the board view (which internally creates the model)
        gameView = new GameBoardView(HEIGHT, WIDTH);
        
        

        if(options.flags.seed)
            gameLogic = new GameOfLife(HEIGHT, WIDTH, options.seed);
        else
            gameLogic = new GameOfLife(HEIGHT, WIDTH);

            /**
             * Client Starts up and user chooses if controller or viewer
             *      *  when controller the user's gui interactions transer to server
             *      * when viewer, client requests board stream , and displays it
             * 
             * Server starts up game and waits for clients to controll it
             *      * when controlled it does what client says
             *      * when viewed it sends game stream to client
            //TODO: clean up start logic
            // for now assuming just viewer
            // do i need all thess passed in data? 
            /// is a gameLogic needed for a view only?
            **/
        if (options.client){
            
            appClient = new AppClient();
            gameController = new NetBoardController(gameLogic, gameView, appClient);
            System.out.println("So Far options.client");
            view = new ConwayAppView(gameController);
            appClient.startTCPControl((NetBoardController)gameController);

            appController = new ConwayAppController(this);

            Scene scene = new Scene(view.getRoot());
            primaryStage.setTitle("Conway's Game of Life");
            primaryStage.setScene(scene);
            primaryStage.show();
            appClient.startStreamCatcher();
             
            appController.initialize(primaryStage);

           
            
        } else if(options.server){ // send board to client, no screen needed

            try {

                gameController = new NetBoardController(gameLogic, appServer);
                // view = new ConwayAppView(gameController);
                System.out.printf("So Far before udpStreamer.startTest");
                appServer = new AppServer(options);
                // appServer.setupStream(options);
                appServer.startStream();

            } catch (SocketException | UnknownHostException e) {
                e.printStackTrace();
            }

        }else{
            gameController = new GameBoardController(gameLogic, gameView, TIME_LIMIT_SEC);
            view = new ConwayAppView(gameController);
            appController = new ConwayAppController(this);

            Scene scene = new Scene(view.getRoot());
            primaryStage.setTitle("Conway's Game of Life");
            primaryStage.setScene(scene);
            primaryStage.show();
    
            appController.initialize(primaryStage);

        }
       
    }


    private static void dealWithOptions(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        options = parser.parseArguments(args);

        if (options.showHelp) {
            parser.printHelp();
            System.exit(0);
            return;
        }

        if (args.length >= 2 && options.width > 0 && options.height > 0) {
            WIDTH = options.width;
            HEIGHT = options.height;
        }


    }


    public static void main(String[] args) {
        dealWithOptions(args);
        
    
        System.out.print("Stand Alone");
        launch(args);
        
    }
}
