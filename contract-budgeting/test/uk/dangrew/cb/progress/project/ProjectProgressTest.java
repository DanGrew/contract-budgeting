package uk.dangrew.cb.progress.project;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.kode.launch.TestApplication;

public class ProjectProgressTest {

   private Project project;
   private ProjectProgress systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      project = new Project( "MyProject" );
      systemUnderTest = new ProjectProgress( project );
   }//End Method

   @Test public void shouldProvideDetails() {
      assertThat( systemUnderTest.project(), is( project ) );
      
      assertThat( systemUnderTest.date().get(), is( LocalDate.now() ) );
      assertThat( systemUnderTest.date(), is( systemUnderTest.date() ) );
      
      assertThat( systemUnderTest.workPackageProgress(), is( empty() ) );
   }//End Method
   
}//End Class
