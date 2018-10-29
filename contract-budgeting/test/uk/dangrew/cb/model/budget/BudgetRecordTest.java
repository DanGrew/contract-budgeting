package uk.dangrew.cb.model.budget;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class BudgetRecordTest {

   private BudgetRecord systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new BudgetRecord();
   }//End Method

   @Test public void shouldProvideInternalBudget() {
      assertThat( systemUnderTest.internalBudget(), is( instanceOf( Budget.class ) ) );
      assertThat( systemUnderTest.internalBudget(), is( systemUnderTest.internalBudget() ) );
   }//End Method
   
   @Test public void shouldProvideContractedBudget() {
      assertThat( systemUnderTest.contractBudget(), is( instanceOf( Budget.class ) ) );
      assertThat( systemUnderTest.contractBudget(), is( systemUnderTest.contractBudget() ) );
   }//End Method

}//End Class
