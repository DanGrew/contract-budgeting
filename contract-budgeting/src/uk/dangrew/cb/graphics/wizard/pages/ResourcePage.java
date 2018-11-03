package uk.dangrew.cb.graphics.wizard.pages;

import javafx.scene.layout.BorderPane;
import uk.dangrew.cb.graphics.wizard.tables.ResourceColumns;
import uk.dangrew.cb.graphics.wizard.tables.ResourceTableController;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.table.BasicConceptControls;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.controls.TableControls;

public class ResourcePage extends BorderPane {

   private final ConceptTable< Resource > table;
   private final TableControls controls;
   private final ResourceTableController controller;
   
   public ResourcePage( ConceptStore< Resource > store ) {
      JavaFxStyle styling = new JavaFxStyle();
      this.controller = new ResourceTableController( store );
      
      this.setTop( styling.simpleHeaderDescription( 
               "Resource Templates",
               "Each type of resource has a separate set of rates associated with it. These may "
               + "vary from project to project. The following resources provide templates to base "
               + "those defined for the project. Define any further templates here needed and update "
               + "any that already exist." 
      ) );
      
      ConceptTableWithControls< Resource > tableWithControls = new ConceptTableWithControls<>( 
               "Resources",
               table = new ConceptTable<>(
                     new ResourceColumns(),
                     controller
               ),
               controls = new TableControls( new BasicConceptControls( controller ) ) 
      );
      this.setCenter( tableWithControls );
   }//End Constructor
   
   ResourceTableController controller(){
      return controller;
   }//End Method
   
   ConceptTable< Resource > table(){
      return table;
   }//End Method
   
   TableControls controls(){
      return controls;
   }//End Method

}//End Class
