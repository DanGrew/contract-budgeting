package uk.dangrew.cb.graphics.wizard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.toolkit.Database;

public class ProjectWizard extends BorderPane {

   private final Button next;
   private final Button previous;
   private final Button ok;
   
   public ProjectWizard( Database database, Project project ) {
      this( new ProjectWizardPageController( database, project ) );
   }//End Constructor
   
   ProjectWizard( ProjectWizardPageController controller ) {
      this.next = new Button( "Next" );
      this.previous = new Button( "Previous" );
      this.ok = new Button( "Ok" );

      HBox wrapper = new HBox( 10 );
      wrapper.setPadding( new Insets( 10 ) );
      wrapper.setAlignment( Pos.CENTER_RIGHT );
      wrapper.getChildren().add( ok );
      wrapper.getChildren().add( previous );
      wrapper.getChildren().add( next );
      this.setBottom( wrapper );
      
      this.next.setOnAction( e -> controller.nextPage() );
      this.previous.setOnAction( e -> controller.previousPage() );
      
      controller.associate( this );
   }//End Constructor
   
   Button nextButton(){
      return next;
   }//End Method
   
   Button previousButton(){
      return previous;
   }//End Method
   
}//End Class
