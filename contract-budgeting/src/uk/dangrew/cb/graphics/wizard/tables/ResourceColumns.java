package uk.dangrew.cb.graphics.wizard.tables;

import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;

public class ResourceColumns implements ConceptTableColumnsPopulator< Resource > {

   static final String NAME_COLUMN_TEXT = "Resource";
   static final double NAME_COLUMN_WIDTH = 0.4;
   static final String BASE_RATE_COLUMN_TEXT = "Base Rate";
   static final double BASE_RATE_COLUMN_WIDTH = 0.2;
   static final String UP_RATE_COLUMN_TEXT = "Up Rate";
   static final double UP_RATE_COLUMN_WIDTH = 0.2;
   static final String NET_RATE_COLUMN_TEXT = "Net Rate";
   static final double NET_RATE_COLUMN_WIDTH = 0.2;
   private final TableConfiguration configuration;
   
   public ResourceColumns() {
      this( new TableConfiguration() );
   }//End Constructor
   
   ResourceColumns( TableConfiguration configuration ) {
      this.configuration = configuration;
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< Resource > table ) {
      configuration.initialiseStringColumn( 
               new TableViewColumnConfigurer<>( table ), 
               NAME_COLUMN_TEXT, 
               NAME_COLUMN_WIDTH, 
               r -> r.properties().nameProperty(), 
               true 
      );
      configuration.initialiseDoubleColumn(  
               new TableViewColumnConfigurer<>( table ), 
               BASE_RATE_COLUMN_TEXT, 
               BASE_RATE_COLUMN_WIDTH, 
               Resource::baseRate,
               true 
      );
      configuration.initialiseDoubleColumn(  
               new TableViewColumnConfigurer<>( table ), 
               UP_RATE_COLUMN_TEXT, 
               UP_RATE_COLUMN_WIDTH, 
               Resource::upRate,
               true 
      );
      configuration.initialiseReadOnlyDoubleColumn(  
               new TableViewColumnConfigurer<>( table ), 
               NET_RATE_COLUMN_TEXT, 
               NET_RATE_COLUMN_WIDTH, 
               Resource::netRate
      );
   }//End Method

}//End Class
