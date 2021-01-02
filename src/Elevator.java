/* File name : Elevator.java */

/**
 * This program represents an elevator and inherits properties from the Stack
 * class. The elevator collects data on the number of total passengers served,
 * passengers skipped (i.e. passengers who could not ride the elevator because
 * it was full), occasions when the elevator was empty.
 *
 * @author Rodrigo Rogel-Perez
 * @version 1.0
 * @since 2020-10-06
 */
public class Elevator extends Stack {

   private int totalPassengers; // Agg. passenger counter
   private int totalSkipped; // Agg. skipped counter
   private int totalEmpty; // Agg. empty occasion counter

   /**
    * Class constructor.
    */
   public Elevator() {
      super(); // Invoke constructor of superclass - Stack
      
      this.totalPassengers = 0;
      this.totalSkipped = 0;
      this.totalEmpty = 0;
   }
   
   /**
    * Increments the number of passengers serviced by one.
    */
   public void incrementTotalServiced() {
      this.totalPassengers++;
   }
   
   /**
    * Increments the number of passengers skipped by one.
    */
   public void incrementTotalSkipped() {
      this.totalSkipped++;
   }
   
   /**
    * Increment the number of empty occasions by one.
    */
   public void incrementEmptyOccasions() {
      this.totalEmpty++;
   }
   
   /**
    * Gets the aggregate data on the number of passengers served and skipped 
    * as well as number of times the elevator was empty as a string.
    * 
    * @return 
    */
   @Override
   public String toString() {
      return ("Total served: " + this.totalPassengers + "\n"
         + "Total Skipped: " + this.totalSkipped + "\n"
         + "Empty Occasions: " + this.totalEmpty);
   }
}
