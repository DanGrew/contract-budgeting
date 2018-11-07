package uk.dangrew.cb.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.dangrew.cb.graphics.workspace.core.CoreInterface;
import uk.dangrew.jupa.javafx.platform.PlatformLifecycle;

public class Beef extends Application {
   
   static final String TITLE = "Budget Evaluation, Evidencing & Forecasting";
   
   public Beef() {}//End Constructor
   
   @Override public void start(Stage stage) throws Exception {
      stage.setTitle( TITLE );
      stage.setOnCloseRequest( event -> PlatformLifecycle.shutdown() );
      stage.setScene( new Scene( new CoreInterface() ) );
      stage.setMaximized( true );
      stage.show();
   }//End Method
   
   public static void main( String[] args ) {
      launch();
   }//End Method
   
}//End Class
