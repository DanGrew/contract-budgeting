package uk.dangrew.cb.graphics.wizard.pages;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
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

public class WorkPackageResourceEntryPageTest {

   private Database database;
   private Project project;
   private WorkPackageResourceEntryPage systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject( database = new Database() );
      systemUnderTest = new WorkPackageResourceEntryPage( 
               project, 
               database.storeFor( ConceptFactory.resource() ), 
               BudgetType.Internal 
      );
   }//End Method

   @Test public void shouldUpdateProjectWhenTableChanges() {
      assertRowsMatch( 4 );
      systemUnderTest.controller().createConcept();
      assertRowsMatch( 5 );
      assertThat( systemUnderTest.table().getRows().get( 4 ).concept().workPackage().get(), is( nullValue() ) );
      //note order is not swapped in table, so chosen 2nd wp for ease of testing
      systemUnderTest.table().getRows().get( 4 ).concept().workPackage().set( project.workPackages().get( 1 ) );
      assertRowsMatch( 5 );
      
      project.workPackages().remove( 0 );
      assertRowsMatch( 3 );
   }//End Method
   
   private void assertRowsMatch( int expectedRows ){
      assertThat( systemUnderTest.table().getRows().size(), is( expectedRows ) );
      
      int rowCount = 0;
      for ( WorkPackage wp : project.workPackages() ) {
         for ( ResourceBudget r : wp.budget().internalBudget().resources() ) {
            assertThat( systemUnderTest.table().getRows().get( rowCount ).concept().effort().get(), is( r.effort().get() ) );
            assertThat( systemUnderTest.table().getRows().get( rowCount ).concept().workPackage().get(), is( wp ) );
            assertThat( systemUnderTest.table().getRows().get( rowCount ).concept().cost().get(), is( r.costBudget().get() ) );
            rowCount++;
         }
      }
      
      assertThat( systemUnderTest.costBudgetField().getText(), is( "" + project.internalCostBudget().get().intValue() ) );
      assertThat( systemUnderTest.totalEffortField().getText(), is( "" + project.totalInternalEffort().get().intValue() ) );
   }//End Method
   
}//End Class
