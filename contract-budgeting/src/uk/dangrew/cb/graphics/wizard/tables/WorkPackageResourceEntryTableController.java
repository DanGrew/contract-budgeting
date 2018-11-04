package uk.dangrew.cb.graphics.wizard.tables;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xml.internal.security.transforms.implementations.FuncHere;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.cb.model.budget.BudgetType;
import uk.dangrew.cb.model.budget.ResourceBudget;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.table.ConceptTableRowImpl;
import uk.dangrew.nuts.graphics.table.ConceptTableViewController;

public class WorkPackageResourceEntryTableController implements ConceptTableViewController< WorkPackageResourceEntry >{
   
   private final Project project;
   private final ObservableList< WorkPackageResourceEntry > allocations;
   private final BudgetType budgetType;
   
   private ConceptTable< WorkPackageResourceEntry > table;
   
   public WorkPackageResourceEntryTableController( Project project, BudgetType budgetType ) {
      this.project = project;
      this.budgetType = budgetType;
      this.allocations = FXCollections.observableArrayList();
   }//End Constructor
   
   @Override public void associate( ConceptTable< WorkPackageResourceEntry > table ) {
      this.table = table;
      this.project.workPackages().forEach( this::workPackageAdded );
      this.project.workPackages().addListener( new FunctionListChangeListenerImpl<>( 
               this::workPackageAdded, this::workPackageRemoved
      ) );
      this.allocations.forEach( this::addRowToTable );
      this.allocations.addListener( new FunctionListChangeListenerImpl<>( 
            this::addRowToTable, this::removeRowFromTable
      ) );
   }//End Method
   
   private void addRowToTable( WorkPackageResourceEntry entry ) {
      this.table.getRows().add( new ConceptTableRowImpl<>( entry ) );
   }//End Method
   
   private void removeRowFromTable( WorkPackageResourceEntry entry ) {
      this.table.getRows().removeIf( r -> r.concept() == entry );
   }//End Method
   
   private void workPackageAdded( WorkPackage wp ) {
      budgetType.getResourceBudget( wp.budget() ).resources().forEach( resourceBudget -> resourceAdded( resourceBudget, wp ) );
   }//End Method
   
   private void workPackageRemoved( WorkPackage wp ) {
      this.allocations.removeIf( entry -> entry.workPackage().get() == wp );
   }//End Method
   
   private void resourceAdded( ResourceBudget resourceBudget, WorkPackage workPackage ) {
      WorkPackageResourceEntry entry = new WorkPackageResourceEntry( resourceBudget, budgetType, workPackage );
      this.allocations.add( entry );
   }//End Method
   
   @Override public WorkPackageResourceEntry createConcept() {
      WorkPackageResourceEntry entry = new WorkPackageResourceEntry( new ResourceBudget(), budgetType );
      this.allocations.add( entry );
      return entry;
   }//End Method

   @Override public void removeSelectedConcept() {
      ConceptTableRow< WorkPackageResourceEntry > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      selection.concept().workPackage().set( null );
      this.allocations.remove( selection.concept() );
   }//End Method

   @Override public void copySelectedConcept() {
      ConceptTableRow< WorkPackageResourceEntry > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      WorkPackageResourceEntry copy = new WorkPackageResourceEntry( new ResourceBudget(), budgetType );
      copy.workPackage().set( selection.concept().workPackage().get() );
      copy.resource().set( selection.concept().resource().get() );
      copy.effort().set( selection.concept().effort().get() );
      this.allocations.add( copy );
   }//End Method

}//End Class
