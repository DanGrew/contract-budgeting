package uk.dangrew.cb.model.budget;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class ResourceBudgetTest {

   private Resource resource1;
   private Resource resource2;
   private ResourceBudget systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      resource1 = new Resource( "Manager" );
      resource1.baseRate().set( 500.0 );
      
      resource2 = new Resource( "Developer" );
      resource2.baseRate().set( 200.0 );
      
      systemUnderTest = new ResourceBudget();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideProperty( ResourceBudget::resource, new Resource( "Developer" ) )
         .shouldProvideDoubleProperty( ResourceBudget::effort, 0.0 );
   }//End Method
   
   @Test public void shouldProvideCostBudget(){
      assertThat( systemUnderTest.costBudget().get(), is( 0.0 ) );
      
      systemUnderTest.resource().set( resource1 );
      systemUnderTest.effort().set( 20.0 );
      assertThat( systemUnderTest.costBudget().get(), is( resource1.netRate().get() * 20.0 ) );
      
      systemUnderTest.resource().set( resource2 );
      assertThat( systemUnderTest.costBudget().get(), is( resource2.netRate().get() * 20.0 ) );
      
      systemUnderTest.effort().set( 30.0 );
      assertThat( systemUnderTest.costBudget().get(), is( resource2.netRate().get() * 30.0 ) );
      
      systemUnderTest.effort().set( null );
      assertThat( systemUnderTest.costBudget().get(), is( 0.0 ) );
      
      systemUnderTest.effort().set( 30.0 );
      systemUnderTest.resource().set( null );
      assertThat( systemUnderTest.costBudget().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldRespondToResourceChanges(){
      systemUnderTest.resource().set( resource1 );
      systemUnderTest.effort().set( 20.0 );
      assertThat( systemUnderTest.costBudget().get(), is( resource1.netRate().get() * 20.0 ) );
      
      resource1.baseRate().set( 250.0 );
      assertThat( systemUnderTest.costBudget().get(), is( resource1.netRate().get() * 20.0 ) );
      
      resource1.upRate().set( 100.0 );
      assertThat( systemUnderTest.costBudget().get(), is( resource1.netRate().get() * 20.0 ) );
   }//End Method

}//End Class
