package uk.dangrew.cb.model.project;

import java.util.function.Function;

import javafx.beans.value.ChangeListener;
import uk.dangrew.cb.model.budget.Budget;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;

public class ProjectBudgetCalculator {
   
   private final ChangeListener< Double > costBudgetListener;
   private Project project;
   
   public ProjectBudgetCalculator() {
      this.costBudgetListener = ( s, o, n ) -> recalculateCostBudgets();
   }//End Constructor
   
   void associate( Project project ) {
      if ( this.project != null ) {
         throw new IllegalStateException();
      }
      
      this.project = project;
      this.project.workPackages().addListener( new FunctionListChangeListenerImpl<>( 
               this::addWorkPackage, this::removeWorkPackage 
      ) );
   }//End Method
   
   private void addWorkPackage( WorkPackage wp ) {
      wp.budget().internalBudget().costBudget().addListener( costBudgetListener );
      wp.budget().contractBudget().costBudget().addListener( costBudgetListener );
      recalculateCostBudgets();
   }//End Method
   
   private void removeWorkPackage( WorkPackage wp ) {
      wp.budget().internalBudget().costBudget().removeListener( costBudgetListener );
      wp.budget().contractBudget().costBudget().removeListener( costBudgetListener );
      recalculateCostBudgets();
   }//End Method
   
   private void recalculateCostBudgets(){
      project.setInternalCostBudget( totalWorkPackageBudget( wp -> wp.budget().internalBudget() ) );
      project.setContractCostBudget( totalWorkPackageBudget( wp -> wp.budget().contractBudget() ) );
   }//End Method
   
   private double totalWorkPackageBudget( Function< WorkPackage, Budget > mapper ){
      return project.workPackages().stream()
               .mapToDouble( wp -> mapper.apply( wp ).costBudget().get() )
               .sum(); 
   }//End Method

}//End Class
