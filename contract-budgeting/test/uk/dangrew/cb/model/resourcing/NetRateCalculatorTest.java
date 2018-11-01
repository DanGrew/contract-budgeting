package uk.dangrew.cb.model.resourcing;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static uk.dangrew.kode.TestCommon.precision;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.resourcing.NetRateCalculator;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.kode.TestCommon;
import uk.dangrew.kode.launch.TestApplication;

public class NetRateCalculatorTest {

   private Resource resource;
   private NetRateCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new NetRateCalculator();
      resource = new Resource( systemUnderTest, "Developer", 0.0, 0.0 );
   }//End Method

   @Test public void shouldRespondToRateChanges() {
      resource.baseRate().set( 100.0 );
      resource.upRate().set( 5.0 );
      assertThat( resource.netRate().get(), is( closeTo( 105.0, precision() ) ) );
      
      resource.baseRate().set( 200.0 );
      assertThat( resource.netRate().get(), is( closeTo( 210.0, precision() ) ) );
      
      resource.upRate().set( 10.0 );
      assertThat( resource.netRate().get(), is( closeTo( 220.0, precision() ) ) );
      
      resource.baseRate().set( null );
      assertThat( resource.netRate().get(), is( closeTo( 0.0, precision() ) ) );
      
      resource.baseRate().set( 100.0 );
      resource.upRate().set( null );
      assertThat( resource.netRate().get(), is( closeTo( 100.0, precision() ) ) );
   }//End Method
   
}//End Class
