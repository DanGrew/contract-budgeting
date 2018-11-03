package uk.dangrew.cb.model.project;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.Properties;

public class Project implements Concept {

   private final ObservableList< WorkPackage > workPackages;
   
   private final Properties properties;
   private final ObjectProperty< String > contract;
   private final ObjectProperty< Double > internalCostBudget;
   private final ObjectProperty< Double > contractCostBudget;
   
   public Project( String name ) {
      this( new ProjectBudgetCalculator(), name );
   }//End Constructor
   
   Project( ProjectBudgetCalculator budgetCalculator, String name ) {
      this.properties = new Properties( name );
      this.contract = new SimpleObjectProperty<>();
      
      this.workPackages = FXCollections.observableArrayList();
      
      this.internalCostBudget = new SimpleObjectProperty<>( 0.0 );
      this.contractCostBudget = new SimpleObjectProperty<>( 0.0 );
      
      budgetCalculator.associate( this );
   }//End Constructor
   
   @Override public Properties properties() {
      return properties;
   }//End Method
   
   public ObjectProperty< String > contract(){
      return contract;
   }//End Method
   
   public ReadOnlyObjectProperty< Double > internalCostBudget() {
      return internalCostBudget;
   }//End Method
   
   void setInternalCostBudget( double costBudget ) {
      this.internalCostBudget.set( costBudget );
   }//End Method

   public ReadOnlyObjectProperty< Double > contractCostBudget() {
      return contractCostBudget;
   }//End Method
   
   void setContractCostBudget( double costBudget ) {
      this.contractCostBudget.set( costBudget );
   }//End Method

   public ObservableList< WorkPackage > workPackages() {
      return workPackages;
   }//End Method

   @Override public Concept duplicate( String referenceId ) {
      throw new UnsupportedOperationException();
   }//End Method
   
}//End Class
