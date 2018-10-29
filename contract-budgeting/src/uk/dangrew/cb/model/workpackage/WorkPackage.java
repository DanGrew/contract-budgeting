package uk.dangrew.cb.model.workpackage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.cb.model.budget.BudgetRecord;

public class WorkPackage {
   
   private final ObjectProperty< String > name;
   private final BudgetRecord budget;
   
   public WorkPackage( String name ) {
      this.name = new SimpleObjectProperty<>( name );
      this.budget = new BudgetRecord();
   }//End Constructor
   
   public ObjectProperty< String > name() {
      return name;
   }//End Method

   public BudgetRecord budget() {
      return budget;
   }//End Method

}//End Class
