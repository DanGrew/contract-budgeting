package uk.dangrew.cb.graphics.workspace.tables;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.cb.model.budget.BudgetType;
import uk.dangrew.cb.model.budget.ResourceBudget;
import uk.dangrew.cb.model.concepts.ConceptFactory;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.ConceptTable;

public class ProjectTableControllerTest {

   private Database database;
   private Project project;
   private ConceptTable< Project > table;
   private ProjectTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject( database = new Database() );
      systemUnderTest = new ProjectTableController( database.storeFor( ConceptFactory.project() ) );
      PlatformImpl.runAndWait( () -> table = new ConceptTable<>( new ProjectColumns( database ), systemUnderTest ) );
   }//End Method

   @Test public void shouldSynchronizeProjectsAndTable() {
      assertRowsMatch( 1 );
      systemUnderTest.createConcept();
      assertRowsMatch( 2 );
      table.getSelectionModel().select( 1 );
      systemUnderTest.removeSelectedConcept();
      assertRowsMatch( 1 );
      table.getSelectionModel().select( 0 );
      systemUnderTest.copySelectedConcept();
      assertRowsMatch( 2 );
   }//End Method
   
   private void assertRowsMatch( int expectedRows ){
      assertThat( table.getRows().size(), is( expectedRows ) );
      assertThat( database.storeFor( ConceptFactory.project() ).objectList().size(), is( expectedRows ) );
      
      int rowCount = 0;
      for ( Project project : database.storeFor( ConceptFactory.project() ).objectList() ) {
         assertThat( table.getRows().get( rowCount ).concept().properties().nameProperty().get(), is( project.properties().nameProperty().get() ) );
         assertThat( table.getRows().get( rowCount ).concept().contract().get(), is( project.contract().get() ) );
         assertThat( table.getRows().get( rowCount ).concept().contractCostBudget().get(), is( project.contractCostBudget().get() ) );
         assertThat( table.getRows().get( rowCount ).concept().totalContractEffort().get(), is( project.totalContractEffort().get() ) );
         assertThat( table.getRows().get( rowCount ).concept().internalCostBudget().get(), is( project.internalCostBudget().get() ) );
         assertThat( table.getRows().get( rowCount ).concept().totalInternalEffort().get(), is( project.totalInternalEffort().get() ) );
         rowCount++;
      }
   }//End Method
   
   @Test public void shouldIgnoreNoSelection(){
      assertRowsMatch( 1 );
      systemUnderTest.copySelectedConcept();
      assertRowsMatch( 1 );
      systemUnderTest.removeSelectedConcept();
      assertRowsMatch( 1 );
   }//End Method

}//End Class
