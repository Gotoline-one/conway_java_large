package com.conway;

import java.util.Arrays;

import com.conway.AppOptions.AppOptions;
import com.conway.ConwayApp.*;
import com.conway.GameBoard.*;
import com.conway.Utilities.CommandLineParser;
import com.conway.NetC2.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConwayApplication extends Application {
    GameBoardView gameView;
    GameOfLife gameLogic;
    public GameBoardController gameController;
    public static int WIDTH = 20, HEIGHT = 20;
    public static double TIME_LIMIT_SEC = 20;
    public static AppOptions options;
    public ConwayAppView view;
    public ConwayAppController appController;

    @Override
    public void start(Stage primaryStage) {

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

        gameController = new GameBoardController(gameLogic, gameView, TIME_LIMIT_SEC);

        view = new ConwayAppView(gameController);
        appController = new ConwayAppController(this);

        Scene scene = new Scene(view.getRoot());
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();

        appController.initialize(primaryStage);

          //TODO: TESTING ONLY 
        //   view.optionsView.openOptionsPane(null);
        // AppClient appClient = new AppClient();
        // appClient.daytimeClient();
        // AppServer appServer = new AppServer();
        
        
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
        System.out.println(Arrays.toString(args));
        dealWithOptions(args);
        launch(args);
    }
}
