package uk.dangrew.cb.graphics.wizard.pages;

import javafx.scene.layout.BorderPane;
import uk.dangrew.cb.graphics.wizard.tables.WorkPackageColumns;
import uk.dangrew.cb.graphics.wizard.tables.WorkPackageTableController;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.workpackage.WorkPackage;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.table.BasicConceptControls;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.controls.TableControls;

public class WorkPackagePage extends BorderPane {

   private final ConceptTable< WorkPackage > table;
   private final TableControls controls;
   private final WorkPackageTableController controller;
   
   public WorkPackagePage( Project project ) {
      JavaFxStyle styling = new JavaFxStyle();
      this.controller = new WorkPackageTableController( project );
      
      this.setTop( styling.simpleHeaderDescription( 
               "Work Packages",
               "Add the work packages present in the project. These can be distinct pieces of work, possible "
               + "representing milestones or sprints." 
      ) );
      
      ConceptTableWithControls< WorkPackage > tableWithControls = new ConceptTableWithControls<>( 
               "Work Packages",
               table = new ConceptTable<>(
                     new WorkPackageColumns(),
                     controller
               ),
               controls = new TableControls( new BasicConceptControls( controller ) ) 
      );
      this.setCenter( tableWithControls );
   }//End Constructor
   
   WorkPackageTableController controller(){
      return controller;
   }//End Method
   
   ConceptTable< WorkPackage > table(){
      return table;
   }//End Method
   
   TableControls controls(){
      return controls;
   }//End Method

}//End Class
