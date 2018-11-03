package uk.dangrew.cb.toolkit;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.notNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.launch.TestApplication;

public class DatabaseTest {

   private Database systemUnderTest;
   
   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new Database();
   }//End Method

   @Test public void shouldProvideConceptStoreForGivenType() {
      TestConceptType type = new TestConceptType();
      ConceptStore< TestConcept > store = systemUnderTest.storeFor( type );
      assertThat( store, is( notNullValue() ) );
      assertThat( systemUnderTest.storeFor( type ), is( store ) );
      
      assertThat( systemUnderTest.storeFor( new TestConceptType() ), is( not( store ) ) );
      
      assertThat( store.createConcept( "Anything" ), is( instanceOf( TestConcept.class ) ) );
   }//End Method

}//End Class
