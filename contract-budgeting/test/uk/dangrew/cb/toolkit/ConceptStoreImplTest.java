package uk.dangrew.cb.toolkit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.concept.ConceptStore;

public class ConceptStoreImplTest {

   private TestConcept concept;
   private ConceptStore< TestConcept > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      concept = new TestConcept( "Food" );
      systemUnderTest = new ConceptStoreImpl<>( new TestConceptType() );
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( concept.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( concept );
      assertThat( systemUnderTest.get( concept.properties().id() ), is( concept ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      TestConcept newConcept = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newConcept.properties().id() ), is( newConcept ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( concept );
      assertThat( systemUnderTest.get( concept.properties().id() ), is( concept ) );
      systemUnderTest.removeConcept( concept );
      assertThat( systemUnderTest.get( concept.properties().id() ), is( nullValue() ) );
   }//End Method

}//End Class
