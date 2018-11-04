package uk.dangrew.cb.graphics.wizard.tables;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.cb.model.budget.BudgetType;
import uk.dangrew.cb.model.budget.ResourceBudget;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.model.workpackage.WorkPackage;

public class WorkPackageResourceEntry {

   private final ChangeListener< Double > modelUpdater;
   
   private final ResourceBudget budget;
   private final BudgetType budgetType;
   
   private final ObjectProperty< WorkPackage > workPackage;
   private final ObjectProperty< Resource > resource;
   private final ObjectProperty< Double > netRate;
   private final ObjectProperty< Double > effort;
   private final ObjectProperty< Double > cost;
   
   public WorkPackageResourceEntry( ResourceBudget budget, BudgetType budgetType ) {
      this( budget, budgetType, null );
   }//End Constructor
   
   public WorkPackageResourceEntry( ResourceBudget budget, BudgetType budgetType, WorkPackage workPackage ) {
      this.budget = budget;
      this.budgetType = budgetType;
      this.workPackage = new SimpleObjectProperty<>( workPackage );
      this.resource = new SimpleObjectProperty<>();
      this.netRate = new SimpleObjectProperty<>();
      this.effort = new SimpleObjectProperty<>();
      this.cost = new SimpleObjectProperty<>();
      
      this.modelUpdater = ( s, o, n ) -> modelUpdated();
      this.budget.resource().addListener( ( s, o, n ) -> modelResourceUpdated( o, n ) );
      this.budget.effort().addListener( modelUpdater );
      this.budget.costBudget().addListener( modelUpdater );
      
      this.resource.addListener( ( s, o, n ) -> viewResourceUpdate( n ) );
      this.effort.addListener( ( s, o, n ) -> viewEffortUpdate( n ) );
      this.workPackage.addListener( ( s, o, n ) -> viewWorkPackageUpdate( o, n ) );
      
      this.modelResourceUpdated( null, budget.resource().get() );
      this.modelUpdated();
   }//End Constructor
   
   private void modelResourceUpdated( Resource previous, Resource updated ){
      if ( previous != null ) {
         previous.netRate().removeListener( modelUpdater );
      }
      
      this.resource.set( updated );
      if ( updated != null ) {
         updated.netRate().addListener( modelUpdater );
      }
      this.modelUpdated();
   }//End Method
   
   private void modelUpdated() {
      if ( resource.get() != null ) {
         this.netRate.set( resource.get().netRate().get() );
      } else {
         this.netRate.set( 0.0 );
      }
      
      this.effort.set( budget.effort().get() );
      this.cost.set( budget.costBudget().get() );
   }//End Method
   
   private void viewResourceUpdate( Resource updated ){
      this.budget.resource().set( updated );
   }//End Method
   
   private void viewEffortUpdate( double effort ) {
      this.budget.effort().set( effort );
   }//End Method
   
   private void viewWorkPackageUpdate( WorkPackage previous, WorkPackage updated ) {
      if ( previous != null ) {
         budgetType.getResourceBudget( previous.budget() ).resources().remove( budget );
      }
      if ( updated != null ) {
         budgetType.getResourceBudget( updated.budget() ).resources().add( budget );
      }
   }//End Method
   
   public ObjectProperty< WorkPackage > workPackage(){
      return workPackage;
   }//End Method
   
   public ObjectProperty< Resource > resource(){
      return resource;
   }//End Method
   
   public ReadOnlyObjectProperty< Double > netRate(){
      return netRate;
   }//End Method
   
   public ObjectProperty< Double > effort(){
      return effort;
   }//End Method
   
   public ReadOnlyObjectProperty< Double > cost(){
      return cost;
   }//End Method
   
}//End Class
