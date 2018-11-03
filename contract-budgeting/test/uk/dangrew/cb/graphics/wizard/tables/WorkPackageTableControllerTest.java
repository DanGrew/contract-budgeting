package uk.dangrew.cb.graphics.wizard.tables;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.ConceptTable;

public class WorkPackageTableControllerTest {

   private Project project;
   private ConceptTable< WorkPackage > table;
   private WorkPackageTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject();
      systemUnderTest = new WorkPackageTableController( project );
      table = new ConceptTable<>( new WorkPackageColumns(), systemUnderTest );
   }//End Method

   @Test public void shouldUpdateTableWhenProjectUpdate() {
      assertRowsMatch( 2 );
      systemUnderTest.createConcept();
      assertRowsMatch( 3 );
      table.getSelectionModel().select( 1 );
      systemUnderTest.removeSelectedConcept();
      assertRowsMatch( 2 );
      systemUnderTest.copySelectedConcept();
      assertRowsMatch( 3 );
   }//End Method
   
   private void assertRowsMatch( int expectedRows ){
      assertThat( table.getRows().size(), is( expectedRows ) );
      assertThat( project.workPackages().size(), is( expectedRows ) );
      for( int i = 0; i < project.workPackages().size(); i++ ) {
         assertThat( table.getRows().get( i ).concept(), is( project.workPackages().get( i ) ) );
      }
   }//End Method
   
   @Test public void shouldIgnoreNoSelection(){
      assertRowsMatch( 2 );
      systemUnderTest.copySelectedConcept();
      assertRowsMatch( 2 );
      systemUnderTest.removeSelectedConcept();
      assertRowsMatch( 2 );
   }//End Method

}//End Class
