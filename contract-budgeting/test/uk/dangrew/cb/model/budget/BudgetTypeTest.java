package uk.dangrew.cb.model.budget;

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

public class BudgetTypeTest {

   private Project project;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject( new Database() );
   }//End Method

   @Test public void shouldProvideCorrectProperties() {
      assertThat( 
               BudgetType.Internal.getResourceBudget( project.workPackages().get( 0 ).budget() ), 
               is( project.workPackages().get( 0 ).budget().internalBudget() ) 
      );
      assertThat( 
               BudgetType.Contract.getResourceBudget( project.workPackages().get( 0 ).budget() ), 
               is( project.workPackages().get( 0 ).budget().contractBudget() ) 
      );
      
      assertThat( BudgetType.Internal.getCostBudget( project ), is( project.internalCostBudget() ) );
      assertThat( BudgetType.Contract.getCostBudget( project ), is( project.contractCostBudget() ) );
      
      assertThat( BudgetType.Internal.getTotalEffort( project ), is( project.totalInternalEffort() ) );
      assertThat( BudgetType.Contract.getTotalEffort( project ), is( project.totalContractEffort() ) );
   }//End Method

}//End Class
