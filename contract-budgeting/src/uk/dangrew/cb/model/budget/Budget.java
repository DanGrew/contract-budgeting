package uk.dangrew.cb.model.budget;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;

public class Budget {

   private final ChangeListener< Double > costBudgetCalculator;
   private final ObjectProperty< Double > costBudget;
   
   private final PrivatelyModifiableObservableListImpl< ResourceBudget > publicResources;
   private final ObservableList< ResourceBudget > resources;
   
   public Budget() {
      this.costBudgetCalculator = ( s, o, n ) -> recalculateCostBudget();
      this.costBudget = new SimpleObjectProperty<>( 0.0 );
      this.resources = FXCollections.observableArrayList();
      this.publicResources = new PrivatelyModifiableObservableListImpl<>( resources );
   }//End Constructor
   
   public void addResourceBudget( ResourceBudget resourceBudget ) {
      resourceBudget.costBudget().addListener( costBudgetCalculator );
      resources.add( resourceBudget );
      recalculateCostBudget();
   }//End Method
   
   public void removeResourceBudget( ResourceBudget resourceBudget ) {
      resourceBudget.costBudget().removeListener( costBudgetCalculator );
      resources.remove( resourceBudget );
      recalculateCostBudget();
   }//End Method
   
   private void recalculateCostBudget(){
      costBudget.set( resources.stream().mapToDouble( b -> b.costBudget().get() ).sum() );
   }//End Method
   
   public ObservableList< ResourceBudget > resources() {
      return publicResources;
   }//End Method
   
   public ReadOnlyObjectProperty< Double > costBudget() {
      return costBudget;
   }//End Method

}//End Class
