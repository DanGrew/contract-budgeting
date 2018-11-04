package uk.dangrew.cb.graphics.wizard.tables;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;

public class WorkPackageResourceEntryColumns implements ConceptTableColumnsPopulator< WorkPackageResourceEntry > {

   static final String WORK_PACKAGE_COLUMN_TEXT = "Work Package";
   static final double WORK_PACKAGE_COLUMN_WIDTH = 0.25;
   static final String NAME_COLUMN_TEXT = "Resource";
   static final double NAME_COLUMN_WIDTH = 0.25;
   static final String NET_RATE_COLUMN_TEXT = "Net Rate (£)";
   static final double NET_RATE_COLUMN_WIDTH = 0.15;
   static final String EFFORT_COLUMN_TEXT = "Effort (days)";
   static final double EFFORT_COLUMN_WIDTH = 0.15;
   static final String COST_COLUMN_TEXT = "Cost (£)";
   static final double COST_COLUMN_WIDTH = 0.15;
   
   private final Project project;
   private final ConceptStore< Resource > resources;
   private final TableConfiguration configuration;
   
   public WorkPackageResourceEntryColumns( Project project, ConceptStore< Resource > resources ) {
      this( new TableConfiguration(), project, resources );
   }//End Constructor
   
   WorkPackageResourceEntryColumns( TableConfiguration configuration, Project project, ConceptStore< Resource > resources ) {
      this.project = project;
      this.resources = resources;
      this.configuration = configuration;
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< WorkPackageResourceEntry > table ) {
      configuration.initialiseDropDownColumn( 
               new TableViewColumnConfigurer<>( table ), 
               WORK_PACKAGE_COLUMN_TEXT, 
               WORK_PACKAGE_COLUMN_WIDTH, 
               WorkPackageResourceEntry::workPackage, 
               ( b, r ) -> b.workPackage().set( r ), 
               new ConceptOptionsImpl<>( project.workPackages() )
      ); 
      configuration.initialiseDropDownColumn( 
               new TableViewColumnConfigurer<>( table ), 
               NAME_COLUMN_TEXT, 
               NAME_COLUMN_WIDTH, 
               WorkPackageResourceEntry::resource, 
               ( b, r ) -> b.resource().set( r ), 
               new ConceptOptionsImpl<>( resources )
      ); 
      configuration.initialiseReadOnlyDoubleColumn(  
               new TableViewColumnConfigurer<>( table ), 
               NET_RATE_COLUMN_TEXT, 
               NET_RATE_COLUMN_WIDTH, 
               WorkPackageResourceEntry::netRate
      );
      configuration.initialiseDoubleColumn(  
               new TableViewColumnConfigurer<>( table ), 
               EFFORT_COLUMN_TEXT, 
               EFFORT_COLUMN_WIDTH, 
               WorkPackageResourceEntry::effort,
               true 
      );
      configuration.initialiseReadOnlyDoubleColumn(  
               new TableViewColumnConfigurer<>( table ), 
               COST_COLUMN_TEXT, 
               COST_COLUMN_WIDTH, 
               WorkPackageResourceEntry::cost
      );
   }//End Method

}//End Class
