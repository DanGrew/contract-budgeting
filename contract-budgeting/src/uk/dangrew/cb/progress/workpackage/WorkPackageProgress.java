package uk.dangrew.cb.progress.workpackage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.model.workpackage.WorkPackage;

public class WorkPackageProgress {

   private final ObjectProperty< WorkPackage > workPackage;
   private final ObjectProperty< Resource > resource;
   private final ObjectProperty< Double > effort;
   
   public WorkPackageProgress() {
      this( null, null, 0.0 );
   }//End Constructor
   
   public WorkPackageProgress( WorkPackage wp, Resource resource, double effort ) {
      this.workPackage = new SimpleObjectProperty<>( wp );
      this.resource = new SimpleObjectProperty<>( resource );
      this.effort = new SimpleObjectProperty<>( effort );
   }//End Constructor
   
   public ObjectProperty< WorkPackage > workPackage() {
      return workPackage;
   }//End Method

   public ObjectProperty< Resource > resource() {
      return resource;
   }//End Method

   public ObjectProperty< Double > effort() {
      return effort;
   }//End Method

}//End Class
