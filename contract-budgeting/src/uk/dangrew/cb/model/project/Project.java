package uk.dangrew.cb.model.project;

import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.cb.model.budget.Budget;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;

public class Project {

   private final ChangeListener< Double > costBudgetListener;
   
   private final ObservableList< WorkPackage > workPackages;
   private final PrivatelyModifiableObservableListImpl< WorkPackage > publicWorkPackages;
   
   private final ObjectProperty< Double > internalCostBudget;
   private final ObjectProperty< Double > contractCostBudget;
   
   public Project() {
      this.workPackages = FXCollections.observableArrayList();
      this.publicWorkPackages = new PrivatelyModifiableObservableListImpl<>( workPackages );
      
      this.costBudgetListener = ( s, o, n ) -> recalculateCostBudgets();
      this.internalCostBudget = new SimpleObjectProperty<>( 0.0 );
      this.contractCostBudget = new SimpleObjectProperty<>( 0.0 );
   }//End Constructor
   
   private void recalculateCostBudgets(){
      internalCostBudget.set( totalWorkPackageBudget( wp -> wp.budget().internalBudget() ) );
      contractCostBudget.set( totalWorkPackageBudget( wp -> wp.budget().contractBudget() ) );
   }//End Method
   
   private double totalWorkPackageBudget( Function< WorkPackage, Budget > mapper ){
      return workPackages.stream()
               .mapToDouble( wp -> mapper.apply( wp ).costBudget().get() )
               .sum(); 
   }//End Method

   public ReadOnlyObjectProperty< Double > internalCostBudget() {
      return internalCostBudget;
   }//End Method

   public ReadOnlyObjectProperty< Double > contractCostBudget() {
      return contractCostBudget;
   }//End Method

   public ObservableList< WorkPackage > workPackages() {
      return publicWorkPackages;
   }//End Method

   public void addWorkPackage( WorkPackage wp ) {
      wp.budget().internalBudget().costBudget().addListener( costBudgetListener );
      wp.budget().contractBudget().costBudget().addListener( costBudgetListener );
      workPackages.add( wp );
   }//End Method
   
}//End Class
