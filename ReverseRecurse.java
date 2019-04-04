/** File name: ReverseRecurse.java
 * Created by: Zhaopu Wang, cs8bwatz
 * Date: 03/07/2017
 *
 * This file includes methods to initiate, print and reverse an integer array
 */

import java.util.Scanner;
public class ReverseRecurse {
   // Method to initiate an array by reading user's choice
   // @return int[] array
   public int[] initArray() {
      Scanner in = new Scanner(System.in);
      int size = 0;
      // ask for the size
      System.out.println(PSA7Strings.MAX_NUM);
      if (in.hasNextInt()) {
         // set the size
         size = in.nextInt();
      }
      else System.exit(1);
      while ( size <= 0 ) {// loop until a proper input
         System.out.println(PSA7Strings.TOO_SMALL);
         if (in.hasNextInt()) 
            size = in.nextInt();
         else System.exit(1);
      }
      // create an empty array
      int[] array = new int[size];
      System.out.printf(PSA7Strings.ENTER_INTS,size);
      int i = 0;
      // fill the array
      while ( i<size ) {
         if ( in.hasNextInt() ) {
            array[i] = in.nextInt();
            i++;
         }
         else {
            int[] arraycopy = new int[i];
            System.arraycopy(((Object)array),0,((Object)arraycopy),0,i);
            return arraycopy;
         }
      }
      return array;
   }

   // Method to print an array
   // @param int[] array
   public void printArray(int[] array) {
      // check empty array
      if ( array.length == 0 )
         System.out.println("Empty array");
      else {
         for ( int i = 0; i < array.length; i++ ) {
            System.out.print( array[i] + " " );
         }
         System.out.println();
      }
      }
   
   // Method to reverse an array changing the original array
   // @param int[] originalArray, int low, int high 
   public void reverse(int[] originalArray,int low, int high) {
      // null pointer check
      if ( originalArray != null && originalArray.length > 0 ) {
         // check if low and high are proper
         if ( low >= 0 && high < originalArray.length ) {            
            //recursive steps
            if ( high > low ) {
               int temp = originalArray[low];
               originalArray[low] = originalArray[high];
               originalArray[high] = temp;
               reverse(originalArray,low+1,high-1);
               }
         }
      }
   }
   
   // Method to reverse an array creating a new array
   // @param int[] originalArray
   // @return int[] reversed array
   public int[] reverse(int[] originalArray) {
      // null pointer check
      if ( originalArray == null || originalArray.length == 0 )
         return originalArray;
      else {
         //base case
         if ( originalArray.length <= 1 ) {
            //reverseArray = new int[originalArray.length];
            return originalArray;
         }
         //recursive case
         else {
            int[] reverseArray = new int[originalArray.length];
            int end = originalArray.length-1;
            int start = 0;
            reverseArray[start] = originalArray[end];
            reverseArray[end] = originalArray[start];
            int[] tempArray = new int[end-start-1];
            System.arraycopy(((Object)originalArray),start+1,((Object)tempArray),0,
            tempArray.length);
            int[] middleArray = reverse(tempArray);
            System.arraycopy(((Object)middleArray),0,((Object)reverseArray),start+1,
              middleArray.length);
            return reverseArray;
         }
      }
   }
}
