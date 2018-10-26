package uk.dangrew.cb.model.budget;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.budget.Budget;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.kode.launch.TestApplication;

public class BudgetTest {

   private ResourceBudget resourceBudget1;
   private ResourceBudget resourceBudget2;
   private Budget systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      resourceBudget1 = new ResourceBudget();
      resourceBudget1.resource().set( new Resource( "Developer" ) );
      resourceBudget1.resource().get().baseRate().set( 500.0 );
      resourceBudget1.effort().set( 45.0 );
      
      resourceBudget2 = new ResourceBudget();
      resourceBudget2.resource().set( new Resource( "Manager" ) );
      resourceBudget2.resource().get().baseRate().set( 600.0 );
      resourceBudget2.effort().set( 10.0 );
      
      systemUnderTest = new Budget();
   }//End Method

   @Test public void shouldProvideCostBudget() {
      assertThat( systemUnderTest.costBudget().get(), is( 0.0 ) );
      systemUnderTest.addResourceBudget( resourceBudget1 );
      assertThat( systemUnderTest.costBudget().get(), is( 45.0 * 500 ) );
      systemUnderTest.addResourceBudget( resourceBudget2 );
      assertThat( systemUnderTest.costBudget().get(), is( 45.0 * 500 + 10 * 600 ) );
      systemUnderTest.removeResourceBudget( resourceBudget2 );
      assertThat( systemUnderTest.costBudget().get(), is( 45.0 * 500 ) );
   }//End Method
   
   @Test public void shouldRespondToBudgetChanges() {
      assertThat( systemUnderTest.costBudget().get(), is( 0.0 ) );
      systemUnderTest.addResourceBudget( resourceBudget1 );
      systemUnderTest.addResourceBudget( resourceBudget2 );
      assertThat( systemUnderTest.costBudget().get(), is( 45.0 * 500 + 10 * 600 ) );
      
      resourceBudget1.resource().get().baseRate().set( 250.0 );
      assertThat( systemUnderTest.costBudget().get(), is( 45.0 * 250 + 10 * 600 ) );
   }//End Method

}//End Class
