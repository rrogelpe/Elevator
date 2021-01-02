/* File name : Stack.java */

/**
 * The Stack program implements a Stack ADT using a linked-list. Passenger
 * objects can be stored sequentially and only the top-most element in the stack
 * can be removed. Additionally, the program allows a stack to absorb the
 * elements in another Stack.
 *
 * @author Rodrigo Rogel-Perez
 * @version 1.0
 * @since 2020-10-06
 */
public class Stack {

   private Passenger head; // Points to the top-most element in the Stack, null otherwise
   private int size; // Counts number of elements in the Stack

   /**
    * Class constructor.
    */
   public Stack() {
      this.head = null;
      this.size = 0;
   }

   /**
    * Adds the passed object to the top of the Stack.
    *
    * @param passenger Object to be inserted into the Stack.
    */
   public void push(Passenger passenger) {
      // If Stack is empty, point head to passenger, otherwise we add the
      // object to the front of the list and point head to the new object
      if (isEmpty()) {
         head = passenger;
      } else {
         Passenger temp = head;
         head = passenger;
         passenger.setNext(temp);
      }

      this.size++;
   }

   /**
    * Removes and returns the top element in the Stack.
    *
    * @return This Stack's top-most element.
    */
   public Passenger pop() {
      // Null is returned if the Stack is empty to keep the program running
      if (isEmpty()) {
         System.out.println("Empty Stack Exception");
         return null;
      }

      // Detach the element at the front of the list and point head to successor
      Passenger temp = head;
      head = temp.getNext();
      temp.setNext(null);
      this.size--;

      return temp;
   }

   /**
    * Gets the element at the top of the Stack.
    *
    * @return This Stack's top-most element.
    */
   public Passenger peek() {
      // Null is returned if the Stack is empty to keep the program running
      if (isEmpty()) {
         System.out.println("Empty Stack Exception");
         return null;
      }

      return head;
   }

   /**
    * Gets the number of elements in the Stack.
    *
    * @return This Stack's size
    */
   public int getSize() {
      return this.size;
   }

   /**
    * Indicates whether the Stack is empty i.e. Stack has no elements.
    *
    * @return Boolean indicating if this Stack has no elements.
    */
   public boolean isEmpty() {
      return getSize() == 0;
   }

   /**
    * Absorbs the elements in the passed Stack.
    *
    * @param stack Stack to be absorbed.
    */
   public void absorbStack(Stack stack) {
      while (!stack.isEmpty()) {
         this.push(stack.pop());
      }
   }
}
