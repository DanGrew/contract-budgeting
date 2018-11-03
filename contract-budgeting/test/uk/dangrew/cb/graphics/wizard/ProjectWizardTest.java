package uk.dangrew.cb.graphics.wizard;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;

public class ProjectWizardTest {
   
   @Mock private ProjectWizardPageController controller;
   private Project project;
   private ProjectWizard systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      project = ProjectTestData.sampleProject();
      systemUnderTest = new ProjectWizard( controller );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      systemUnderTest = new ProjectWizard( new Database(), project );
      TestApplication.launch( () -> systemUnderTest, false );
      
      Thread.sleep( 99999999 );
   }//End Method
   
   @Test public void shouldInstructController(){
      systemUnderTest.nextButton().fire();
      verify( controller ).nextPage();
      
      systemUnderTest.previousButton().fire();
      verify( controller ).previousPage();
   }//End Method

}//End Class
