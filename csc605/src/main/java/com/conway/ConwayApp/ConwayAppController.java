package com.conway.ConwayApp;

import com.conway.*;
import com.conway.AppOptions.*;
import com.conway.GameBoard.*;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConwayAppController {
    private ConwayApplication model;
    private final ConwayAppView view;
    private final AppOptions options;
    private final GameBoardController gameController;

    VBox mainNode;
    Label fpsCounterLabel;
    Label statusLabel;

    public ConwayAppController( ConwayApplication myModel) {

        this.view = myModel.view;
        this.options = ConwayApplication.options;
        this.mainNode = (VBox)view.getRoot();
        this.fpsCounterLabel = (Label) mainNode.lookup("#fpsCounterLabel"); 
        this.statusLabel = (Label) mainNode.lookup("#statusLabel");
        
        this.gameController = myModel.gameController;// gameController;

        if (model != null && model.appController != null)
            model.appController.fpsCounterLabel.setText("starting");

        // Register main application events
        if (view.getQMenuItem() == null) {
            System.out.println("getQMenuItem is null");
        } else {
            view.getQMenuItem().setOnAction(this::handleQuit);
        }

        if(view.getMenuItemStart() == null){
            System.out.println("getMenuItemStart is null");
        }else{
            ((MenuItem) view.getMenuItemStart()).setOnAction(e -> {
                gameController.startGame();
            });
        }

        if(view.getMenuItemStop() == null){
            System.out.println("getMenuItemStop is null");  
        }else{
            ((MenuItem) view.getMenuItemStop()).setOnAction(e -> {
                gameController.stopGame();
            });
        }

        if(view.getMenuItemReset() == null){
            System.out.println("getMenuItemReset is null");
        }else{
            ((MenuItem) view.getMenuItemReset()).setOnAction(e -> {
                gameController.resetGame();
            });
        }


        if (options.debug) {
            System.out.println("ConwayAppController initialized.");
        }
            
    }

    public void initialize(Stage primaryStage) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            String fname = "default";
            if (options.flags.filename) {
                fname = options.filename;
            }
            if(options.jsonOutput) {
                System.out.println("Saving to JSON file: " + fname + ".json");
            }
            if(options.csvOutput) {
                System.out.println("Saving to CSV file: " + fname + ".csv");
            }

            System.out.println("Saving to JSON file: " + options.jsonOutput);
            if (options.jsonOutput) {
                if(options.debug) {System.out.println("Saving to JSON file: " + fname + ".json");}
                gameController.saveBoardToJSONFile(new File("./" + fname + ".json"));
            }

            if (options.csvOutput) {
                gameController.saveBoardToCSVFile(new File("./" + fname + ".csv"));
            }

            if (options.debug) System.out.println("Shutdown hook triggered (Ctrl+C or SIGTERM).");
            cleanupBeforeExit(); // Final save/flush/log
        }));

        primaryStage.setOnCloseRequest(event -> {
            if (options.debug) System.out.println("Window close detected.");
            cleanupBeforeExit(); // Called also on GUI close
        });

        if (options.quitOnEnd) {
            if(options.debug)   {
                System.out.println("Setting end of game time "+ options.timeInSeconds); 
            }
            
            gameController.setOnEndGame(() -> {
                if (options.debug) System.out.println("Game Ended");
                cleanupBeforeExit(); // Final save/flush/log
                primaryStage.close(); // Close the application window
                System.exit(0);
            });
        }
    }

    public void handleQuit(ActionEvent actionEvent) {
        this.cleanupBeforeExit();
        System.exit(0);
    }

    public void cleanupBeforeExit() {
        try{
            view.optionsController.handleAppExit();
        }catch(IllegalStateException e){
            e.printStackTrace();
        }
    }

    public void updateFPS(double fps) {
        fpsCounterLabel.setText(String.valueOf(fps));
    }

    public void setStatus(String newStatus) {
        statusLabel.setText(newStatus);
    }
}
