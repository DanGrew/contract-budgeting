package uk.dangrew.cb.model.workpackage;

import uk.dangrew.cb.model.budget.BudgetRecord;
import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.Properties;

public class WorkPackage implements Concept {
   
   private final Properties properties;
   private final BudgetRecord budget;
   
   public WorkPackage( String name ) {
      this.properties = new Properties( name );
      this.budget = new BudgetRecord();
   }//End Constructor
   
   @Override public Properties properties() {
      return properties;
   }//End Method
   
   public BudgetRecord budget() {
      return budget;
   }//End Method
   
   @Override public Concept duplicate() {
      throw new UnsupportedOperationException();
   }//End Method

}//End Class
