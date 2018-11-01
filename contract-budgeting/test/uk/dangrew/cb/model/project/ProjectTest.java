package uk.dangrew.cb.model.project;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
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
      
      systemUnderTest = new Project( "MyProject" );
      systemUnderTest.workPackages().add( firstWorkPackage );
   }//End Method
   
   @Test public void shouldProvideNameAndContract(){
      assertThat( systemUnderTest.properties().nameProperty().get(), is( "MyProject" ) );
      assertThat( systemUnderTest.properties().nameProperty(), is( systemUnderTest.properties().nameProperty() ) );
      
      assertThat( systemUnderTest.contract().get(), is( nullValue() ) );
      assertThat( systemUnderTest.contract(), is( systemUnderTest.contract() ) );
   }//End Method

   @Test public void shouldProvideInternalCostBudget(){
      assertThat( systemUnderTest.internalCostBudget().get(), is( 0.0 ) );
      
      firstWorkPackage.budget().internalBudget().resources().add( new ResourceBudget( 20, new Resource( "Developer", 500, 0 ) ) );
      assertThat( systemUnderTest.internalCostBudget().get(), is( 10000.0 ) );
   }//End Method
   
   @Test public void shouldProvideContractCostBudget(){
      assertThat( systemUnderTest.contractCostBudget().get(), is( 0.0 ) );
      
      firstWorkPackage.budget().contractBudget().resources().add( new ResourceBudget( 20, new Resource( "Developer", 500, 0 ) ) );
      assertThat( systemUnderTest.contractCostBudget().get(), is( 10000.0 ) );
   }//End Method
   
   @Test public void shouldProvideWorkPackages(){
      assertThat( systemUnderTest.workPackages(), hasSize( 1 ) );
      systemUnderTest.workPackages().add( new WorkPackage( "anything ") );
      assertThat( systemUnderTest.workPackages(), hasSize( 2 ) );
   }//End Method
   
   @Test public void shouldRecalculateBudgetWhenAddingWorkPackages(){
      systemUnderTest.workPackages().remove( firstWorkPackage );
      firstWorkPackage.budget().contractBudget().resources().add( new ResourceBudget( 20, new Resource( "Developer", 500, 0 ) ) );
      assertThat( systemUnderTest.contractCostBudget().get(), is( 0.0 ) );
      systemUnderTest.workPackages().add( firstWorkPackage );
      assertThat( systemUnderTest.contractCostBudget().get(), is( not( 0.0 ) ) );
   }//End Method

}//End Class
