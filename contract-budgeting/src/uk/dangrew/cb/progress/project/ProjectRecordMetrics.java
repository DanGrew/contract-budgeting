package uk.dangrew.cb.progress.project;

import javafx.beans.value.ChangeListener;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.cb.progress.workpackage.WorkPackageProgress;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;

public class ProjectRecordMetrics {
   
   private final ChangeListener< Double > doubleRecalculator;
   private final ChangeListener< Resource > resourceRecalculator;
   private final ChangeListener< WorkPackage > workPackageRecalculator;
   private final FunctionListChangeListenerImpl< WorkPackageProgress > wpProgressListener;
   private ProjectRecord projectRecord;
   
   public ProjectRecordMetrics() {
      this.doubleRecalculator = ( s, o, n ) -> recalculateCostBudgetUsage();
      this.resourceRecalculator = ( s, o, n ) -> recalculateCostBudgetUsage();
      this.workPackageRecalculator = ( s, o, n ) -> recalculateCostBudgetUsage();
      this.wpProgressListener = new FunctionListChangeListenerImpl<>( 
               this::addWorkPackageProgress, this::removeWorkPackageProgress
      );
   }//End Constructor

   public void associate( ProjectRecord projectRecord ) {
      if ( this.projectRecord != null ) {
         throw new IllegalStateException();
      }
      
      this.projectRecord = projectRecord;
      this.projectRecord.progress().addListener( new FunctionListChangeListenerImpl<>( 
               this::addProjectProgress, this::removeProjectProgress
      ) );
      this.projectRecord.project().contractCostBudget().addListener( doubleRecalculator );
      this.projectRecord.project().internalCostBudget().addListener( doubleRecalculator );
      this.recalculateCostBudgetUsage();
   }//End Method
   
   private void addProjectProgress( ProjectProgress progress ) {
      progress.workPackageProgress().forEach( this::addWorkPackageProgress );
      progress.workPackageProgress().addListener( wpProgressListener );
      recalculateCostBudgetUsage();
   }//End Method
   
   private void removeProjectProgress( ProjectProgress progress ) {
      progress.workPackageProgress().forEach( this::removeWorkPackageProgress );
      progress.workPackageProgress().removeListener( wpProgressListener );
      recalculateCostBudgetUsage();
   }//End Method
   
   private void addWorkPackageProgress( WorkPackageProgress progress ) {
      progress.effort().addListener( doubleRecalculator );
      progress.workPackage().addListener( workPackageRecalculator );
      progress.resource().addListener( resourceRecalculator );
      recalculateCostBudgetUsage();
   }//End Method
   
   private void removeWorkPackageProgress( WorkPackageProgress progress ) {
      progress.effort().removeListener( doubleRecalculator );
      progress.workPackage().removeListener( workPackageRecalculator );
      progress.resource().removeListener( resourceRecalculator );
      recalculateCostBudgetUsage();
   }//End Method
   
   private void recalculateCostBudgetUsage(){
      double budgetUsed = 0.0;
      for ( ProjectProgress projectProgress : projectRecord.progress() ) {
         for ( WorkPackageProgress workPackageProgress : projectProgress.workPackageProgress() ) {
            double usage = workPackageProgress.resource().get().netRate().get() * workPackageProgress.effort().get();
            budgetUsed += usage;
         }
      }
      
      projectRecord.setCostBudgetUsed( budgetUsed );
      projectRecord.setContractCostBudgetRemaining( projectRecord.project().contractCostBudget().get() - budgetUsed );
      projectRecord.setInternalCostBudgetRemaining( projectRecord.project().internalCostBudget().get() - budgetUsed );
   }//End Method
   
}//End Class
