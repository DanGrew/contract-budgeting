package uk.dangrew.cb.progress.project;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.progress.workpackage.WorkPackageProgress;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;

public class ProjectRecordMetricsTest {
   
   private ProjectRecord projectRecord;
   private Project project;
   private ProjectRecordMetrics systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      project = ProjectTestData.sampleProject( new Database() );
      systemUnderTest = new ProjectRecordMetrics();
      projectRecord = new ProjectRecord( systemUnderTest, project );
   }//End Method

   @Test public void shouldUpdateCostBudget() {
      assertThat( projectRecord.costBudgetUsed().get(), is( 0.0 ) );
      assertThat( projectRecord.contractCostBudgetRemaining().get(), is( project.contractCostBudget().get() ) );
      
      ProjectProgress progress1 = new ProjectProgress( project );
      progress1.workPackageProgress().add( new WorkPackageProgress( 
               project.workPackages().get( 0 ),
               project.workPackages().get( 0 ).budget().contractBudget().resources().get( 0 ).resource().get(),
               2.0
      ) );
      projectRecord.progress().add( progress1 );
      
      assertThat( projectRecord.costBudgetUsed().get(), is( 1200.0 ) );
      assertThat( projectRecord.contractCostBudgetRemaining().get(), is( project.contractCostBudget().get() - 1200 ) );
      assertThat( projectRecord.internalCostBudgetRemaining().get(), is( project.internalCostBudget().get() - 1200 ) );
      
      progress1.workPackageProgress().get( 0 ).resource().set( 
               project.workPackages().get( 0 ).budget().contractBudget().resources().get( 1 ).resource().get() 
      );
      assertThat( projectRecord.costBudgetUsed().get(), is( 1000.0 ) );
      assertThat( projectRecord.contractCostBudgetRemaining().get(), is( project.contractCostBudget().get() - 1000 ) );
      assertThat( projectRecord.internalCostBudgetRemaining().get(), is( project.internalCostBudget().get() - 1000 ) );
   }//End Method

}//End Class
