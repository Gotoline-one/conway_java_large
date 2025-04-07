module com.conway {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;

    
    exports com.conway ;
    exports com.conway.GameBoard ;
    exports com.conway.MyApp ;
    opens com.conway to javafx.controls;
    opens com.conway.GameBoard to javafx.graphics;
    opens com.conway.MyApp to javafx.graphics;
    
}