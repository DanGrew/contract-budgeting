package uk.dangrew.cb.graphics.wizard.pages;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;

public class ProjectSummaryPageTest {

   private Project project;
   private ProjectSummaryPage systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject( new Database() );
      systemUnderTest = new ProjectSummaryPage( project );
   }//End Method

   @Test public void shouldSynchronizeName() {
      systemUnderTest.nameField().setText( "This is my project!" );
      assertThat( project.properties().nameProperty().get(), is( "This is my project!" ) );
      
      project.properties().nameProperty().set( "Now it's this!" );
      assertThat( systemUnderTest.nameField().getText(), is( "Now it's this!" ) );
   }//End Method
   
   @Test public void shouldSynchronizeContract() {
      systemUnderTest.contractField().setText( "This is my contract!" );
      assertThat( project.contract().get(), is( "This is my contract!" ) );
      
      project.contract().set( "Now it's this!" );
      assertThat( systemUnderTest.contractField().getText(), is( "Now it's this!" ) );
   }//End Method
   
   @Test public void shouldProvideSummaryProperties(){
      assertThat( systemUnderTest.contractBudgetField().getText(), is( "69000" ) );
      assertThat( systemUnderTest.internalBudgetField().getText(), is( "95000" ) );
      assertThat( systemUnderTest.totalContractEffortField().getText(), is( "135" ) );
      assertThat( systemUnderTest.totalInternalEffortField().getText(), is( "185" ) );
      
      project.workPackages().get( 0 ).budget().internalBudget().resources().get( 0 ).effort().set( 25.0 );
      project.workPackages().get( 0 ).budget().contractBudget().resources().get( 0 ).effort().set( 25.0 );
      
      assertThat( systemUnderTest.contractBudgetField().getText(), is( "78000" ) );
      assertThat( systemUnderTest.internalBudgetField().getText(), is( "101000" ) );
      assertThat( systemUnderTest.totalContractEffortField().getText(), is( "150" ) );
      assertThat( systemUnderTest.totalInternalEffortField().getText(), is( "195" ) );
   }//End Method

}//End Class
