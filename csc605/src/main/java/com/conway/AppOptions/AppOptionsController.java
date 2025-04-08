package com.conway.AppOptions;

import javafx.application.Platform;

public class AppOptionsController {
    AppOptionsView view; 

    public AppOptionsController(AppOptionsView optionsView){
        this.view  = optionsView;

    }

    public void handleCancelBtn(javafx.event.ActionEvent e){
        // clear out all options

        // close
        view.stage.close();
    }

    public void handleCloseWindow(javafx.event.ActionEvent e){
        // save settings 


        // close
        view.stage.close();
    }

    public void handleAppExit(){
        if(view.stage !=null){
            Platform.exit();
        }
    }


     public AppOptions viewToOptions(){
        AppOptions ao = new AppOptions();
            // ao.fname = view.fnameTextField.getText();
            // ao.seed = Long.valueOf( view.seedTextField.getText());
            // String c =  view.fileTypeChoiceBox.getValue();

            // if(c =="json"){
            //     ao.fileType=  FileType.JSON;
            // }else if(c == "csv"){
            //     ao.fileType=  FileType.CSV;
            // }

        return ao;
     }

}
