package uk.dangrew.cb.graphics.wizard;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.toolkit.Database;

public class ProjectWizardWindow {
   
   private final Database database;
   private final Stage stage;
   
   public ProjectWizardWindow( Database database ) {
      this( new Stage(), database );
   }//End Constructor
   
   ProjectWizardWindow( Stage stage, Database database ) {
      this.database = database;
      this.stage = stage;
      this.stage.setScene( null );
      this.stage.setAlwaysOnTop( true );
      this.stage.hide();
   }//End Constructor
   
   public void show( Project project ){
      this.stage.setScene( new Scene( new ProjectWizard( new ProjectWizardPageController( database, project ) ) ) );
      PlatformImpl.runAndWait( stage::showAndWait );
   }//End Method
   
}//End Class
