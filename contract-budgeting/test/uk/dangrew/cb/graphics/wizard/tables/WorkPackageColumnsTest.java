package uk.dangrew.cb.graphics.wizard.tables;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnConfigurer;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;

public class WorkPackageColumnsTest {

   @Captor private ArgumentCaptor< TableColumnConfigurer > columnConfigurerCaptor;
   @Captor private ArgumentCaptor< Function< WorkPackage, ObjectProperty< String > > > propertyRetrieverCaptor;
   
   @Spy private TableConfiguration configuration;
   private ConceptTable< WorkPackage > table;
   private WorkPackageColumns systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new WorkPackageColumns( configuration );
      table = new ConceptTable<>( systemUnderTest, mock( WorkPackageTableController.class ) );
   }//End Method

   @Test public void shouldProvideNameColumn() {
      verify( configuration ).initialiseStringColumn(
               columnConfigurerCaptor.capture(), 
               eq( WorkPackageColumns.NAME_COLUMN_TEXT ), 
               eq( WorkPackageColumns.NAME_COLUMN_WIDTH ), 
               propertyRetrieverCaptor.capture(),
               eq( true )
      );
      
      assertThat( columnConfigurerCaptor.getValue(), is( instanceOf( TableViewColumnConfigurer.class ) ) );
      
      WorkPackage wp = new WorkPackage( "Anything" );
      assertThat( propertyRetrieverCaptor.getValue().apply( wp ), is( wp.name() ) );
   }//End Method

}//End Class
