package uk.dangrew.cb.graphics.wizard.pages;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import uk.dangrew.cb.graphics.wizard.tables.WorkPackageResourceEntry;
import uk.dangrew.cb.graphics.wizard.tables.WorkPackageResourceEntryColumns;
import uk.dangrew.cb.graphics.wizard.tables.WorkPackageResourceEntryTableController;
import uk.dangrew.cb.model.budget.BudgetType;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ReadOnlyDoubleAsTextProperty;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.graphics.table.BasicConceptControls;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.controls.TableControls;

public class WorkPackageResourceEntryPage extends BorderPane {

   private final ConceptTable< WorkPackageResourceEntry > table;
   private final TableControls controls;
   private final WorkPackageResourceEntryTableController controller;
   
   private final ReadOnlyDoubleAsTextProperty totalEffort;
   private final ReadOnlyDoubleAsTextProperty costBudget;
   
   public WorkPackageResourceEntryPage( Project project, ConceptStore< Resource > store, BudgetType budgetType ) {
      JavaFxStyle styling = new JavaFxStyle();
      this.controller = new WorkPackageResourceEntryTableController( project, budgetType );
      
      this.setTop( styling.simpleHeaderDescription( 
               "Allocating resources to work packages " + "(" + budgetType.name() + ")",
               "To build a budget for a project we assign effort for a given resource. It is good practice to "
               + "break down a project into work packages, which we've done in a previous step. Now we are "
               + "assigning effort to those work packages. This will build up the cost and budgeting of the "
               + "project overall. \n"
               + "We have two types of budget - contract and internal. Contract represents the chargeable aspects "
               + "and internal represents the addition effort put forward from company funds. Ideally, these "
               + "would be equal i.e. no internal funding, however that is not always the case. This separation "
               + "allows for separate budget tracking." 
      ) );
      
      ConceptTableWithControls< WorkPackageResourceEntry > tableWithControls = new ConceptTableWithControls<>( 
               "Allocations",
               table = new ConceptTable<>(
                     new WorkPackageResourceEntryColumns( project, store ),
                     controller
               ),
               controls = new TableControls( new BasicConceptControls( controller ) ) 
      );
      this.setCenter( tableWithControls );
      
      this.setBottom( new PropertiesPane( 
               "Summary", 
               new PropertyRowBuilder()
                  .withLabelName( "Total Effort (days)" )
                  .withBinding( totalEffort = new ReadOnlyDoubleAsTextProperty( budgetType.getTotalEffort( project ) ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Total Budget (£)" )
                  .withBinding( costBudget = new ReadOnlyDoubleAsTextProperty( budgetType.getCostBudget( project ) ) )
      ) );
   }//End Constructor
   
   WorkPackageResourceEntryTableController controller(){
      return controller;
   }//End Method
   
   ConceptTable< WorkPackageResourceEntry > table(){
      return table;
   }//End Method
   
   TableControls controls(){
      return controls;
   }//End Method
   
   TextField costBudgetField(){
      return costBudget.region();
   }//End Method
   
   TextField totalEffortField(){
      return totalEffort.region();
   }//End Method

}//End Class
