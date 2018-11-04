package uk.dangrew.cb.model.workpackage;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.budget.BudgetRecord;
import uk.dangrew.kode.launch.TestApplication;

public class WorkPackageTest {

   private WorkPackage systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new WorkPackage( "myWorkpackage" );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.properties().nameProperty().get(), is( "myWorkpackage" ) );
   }//End Method
   
   @Test public void shouldProvideBudget() {
      assertThat( systemUnderTest.budget(), is( instanceOf( BudgetRecord.class ) ) );
      assertThat( systemUnderTest.budget(), is( systemUnderTest.budget() ) );
   }//End Method
   
}//End Class
