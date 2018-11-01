package uk.dangrew.cb.model.budget;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.cb.model.resourcing.Resource;

public class ResourceBudget {

   private final ObjectProperty< Resource > resource;
   private final ObjectProperty< Double > effort;
   private final ObjectProperty< Double > costBudget;
   
   public ResourceBudget() {
      this( 0.0, null );
   }//End Constructor
   
   public ResourceBudget( double effort, Resource resource ) {
      this( new ResourceBudgetCalculator(), effort, resource );
   }//End Constructor
   
   ResourceBudget( ResourceBudgetCalculator budgetCalculator, double effort, Resource resource ) {
      this.resource = new SimpleObjectProperty<>( resource );
      this.effort = new SimpleObjectProperty<>( effort );
      this.costBudget = new SimpleObjectProperty<>( 0.0 );
      
      budgetCalculator.associate( this );
   }//End Constructor
   
   public ObjectProperty< Resource > resource(){
      return resource;
   }//End Method
   
   public ObjectProperty< Double > effort(){
      return effort;
   }//End Method

   public ReadOnlyObjectProperty< Double > costBudget() {
      return costBudget;
   }//End Method
   
   void setCostBudget( double budget ) {
      this.costBudget.set( budget );
   }//End Method
   
}//End Class
