package uk.dangrew.cb.graphics.workspace.tables;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.table.ConceptTableRowImpl;
import uk.dangrew.nuts.graphics.table.ConceptTableViewController;

public class ProjectTableController implements ConceptTableViewController< Project  >{
   
   private final ConceptStore< Project > projects;
   private ConceptTable< Project > table;
   
   public ProjectTableController( ConceptStore< Project > projects ) {
      this.projects = projects;
   }//End Constructor
   
   @Override public void associate( ConceptTable< Project > table ) {
      this.table = table;
      this.projects.objectList().forEach( this::addProject );
      this.projects.objectList().addListener( new FunctionListChangeListenerImpl<>(
               this::addProject, this::removeProject
      ) );
   }//End Method
   
   private void addProject( Project project ) {
      table.getRows().add( new ConceptTableRowImpl<>( project ) );
   }//End Method
   
   private void removeProject( Project project ) {
      table.getRows().removeIf( r -> r.concept() == project );
   }//End Method

   @Override public Project createConcept() {
      return projects.createConcept( "Project" );
   }//End Method

   @Override public void removeSelectedConcept() {
      ConceptTableRow< Project > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      this.projects.removeConcept( selection.concept() );
   }//End Method

   @Override public void copySelectedConcept() {
      ConceptTableRow< Project > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      Project copy = selection.concept().duplicate();
      projects.store( copy );
   }//End Method

}//End Class
