package uk.dangrew.cb.graphics.wizard.pages;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.kode.launch.TestApplication;

public class ProjectSetupPageTest {

   private Project project;
   private ProjectSetupPage systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject();
      systemUnderTest = new ProjectSetupPage( project );
   }//End Method

   @Test public void shouldSynchronizeName() {
      systemUnderTest.nameField().setText( "This is my project!" );
      assertThat( project.properties().nameProperty().get(), is( "This is my project!" ) );
      
      project.properties().nameProperty().set( "Now it's this!" );
      assertThat( systemUnderTest.nameField().getText(), is( "Now it's this!" ) );
   }//End Method
   
   @Test public void shouldSynchronizeContract() {
      systemUnderTest.contractField().setText( "This is my contract!" );
      assertThat( project.contract().get(), is( "This is my contract!" ) );
      
      project.contract().set( "Now it's this!" );
      assertThat( systemUnderTest.contractField().getText(), is( "Now it's this!" ) );
   }//End Method

}//End Class
