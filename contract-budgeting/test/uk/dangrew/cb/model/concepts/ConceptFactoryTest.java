package uk.dangrew.cb.model.concepts;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class ConceptFactoryTest {

   private ConceptFactory systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ConceptFactory();
   }//End Method

   @Test public void shouldProvideConcepts() {
      assertThat( systemUnderTest.resource(), is( notNullValue() ) );
      assertThat( systemUnderTest.resource(), is( systemUnderTest.resource() ) );
      
      assertThat( systemUnderTest.project(), is( notNullValue() ) );
      assertThat( systemUnderTest.project(), is( systemUnderTest.project() ) );
   }//End Method

}//End Class
