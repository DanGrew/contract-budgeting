package uk.dangrew.cb.graphics.wizard.tables;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.cb.model.budget.BudgetType;
import uk.dangrew.cb.model.concepts.ConceptFactory;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnConfigurer;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;

public class WorkPackageResourceEntryColumnsTest {

   private Database database;
   private Project project;
   
   @Captor private ArgumentCaptor< TableColumnConfigurer > columnConfigurerCaptor;
   @Captor private ArgumentCaptor< Function< WorkPackage, ObjectProperty< String > > > propertyRetrieverCaptor;
   
   @Spy private TableConfiguration configuration;
   private ConceptTable< WorkPackageResourceEntry > table;
   private WorkPackageResourceEntryColumns systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      project = ProjectTestData.sampleProject( database = new Database() );
      systemUnderTest = new WorkPackageResourceEntryColumns( configuration, project, database.storeFor( ConceptFactory.resource() ) );
      table = new ConceptTable<>( systemUnderTest, new WorkPackageResourceEntryTableController( project, BudgetType.Internal ) );
   }//End Method

   @Test public void shouldProvideColumns() {
      assertThat( table.getColumns().get( 0 ).getText(), is( WorkPackageResourceEntryColumns.WORK_PACKAGE_COLUMN_TEXT ) );
      assertThat( table.getColumns().get( 1 ).getText(), is( WorkPackageResourceEntryColumns.NAME_COLUMN_TEXT ) );
      assertThat( table.getColumns().get( 2 ).getText(), is( WorkPackageResourceEntryColumns.NET_RATE_COLUMN_TEXT ) );
      assertThat( table.getColumns().get( 3 ).getText(), is( WorkPackageResourceEntryColumns.EFFORT_COLUMN_TEXT ) );
      assertThat( table.getColumns().get( 4 ).getText(), is( WorkPackageResourceEntryColumns.COST_COLUMN_TEXT ) );
   }//End Method
   
   @Test public void shouldPopulateColumns(){
      assertThat( table.getColumns().get( 0 ).getCellData( 0 ), is( project.workPackages().get( 0 ) ) );
      assertThat( table.getColumns().get( 1 ).getCellData( 0 ), is( project.workPackages().get( 0 ).budget().internalBudget().resources().get( 0 ).resource().get() ) );
      assertThat( 
               table.getColumns().get( 2 ).getCellData( 0 ), 
               is( project.workPackages().get( 0 ).budget().internalBudget().resources().get( 0 ).resource().get().netRate().get().toString() ) 
      );
      assertThat( 
               table.getColumns().get( 3 ).getCellData( 0 ), 
               is( project.workPackages().get( 0 ).budget().internalBudget().resources().get( 0 ).effort().get().toString() ) 
      );
      assertThat( 
               table.getColumns().get( 4 ).getCellData( 0 ), 
               is( project.workPackages().get( 0 ).budget().internalBudget().resources().get( 0 ).costBudget().get().toString() ) 
      );
   }//End Method

}//End Class
