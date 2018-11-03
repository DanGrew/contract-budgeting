package uk.dangrew.cb.toolkit;

public class TestConceptType implements DatabaseConcept< TestConcept > {

   @Override public TestConcept instantiate( String name ) {
      return new TestConcept( name );
   }//End Method

   @Override public TestConcept instantiate( String id, String name ) {
      return new TestConcept( id, name );
   }//End Method

}//End Class
