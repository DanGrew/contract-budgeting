package uk.dangrew.cb.graphics.wizard.pages;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.kode.javafx.custom.BoundTextProperty;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ReadOnlyDoubleAsTextProperty;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;

public class ProjectSummaryPage extends BorderPane {

   private final BoundTextProperty nameBinding;
   private final BoundTextProperty contractBinding;
   private final ReadOnlyDoubleAsTextProperty contractBudget;
   private final ReadOnlyDoubleAsTextProperty totalContractEffort;
   private final ReadOnlyDoubleAsTextProperty internalBudget;
   private final ReadOnlyDoubleAsTextProperty totalInternalEffort;
   
   public ProjectSummaryPage( Project project ) {
      JavaFxStyle styling = new JavaFxStyle();
      
      this.setTop( styling.simpleHeaderDescription( 
               "Project Summary",
               "The following provides a summary of the project given the budgeting configured in "
               + "previous pages." 
      ) );
      
      this.setCenter( new PropertiesPane( 
               "Summary", 
               new PropertyRowBuilder()
                  .withLabelName( "Name" )
                  .withBinding( nameBinding = new BoundTextProperty( project.properties().nameProperty(), false ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Contract" )
                  .withBinding( contractBinding = new BoundTextProperty( project.contract(), false ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Contract Budget (£)" )
                  .withBinding( contractBudget = new ReadOnlyDoubleAsTextProperty( project.contractCostBudget() ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Total Contract Effort (days)" )
                  .withBinding( totalContractEffort = new ReadOnlyDoubleAsTextProperty( project.totalContractEffort() ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Internal Budget (£)" )
                  .withBinding( internalBudget = new ReadOnlyDoubleAsTextProperty( project.internalCostBudget() ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Total Internal Effort (days)" )
                  .withBinding( totalInternalEffort = new ReadOnlyDoubleAsTextProperty( project.totalInternalEffort() ) )
      ) );
   }//End Constructor
   
   TextField nameField(){
      return nameBinding.region();
   }//End Method

   TextField contractField(){
      return contractBinding.region();
   }//End Method
   
   TextField contractBudgetField(){
      return contractBudget.region();
   }//End Method
   
   TextField internalBudgetField(){
      return internalBudget.region();
   }//End Method
   
   TextField totalContractEffortField(){
      return totalContractEffort.region();
   }//End Method
   
   TextField totalInternalEffortField(){
      return totalInternalEffort.region();
   }//End Method
}//End Class
