module com.conway {
    requires transitive javafx.controls;
    requires javafx.graphics;
    
    exports com.conway.AppOptions ;
    exports com.conway.GameBoard ;
    exports com.conway.MyApp ;
    exports com.conway.Utilities ;
    exports com.conway ;

    opens com.conway to javafx.controls;
}