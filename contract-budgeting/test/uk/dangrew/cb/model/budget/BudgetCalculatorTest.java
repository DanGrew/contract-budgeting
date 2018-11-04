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
import uk.dangrew.kode.launch.TestApplication;

public class BudgetCalculatorTest {

   private Budget budget;
   private BudgetCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new BudgetCalculator();
      budget = new Budget( systemUnderTest );
   }//End Method

   @Test public void shouldProvideCostBudget() {
      assertThat( budget.costBudget().get(), is( closeTo( 0.0, precision() ) ) );
      budget.resources().add( new ResourceBudget( 10.0, new Resource( "PM", 500.0, 0.0 ) ) );
      assertThat( budget.costBudget().get(), is( closeTo( 5000.0, precision() ) ) );
      budget.resources().add( new ResourceBudget( 1.0, new Resource( "PM2", 600.0, 0.0 ) ) );
      assertThat( budget.costBudget().get(), is( closeTo( 5600.0, precision() ) ) );
      budget.resources().remove( budget.resources().get( 0 ) );
      assertThat( budget.costBudget().get(), is( closeTo( 600.0, precision() ) ) );
      
      budget.resources().get( 0 ).resource().get().baseRate().set( 400.0 );
      assertThat( budget.costBudget().get(), is( closeTo( 400.0, precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideTotalEffort() {
      assertThat( budget.totalEffort().get(), is( closeTo( 0.0, precision() ) ) );
      budget.resources().add( new ResourceBudget( 10.0, new Resource( "PM", 500.0, 0.0 ) ) );
      assertThat( budget.totalEffort().get(), is( closeTo( 10.0, precision() ) ) );
      budget.resources().add( new ResourceBudget( 1.0, new Resource( "PM2", 600.0, 0.0 ) ) );
      assertThat( budget.totalEffort().get(), is( closeTo( 11.0, precision() ) ) );
      budget.resources().remove( budget.resources().get( 0 ) );
      assertThat( budget.totalEffort().get(), is( closeTo( 1.0, precision() ) ) );
   }//End Method
   
   @Test public void shouldRespondToResourceChanges() {
      assertThat( budget.costBudget().get(), is( closeTo( 0.0, precision() ) ) );
      budget.resources().add( new ResourceBudget( 10.0, new Resource( "PM", 500.0, 0.0 ) ) );
      assertThat( budget.costBudget().get(), is( closeTo( 5000.0, precision() ) ) );
      budget.resources().get( 0 ).effort().set( 20.0 );
      assertThat( budget.costBudget().get(), is( closeTo( 10000.0, precision() ) ) );
      budget.resources().get( 0 ).resource().get().baseRate().set( 250.0 );
      assertThat( budget.costBudget().get(), is( closeTo( 5000.0, precision() ) ) );
   }//End Method

}//End Class
