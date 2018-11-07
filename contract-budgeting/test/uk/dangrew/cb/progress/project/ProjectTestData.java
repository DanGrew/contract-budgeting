package uk.dangrew.cb.progress.project;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import uk.dangrew.cb.model.budget.ResourceBudget;
import uk.dangrew.cb.model.concepts.ConceptFactory;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.cb.toolkit.Database;

public class ProjectTestData {

   public static Project sampleProject( Database database ){
      Resource r1 = database.storeFor( ConceptFactory.resource() ).createConcept( "PM" );
      r1.baseRate().set( 600.0 );
      Resource r2 = database.storeFor( ConceptFactory.resource() ).createConcept( "Developer" );
      r2.baseRate().set( 500.0 );
      
      WorkPackage wp1 = new WorkPackage( "WP1" );
      WorkPackage wp2 = new WorkPackage( "WP2" );
      
      wp1.budget().contractBudget().resources().add( new ResourceBudget( 10, r1 ) );
      wp1.budget().contractBudget().resources().add( new ResourceBudget( 100, r2 ) );
      
      wp2.budget().contractBudget().resources().add( new ResourceBudget( 5, r1 ) );
      wp2.budget().contractBudget().resources().add( new ResourceBudget( 20, r2 ) );
      
      wp1.budget().internalBudget().resources().add( new ResourceBudget( 15, r1 ) );
      wp1.budget().internalBudget().resources().add( new ResourceBudget( 110, r2 ) );
      
      wp2.budget().internalBudget().resources().add( new ResourceBudget( 10, r1 ) );
      wp2.budget().internalBudget().resources().add( new ResourceBudget( 50, r2 ) );
      
      Project project = database.storeFor( ConceptFactory.project() ).createConcept( "Project" );
      project.contract().set( "C234" );
      project.workPackages().add( wp1 );
      project.workPackages().add( wp2 );
      
      //contract budget = (10*600)+(100*500)+(5*500)+(20*500) = 68500
      assertThat( project.contractCostBudget().get(), is( 69000.0 ) );
      //internal budget = (15*600)+(110*500)+(10*500)+(50*500) = 95000
      assertThat( project.internalCostBudget().get(), is( 95000.0 ) );
      return project;
   }//End Method

}//End Class
