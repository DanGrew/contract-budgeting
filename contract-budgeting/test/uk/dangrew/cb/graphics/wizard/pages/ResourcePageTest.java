package uk.dangrew.cb.graphics.wizard.pages;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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

public class ResourcePageTest {

   private ConceptStore< Resource > store;
   private ResourcePage systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      store = new ConceptStoreImpl<>( new ConceptFactory().resource() );
      systemUnderTest = new ResourcePage( store );
   }//End Method

   @Test public void shouldUpdateTableWhenProjectUpdate() {
      assertRowsMatch( 0 );
      systemUnderTest.controller().createConcept();
      assertRowsMatch( 1 );
      systemUnderTest.controller().createConcept();
      assertRowsMatch( 2 );
      systemUnderTest.table().getSelectionModel().select( 1 );
      systemUnderTest.controller().removeSelectedConcept();
      assertRowsMatch( 1 );
      systemUnderTest.controller().copySelectedConcept();
      assertRowsMatch( 2 );
   }//End Method
   
   private void assertRowsMatch( int expectedRows ){
      assertThat( systemUnderTest.table().getRows().size(), is( expectedRows ) );
      assertThat( store.objectList().size(), is( expectedRows ) );
      for( int i = 0; i < store.objectList().size(); i++ ) {
         assertThat( systemUnderTest.table().getRows().get( i ).concept(), is( store.objectList().get( i ) ) );
      }
   }//End Method
   
}//End Class
