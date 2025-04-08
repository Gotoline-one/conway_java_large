package com.conway.MyApp;

import javafx.application.Platform;

public class AppOptionsController {
    AppOptionsView view; 

    public AppOptionsController(AppOptionsView optionsView){
        this.view  = optionsView;

    }
    public void handleCloseWindow(javafx.event.ActionEvent e){
        // Platform.exit();
        view.stage.close();
    }

    public void handleAppExit(){
        if(view.stage !=null){
            Platform.exit();
        }
    }
}
