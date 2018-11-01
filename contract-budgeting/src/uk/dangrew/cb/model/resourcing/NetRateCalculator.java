package uk.dangrew.cb.model.resourcing;

import javafx.beans.value.ChangeListener;

public class NetRateCalculator {
   
   private final ChangeListener< Double > netCalculator;
   
   private Resource resource;
   
   public NetRateCalculator() {
      this.netCalculator = ( s, o, n ) -> recalculateNetRate();
   }//End Constructor

   public void associate( Resource resource ) {
      if ( this.resource != null ) {
         throw new IllegalStateException();
      }
      
      this.resource = resource;
      this.recalculateNetRate();
      
      this.resource.baseRate().addListener( netCalculator );
      this.resource.upRate().addListener( netCalculator );
   }//End Method
   
   private void recalculateNetRate(){
      if ( resource.baseRate().get() == null ) {
         resource.setNetRate( 0.0 );
         return;
      }
      
      if ( resource.upRate().get() == null ) {
         resource.setNetRate( resource.baseRate().get() );
         return;
      }
      
      resource.setNetRate( resource.baseRate().get() * ( 1 + 0.01 * resource.upRate().get() ) );
   }//End Method

}//End Class
