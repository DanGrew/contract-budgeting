package uk.dangrew.cb.model.project;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.budget.ResourceBudget;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.launch.TestApplication;

public class ProjectTest {

   private WorkPackage firstWorkPackage;
   private Project systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      firstWorkPackage = new WorkPackage( "First" );
      
      systemUnderTest = new Project();
      systemUnderTest.addWorkPackage( firstWorkPackage );
   }//End Method

   @Test public void shouldProvideInternalCostBudget(){
      assertThat( systemUnderTest.internalCostBudget().get(), is( 0.0 ) );
      
      firstWorkPackage.budget().internalBudget().addResourceBudget( new ResourceBudget( 20, new Resource( "Developer", 500, 0 ) ) );
      assertThat( systemUnderTest.internalCostBudget().get(), is( 10000.0 ) );
   }//End Method
   
   @Test public void shouldProvideContractCostBudget(){
      assertThat( systemUnderTest.contractCostBudget().get(), is( 0.0 ) );
      
      firstWorkPackage.budget().contractBudget().addResourceBudget( new ResourceBudget( 20, new Resource( "Developer", 500, 0 ) ) );
      assertThat( systemUnderTest.contractCostBudget().get(), is( 10000.0 ) );
   }//End Method
   
   @Test public void shouldProvideWorkPackages(){
      assertThat( systemUnderTest.workPackages(), hasSize( 1 ) );
      systemUnderTest.addWorkPackage( new WorkPackage( "anything ") );
      assertThat( systemUnderTest.workPackages(), hasSize( 2 ) );
   }//End Method

}//End Class
