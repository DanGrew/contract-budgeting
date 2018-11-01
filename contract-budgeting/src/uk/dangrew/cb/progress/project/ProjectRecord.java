package uk.dangrew.cb.progress.project;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.cb.model.project.Project;

public class ProjectRecord {
   
   private final Project project;
   private final ObservableList< ProjectProgress > progress;
   private final ObjectProperty< Double > costBudgetUsed;
   private final ObjectProperty< Double > contractCostBudgetRemaining;
   private final ObjectProperty< Double > internalCostBudgetRemaining;
   
   public ProjectRecord( Project project ) {
      this( new ProjectRecordMetrics(), project );
   }//End Constructor
   
   ProjectRecord( ProjectRecordMetrics metrics, Project project ) {
      this.project = project;
      this.progress = FXCollections.observableArrayList();
      this.costBudgetUsed = new SimpleObjectProperty<>( 0.0 );
      this.contractCostBudgetRemaining = new SimpleObjectProperty<>( 0.0 );
      this.internalCostBudgetRemaining = new SimpleObjectProperty<>( 0.0 );
      
      metrics.associate( this );
   }//End Constructor
   
   public Project project(){
      return project;
   }//End Method
   
   public ObservableList< ProjectProgress > progress(){
      return progress;
   }//End Method
   
   public ReadOnlyObjectProperty< Double > contractCostBudget(){
      return project.contractCostBudget();
   }//End Method
   
   public ReadOnlyObjectProperty< Double > internalCostBudget(){
      return project.internalCostBudget();
   }//End Method
   
   public ReadOnlyObjectProperty< Double > costBudgetUsed(){
      return costBudgetUsed;
   }//End Method
   
   void setCostBudgetUsed( double budget ) {
      this.costBudgetUsed.set( budget );
   }//End Method
   
   public ReadOnlyObjectProperty< Double > contractCostBudgetRemaining(){
      return contractCostBudgetRemaining;
   }//End Method
   
   void setContractCostBudgetRemaining( double budget ) {
      this.contractCostBudgetRemaining.set( budget );
   }//End Method
   
   public ReadOnlyObjectProperty< Double > internalCostBudgetRemaining(){
      return internalCostBudgetRemaining;
   }//End Method
   
   void setInternalCostBudgetRemaining( double budget ) {
      this.internalCostBudgetRemaining.set( budget );
   }//End Method
}//End Class
