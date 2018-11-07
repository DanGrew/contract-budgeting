package uk.dangrew.cb.graphics.workspace.core;

import javafx.scene.layout.BorderPane;
import uk.dangrew.cb.graphics.workspace.tables.ProjectColumns;
import uk.dangrew.cb.graphics.workspace.tables.ProjectTableController;
import uk.dangrew.cb.model.concepts.ConceptFactory;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.progress.project.ProjectTestData;
import uk.dangrew.cb.toolkit.Database;
import uk.dangrew.nuts.graphics.table.BasicConceptControls;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.controls.TableControls;

public class CoreInterface extends BorderPane {
   
   private final Database database;
   private final ConceptTableWithControls< Project > tableWithControls;
   
   public CoreInterface() {
      this.database = new Database();

      ProjectTableController controller = new ProjectTableController( database.storeFor( ConceptFactory.project() ) );
      this.tableWithControls = new ConceptTableWithControls<>( 
               "Projects",
               new ConceptTable<>(
                     new ProjectColumns( database ),
                     controller
               ),
               new TableControls( new BasicConceptControls( controller ) ) 
      );
      this.setCenter( tableWithControls );
   }//End Constructor
   
   Database database(){
      return database;
   }//End Method
   
   ConceptTableWithControls< Project > tableWithControls(){
      return tableWithControls;
   }//End Method

}//End Class
