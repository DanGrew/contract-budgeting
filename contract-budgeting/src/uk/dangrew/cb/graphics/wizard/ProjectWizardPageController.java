package uk.dangrew.cb.graphics.wizard;

import java.nio.channels.IllegalSelectorException;
import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;
import uk.dangrew.cb.graphics.wizard.pages.ProjectSetupPage;
import uk.dangrew.cb.graphics.wizard.pages.ResourcePage;
import uk.dangrew.cb.graphics.wizard.pages.WorkPackagePage;
import uk.dangrew.cb.model.concepts.ConceptFactory;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.toolkit.Database;

public class ProjectWizardPageController {
   
   private final Project project;
   private final List< Node > pages;
   
   private ProjectWizard wizard;
   private int currentPage;
   
   public ProjectWizardPageController( Database database, Project project ) {
      this( 
               Arrays.asList( 
                  new ProjectSetupPage( project ),
                  new WorkPackagePage( project ),
                  new ResourcePage( database.storeFor( new ConceptFactory().resource() ) )
               ), 
               project 
      );
   }//End Constructor
   
   ProjectWizardPageController( List< Node > pages, Project project ) {
      this.project = project;
      this.pages = pages;
   }//End Constructor
   
   void associate( ProjectWizard wizard ) {
      if ( this.wizard != null ) {
         throw new IllegalSelectorException();
      }
      this.wizard = wizard;
      this.firstPage();
   }//End Method
   
   private void firstPage(){
      currentPage = 1;
      previousPage();
   }//End Method
   
   private void updateButtonEnablement(){
      if ( currentPage > 0 ) {
         wizard.previousButton().setDisable( false );
      } else {
         wizard.previousButton().setDisable( true );
      }
      
      if ( currentPage < pages.size() - 1 ) {
         wizard.nextButton().setDisable( false );
      } else {
         wizard.nextButton().setDisable( true );
      }
   }//End Method
   
   public void nextPage(){
      if ( currentPage == pages.size() - 1 ) {
         return;
      }
      
      currentPage++;
      wizard.setCenter( pages.get( currentPage ) );
      updateButtonEnablement();
   }//End Method
   
   public void previousPage(){
      if ( currentPage == 0 ) {
         return;
      }
      
      currentPage--;
      wizard.setCenter( pages.get( currentPage ) );
      updateButtonEnablement();
   }//End Method
   
}//End Class
