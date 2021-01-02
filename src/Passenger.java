/* File name : Passenger.java */

/**
 * The Passenger program represents a person waiting for the elevator. A
 * passenger initially waits for the elevator on one of the floors and,
 * regardless if they use the elevator, move to another floor.
 *
 * @author Rodrigo Rogel-Perez
 * @version 1.0
 * @since 2020-10-06
 */
class Passenger {

   private final String name; // First name of passenger, serves as identifier
   private final int floorEntered; // Floor no. where passenger initially waited
   private final int floorExited; // Floor no. of the passenger's destination
   private int numTempExits; // Counts no. times passengers had to temp. exit
   private Passenger next; // Passenger infront of this passenger
   private Passenger previous; // Passenger behind this passenger

   /**
    * Class constructor.
    *
    * @param name First name of passenger.
    * @param floorEntered Floor no. where passenger initially waited.
    * @param floorExited Floor no. of the passenger's destination.
    */
   public Passenger(String name, int floorEntered, int floorExited) {

      this.name = name;
      this.floorEntered = floorEntered;
      this.floorExited = floorExited;
      this.numTempExits = 0;
      this.next = null;
      this.previous = null;
   }

   /**
    * Gets the passenger's first name.
    *
    * @return This passenger's first name.
    */
   public String getName() {
      return this.name;
   }
   
   /**
    * Gets the floor no. where the passenger waited.
    *
    * @return This passenger's beginning floor no.
    */
   public int getFloorEntered() {
      return this.floorEntered;
   }
   
   /**
    * Gets the floor no. where the passenger ended at regardless if they 
    * rode the elevator.
    *
    * @return This passenger's ending floor no.
    */
   public int getFloorExited() {
      return this.floorExited;
   }
   
   /**
    * Gets the number of times the passenger had to temporarily exit
    * to let other passengers in the elevator out.
    * 
    * @return This passenger's number of temporary exits.
    */
   public int getNumOfTempExits() {
      return this.numTempExits;
   }
   
   /**
    * Gets the passenger 'behind' this one. 
    * 
    * @return This passenger's neighbor directly behind.
    */
   public Passenger getNext() {
      return this.next;
   }
   
   /**
    * Gets the passenger 'ahead' of this one.
    * 
    * @return This passenger's neighbor directly ahead. 
    */
   public Passenger getPrevious() {
      return this.previous;
   }
   
   /**
    * Increments the temporary exit counter by one of this passenger.
    * 
    */
   public void incrementTempExits() {
      this.numTempExits++;
   }
   
   /**
    * Sets the next pointer of this passenger to the given passenger object.
    * 
    * @param next Passenger to be adjacent to this passenger.
    */
   public void setNext(Passenger next) {
      this.next = next;
   }
   
   /**
    * Sets the previous pointer of this passenger to the given passenger object.
    * 
    * @param previous Passenger to be adjacent to this passenger.
    */
   public void setPrevious(Passenger previous) {
      this.previous = previous;
   }
   
   /**
    * Generates a string with data points relevant to this passenger. 
    * Data points include name, floor entered, floor exited, and number of 
    * temporary exits.
    *
    * @return This passenger's data as a string
    */
   @Override
   public String toString() {
      return ("Name: " + getName() + "\n" + 
              "Start Floor: " + getFloorEntered() + "\n" + 
              "End Floor: " + getFloorExited() + "\n" +
              "Temporary Exits: " + getNumOfTempExits());
   }
}
