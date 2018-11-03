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
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnConfigurer;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;

public class ResourceColumnsTest {

   @Captor private ArgumentCaptor< TableColumnConfigurer > columnConfigurerCaptor;
   @Captor private ArgumentCaptor< Function< Resource, ObjectProperty< String > > > propertyRetrieverCaptor;
   @Captor private ArgumentCaptor< Function< Resource, ObjectProperty< Double > > > rateRetrieverCaptor;
   
   @Spy private TableConfiguration configuration;
   private ConceptTable< Resource > table;
   private ResourceColumns systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ResourceColumns( configuration );
      table = new ConceptTable<>( systemUnderTest, mock( ResourceTableController.class ) );
   }//End Method

   @Test public void shouldProvideNameColumn() {
      verify( configuration ).initialiseStringColumn(
               columnConfigurerCaptor.capture(), 
               eq( ResourceColumns.NAME_COLUMN_TEXT ), 
               eq( ResourceColumns.NAME_COLUMN_WIDTH ), 
               propertyRetrieverCaptor.capture(),
               eq( true )
      );
      
      assertThat( columnConfigurerCaptor.getValue(), is( instanceOf( TableViewColumnConfigurer.class ) ) );
      
      Resource resource = new Resource( "Anything" );
      assertThat( propertyRetrieverCaptor.getValue().apply( resource ), is( resource.properties().nameProperty() ) );
   }//End Method
   
   @Test public void shouldProvideBaseRateColumn() {
      verify( configuration ).initialiseDoubleColumn(
               columnConfigurerCaptor.capture(), 
               eq( ResourceColumns.BASE_RATE_COLUMN_TEXT ), 
               eq( ResourceColumns.BASE_RATE_COLUMN_WIDTH ), 
               rateRetrieverCaptor.capture(),
               eq( true )
      );
      
      assertThat( columnConfigurerCaptor.getValue(), is( instanceOf( TableViewColumnConfigurer.class ) ) );
      
      Resource resource = new Resource( "Anything" );
      assertThat( rateRetrieverCaptor.getValue().apply( resource ), is( resource.baseRate() ) );
   }//End Method
   
   @Test public void shouldProvideUpRateColumn() {
      verify( configuration ).initialiseDoubleColumn(
               columnConfigurerCaptor.capture(), 
               eq( ResourceColumns.UP_RATE_COLUMN_TEXT ), 
               eq( ResourceColumns.UP_RATE_COLUMN_WIDTH ), 
               rateRetrieverCaptor.capture(),
               eq( true )
      );
      
      assertThat( columnConfigurerCaptor.getValue(), is( instanceOf( TableViewColumnConfigurer.class ) ) );
      
      Resource resource = new Resource( "Anything" );
      assertThat( rateRetrieverCaptor.getValue().apply( resource ), is( resource.upRate() ) );
   }//End Method
   
   @Test public void shouldProvideNetRateColumn() {
      verify( configuration ).initialiseReadOnlyDoubleColumn(
               columnConfigurerCaptor.capture(), 
               eq( ResourceColumns.NET_RATE_COLUMN_TEXT ), 
               eq( ResourceColumns.NET_RATE_COLUMN_WIDTH ), 
               rateRetrieverCaptor.capture()
      );
      
      assertThat( columnConfigurerCaptor.getValue(), is( instanceOf( TableViewColumnConfigurer.class ) ) );
      
      Resource resource = new Resource( "Anything" );
      assertThat( rateRetrieverCaptor.getValue().apply( resource ), is( resource.netRate() ) );
   }//End Method

}//End Class
