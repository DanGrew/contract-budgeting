package uk.dangrew.cb.progress.workpackage;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class WorkPackageProgressTest {

   private WorkPackageProgress systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new WorkPackageProgress();
   }//End Method

   @Test public void shouldProvideProgress() {
      assertThat( systemUnderTest.workPackage().get(), is( nullValue() ) );
      assertThat( systemUnderTest.workPackage(), is( systemUnderTest.workPackage() ) );
      
      assertThat( systemUnderTest.resource().get(), is( nullValue() ) );
      assertThat( systemUnderTest.resource(), is( systemUnderTest.resource() ) );
      
      assertThat( systemUnderTest.effort().get(), is( 0.0 ) );
      assertThat( systemUnderTest.effort(), is( systemUnderTest.effort() ) );
   }//End Method

}//End Class
