package uk.dangrew.cb.toolkit;

import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.Properties;

public class TestConcept implements Concept {

   private final Properties properties;
   
   public TestConcept( String name ) {
      this.properties = new Properties( name );
   }//End Constructor
   
   public TestConcept( String id, String name ) {
      this.properties = new Properties( id, name );
   }//End Constructor
   
   @Override public Properties properties() {
      return properties;
   }//End Method

   @Override public Concept duplicate() {
      return new TestConcept( properties.nameProperty().get() );
   }//End Method

}//End Class
