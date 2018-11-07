package uk.dangrew.cb.graphics.workspace.tables;

import uk.dangrew.cb.graphics.wizard.ProjectWizardWindow;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;

public class ProjectColumns implements ConceptTableColumnsPopulator< Project > {

   static final String NAME_COLUMN_TEXT = "Project";
   static final double NAME_COLUMN_WIDTH = 0.25;
   static final String CONTRACT_COLUMN_TEXT = "Contract";
   static final double CONTRACT_COLUMN_WIDTH = 0.15;
   static final String CONTRACT_BUDGET_COLUMN_TEXT = "Contract Budget";
   static final double CONTRACT_BUDGET_COLUMN_WIDTH = 0.1;
   static final String CONTRACT_EFFORT_COLUMN_TEXT = "Contract Effort";
   static final double CONTRACT_EFFORT_COLUMN_WIDTH = 0.1;
   static final String INTERNAL_BUDGET_COLUMN_TEXT = "Internal Budget";
   static final double INTERNAL_BUDGET_COLUMN_WIDTH = 0.1;
   static final String INTERNAL_EFFORT_COLUMN_TEXT = "Internal Effort";
   static final double INTERNAL_EFFORT_COLUMN_WIDTH = 0.1;
   static final String EDIT_EFFORT_COLUMN_TEXT = "";
   static final double EDIT_EFFORT_COLUMN_WIDTH = 0.1;
   
   private final ProjectWizardWindow wizard;
   private final TableConfiguration configuration;
   
   public ProjectColumns( Database database ) {
      this( new TableConfiguration(), new ProjectWizardWindow( database ) );
   }//End Constructor
   
   ProjectColumns( TableConfiguration configuration, ProjectWizardWindow wizard ) {
      this.configuration = configuration;
      this.wizard = wizard;
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< Project > table ) {
      configuration.initialiseStringColumn( 
               new TableViewColumnConfigurer<>( table ), 
               NAME_COLUMN_TEXT, 
               NAME_COLUMN_WIDTH, 
               p -> p.properties().nameProperty(), 
               false 
      );
      configuration.initialiseStringColumn( 
               new TableViewColumnConfigurer<>( table ), 
               CONTRACT_COLUMN_TEXT, 
               CONTRACT_COLUMN_WIDTH, 
               Project::contract, 
               false 
      );
      configuration.initialiseReadOnlyDoubleColumn(  
               new TableViewColumnConfigurer<>( table ), 
               CONTRACT_BUDGET_COLUMN_TEXT, 
               CONTRACT_BUDGET_COLUMN_WIDTH, 
               Project::contractCostBudget 
      );
      configuration.initialiseReadOnlyDoubleColumn(  
               new TableViewColumnConfigurer<>( table ), 
               CONTRACT_EFFORT_COLUMN_TEXT, 
               CONTRACT_EFFORT_COLUMN_WIDTH, 
               Project::totalContractEffort 
      );   
      configuration.initialiseReadOnlyDoubleColumn(  
               new TableViewColumnConfigurer<>( table ), 
               INTERNAL_BUDGET_COLUMN_TEXT, 
               INTERNAL_BUDGET_COLUMN_WIDTH, 
               Project::internalCostBudget 
      );
      configuration.initialiseReadOnlyDoubleColumn(  
               new TableViewColumnConfigurer<>( table ), 
               INTERNAL_EFFORT_COLUMN_TEXT, 
               INTERNAL_EFFORT_COLUMN_WIDTH, 
               Project::totalInternalEffort 
      );   
      configuration.initialiseButtonColumn(  
               new TableViewColumnConfigurer<>( table ), 
               EDIT_EFFORT_COLUMN_TEXT, 
               EDIT_EFFORT_COLUMN_WIDTH, 
               "Edit",
               wizard::show
      );
   }//End Method

}//End Class
