package uk.dangrew.cb.graphics.wizard.tables;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.concepts.ConceptFactory;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.ConceptStoreImpl;
import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.ConceptTable;

public class ResourceTableControllerTest {

   private ConceptStore< Resource > store;
   private ConceptTable< Resource > table;
   private ResourceTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      store = new ConceptStoreImpl<>( new ConceptFactory().resource() );
      systemUnderTest = new ResourceTableController( store );
      table = new ConceptTable<>( new ResourceColumns(), systemUnderTest );
   }//End Method

   @Test public void shouldUpdateTableWhenProjectUpdate() {
      assertRowsMatch( 0 );
      systemUnderTest.createConcept();
      assertRowsMatch( 1 );
      systemUnderTest.createConcept();
      assertRowsMatch( 2 );
      table.getSelectionModel().select( 1 );
      systemUnderTest.removeSelectedConcept();
      assertRowsMatch( 1 );
      systemUnderTest.copySelectedConcept();
      assertRowsMatch( 2 );
   }//End Method
   
   private void assertRowsMatch( int expectedRows ){
      assertThat( table.getRows().size(), is( expectedRows ) );
      assertThat( store.objectList().size(), is( expectedRows ) );
      for( int i = 0; i < store.objectList().size(); i++ ) {
         assertThat( table.getRows().get( i ).concept(), is( store.objectList().get( i ) ) );
      }
   }//End Method
   
   @Test public void shouldIgnoreNoSelection(){
      assertRowsMatch( 0 );
      systemUnderTest.copySelectedConcept();
      assertRowsMatch( 0 );
      systemUnderTest.removeSelectedConcept();
      assertRowsMatch( 0 );
   }//End Method

}//End Class
