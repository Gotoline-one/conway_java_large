package com.conway.MyApp;

import com.conway.GameBoard.*;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MyAppView {
    private VBox root;
    private MenuItem menuItemQuit, menuItemStart, menuItemReset, menuItemStop;
    private Label statusLabel;
    private Label fpsCounterLabel;

    private GridPane gameBoard;
    private GameBoardController gameController;

    public MyAppView(GameBoardController gbc){

        this.gameController = gbc;
        GameBoardView gbv = gameController.getView();
        setBoard(gbv.getView());

        
        buildUI();
    }
    
    private void buildUI(){
        root = new VBox();
        
        
            Menu menu         = new Menu("Menu"); 
            HBox menuToolHBox = new HBox(buildMenuBar(menu));

            Button startButton = new Button("Start");
            startButton.setOnAction(e -> {
                gameController.startGame();
            });

            Button stopButton = new Button("Stop");
            stopButton.setOnAction(e -> {
                gameController.stopGame();
            }); 

            Button resetButton = new Button("Reset");   
            resetButton.setOnAction(e -> {
                gameController.resetGame();
            });

            TextField seedField = new TextField("Seed");
            seedField.setOnAction(e -> {
                gameController.setSeed(seedField.getText());
            }); 

            Button loadSeeButton = new Button("Load Seed");
            loadSeeButton.setOnAction(e -> {
                gameController.loadSeed(seedField.getText());
            });

           //TODO: Add in the save and load buttons
            // Button saveCSVButton = new Button("Save CSV");
            // saveCSVButton.setOnAction(e -> {
            //     gameController.saveBoardToCSVFile();
            // });

            // Button saveJSONButton = new Button("Save JSON");
            // saveJSONButton.setOnAction(e -> {
            //     gameController.saveBoardToJSONFile();
            // });

            // Button loadCSVButton = new Button("Load CSV");
            // loadCSVButton.setOnAction(e -> {
            //     gameController.loadBoardFromCSVFile();
            // });

            // Button loadJSONButton = new Button("Load JSON");
            // loadJSONButton.setOnAction(e -> {
            //     gameController.loadBoardFromJSONFile();
            // });

            // menuToolHBox.getChildren().addAll(startButton, stopButton, resetButton, seedField, saveCSVButton, saveJSONButton, loadCSVButton, loadJSONButton);
           menuToolHBox.getChildren().addAll(startButton, stopButton, resetButton, seedField, loadSeeButton);

         
            
            fpsCounterLabel = new Label();
            menuToolHBox.getChildren().add(fpsCounterLabel);
        root.getChildren().add(menuToolHBox);

        if(gameBoard !=null){
            root.getChildren().add(gameBoard);
        }
        
        
            statusLabel = new Label("initializing");
            HBox statusBox = new HBox(statusLabel);

        root.getChildren().add(statusBox);


    }

    private MenuBar buildMenuBar(Menu menu){
       

        menuItemStart = new MenuItem("Start"); 
        menu.getItems().add(menuItemStart); 

        menuItemStop = new MenuItem("Stop"); 
        menu.getItems().add(menuItemStop); 
        
        menuItemReset = new MenuItem("Reset");
        menu.getItems().add(menuItemReset);     

        menuItemQuit = new MenuItem("Quit");
        menu.getItems().add(menuItemQuit);

        
        
        
        return new MenuBar(menu); 
        
    }


    public void setfpsCounterLabel(String newLabel){
        fpsCounterLabel.setText(newLabel);
    }

    public Parent getRoot(){
        return root;
    }
    
    public void setBoard(GridPane gp){
        this.gameBoard = gp;
    }

    public MenuItem getQMenuItem(){
        return menuItemQuit;
    }
    public MenuItem getMenuItemStart() {
        return menuItemStart;
    }

    public MenuItem getMenuItemStop() {
        return menuItemStop;
    }

    public MenuItem getMenuItemReset() {
        return menuItemReset;
    }

}
