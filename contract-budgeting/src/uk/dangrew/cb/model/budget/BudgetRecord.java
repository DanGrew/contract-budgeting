package uk.dangrew.cb.model.budget;

import uk.dangrew.cb.model.budget.Budget;

public class BudgetRecord {
   
   private final Budget internalBudget;
   private final Budget contractBudget;
   
   public BudgetRecord() {
      this.internalBudget = new Budget();
      this.contractBudget = new Budget();
   }//End Constructor

   public Budget internalBudget() {
      return internalBudget;
   }//End Method

   public Budget contractBudget() {
      return contractBudget;
   }//End Method

}//End Class
