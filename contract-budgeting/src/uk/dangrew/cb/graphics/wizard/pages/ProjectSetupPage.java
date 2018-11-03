package uk.dangrew.cb.graphics.wizard.pages;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.kode.javafx.custom.BoundTextProperty;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;

public class ProjectSetupPage extends BorderPane {

   private final BoundTextProperty nameBinding;
   private final BoundTextProperty contractBinding;
   
   public ProjectSetupPage( Project project ) {
      JavaFxStyle styling = new JavaFxStyle();
      
      this.setTop( styling.simpleHeaderDescription( 
               "Project Setup",
               "Configure the base information for this project." 
      ) );
      
      this.setCenter( new PropertiesPane( 
               "Properties", 
               new PropertyRowBuilder()
                  .withLabelName( "Name" )
                  .withBinding( nameBinding = new BoundTextProperty( project.properties().nameProperty(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Contract" )
                  .withBinding( contractBinding = new BoundTextProperty( project.contract(), true ) )
      ) );
   }//End Constructor
   
   TextField nameField(){
      return nameBinding.region();
   }//End Method

   TextField contractField(){
      return contractBinding.region();
   }//End Method
}//End Class
