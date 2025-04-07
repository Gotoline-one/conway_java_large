module com.conway {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;


    // opens com.conway to javafx.fxml;
    opens com.conway to javafx.controls;
    exports com.conway ;
    exports com.conway.GameBoard ;
    exports com.conway.MyApp ;
}
