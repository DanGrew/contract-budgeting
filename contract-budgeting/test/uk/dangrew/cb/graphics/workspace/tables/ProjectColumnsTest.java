package uk.dangrew.cb.graphics.workspace.tables;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import uk.dangrew.cb.graphics.wizard.ProjectWizardWindow;
import uk.dangrew.cb.model.concepts.ConceptFactory;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.TableConfiguration;

public class ProjectColumnsTest {

   private Database database;
   private Project project;
   
   @Mock private ProjectWizardWindow wizard;
   @Spy private TableConfiguration configuration;
   private ConceptTable< Project > table;
   private ProjectColumns systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject( database = new Database() );
      systemUnderTest = new ProjectColumns( configuration, wizard );
      table = new ConceptTable<>( systemUnderTest, new ProjectTableController( database.storeFor( ConceptFactory.project() ) ) );
   }//End Method

   @Test public void shouldProvideColumns() {
      assertThat( table.getColumns().get( 0 ).getText(), is( ProjectColumns.NAME_COLUMN_TEXT ) );
      assertThat( table.getColumns().get( 1 ).getText(), is( ProjectColumns.CONTRACT_COLUMN_TEXT ) );
      assertThat( table.getColumns().get( 2 ).getText(), is( ProjectColumns.CONTRACT_BUDGET_COLUMN_TEXT ) );
      assertThat( table.getColumns().get( 3 ).getText(), is( ProjectColumns.CONTRACT_EFFORT_COLUMN_TEXT ) );
      assertThat( table.getColumns().get( 4 ).getText(), is( ProjectColumns.INTERNAL_BUDGET_COLUMN_TEXT ) );
      assertThat( table.getColumns().get( 5 ).getText(), is( ProjectColumns.INTERNAL_EFFORT_COLUMN_TEXT ) );
      assertThat( table.getColumns().get( 6 ).getText(), is( ProjectColumns.EDIT_EFFORT_COLUMN_TEXT ) );
   }//End Method
   
   @Test public void shouldPopulateColumns(){
      assertThat( table.getColumns().get( 0 ).getCellData( 0 ), is( project.properties().nameProperty().get() ) );
      assertThat( table.getColumns().get( 1 ).getCellData( 0 ), is( project.contract().get() ) );
      assertThat( table.getColumns().get( 2 ).getCellData( 0 ), is( "" + project.contractCostBudget().get() ) );
      assertThat( table.getColumns().get( 3 ).getCellData( 0 ), is( "" + project.totalContractEffort().get() ) );
      assertThat( table.getColumns().get( 4 ).getCellData( 0 ), is( "" + project.internalCostBudget().get() ) );
      assertThat( table.getColumns().get( 5 ).getCellData( 0 ), is( "" + project.totalInternalEffort().get() ) );
   }//End Method
   
   @Ignore
   @Test public void shouldShowWizard(){
      //can't get access to button???
      verify( wizard ).show( project );
   }//End Method

}//End Class
