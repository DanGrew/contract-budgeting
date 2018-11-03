package uk.dangrew.cb.toolkit;

import uk.dangrew.kode.concept.Concept;

public interface DatabaseConcept< TypeT extends Concept > {

   public TypeT instantiate( String name );

   public TypeT instantiate( String id, String name );

}//End Interface

