package uk.dangrew.cb.model.resourcing;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.Properties;

public class Resource implements Concept {

   private final Properties properties;
   private final ObjectProperty< Double > baseRate;
   private final ObjectProperty< Double > upRate;
   private final ObjectProperty< Double > netRate;
   
   public Resource( String name ) {
      this( name, 0.0, 0.0 );
   }//End Constructor
   
   public Resource( String name, double baseRate, double upRate ) {
      this.properties = new Properties( name );
      this.baseRate = new SimpleObjectProperty<>( baseRate );
      this.upRate = new SimpleObjectProperty<>( upRate );
      this.netRate = new SimpleObjectProperty<>( 0.0 );
      this.recalculateNetRate();
      
      ChangeListener< Double > netCalculator = ( s, o, n ) -> recalculateNetRate();
      this.baseRate.addListener( netCalculator );
      this.upRate.addListener( netCalculator );
   }//End Constructor
   
   private void recalculateNetRate(){
      netRate.set( baseRate.get() * ( 1 + 0.01 * upRate.get() ) );
   }//End Method
   
   @Override public Properties properties() {
      return properties;
   }//End Method
   
   public ObjectProperty< Double > baseRate() {
      return baseRate;
   }//End Method

   public ObjectProperty< Double > upRate() {
      return upRate;
   }//End Method

   public ReadOnlyObjectProperty< Double > netRate() {
      return netRate;
   }//End Method

   @Override public Concept duplicate( String referenceId ) {
      throw new UnsupportedOperationException();
   }//End Method

}//End Class
