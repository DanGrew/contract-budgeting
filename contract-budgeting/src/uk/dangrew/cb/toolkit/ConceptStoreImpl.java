package uk.dangrew.cb.toolkit;

import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;

public class ConceptStoreImpl< TypeT extends Concept > extends MappedObservableStoreManagerImpl< String, TypeT > implements ConceptStore< TypeT >{

   private final DatabaseConcept< TypeT > databaseConcept;
   
   public ConceptStoreImpl( DatabaseConcept< TypeT > databaseConcept ) {
      super( f -> f.properties().id() );
      this.databaseConcept = databaseConcept;
   }//End Constructor

   @Override public TypeT createConcept( String name ) {
      TypeT concept = databaseConcept.instantiate( name );
      store( concept );
      return concept;
   }//End Method
   
   @Override public TypeT createConcept( String id, String name ) {
      TypeT concept = databaseConcept.instantiate( id, name );
      store( concept );
      return concept;
   }//End Method
   
   @Override public void removeConcept( TypeT concept ) {
      remove( concept.properties().id() );
   }//End Method
}//End Class
