package uk.dangrew.cb.model.budget;

import javafx.beans.value.ChangeListener;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;

public class BudgetCalculator {
   
   private final ChangeListener< Double > costBudgetCalculator;
   private final ChangeListener< Double > toalEffortCalculator;

   private Budget budget;
   
   public BudgetCalculator() {
      this.costBudgetCalculator = ( s, o, n ) -> recalculateCostBudget();
      this.toalEffortCalculator = ( s, o, n ) -> recalculateTotalEffort();
   }//End Constructor

   public void associate( Budget budget ) {
      if ( this.budget != null ) {
         throw new IllegalStateException();
      }
      
      this.budget = budget;
      this.budget.resources().addListener( new FunctionListChangeListenerImpl<>( 
               this::addResourceBudget, this::removeResourceBudget
      ) );
   }//End Method
   
   private void recalculateCostBudget(){
      this.budget.setCostBudget( 
               this.budget.resources().stream()
                     .mapToDouble( b -> b.costBudget().get() )
                     .sum() 
      );
   }//End Method
   
   private void recalculateTotalEffort(){
      this.budget.setTotalEffort( 
               this.budget.resources().stream()
                     .mapToDouble( b -> b.effort().get() )
                     .sum() 
      );
   }//End Method
   
   private void addResourceBudget( ResourceBudget resourceBudget ) {
      resourceBudget.costBudget().addListener( costBudgetCalculator );
      resourceBudget.effort().addListener( toalEffortCalculator );
      recalculateCostBudget();
      recalculateTotalEffort();
   }//End Method
   
   private void removeResourceBudget( ResourceBudget resourceBudget ) {
      resourceBudget.costBudget().removeListener( costBudgetCalculator );
      resourceBudget.effort().removeListener( toalEffortCalculator );
      recalculateCostBudget();
      recalculateTotalEffort();
   }//End Method
   
}//End Class
