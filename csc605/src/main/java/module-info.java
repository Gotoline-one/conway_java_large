module com.conway {
    requires transitive javafx.controls;
    requires javafx.graphics;
    requires com.google.protobuf;
    
    exports com.conway.AppOptions ;
    exports com.conway.GameBoard ;
    exports com.conway.ConwayApp ;
    exports com.conway.ConwayNetworked;
    exports com.conway.Utilities ;
    
    exports com.conway ;
    
   

    opens com.conway to javafx.controls;
}