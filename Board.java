/**
 * File Name: Board.java
 * Created by: Zhaopu Wang, cs8bwatz
 * Date: 02/07/2017
 *
 * Board.java is the class of board for game 2048
 * This class stores NUM_START_TILES,TWO_PROBABILITY,GRID_SIZE,random
 * grid and score
 * This class includes methods for saving, adding random tiles, fliping
 * testing and moving of the board
 */


/**
 * Sample Board
 * <p/>
 *     0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

public class Board {

   ///////////////////////////////////////////
   ////////////instance variables/////////////
   ///////////////////////////////////////////

    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random;
    private int[][] grid;
    private int score;

    /////////////////////////////////////////////
    /////////////////Constructors////////////////
    /////////////////////////////////////////////

    // Constructs a fresh board with random tiles
    public Board(Random random, int boardSize) {
        this.random = random; 
        if ( boardSize < 2 )
           boardSize = 4;
        this.GRID_SIZE = boardSize; 
        this.grid = new int[boardSize][boardSize];
        for ( int x = 0; x < boardSize; x++ ){
           for (int y = 0; y < boardSize; y++ ){
              this.grid[x][y] = 0;
           }
        }
        this.score = 0;
        for ( int i = 0; i < this.NUM_START_TILES; i++){
          this.addRandomTile();
        }
    }

    // Construct a board based off of an input file
    public Board(Random random, String inputBoard) throws IOException {
        //scan the inputBoard and set it to a new board
        Scanner sc = new Scanner(new File(inputBoard));
        if (sc.hasNextInt())
           this.GRID_SIZE = sc.nextInt();
        else{
           this.GRID_SIZE = 4;
           System.out.println("error: GRID_SIZE not found");
        }
        if (sc.hasNextInt())
           this.score = sc.nextInt();
        else{
           this.score = 0;
           System.out.println("error: score not found");
        }
        this.grid = new int[this.GRID_SIZE][this.GRID_SIZE];
        for (int x = 0; x < this.GRID_SIZE; x++){
           for (int y = 0; y < this.GRID_SIZE; y++){
              if (sc.hasNextInt()) 
                 this.grid[x][y] = sc.nextInt();
              else {
                 this.grid[x][y] = 0;
                 System.out.println("error: grid not found");
              }
           }
        }
        this.random = random; 
        }

    /////////////////////////////////////////////////
    //////////////////////methods////////////////////
    /////////////////////////////////////////////////
    
    // Method to save the board
    // @param String outputBoard, the name of the board saved
    // @return -
    public void saveBoard(String outputBoard) throws IOException {
       //print the countains of the board to a new file
       PrintWriter pw = new PrintWriter(new File(outputBoard));
       pw.print(this.GRID_SIZE);
       pw.println();
       pw.print(this.score);
       pw.println();
       for (int x = 0; x < this.GRID_SIZE; x++){ //print the grids
           for (int y = 0; y < this.GRID_SIZE; y++){
              pw.print(grid[x][y]+" ");
          }
           pw.println();
       }
       pw.close();
    }

    // Method to add a random tile (of value 2 or 4) to a
    // random empty space on the board
    // @param -
    // @return -
    public void addRandomTile() {
       // count the available tiles
       int count = 0;
       for (int x = 0; x < this.GRID_SIZE; x++){
          for (int y = 0; y < this.GRID_SIZE; y++){
             if (this.grid[x][y] == 0)
                count++;
          }
       }
       // if no available tiles, exit
       if ( count == 0 )
          return;
       // get the random location
       int location = this.random.nextInt(count);
       // get the random value deciding 2 / 4
       int value = this.random.nextInt(100);
       int newCount = 0;
       // add the value to the location
       for (int x = 0; x < this.GRID_SIZE; x++){  
          for (int y = 0; y < this.GRID_SIZE; y++){
             if (this.grid[x][y] == 0){
                if (newCount == location){
                   if (value < this.TWO_PROBABILITY)
                     this.grid[x][y] = 2;
                   else this.grid[x][y] = 4;
                }
                newCount++;
             }
          }
       }
    }

    // Method to flip the board horizontally or vertically,
    // Rotate the board by 90 degrees clockwise or 90 degrees 
    // counter-clockwise.
    // @param int change, what kind of change
    // @return -
    public void flip(int change) {
       /////////////flipping horizontally//////////
       if ( change == 1 ){
          for (int x = 0; x < this.GRID_SIZE; x++){
              for (int y = 0; y < this.GRID_SIZE/2; y++){
                 int temp = this.grid[x][y];
                 this.grid[x][y] = this.grid[x][this.GRID_SIZE-y-1];
                 this.grid[x][this.GRID_SIZE-y-1] = temp;
              }
          }
       }
       /////////////flipping vertically/////////
       if ( change == 2){
          for (int x = 0; x < this.GRID_SIZE/2; x++){
              for (int y = 0; y < this.GRID_SIZE; y++){
                 int temp = this.grid[x][y];
                 this.grid[x][y] = this.grid[this.GRID_SIZE-x-1][y];
                 this.grid[this.GRID_SIZE-x-1][y] = temp;
              }
          }
       }
       /////////////rotating clockwise////////////
        if ( change == 3){
          int size = this.GRID_SIZE;
          int[][] newGrid = new int[size][size];
          for (int x = 0; x < this.GRID_SIZE; x++){
              for (int y = 0; y < this.GRID_SIZE; y++){
                 newGrid[x][y] = this.grid[size-y-1][x];
              }
          }
          this.grid = newGrid;
       }
       ///////////rotating counterclockwise///////////
        if ( change == 4){
          int size = this.GRID_SIZE;
          int[][] newGrid = new int[size][size];
          for (int x = 0; x < this.GRID_SIZE; x++){
              for (int y = 0; y < this.GRID_SIZE; y++){
                  newGrid[size-y-1][x] = this.grid[x][y];
              }
          }
          this.grid = newGrid;
       }
    }

    // Method that test the format of input file
    // @param String inputFile, the filename of inputfile
    // @return boolean, true if correct format, else false
    public static boolean isInputFileCorrectFormat(String inputFile) {
        try {
            Scanner sc = new Scanner(new File(inputFile));
            //test if the first two line is only one int
               for ( int i = 0; i < 2; i++ ){
                  while (sc.hasNext() ){
                     String next = sc.nextLine();
                     for ( int j = 0; j < next.length(); j++ ){
                        if (Character.isDigit(next.charAt(j)) == false )
                           return false;
                     }
                  }
               }
              
             //test if all the elements are int 
             sc.reset();
             while ( sc.hasNext() ){
                String number = sc.next();
                for ( int i = 0; i < number.length(); i++ ){
                   if (Character.isDigit(number.charAt(i)) == false )
                      return false;
             }
             }
             //test if all the numbers are non-negative
             sc.reset();
             while ( sc.hasNext() ){
                int number = sc.nextInt();
                if ( number < 0 ) return false;
             }
             //test if the size is smaller than 2
             sc.reset();
             if ( sc.hasNext() ){
                int size = sc.nextInt();
                if ( size < 2 ) return false;
             }
             else return false;
             //test if the other numbers are multiples of 2
             while ( sc.hasNext() ){
                int number = sc.nextInt();
                if ( number%2 != 0 ) return false;
             }

            //if all passed, return true
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to check whether the baord can move in certain
    // direction
    // @param direction, the direciton to check
    // @return boolean, the result
    public boolean canMove(Direction direction){
       if ( direction.equals(Direction.UP) )
          return this.canMoveUp();
       if ( direction.equals(Direction.DOWN) )
          return this.canMoveDown();
       if ( direction.equals(Direction.LEFT) )
          return this.canMoveLeft();
       if ( direction.equals(Direction.RIGHT) )
          return this.canMoveRight();
       return false;
       }

    // Method to check whether the board can move left
    // @return boolean
    public boolean canMoveLeft(){
         for (int x = 0; x < this.GRID_SIZE; x++){
            for (int y = 1; y < this.GRID_SIZE; y++){
               if ( this.grid[x][y] != 0 ){//skip the empty grid
                  if ( (this.grid[x][y-1] == this.grid[x][y]) || 
                     (this.grid[x][y-1] == 0) )
                     //if the grid on the left is same or 0
                     return true;
               }
            }
         }
         return false;
    }
    
    // Method to check whether the board can move right
    // @return boolean
    public boolean canMoveRight(){
         for ( int x = 0; x < this.GRID_SIZE; x++ ){
            for ( int y = 0; y < this.GRID_SIZE-1; y++){
               if ( this.grid[x][y] != 0 ){
                  if ( (this.grid[x][y+1] == this.grid[x][y]) ||
                     (this.grid[x][y+1]) == 0 )
                     //if the grid on the right is same or 0
                     return true;
               }
            }
         }
         return false;
    }

    //Method to check whether the board can move up
    public boolean canMoveUp(){
          for ( int x = 1; x < this.GRID_SIZE; x++ ){
             for ( int y = 0; y < this.GRID_SIZE; y++){
                if ( this.grid[x][y] != 0 ){
                   if ( (this.grid[x-1][y] == this.grid[x][y]) || 
                      (this.grid[x-1][y] == 0) ){
                      //if the upper grid is same or 0
                      return true;
                      }
                }
             }
          }
          return false;
    }

    // Method to check whether the baord can move down
    public boolean canMoveDown(){
         for ( int x = 0; x < this.GRID_SIZE-1; x++ ){
            for ( int y = 0; y < this.GRID_SIZE; y++ ){
               if ( this.grid[x][y] != 0 ){
                  if ( (this.grid[x+1][y] == this.grid[x][y]) ||
                     (this.grid[x+1][y] == 0))
                     //if the lower grid is same or 0
                     return true;
               }
            }
         }
         return false;
}

    // Method to perform a move Operation in certain direciton
    // @param direction
    // @return boolean, if the move is successful
    public boolean move(Direction direction) {
       if ( this.canMove(direction) == false )
          return false;
       else if ( direction.equals(Direction.UP) )
          this.moveUp();
       else if ( direction.equals(Direction.DOWN) )
          this.moveDown();
       else if ( direction.equals(Direction.LEFT) )
          this.moveLeft();
       else if ( direction.equals(Direction.RIGHT) )
          this.moveRight();
       return true;
    }
    
    // Method to move up
    public void moveUp(){
       //////////////////////merging//////////////////////
       for ( int y = 0; y < this.GRID_SIZE; y++ ){
          for ( int x = 0; x < this.GRID_SIZE-1; x++ ){
             if ( this.grid[x][y] != 0 ){
                // find the next non-zero tile
                int next = x+1;
                while ( this.grid[next][y] == 0 && next < this.GRID_SIZE-1 ){
                   next++;}
                // if the next nonzero tile is the same, merge together
                if ( this.grid[x][y] == this.grid[next][y] ){
                   this.grid[x][y] = this.grid[next][y] + this.grid[x][y];
                   this.grid[next][y] = 0;
                   this.score += this.grid[x][y];
                   }
             }
          }
       }
       /////////////////////////moving/////////////////////
       for ( int count = 0; count < this.GRID_SIZE; count++ ){
       for ( int y = 0; y < this.GRID_SIZE; y++ ){
          for ( int x = this.GRID_SIZE-1; x > 0; x-- ){
             if ( this.grid[x][y] != 0 ){
                // if the next tile is 0, move
                if ( this.grid[x-1][y] == 0 ){
                   this.grid[x-1][y] = this.grid[x][y];
                   this.grid[x][y] = 0;
                }
             }
          }
       }
       }
    }

    // Method to move down
    public void moveDown(){
       /////////////////////merging////////////////////
       for ( int y = 0; y < this.GRID_SIZE; y++ ){
          for ( int x = this.GRID_SIZE-1; x > 0; x-- ){
             if ( this.grid[x][y] != 0 ){
                int next = x-1;
                while ( this.grid[next][y] == 0 && next > 0 ){
                   next--;}
                if ( this.grid[x][y] == this.grid[next][y] ){
                   this.grid[x][y] = this.grid[next][y] + this.grid[x][y];
                   this.grid[next][y] = 0;
                   this.score += this.grid[x][y];
                }
             }
          }
       }
       /////////////////////moving//////////////////////
       for ( int count = 0; count < this.GRID_SIZE; count++ ){
       for ( int y = 0; y < this.GRID_SIZE; y++ ){
          for ( int x = 0; x < this.GRID_SIZE-1; x++ ){
             if ( this.grid[x][y] != 0 ){
                if ( this.grid[x+1][y] == 0 ){
                   this.grid[x+1][y] = this.grid[x][y];
                   this.grid[x][y] = 0;
                }
             }
          }
       }
       }
}
    
    // Method to move left
    public void moveLeft(){
       /////////////////////merging////////////////////
       for ( int x = 0; x < this.GRID_SIZE; x++ ){
          for ( int y = 0; y < this.GRID_SIZE-1; y++ ){
             if ( this.grid[x][y] != 0 ){
                int next = y+1;
                while ( this.grid[x][next] == 0 && next < GRID_SIZE-1 ){
                   next++;}
                if ( this.grid[x][y] == this.grid[x][next] ){
                   this.grid[x][y] = this.grid[x][y] + this.grid[x][next];
                   this.grid[x][next] = 0;
                   this.score += this.grid[x][y];
                }
             }
             }
       }
       //////////////////////moving/////////////////
       for ( int count = 0; count < this.GRID_SIZE; count++){
       for ( int x = 0; x < this.GRID_SIZE; x++ ){
          for ( int y = this.GRID_SIZE-1; y > 0; y-- ){
             if ( this.grid[x][y] != 0 ){
                if ( this.grid[x][y-1] == 0 ){
                   this.grid[x][y-1] = this.grid[x][y];
                   this.grid[x][y] = 0;
                }
             }
          }
       }
       }
    }
    
    // Method to move right
    public void moveRight(){
       ///////////////////////merging////////////////////
       for ( int x = 0; x < this.GRID_SIZE; x++ ){
          for ( int y = this.GRID_SIZE-1; y > 0; y-- ){
             if ( this.grid[x][y] != 0){
                int next = y-1;
                while ( this.grid[x][next] == 0 && next > 0 ){
                   next--;}
                if ( this.grid[x][y] == this.grid[x][next] ){
                   this.grid[x][y] = this.grid[x][y] + this.grid[x][next];
                   this.grid[x][next] = 0;
                   this.score += this.grid[x][y];
                }
             }
          }
       }
       ////////////////////////moving/////////////////
       for ( int count = 0; count < this.GRID_SIZE; count++ ){
       for ( int x = 0; x < this.GRID_SIZE; x++ ){
          for ( int y = 0; y < this.GRID_SIZE-1; y++ ){
             if ( this.grid[x][y] != 0 ){
                if ( this.grid[x][y+1] == 0 ){
                   this.grid[x][y+1] = this.grid[x][y];
                   this.grid[x][y] = 0;
                }
             }
          }
       }
       }
    }

    // Method to check if we have a game over
    public boolean isGameOver() {
        if ( this.canMove(Direction.UP) ) return false;
        if ( this.canMove(Direction.DOWN) ) return false;
        if ( this.canMove(Direction.LEFT) ) return false;
        if ( this.canMove(Direction.RIGHT) ) return false;
        return true;
    }


    // Return the reference to the 2048 Grid
    public int[][] getGrid() {
        return grid;
    }

    // Return the score
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}
