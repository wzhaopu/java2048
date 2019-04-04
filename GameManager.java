/**
 * File name: GameManager.java
 * Created by: Zhaopu Wang, cs8bwatz
 * Date: 01/31/2017
 *
 * GameManager.java is the class of game manager for game 2048
 * This class stores board and the output file name
 * This class includes method of playing the game
 */

import java.util.*;
import java.io.*;

public class GameManager {
    /////////////////////////////////////////////////////
    ///////////////// Instance variables ////////////////
    /////////////////////////////////////////////////////
 
    private Board board; // The actual 2048 board
    private String outputFileName; // File to save the board to when exiting

    /////////////////////////////////////////////////////
    /////////////// GameManager Constructor /////////////
    /////////////////////////////////////////////////////

    // Generate new game
    public GameManager(String outputBoard, int boardSize, Random random) {
        System.out.println("Generating a New Board");
        if ( boardSize < 2)
           boardSize = 4;
        this.board = new Board(random, boardSize);
        this.outputFileName = outputBoard;
    }

    // Load a saved game
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
        System.out.println("Loading Board from " + inputBoard);
        this.board = new Board(random, inputBoard);
        this.outputFileName = outputBoard;        
    }

    /////////////////////////////////////////////////////
    /////////////////////// Methods /////////////////////
    /////////////////////////////////////////////////////

    // Method of main play loop
    // Takes in input from the user to specify moves to execute
    // valid moves are:
    //      k - Move up
    //      j - Move Down
    //      h - Move Left
    //      l - Move Right
    //      q - Quit and Save Board
    //
    //  If an invalid command is received then print the controls
    //  to remind the user of the valid moves.
    //
    //  Once the player decides to quit or the game is over,
    //  save the game board to a file based on the outputFileName
    //  string that was set in the constructor and then return
    //
    //  If the game is over print "Game Over!" to the terminal
    // @param -
    // @return -
    public void play() throws IOException {
       this.printControls();
       System.out.println(this.board);       
       Scanner sc = new Scanner(System.in);
       while ( sc.hasNext() ){
          if (this.board.isGameOver()==true)
             System.out.println("Game Over!");
          String move = sc.next();
          if ( move.equals("k") ){
             if (this.board.move(Direction.UP))
               this.board.addRandomTile();
             System.out.println(this.board);
             }
          else if ( move.equals("j") ){
             if (this.board.move(Direction.DOWN))
               this.board.addRandomTile();
             System.out.println(this.board);
          }
          else if ( move.equals("h") ){
             if (this.board.move(Direction.LEFT))
               this.board.addRandomTile();
             System.out.println(this.board);
          }
          else if ( move.equals("l") ){
             if (this.board.move(Direction.RIGHT))
               this.board.addRandomTile();
             System.out.println(this.board);
          }
          else if ( move.equals("q") ){
             this.board.saveBoard(this.outputFileName);
             return;
          }
          else this.printControls();
       }
    }
    
    // Method to print the Controls for the Game
    // @param -
    // @return -
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    k - Move Up");
        System.out.println("    j - Move Down");
        System.out.println("    h - Move Left");
        System.out.println("    l - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}
