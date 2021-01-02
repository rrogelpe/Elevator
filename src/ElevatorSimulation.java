/* File name : ElevatorSimulation.java */
import java.io.*;
import java.util.Scanner;

/**
 * This program reads elevator passenger data from an input file and simulates 
 * an operating elevator. Under the specified constraints, the elevator cannot 
 * hold more than five passengers. At each floor, the program prints information
 * on people entering, leaving, or skipping the elevator. The program terminates
 * when there are no people waiting on the elevator and when the there are no 
 * passengers riding the elevator.
 * 
 * @author Rodrigo Rogel-Perez
 * @version 1.0
 * @since 2020-10-06
 */
public class ElevatorSimulation {
   
   /**
    * Main entry point of the program.
    * 
    * @param args Holds one command line argument: the input file name.  
    */
   public static void main(String[] args) {
      
      boolean isAscending = true; // Elevator is initially ascending
      int floorNum = 1; // Elevator starts in the first floor
      String text = "";
      boolean isActive;
      
      //  Check for command line arguments.
      // This chunk of code was copied from the sample provided by the professor
      if (args.length != 2) {
         System.out.println("Usage:  java ElevatorSimulation [input file pathname]"
            + " [output file pathname]");
         System.exit(-1);
      }
      
      // Store passenger data into a stack
      Stack waitingStack = preparePassengerStack(args[0], args[1]); 
      
      Elevator passengerStack = new Elevator();
      
      // Each index position represents a floor and the element value is the  
      // number of people inside the elevator that will exit at that floor
      int[] exitFloorRequests = new int[]{0, 0, 0, 0, 0};
      
      // Simulate an operating elevator, going up and down, stopping at floors
      // where people are waiting or exiting.
      while (!waitingStack.isEmpty() || !passengerStack.isEmpty()) {
         Stack tempExit = new Stack();
         isActive = false;
            
         if (floorNum == 5) {
            isAscending = false;
         } else if (floorNum == 1) {
            isAscending = true;
         }
         
         // Remove people scheduled to get off on current floor
         if (exitFloorRequests[floorNum - 1] > 0) {
            isActive = !isActive;
            if (isActive) {
               text += "\n*****************************************\n" 
                  + "*\t\tFloor " + floorNum + "\t\t\t*" 
                  + "\n*****************************************\n";
            }

            do {
               Passenger passenger = passengerStack.pop();
               if (passenger.getFloorExited() == (floorNum)) {
                  exitFloorRequests[floorNum - 1]--;
                  text += "   -" + passenger.getName() + " " 
                     + "exits the elevator having temporarily exited "
                     + passenger.getNumOfTempExits() + " times.\n";
               } else {
                  tempExit.push(passenger);
                  passenger.incrementTempExits();
                  text += "   -" + passenger.getName()
                     + " temporarily exits "
                     + "the elevator.\n";
               }
            } while (exitFloorRequests[floorNum - 1] > 0);
            // Restore people who got off temporarily
            passengerStack.absorbStack(tempExit); 
            
            // Elevator is empty 
            if (passengerStack.isEmpty()) {
               passengerStack.incrementEmptyOccasions();
               text += "   -Elevator is empty.\n";
            }
         }
        
         // Load people waiting to get in
         if (waitingStack.getSize() > 0
            && waitingStack.peek().getFloorEntered() == (floorNum)) {
            isActive = !isActive;
            if (isActive) {
               text += "\n*****************************************\n"
                  + "*\t\tFloor " + floorNum + "\t\t\t*"
                  + "\n*****************************************\n";
            }
            
            do {
               Passenger passenger = waitingStack.pop();
               // Person does not ride elevator if current floor is destination
               if(passenger.getFloorExited() == floorNum) {
                  text += "   -" + passenger.getName() + " did not ride the "
                     + "elevator because this floor is his/her destination.\n";
               }
               // Person rides the elevator if it is not full
               else if (passengerStack.getSize() < 5) {
                  passengerStack.push(passenger);
                  passengerStack.incrementTotalServiced();
                  exitFloorRequests[passenger.getFloorExited() - 1]++;
                  text += "   -" + passenger.getName() + " " 
                     + "enters the elevator " 
                     + "with destination to floor no. "
                     + passenger.getFloorExited() + ".\n";
               } else {
                  passengerStack.incrementTotalSkipped();
                  text += "   -" + passenger.getName() 
                     + " could not ride the elevator because it is full.\n";
               }
            } while (waitingStack.getSize() > 0
               && waitingStack.peek().getFloorEntered() == (floorNum));
            
            // Elevator is full
            if (passengerStack.getSize() == 5) {
               text += "   -Elevator is full.\n";
            }
            // Direction of elevator
            if (!isActive) {
               text += "   -Elevator is " + ((isAscending)
                  ? "Ascending"
                  : "Descending");
            }
         } 
         // Ascend one floor if elevator is ascending, descend one floor otherwise
         floorNum = (isAscending) ? (floorNum + 1) : (floorNum - 1);
         
         // Direction of elevator
         if (isActive) {
            text += "   -Elevator is " + ((isAscending)
               ? "Ascending"
               : "Descending");
         }
      }
      
      text += "\n\nElevator is empty and there are more people "
         + "waiting to ride it.\n\n" + passengerStack.toString() 
         + "\n\nEnd of simulation... program will now terminate\n";
      
      writeResult(text, args[1]);
   }
   
   /**
    * Returns a Stack ADT containing passenger data provided in the input file.
    * The program prints the row and column number of any value it can not
    * process as well as the contents of the row itself.
    * 
    * @param inFileName Name of file containing elevator passenger data.
    * @return Stack with passenger objects.
    */
   private static Stack preparePassengerStack(String inFileName, String outFileName) {

      String record; // Row of passenger data
      String name; // Store passenger's name
      String text;
      int floorEntered; // Store passenger's exit floor no.
      int floorExited; // Store passenger's exit floor no.
      int rowNum; // Used to indicate the row number of faulty record, if any

      Stack passengerStack = null; // Will store all passenger data in order
      Stack reverseStack = null;
      
      // Open file and parse the data line by line
      try (BufferedReader input = new BufferedReader(new FileReader(inFileName))) {
         reverseStack = new Stack();
         rowNum = 1;
         while ((record = input.readLine()) != null) {
            // Data file can contain comments so we ignore those lines.
            // Also, input file may contain blank lines in between rows.
            if (!record.isEmpty() && !record.substring(0, 2).equals("//")) {
               Scanner scan = new Scanner(record);
               name = scan.next();
               
               // We check the values in the second and third columns...
               // the values must be in the closed interval [1, 5] and have
               // to be intergers.
               try {
                  floorEntered = Integer.parseInt(scan.next());
                  if (floorEntered < 1) {
                     text = "Value in row " + rowNum 
                        + " column 2 can not be less than 1.\n" 
                        + record + "\n";
                     writeResult(text, outFileName);
                     System.exit(-1);
                  }
                  else if (floorEntered > 5) {
                     text = "Value in row " + rowNum 
                        + " column 2 can not be greater than 5.\n" 
                        + record + "\n";
                     writeResult(text, outFileName);
                     System.exit(-1);
                  }
                  try {
                     floorExited = Integer.parseInt(scan.next());
                     if (floorExited < 1) {
                        text = "Value in row " + rowNum 
                           + " column 3 can not be less than 1.\n" 
                           + record + "\n";
                        writeResult(text, outFileName);
                        System.exit(-1);
                     }
                     else if (floorExited > 5) {
                        text = "Value in row " + rowNum 
                           + " column 2 can not be greater than 5.\n"
                           + record + "\n";
                        writeResult(text, outFileName);
                        System.exit(-1);
                     }
                     // Create person node and add to stack
                     Passenger person = new Passenger(name, 
                        floorEntered, 
                        floorExited);
                     reverseStack.push(person);
                  } catch (NumberFormatException e){
                     text = "Value in row " + rowNum 
                        + " column 3 is not an integer.\n" 
                        + record + "\n";
                     writeResult(text, outFileName);
                     System.exit(-1);
                  }
               } catch (NumberFormatException e) {
                  text = "Value in row " + rowNum 
                     + " column 2 is not an integer.\n" 
                     + record + "\n";
                  writeResult(text, outFileName);
                  System.exit(-1);
               }
               rowNum++;
            }
         }
      } catch (FileNotFoundException e) {
         System.out.println("File Not Found " + e);
         System.exit(-1);
      } catch (IOException e) {
         System.out.println("An I/O Error Ocurred " + e);
         System.exit(-1);
      }

      passengerStack = new Stack();
      // Pop items from one stack and add them to another to restore order
      passengerStack.absorbStack(reverseStack);

      return passengerStack;
   }
   
   /**
    * Write text to the specified file.
    *
    * @param text The text to write.
    * @param fileName File name on which text will be written.
    */
   private static void writeResult(String text, String fileName) {

      // Write to new file using try-with-resource statement to automatically close stream at end of session
      try ( PrintWriter output = new PrintWriter( new BufferedWriter( new FileWriter( fileName ) ) ) ) {
         output.print( text );
      }
      // Throw exception if other I/O related error is encoutered
      catch ( IOException e ) {
         System.out.println( "An I/O Error Occurred " + e );
         System.exit(-1);
      }
   }
}
