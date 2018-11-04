package uk.dangrew.cb.graphics.wizard.tables;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.budget.BudgetType;
import uk.dangrew.cb.model.budget.ResourceBudget;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;

public class WorkPackageResourceEntryTest {

   private Project project;
   private ResourceBudget budget;
   private WorkPackageResourceEntry systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject( new Database() );
      budget = new ResourceBudget( 100, new Resource( "Developer", 500, 5 ) );
      systemUnderTest = new WorkPackageResourceEntry( budget, BudgetType.Internal );
   }//End Method

   @Test public void shouldInitialiseWithBudgetValues() {
      assertThat( systemUnderTest.resource().get(), is( notNullValue() ) );
      assertThat( systemUnderTest.netRate().get(), is( 525.0 ) );
      assertThat( systemUnderTest.effort().get(), is( 100.0 ) );
      assertThat( systemUnderTest.cost().get(), is( 52500.00 ) );
   }//End Method
   
   @Test public void shouldUpdateEntryValuesWhenModelChanges() {
      budget.resource().set( new Resource( "PM", 600, 10 ) );

      assertThat( systemUnderTest.netRate().get(), is( 660.0 ) );
      assertThat( systemUnderTest.effort().get(), is( 100.0 ) );
      assertThat( systemUnderTest.cost().get(), is( 66000.00 ) );
      
      budget.effort().set( 50.0 );
      
      assertThat( systemUnderTest.netRate().get(), is( 660.0 ) );
      assertThat( systemUnderTest.effort().get(), is( 50.0 ) );
      assertThat( systemUnderTest.cost().get(), is( 33000.00 ) );
      
      budget.resource().get().baseRate().set( 300.0 );
      
      assertThat( systemUnderTest.netRate().get(), is( 330.0 ) );
      assertThat( systemUnderTest.effort().get(), is( 50.0 ) );
      assertThat( systemUnderTest.cost().get(), is( 16500.00 ) );
      
      budget.resource().set( null );

      assertThat( systemUnderTest.netRate().get(), is( 0.0 ) );
      assertThat( systemUnderTest.effort().get(), is( 50.0 ) );
      assertThat( systemUnderTest.cost().get(), is( 0.00 ) );
   }//End Method

   @Test public void shouldUpdateModelFromEntryChanges(){
      systemUnderTest.resource().set( new Resource( "PM", 600, 10 ) );

      assertThat( budget.effort().get(), is( 100.0 ) );
      assertThat( budget.costBudget().get(), is( 66000.00 ) );
      
      systemUnderTest.effort().set( 50.0 );
      
      assertThat( budget.effort().get(), is( 50.0 ) );
      assertThat( budget.costBudget().get(), is( 33000.00 ) );
      
      systemUnderTest.resource().set( null );
      
      assertThat( budget.effort().get(), is( 50.0 ) );
      assertThat( budget.costBudget().get(), is( 0.00 ) );
   }//End Method
   
   @Test public void shouldMoveBudgetBetweenWorkPackages(){
      assertThat( systemUnderTest.workPackage().get(), is( nullValue() ) );
      assertProjectWorkPackageContainsBudget( false, 0 );
      assertProjectWorkPackageContainsBudget( false, 1 );
      
      systemUnderTest.workPackage().set( project.workPackages().get( 0 ) );
      assertProjectWorkPackageContainsBudget( true, 0 );
      assertProjectWorkPackageContainsBudget( false, 1 );
      
      systemUnderTest.workPackage().set( project.workPackages().get( 1 ) );
      assertProjectWorkPackageContainsBudget( false, 0 );
      assertProjectWorkPackageContainsBudget( true, 1 );
   }//End Method
   
   private void assertProjectWorkPackageContainsBudget( boolean contains, int wpIndex ) {
      assertThat( project.workPackages().get( wpIndex ).budget().internalBudget().resources().contains( budget ), is( contains ) );
   }//End Method
}//End Class
