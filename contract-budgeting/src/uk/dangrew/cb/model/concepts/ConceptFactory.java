package uk.dangrew.cb.model.concepts;

import uk.dangrew.cb.model.project.Project;
import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.cb.toolkit.DatabaseConcept;

public class ConceptFactory {

   private static final DatabaseConcept< Project > PROJECT = new DatabaseConcept< Project >() {

      @Override public Project instantiate( String name ) {
         return new Project( name );
      }//End Method

      @Override public Project instantiate( String id, String name ) {
         return new Project( id, name );
      }//End Method
   };
   
   private static final DatabaseConcept< Resource > RESOURCE = new DatabaseConcept< Resource >() {

      @Override public Resource instantiate( String name ) {
         return new Resource( name );
      }//End Method

      @Override public Resource instantiate( String id, String name ) {
         return new Resource( id, name );
      }//End Method
   };
   
   public static DatabaseConcept< Project > project() {
      return PROJECT;
   }//End Method
   
   public static DatabaseConcept< Resource > resource() {
      return RESOURCE;
   }//End Method

}//End Constructor
