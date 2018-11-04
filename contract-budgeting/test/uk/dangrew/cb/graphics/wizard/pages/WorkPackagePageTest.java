package uk.dangrew.cb.graphics.wizard.pages;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;

public class WorkPackagePageTest {

   private Project project;
   private WorkPackagePage systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject( new Database() );
      systemUnderTest = new WorkPackagePage( project );
   }//End Method

   @Test public void shouldUpdateTableWhenProjectUpdate() {
      assertRowsMatch( 2 );
      systemUnderTest.controller().createConcept();
      assertRowsMatch( 3 );
      systemUnderTest.table().getSelectionModel().select( 1 );
      systemUnderTest.controller().removeSelectedConcept();
      assertRowsMatch( 2 );
      systemUnderTest.controller().copySelectedConcept();
      assertRowsMatch( 3 );
   }//End Method
   
   private void assertRowsMatch( int expectedRows ){
      assertThat( systemUnderTest.table().getRows().size(), is( expectedRows ) );
      assertThat( project.workPackages().size(), is( expectedRows ) );
      for( int i = 0; i < project.workPackages().size(); i++ ) {
         assertThat( systemUnderTest.table().getRows().get( i ).concept(), is( project.workPackages().get( i ) ) );
      }
   }//End Method
   
}//End Class
