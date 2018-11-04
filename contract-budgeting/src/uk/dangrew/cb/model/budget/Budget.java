package uk.dangrew.cb.model.budget;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Budget {

   private final ObjectProperty< Double > costBudget;
   private final ObjectProperty< Double > totalEffort;
   private final ObservableList< ResourceBudget > resources;
   
   public Budget() {
      this( new BudgetCalculator() );
   }//End Constructor
   
   Budget( BudgetCalculator budgetCalculator ) {
      this.costBudget = new SimpleObjectProperty<>( 0.0 );
      this.totalEffort = new SimpleObjectProperty<>( 0.0 );
      this.resources = FXCollections.observableArrayList();
      
      budgetCalculator.associate( this );
   }//End Constructor
   
   public ObservableList< ResourceBudget > resources() {
      return resources;
   }//End Method
   
   public ReadOnlyObjectProperty< Double > costBudget() {
      return costBudget;
   }//End Method
   
   void setCostBudget( double costBudget ) {
      this.costBudget.set( costBudget );
   }//End Method
   
   public ReadOnlyObjectProperty< Double > totalEffort() {
      return totalEffort;
   }//End Method
   
   void setTotalEffort( double effort ) {
      this.totalEffort.set( effort );
   }//End Method
   
}//End Class
