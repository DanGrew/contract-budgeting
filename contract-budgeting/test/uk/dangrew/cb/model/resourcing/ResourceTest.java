package uk.dangrew.cb.model.resourcing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class ResourceTest {

   private Resource systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new Resource( "Developer" );
   }//End Method
   
   @Test public void shouldProvideType(){
      assertThat( systemUnderTest.properties().nameProperty().get(), is( "Developer" ) );
   }//End Method

   @Test public void shouldProvideBaseRate() {
      assertThat( systemUnderTest.baseRate().get(), is( 0.0 ) );
      systemUnderTest.baseRate().set( 200.0 );
      assertThat( systemUnderTest.baseRate().get(), is( 200.0 ) );
      systemUnderTest.upRate().set( 5.0 );
      assertThat( systemUnderTest.baseRate().get(), is( 200.0 ) );
   }//End Method
   
   @Test public void shouldProvideUpRate() {
      systemUnderTest.baseRate().set( 200.0 );
      systemUnderTest.upRate().set( 5.0 );
      assertThat( systemUnderTest.netRate().get(), is( 210.0 ) );
      
      systemUnderTest.baseRate().set( 100.0 );
      assertThat( systemUnderTest.netRate().get(), is( 105.0 ) );
      
      systemUnderTest.upRate().set( 20.0 );
      assertThat( systemUnderTest.netRate().get(), is( 120.0 ) );
   }//End Method

}//End Class
