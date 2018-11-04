package uk.dangrew.cb.model.budget;

import java.util.function.Function;

import javafx.beans.property.ReadOnlyObjectProperty;
import uk.dangrew.cb.model.project.Project;

public enum BudgetType {

   Internal( 
            BudgetRecord::internalBudget,
            Project::totalInternalEffort,
            Project::internalCostBudget
   ),
   Contract( 
            BudgetRecord::contractBudget,
            Project::totalContractEffort,
            Project::contractCostBudget
   );
   
   private final Function< BudgetRecord, Budget > budgetMapper;
   private final Function< Project, ReadOnlyObjectProperty< Double > > totalEffortMapper;
   private final Function< Project, ReadOnlyObjectProperty< Double > > costBudgetMapper;
   
   private BudgetType( 
            Function< BudgetRecord, Budget > budgetMapper,
            Function< Project, ReadOnlyObjectProperty< Double > > totalEffortMapper,
            Function< Project, ReadOnlyObjectProperty< Double > > costBudgetMapper
   ) {
      this.budgetMapper = budgetMapper;
      this.totalEffortMapper = totalEffortMapper;
      this.costBudgetMapper = costBudgetMapper;
   }//End Method
   
   public Budget getResourceBudget( BudgetRecord budgetRecord ) {
      return this.budgetMapper.apply( budgetRecord );
   }//End Method
   
   public ReadOnlyObjectProperty< Double > getTotalEffort( Project project ){
      return totalEffortMapper.apply( project );
   }//End Method
   
   public ReadOnlyObjectProperty< Double > getCostBudget( Project project ){
      return costBudgetMapper.apply( project );
   }//End Method
   
}//End Enum
