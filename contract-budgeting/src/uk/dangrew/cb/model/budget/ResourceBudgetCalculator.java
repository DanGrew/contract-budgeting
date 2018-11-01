package uk.dangrew.cb.model.budget;

import javafx.beans.value.ChangeListener;
import uk.dangrew.cb.model.resourcing.Resource;

public class ResourceBudgetCalculator {
   
   private final ChangeListener< Double > costBudgetRecalculator;
   private ResourceBudget resourceBudget;

   public ResourceBudgetCalculator() {
      this.costBudgetRecalculator = ( s, o, n ) -> recalculateCostBudget();
   }//End Constructor
   
   public void associate( ResourceBudget resourceBudget ) {
      if ( this.resourceBudget != null ) {
         throw new IllegalStateException();
      }

      this.resourceBudget = resourceBudget;
      this.resourceBudget.resource().addListener( ( s, o, n ) -> manageListeners( o, n ) );
      this.manageListeners( null, resourceBudget.resource().get() );
      this.resourceBudget.effort().addListener( costBudgetRecalculator );
      this.recalculateCostBudget();
   }//End Method
   
   private void manageListeners( Resource previous, Resource updated ) {
      if ( previous != null ) {
         previous.netRate().removeListener( costBudgetRecalculator );
      }
      
      if ( updated != null ) {
         updated.netRate().addListener( costBudgetRecalculator );
      }
      
      recalculateCostBudget();
   }//End Method
   
   private void recalculateCostBudget(){
      if ( resourceBudget.resource().get() == null || resourceBudget.effort().get() == null ) {
         resourceBudget.setCostBudget( 0.0 );
         return;
      }
      
      resourceBudget.setCostBudget( resourceBudget.resource().get().netRate().get() * resourceBudget.effort().get() );
   }//End Method

}//End Class
