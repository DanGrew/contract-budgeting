package uk.dangrew.cb.model.project;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.budget.ResourceBudget;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.launch.TestApplication;

public class ProjectBudgetCalculatorTest {

   private Project project;
   private ProjectBudgetCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProjectBudgetCalculator();
      project = new Project( systemUnderTest, "Project" );
   }//End Method

   @Test public void shouldCalculateBudget() {
      WorkPackage wp1 = new WorkPackage( "WP1" );
      wp1.budget().internalBudget().resources().add( new ResourceBudget( 10.0, new Resource( "R1", 500, 0 ) ) );
      wp1.budget().contractBudget().resources().add( new ResourceBudget( 5.0, new Resource( "R2", 500, 0 ) ) );
      project.workPackages().add( wp1 );
      
      assertThat( project.internalCostBudget().get(), is( 5000.0 ) );
      assertThat( project.contractCostBudget().get(), is( 2500.0 ) );
      
      wp1.budget().internalBudget().resources().get( 0 ).effort().set( 20.0 );
      wp1.budget().contractBudget().resources().get( 0 ).effort().set( 10.0 );
      
      assertThat( project.internalCostBudget().get(), is( 10000.0 ) );
      assertThat( project.contractCostBudget().get(), is( 5000.0 ) );
   }//End Method

}//End Class
