package uk.dangrew.cb.graphics.wizard.tables;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.budget.BudgetType;
import uk.dangrew.cb.model.budget.ResourceBudget;
import uk.dangrew.cb.model.concepts.ConceptFactory;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.ConceptTable;

public class WorkPackageResourceEntryTableControllerTest {

   private Database database;
   private Project project;
   private ConceptTable< WorkPackageResourceEntry > table;
   private WorkPackageResourceEntryTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject( database = new Database() );
      systemUnderTest = new WorkPackageResourceEntryTableController( project, BudgetType.Internal );
      table = new ConceptTable<>( new WorkPackageResourceEntryColumns( 
               project, 
               database.storeFor( ConceptFactory.resource() )
      ), systemUnderTest );
   }//End Method

   @Test public void shouldSynchronizeProjectAndTable() {
      assertRowsMatch( 4 );
      systemUnderTest.createConcept();
      assertRowsMatch( 5 );
      table.getSelectionModel().select( 1 );
      systemUnderTest.removeSelectedConcept();
      assertRowsMatch( 4 );
      table.getSelectionModel().select( 3 );
      systemUnderTest.copySelectedConcept();
      assertRowsMatch( 5 );
   }//End Method
   
   @Ignore //not supported yet
   @Test public void shouldListenForBudgetChanges(){
      assertRowsMatch( 4 );
      project.workPackages().add( new WorkPackage( "WP3" ) );
      assertRowsMatch( 4 );
      project.workPackages().get( 2 ).budget().internalBudget().resources().add( new ResourceBudget() );
      assertRowsMatch( 5 );
   }//End Method
   
   private void assertRowsMatch( int expectedRows ){
      assertThat( table.getRows().size(), is( expectedRows ) );
      
      int rowCount = 0;
      for ( WorkPackage wp : project.workPackages() ) {
         for ( ResourceBudget r : wp.budget().internalBudget().resources() ) {
            assertThat( table.getRows().get( rowCount ).concept().effort().get(), is( r.effort().get() ) );
            assertThat( table.getRows().get( rowCount ).concept().workPackage().get(), is( wp ) );
            assertThat( table.getRows().get( rowCount ).concept().cost().get(), is( r.costBudget().get() ) );
            rowCount++;
         }
      }
   }//End Method
   
   @Test public void shouldIgnoreNoSelection(){
      assertRowsMatch( 4 );
      systemUnderTest.copySelectedConcept();
      assertRowsMatch( 4 );
      systemUnderTest.removeSelectedConcept();
      assertRowsMatch( 4 );
   }//End Method

}//End Class
