package uk.dangrew.cb.progress.project;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class ProjectRecordTest {

   private Project project;
   private ProjectRecord systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      project = ProjectTestData.sampleProject();
      systemUnderTest = new ProjectRecord( project );
   }//End Method

   @Test public void shouldProvideDetails() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideObject( ProjectRecord::project )
         .shouldProvideCollection( ProjectRecord::progress, new ProjectProgress( project ) )
         .shouldProvideReadOnlyDoubleProperty( ProjectRecord::contractCostBudget )
         .shouldProvideReadOnlyDoubleProperty( ProjectRecord::costBudgetUsed )
         .shouldProvideReadOnlyDoubleProperty( ProjectRecord::contractCostBudgetRemaining )
         .shouldProvideReadOnlyDoubleProperty( ProjectRecord::internalCostBudgetRemaining );
   }//End Method
   
   @Test public void shouldDynamicallyCalculateBudget(){
      double costBudget = ( 10 * 600 ) +
                          ( 100 * 500 ) +
                          ( 5 * 600 ) +
                          ( 20 * 500 );
      assertThat( systemUnderTest.contractCostBudget().get(), is( costBudget ) );
      
      project
         .workPackages().get( 0 )
         .budget().contractBudget().resources().get( 0 )
         .resource().get().baseRate().set( 700.0 );
      assertThat( systemUnderTest.contractCostBudget().get(), is( costBudget + 1500 ) );
   }//End Method
   
}//End Class
