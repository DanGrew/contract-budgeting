package uk.dangrew.cb.model.budget;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.cb.model.resourcing.Resource;

public class ResourceBudget {

   private final ChangeListener< Double > costBudgetRecalculator;
   
   private final ObjectProperty< Resource > resource;
   private final ObjectProperty< Double > effort;
   private final ObjectProperty< Double > costBudget;
   
   public ResourceBudget() {
      this.resource = new SimpleObjectProperty<>();
      this.effort = new SimpleObjectProperty<>( 0.0 );
      this.costBudget = new SimpleObjectProperty<>( 0.0 );
      
      this.costBudgetRecalculator = ( s, o, n ) -> recalculateCostBudget();
      
      this.resource.addListener( ( s, o, n ) -> {
         this.manageListeners( o, n );
         this.recalculateCostBudget();  
      } );
      this.effort.addListener( costBudgetRecalculator );
   }//End Constructor
   
   private void manageListeners( Resource previous, Resource updated ) {
      if ( previous != null ) {
         previous.netRate().addListener( costBudgetRecalculator );
      }
      
      if ( updated != null ) {
         updated.netRate().addListener( costBudgetRecalculator );
      }
   }//End Method
   
   private void recalculateCostBudget(){
      if ( resource.get() == null || effort.get() == null ) {
         costBudget.set( 0.0 );
         return;
      }
      
      costBudget.set( resource.get().netRate().get() * effort.get() );
   }//End Method
   
   public ObjectProperty< Resource > resource(){
      return resource;
   }//End Method
   
   public ObjectProperty< Double > effort(){
      return effort;
   }//End Method

   public ReadOnlyObjectProperty< Double > costBudget() {
      return costBudget;
   }//End Method
   
}//End Class
