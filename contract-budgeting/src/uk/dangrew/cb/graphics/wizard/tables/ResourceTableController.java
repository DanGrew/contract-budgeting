package uk.dangrew.cb.graphics.wizard.tables;

import uk.dangrew.cb.model.resourcing.Resource;
import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.table.ConceptTableRowImpl;
import uk.dangrew.nuts.graphics.table.ConceptTableViewController;

public class ResourceTableController implements ConceptTableViewController< Resource  >{
   
   private final ConceptStore< Resource > databaseStore;
   private ConceptTable< Resource > table;
   
   public ResourceTableController( ConceptStore< Resource > databaseStore ) {
      this.databaseStore = databaseStore;
   }//End Constructor
   
   @Override public void associate( ConceptTable< Resource > table ) {
      this.table = table;
      this.databaseStore.objectList().forEach( this::addResource );
      this.databaseStore.objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::addResource, this::removeResource 
      ) );
   }//End Method
   
   private void addResource( Resource resource ) {
      table.getRows().add( new ConceptTableRowImpl<>( resource ) );
   }//End Method
   
   private void removeResource( Resource resource ) {
      table.getRows().removeIf( r -> r.concept() == resource );
   }//End Method

   @Override public Resource createConcept() {
      return databaseStore.createConcept( "Resource" );
   }//End Method

   @Override public void removeSelectedConcept() {
      ConceptTableRow< Resource > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      this.databaseStore.removeConcept( selection.concept() );
   }//End Method

   @Override public void copySelectedConcept() {
      ConceptTableRow< Resource > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      Resource copy = selection.concept().duplicate();
      databaseStore.store( copy );
   }//End Method

}//End Class
