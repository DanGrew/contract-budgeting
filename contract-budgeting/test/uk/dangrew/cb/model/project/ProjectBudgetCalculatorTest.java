package uk.dangrew.cb.model.project;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.budget.ResourceBudget;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.concept.Properties;
import uk.dangrew.kode.launch.TestApplication;

public class ProjectBudgetCalculatorTest {

   private WorkPackage workPackage;
   private Project project;
   private ProjectBudgetCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProjectBudgetCalculator();
      
      workPackage = new WorkPackage( "WP1" );
      workPackage.budget().internalBudget().resources().add( new ResourceBudget( 10.0, new Resource( "R1", 500, 0 ) ) );
      workPackage.budget().contractBudget().resources().add( new ResourceBudget( 5.0, new Resource( "R2", 500, 0 ) ) );
      project = new Project( systemUnderTest, new Properties( "Project" ) );
      project.workPackages().add( workPackage );
   }//End Method

   @Test public void shouldCalculateBudget() {
      assertThat( project.internalCostBudget().get(), is( 5000.0 ) );
      assertThat( project.contractCostBudget().get(), is( 2500.0 ) );
      
      workPackage.budget().internalBudget().resources().get( 0 ).effort().set( 20.0 );
      workPackage.budget().contractBudget().resources().get( 0 ).effort().set( 10.0 );
      
      assertThat( project.internalCostBudget().get(), is( 10000.0 ) );
      assertThat( project.contractCostBudget().get(), is( 5000.0 ) );
   }//End Method
   
   @Test public void shouldUpdateTotalEffortWhenEffortChanges(){
      assertThat( project.totalContractEffort().get(), is( 5.0 ) );
      assertThat( project.totalInternalEffort().get(), is( 10.0 ) );
      
      workPackage.budget().internalBudget().resources().clear();
      workPackage.budget().contractBudget().resources().clear();
      
      assertThat( project.totalContractEffort().get(), is( 0.0 ) );
      assertThat( project.totalInternalEffort().get(), is( 0.0 ) );
      
      workPackage.budget().contractBudget().resources().add( new ResourceBudget( 10, new Resource( "" ) ) );
      assertThat( project.totalContractEffort().get(), is( 10.0 ) );
      assertThat( project.totalInternalEffort().get(), is( 0.0 ) );
      
      workPackage.budget().internalBudget().resources().add( new ResourceBudget( 5, new Resource( "" ) ) );
      assertThat( project.totalContractEffort().get(), is( 10.0 ) );
      assertThat( project.totalInternalEffort().get(), is( 5.0 ) );
      
      project.workPackages().remove( workPackage );
      assertThat( project.totalContractEffort().get(), is( 0.0 ) );
      assertThat( project.totalInternalEffort().get(), is( 0.0 ) );
      
      project.workPackages().add( workPackage );
      assertThat( project.totalContractEffort().get(), is( 10.0 ) );
      assertThat( project.totalInternalEffort().get(), is( 5.0 ) );
      
      workPackage.budget().contractBudget().resources().get( 0 ).effort().set( 20.0 );
      assertThat( project.totalContractEffort().get(), is( 20.0 ) );
      assertThat( project.totalInternalEffort().get(), is( 5.0 ) );
   }//End Method

}//End Class
