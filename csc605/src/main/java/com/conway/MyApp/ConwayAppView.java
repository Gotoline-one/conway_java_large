package com.conway.MyApp;

import com.conway.AppOptions.AppOptionsController;
import com.conway.AppOptions.AppOptionsView;
import com.conway.GameBoard.GameBoardController;
import com.conway.GameBoard.GameBoardView;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConwayAppView {
    private VBox root;
    private MenuItem menuItemQuit, menuItemStart, menuItemReset, menuItemStop, menuItemSettings;
    private Label statusLabel;
    private Label fpsCounterLabel;

    private GridPane gameBoard;
    private GameBoardController gameController;
    public AppOptionsView optionsView; 
    public AppOptionsController optionsController; 
    public ConwayAppView(GameBoardController gbc){

        this.gameController = gbc;
        GameBoardView gbv = gameController.getView();
        setBoard(gbv.getView());

        
        buildUI();

      
    }
    
    private void buildUI(){
        root = new VBox();
        optionsView = new AppOptionsView();
        optionsController = optionsView.getController();
        
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

            TextField seedField = new TextField("3141592653589793238");
            seedField.setOnAction(e -> {
                gameController.setSeed(seedField.getText());
            }); 

            Button loadSeeButton = new Button("Load Seed");
            loadSeeButton.setOnAction(e -> {
                gameController.loadSeed(seedField.getText());
            });


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


        menuItemSettings = new MenuItem("Settings");
        menuItemSettings.setOnAction(optionsView::openOptionsPane);
        menu.getItems().add(menuItemSettings);

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
