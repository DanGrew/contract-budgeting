package uk.dangrew.cb.toolkit;

import java.util.HashMap;
import java.util.Map;

import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.ConceptStore;

public class Database {

   private final Map< DatabaseConcept< ? extends Concept >, ConceptStore< ? extends Concept > > stores;
   
   public Database() {
      this.stores = new HashMap<>();
   }//End Constructor
   
   public < ConceptTypeT extends Concept > ConceptStore< ConceptTypeT > storeFor( DatabaseConcept< ConceptTypeT > conceptType ) {
      ConceptStore< ConceptTypeT > store = ( ConceptStore< ConceptTypeT > ) stores.get( conceptType );
      if ( store == null ) {
         store = new ConceptStoreImpl<>( conceptType );
         stores.put( conceptType, store );
      }
      return store;
   }//End Method

}//End Constructor
