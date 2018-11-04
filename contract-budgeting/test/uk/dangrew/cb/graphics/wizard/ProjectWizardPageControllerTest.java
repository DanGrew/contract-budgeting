package uk.dangrew.cb.graphics.wizard;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;

public class ProjectWizardPageControllerTest {

   private Project project;
   private ProjectWizard wizard;
   
   private List< Node > pages;
   private ProjectWizardPageController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject( new Database() );
      pages = Arrays.asList( new BorderPane(), new GridPane() );
      systemUnderTest = new ProjectWizardPageController( pages, project );
      wizard = new ProjectWizard( systemUnderTest );
   }//End Method

   @Test public void shouldShowNextPage() {
      assertThat( wizard.nextButton().isDisable(), is( false ) );
      assertThat( wizard.getCenter(), is( pages.get( 0 ) ) );
      systemUnderTest.nextPage();
      assertThat( wizard.nextButton().isDisable(), is( true ) );
      assertThat( wizard.getCenter(), is( pages.get( 1 ) ) );
      systemUnderTest.nextPage();
      assertThat( wizard.nextButton().isDisable(), is( true ) );
      assertThat( wizard.getCenter(), is( pages.get( 1 ) ) );
   }//End Method
   
   @Test public void shouldShowPreviousPage() {
      systemUnderTest.nextPage();
      assertThat( wizard.previousButton().isDisable(), is( false ) );
      assertThat( wizard.getCenter(), is( pages.get( 1 ) ) );
      systemUnderTest.previousPage();
      assertThat( wizard.previousButton().isDisable(), is( true ) );
      assertThat( wizard.getCenter(), is( pages.get( 0 ) ) );
      systemUnderTest.previousPage();
      assertThat( wizard.previousButton().isDisable(), is( true ) );
      assertThat( wizard.getCenter(), is( pages.get( 0 ) ) );
   }//End Method

}//End Class
