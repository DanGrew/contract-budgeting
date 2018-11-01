package uk.dangrew.cb.model.budget;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static uk.dangrew.kode.TestCommon.precision;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.kode.TestCommon;
import uk.dangrew.kode.launch.TestApplication;

public class ResourceBudgetCalculatorTest {

   private ResourceBudget budget;
   private Resource resource;
   private ResourceBudgetCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      resource = new Resource( "Developer", 200, 0.0 );
      
      systemUnderTest = new ResourceBudgetCalculator();
      budget = new ResourceBudget( systemUnderTest, 10.0, resource );
   }//End Method

   @Test public void shouldRespondToResouceNetChange() {
      assertThat( budget.costBudget().get(), is( closeTo( 2000.0, precision() ) ) );
      resource.upRate().set( 10.0 );
      assertThat( budget.costBudget().get(), is( closeTo( 2200.0, precision() ) ) );
   }//End Method
   
   @Test public void shouldRespondToEffortChange() {
      assertThat( budget.costBudget().get(), is( closeTo( 2000.0, precision() ) ) );
      budget.effort().set( 20.0 );
      assertThat( budget.costBudget().get(), is( closeTo( 4000.0, precision() ) ) );
   }//End Method
   
   @Test public void shouldRespondToResourceChange() {
      assertThat( budget.costBudget().get(), is( closeTo( 2000.0, precision() ) ) );
      resource.upRate().set( 10.0 );
      assertThat( budget.costBudget().get(), is( closeTo( 2200.0, precision() ) ) );
      
      budget.resource().set( new Resource( "PM" ) );
      assertThat( budget.costBudget().get(), is( closeTo( 0.0, precision() ) ) );
      
      resource.baseRate().set( 99999.0 );
      assertThat( budget.costBudget().get(), is( closeTo( 0.0, precision() ) ) );
   }//End Method

}//End Class
