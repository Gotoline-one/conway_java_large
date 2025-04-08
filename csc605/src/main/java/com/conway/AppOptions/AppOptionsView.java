package com.conway.AppOptions;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppOptionsView {
    protected AppOptionsController controller;
    protected Stage stage;


    protected TextField seedTextField;
    protected TextField fnameTextField;
    protected ChoiceBox<String> fileTypeChoiceBox; 


    public AppOptionsView (){
        this.controller = new AppOptionsController(this);
        
    }

    public AppOptionsController getController() {
        return this.controller;
    }

    public void  openOptionsPane(javafx.event.ActionEvent e) {
        this.stage = new Stage();
        stage.setTitle("Settings");
         VBox root = new VBox();
        // Set preferred size (the FXML specified 600x400)
        root.setPrefWidth(600);
        root.setPrefHeight(400);
        // (The FXML min/max sizes were set to "-Infinity", which generally means “unspecified” in FXML.)

        // === TabPane Setup ===
        TabPane tabPane = new TabPane();
        tabPane.setPrefWidth(600);
        tabPane.setPrefHeight(355);
        // FXML: tabClosingPolicy="UNAVAILABLE"
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        // In FXML, VBox.vgrow="ALWAYS" is used on the TabPane
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        // ----- First Tab: "Main Options" -----
        Tab tabMainOptions = new Tab("Main Options");

        // Create content container (AnchorPane)
        AnchorPane anchorMain = new AnchorPane();
        anchorMain.setPrefWidth(200);
        anchorMain.setPrefHeight(180);
        // (minWidth and minHeight default to 0)

        // Create and position TextField
        seedTextField = new TextField("TEST");
        seedTextField.setLayoutX(179);
        seedTextField.setLayoutY(44);

        // Create and position Label ("Default Seed")
        Label labelDefaultSeed = new Label("Default Seed");
        labelDefaultSeed.setLayoutX(24);
        labelDefaultSeed.setLayoutY(49);
        labelDefaultSeed.setPrefWidth(201);
        labelDefaultSeed.setPrefHeight(19);

        // Add controls to the AnchorPane
        anchorMain.getChildren().addAll(seedTextField, labelDefaultSeed);
        // Set the AnchorPane as the content for the tab
        tabMainOptions.setContent(anchorMain);

        // ----- Second Tab: "File Save Options" -----
        Tab tabFileSave = new Tab("File Save Options");

        // Create content container (AnchorPane)
        AnchorPane anchorFile = new AnchorPane();
        anchorFile.setPrefWidth(200);
        anchorFile.setPrefHeight(180);

        // Create and position TextField
        fnameTextField = new TextField("aaaaaaa");
        fnameTextField.setLayoutX(240);
        fnameTextField.setLayoutY(86);

        // Create and position Label ("Default Filename")
        Label labelFilename = new Label("Default Filename");
        labelFilename.setLayoutX(14);
        labelFilename.setLayoutY(91);
        labelFilename.setPrefWidth(121);
        labelFilename.setPrefHeight(19);

        // Create and position CheckBox ("Save stats to file")
        CheckBox checkBoxSaveStats = new CheckBox("Save stats to file");
        checkBoxSaveStats.setMnemonicParsing(false);
        checkBoxSaveStats.setLayoutX(239);
        checkBoxSaveStats.setLayoutY(278);

        // Create and position Label ("File Type")
        Label labelFileType = new Label("File Type");
        labelFileType.setLayoutX(14);
        labelFileType.setLayoutY(134);
        labelFileType.setPrefWidth(121);
        labelFileType.setPrefHeight(19);

        // Create and position ChoiceBox
        ChoiceBox<String> choiceBoxFileType = new ChoiceBox<>();
        choiceBoxFileType.setLayoutX(240);
        choiceBoxFileType.setLayoutY(129);
        // choiceBoxFileType.setPrefWidth(150);
        choiceBoxFileType.getItems().addAll(".json", ".csv"); 
        // (You can add items to the ChoiceBox, e.g., choiceBoxFileType.getItems().addAll("Option1", "Option2"); )
        
        // Add controls to the AnchorPane
        anchorFile.getChildren().addAll(fnameTextField, labelFilename, checkBoxSaveStats, labelFileType, choiceBoxFileType);
        tabFileSave.setContent(anchorFile);

        // TODO: future network options
        // // ----- Third Tab: "Network Options" -----
        // Tab tabNetwork = new Tab("Network Options");

        // // Create an empty AnchorPane for Network Options
        // AnchorPane anchorNetwork = new AnchorPane();
        // anchorNetwork.setPrefWidth(200);
        // anchorNetwork.setPrefHeight(180);
        // tabNetwork.setContent(anchorNetwork);

        // Add all tabs to the TabPane
        // tabPane.getTabs().addAll(tabMainOptions, tabFileSave, tabNetwork);
        tabPane.getTabs().addAll(tabMainOptions, tabFileSave);

        // === HBox for Buttons at the Bottom ===
        HBox buttonBar = new HBox();
        // In FXML: VBox.vgrow="ALWAYS" is used on the HBox as well
        VBox.setVgrow(buttonBar, Priority.ALWAYS);

        // Create "Save" button
        Button btnSave = new Button("Save");
        btnSave.setMnemonicParsing(false);

        // Create a spacer Pane with prefWidth (and allow it to grow)
        Pane spacer1 = new Pane();
        // spacer1.setPrefWidth(200);
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        // Create "Cancel" button
        Button btnCancel = new Button("Cancel");
        btnCancel.setMnemonicParsing(false);
        btnCancel.setOnAction(event -> controller.handleCancelBtn(event));

        // Create second spacer Pane
        Pane spacer2 = new Pane();
        // spacer2.setPrefWidth(200);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        // Create "Exit" button
        Button btnExit = new Button("Exit");
        btnExit.setMnemonicParsing(false);
        btnExit.setOnAction( event -> controller.handleCloseWindow(event) );

        // Add buttons and spacers to the HBox in order
        buttonBar.getChildren().addAll(btnSave, spacer1, btnCancel, spacer2, btnExit);

        // === Assemble the Root VBox ===
        // The root VBox contains the TabPane and the HBox for the buttons.
        root.getChildren().addAll(tabPane, buttonBar);

        // Create the scene using the root container
        Scene scene = new Scene(root, 400, 400);
   
        stage.setScene(scene);
        stage.show();

        

    }


}
