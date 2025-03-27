module com.comway {
    requires javafx.controls;
    requires java.base;

    // requires javafx.fxml;
    exports com.conway.MyApp;
    exports com.conway.GameBoard;


    // opens com.conway to javafx.fxml;
    exports com.conway;
}
