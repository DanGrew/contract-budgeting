package uk.dangrew.cb.graphics.workspace.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.cb.model.concepts.ConceptFactory;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.controls.TableControlType;

public class CoreInterfaceTest {

   private CoreInterface systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      PlatformImpl.runAndWait( () -> systemUnderTest = new CoreInterface() );
   }//End Method

   @Test public void shouldAddProjectsToTable() {
      Project project = systemUnderTest.database().storeFor( ConceptFactory.project() ).createConcept( "TestProject" );
      assertThat( systemUnderTest.tableWithControls().table().getRows().get( 0 ).concept(), is( project ) );
   }//End Method
   
   @Test public void shouldCreateNewProject() {
      systemUnderTest.tableWithControls().controls().getButton( TableControlType.Add ).fire();
      Project added = systemUnderTest.database().storeFor( ConceptFactory.project() ).objectList().get( 0 );
      assertThat( systemUnderTest.tableWithControls().table().getRows().get( 0 ).concept(), is( added ) );
   }//End Method

}//End Class
