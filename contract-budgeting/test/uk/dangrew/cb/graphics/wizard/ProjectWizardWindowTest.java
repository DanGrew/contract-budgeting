package uk.dangrew.cb.graphics.wizard;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.sun.javafx.application.PlatformImpl;

import javafx.stage.Stage;
import uk.dangrew.cb.model.concepts.ConceptFactory;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;

public class ProjectWizardWindowTest {

   @Spy private Stage stage;
   private Database database;
   private ProjectWizardWindow systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      PlatformImpl.runAndWait( () -> systemUnderTest = new ProjectWizardWindow( stage = new Stage(), database ) );
   }//End Method

   @Test public void shouldShowProject() {
      assertThat( stage.getScene(), is( nullValue() ) );
      PlatformImpl.runAndWait( () -> systemUnderTest.show( database.storeFor( ConceptFactory.project() ).createConcept( "Project" ) ) );
      assertThat( stage.getScene(), is( notNullValue() ) );
   }//End Method

}//End Class
