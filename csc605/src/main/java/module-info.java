module com.conway {
    requires transitive javafx.controls;

    
    exports com.conway ;
    exports com.conway.GameBoard ;
    exports com.conway.MyApp ;

    opens com.conway to javafx.controls;
    
}