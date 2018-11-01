package uk.dangrew.cb.progress.project;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.progress.workpackage.WorkPackageProgress;

public class ProjectProgress {

   private final Project project;
   private final ObjectProperty< LocalDate > date;
   private final ObservableList< WorkPackageProgress > progress;
   
   public ProjectProgress( Project project ) {
      this.project = project;
      this.date = new SimpleObjectProperty<>( LocalDate.now() );
      this.progress = FXCollections.observableArrayList();
   }//End Constructor

   public Project project() {
      return project;
   }//End Method

   public ObjectProperty< LocalDate > date() {
      return date;
   }//End Method

   public ObservableList< WorkPackageProgress > workPackageProgress() {
      return progress;
   }//End Method

}//End Class
