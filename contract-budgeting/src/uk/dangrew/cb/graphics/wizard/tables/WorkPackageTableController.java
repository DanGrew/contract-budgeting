package uk.dangrew.cb.graphics.wizard.tables;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.table.ConceptTableRowImpl;
import uk.dangrew.nuts.graphics.table.ConceptTableViewController;

public class WorkPackageTableController implements ConceptTableViewController< WorkPackage  >{
   
   private final Project project;
   private ConceptTable< WorkPackage > table;
   
   public WorkPackageTableController( Project project ) {
      this.project = project;
   }//End Constructor
   
   @Override public void associate( ConceptTable< WorkPackage > table ) {
      this.table = table;
      this.project.workPackages().forEach( this::addWorkPackage );
      this.project.workPackages().addListener( new FunctionListChangeListenerImpl<>( 
               this::addWorkPackage, this::removeWorkPackage 
      ) );
   }//End Method
   
   private void addWorkPackage( WorkPackage wp ) {
      table.getRows().add( new ConceptTableRowImpl<>( wp ) );
   }//End Method
   
   private void removeWorkPackage( WorkPackage wp ) {
      table.getRows().removeIf( r -> r.concept() == wp );
   }//End Method

   @Override public WorkPackage createConcept() {
      WorkPackage workPackage = new WorkPackage( "Work Package" );
      project.workPackages().add( workPackage );
      return workPackage;
   }//End Method

   @Override public void removeSelectedConcept() {
      ConceptTableRow< WorkPackage > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      this.project.workPackages().remove( selection.concept() );
   }//End Method

   @Override public void copySelectedConcept() {
      ConceptTableRow< WorkPackage > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      this.project.workPackages().add( new WorkPackage( selection.concept().properties().nameProperty().get() ) );
   }//End Method

}//End Class
