package uk.dangrew.cb.graphics.wizard.tables;

import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;

public class WorkPackageColumns implements ConceptTableColumnsPopulator< WorkPackage > {

   static final String NAME_COLUMN_TEXT = "Work Package";
   static final double NAME_COLUMN_WIDTH = 1.0;
   private final TableConfiguration configuration;
   
   public WorkPackageColumns() {
      this( new TableConfiguration() );
   }//End Constructor
   
   WorkPackageColumns( TableConfiguration configuration ) {
      this.configuration = configuration;
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< WorkPackage > table ) {
      configuration.initialiseStringColumn( 
               new TableViewColumnConfigurer<>( table ), 
               NAME_COLUMN_TEXT, 
               NAME_COLUMN_WIDTH, 
               wp -> wp.properties().nameProperty(), 
               true 
      );
   }//End Method

}//End Class
