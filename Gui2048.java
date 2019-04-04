//------------------------------------------------------------------//
// Gui2048.java                                                     //
//                                                                  //
// GUI Driver for 2048                                              //
//                                                                  //
// Author:  CSE-8B group, Zhaopu Wang, cs8bwatz		                 //
// Date:    03/07/2017                                              //
//------------------------------------------------------------------//

/**
 * ******************File description***************
 * This file is the GUI Driver for 2048, working with Board.java
 * It stores color values, int values of the GUI
 * It includes runner and event handler methods
 */
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    private static final int TILE_WIDTH = 106;

    private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
    private static final int TEXT_SIZE_MID = 45; // Mid value tiles
    //(128, 256, 512)
    private static final int TEXT_SIZE_HIGH = 35; // High value tiles
    //(1024, 2048, Higher)

    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
    private static final Color COLOR_2 = Color.rgb(238, 228, 218);
    private static final Color COLOR_4 = Color.rgb(237, 224, 200);
    private static final Color COLOR_8 = Color.rgb(242, 177, 121);
    private static final Color COLOR_16 = Color.rgb(245, 149, 99);
    private static final Color COLOR_32 = Color.rgb(246, 124, 95);
    private static final Color COLOR_64 = Color.rgb(246, 94, 59);
    private static final Color COLOR_128 = Color.rgb(237, 207, 114);
    private static final Color COLOR_256 = Color.rgb(237, 204, 97);
    private static final Color COLOR_512 = Color.rgb(237, 200, 80);
    private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
    private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
    private static final Color COLOR_OTHER = Color.BLACK;
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
    // For tiles >= 8

    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);
    // For tiles < 8

    private GridPane pane;
    private GridPane gameover;

    /** Add your own Instance Variables here */
    private Rectangle[][] rectangles;
    private Text[][] numbers;
    private Text scoretxt;
    private Scene scene;
    private boolean isGM;



    // Start method
    // @param Stage primaryStage
    @Override
    public void start(Stage primaryStage)
    {
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        // Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
        // Set the spacing between the Tiles
        pane.setHgap(15);
        pane.setVgap(15);


        /** Add your Code for the GUI Here */
        //gameover pane
        isGM = false;
        gameover = new GridPane();
        gameover.setAlignment(Pos.CENTER);
        gameover.setStyle("-fx-background-color: rgb(187, 173, 160)");
        gameover.add(pane,0,0);

        scene = new Scene(gameover);
        scene.setOnKeyPressed(new myKeyHandler());        
        primaryStage.setTitle("Gui2048");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //2048
        Text title2048 = new Text();
        title2048.setText("2048");
        title2048.setFont(Font.font("Times New Roman", FontWeight.BOLD, 
         FontPosture.ITALIC,55));
        pane.add(title2048,0,0,2,1);
        pane.setHalignment(title2048,HPos.CENTER);

        //score
        scoretxt = new Text();
        scoretxt.setText("Score:"+board.getScore());
        scoretxt.setFont(Font.font("Times New Roman", FontWeight.BOLD,45 ));
        pane.add(scoretxt,2,0,2,1);
        pane.setHalignment(scoretxt, HPos.CENTER);

        //tiles
        rectangles = new Rectangle[board.GRID_SIZE][board.GRID_SIZE];
        numbers = new Text[board.GRID_SIZE][board.GRID_SIZE];
        for ( int x = 0; x < board.GRID_SIZE; x++ ) {
           for ( int y = 0; y < board.GRID_SIZE; y++ ) {
              this.rectangles[x][y] = new Rectangle(TILE_WIDTH,TILE_WIDTH);
              Text number = new Text();
              setNum(x,y,number);
              changeTile(x,y,board.getGrid()[x][y]);
              pane.add(rectangles[x][y],y,x+1);
              pane.setHalignment(rectangles[x][y],HPos.CENTER);
              pane.add(numbers[x][y],y,x+1);
              pane.setHalignment(numbers[x][y],HPos.CENTER);
              
           }
        }
        
        



    }

    /** Add your own Instance Methods Here */
    // Getter method of rectangles
    // @param row, column
    // @return Rectangle
    public Rectangle getRec ( int row, int col ) {
       return this.rectangles[row][col];
    }
   
    // Setter method of rectangles
    // @param row, column, new Rectangle
    private void setRec ( int row, int col , Rectangle newRec ) {
       this.rectangles[row][col] = newRec;
    }
    
    // Getter method of number values
    // @param row, column
    // @return text of number
    public Text getNum ( int row, int col ) {
       return this.numbers[row][col];
    }
    
    // Setter method of number values
    // @param row, column, new number
    private void setNum ( int row, int col, Text num ) {
       this.numbers[row][col] = num;
    }
    
    // Method to change a tile
    // @param row, column,  number of the new tile
    public void changeTile(int row, int col, int newNum) {
       Text number = this.getNum(row,col);
       Rectangle square = this.getRec(row,col);
       String value = new String( Integer.toString(newNum) );
       number.setText(value);
          //0-8
          if ( newNum <= 8 ) {
             number.setFont(Font.font("Times New Roman", FontWeight.BOLD, TEXT_SIZE_LOW));
             number.setFill(COLOR_VALUE_DARK);
             if ( newNum == 0 ) {
                number.setText("");
                square.setFill(COLOR_EMPTY);
             }
             else if ( newNum == 2 ) 
                square.setFill(COLOR_2);
             else if ( newNum == 4 )
                square.setFill(COLOR_4);
             else if ( newNum == 8 ) 
                square.setFill(COLOR_8);
          }
          //8-128
          else if ( newNum > 8 && newNum < 128 ) {
             number.setFont(Font.font("Times New Roman", FontWeight.BOLD, TEXT_SIZE_LOW));             number.setFill(COLOR_VALUE_LIGHT);             
             if ( newNum == 16 ) 
                square.setFill(COLOR_16);
             else if ( newNum == 32 ) 
                square.setFill(COLOR_32);
             else if ( newNum == 64 ) 
                square.setFill(COLOR_64);
          }
          //128-512
          else if ( newNum >= 128 && newNum <= 512 ) {
             number.setFont(Font.font("Times New Roman", FontWeight.BOLD, TEXT_SIZE_MID));
             number.setFill(COLOR_VALUE_LIGHT);
             if ( newNum == 128 ) 
                square.setFill(COLOR_128);
             else if ( newNum == 256 )
                square.setFill(COLOR_256);
             else if ( newNum == 512 )
                square.setFill(COLOR_512);                
          }
          //1024-
          else if ( newNum >= 1024 ) {
             number.setFill(COLOR_VALUE_LIGHT);
             number.setFont(Font.font("Times New Roman", FontWeight.BOLD, TEXT_SIZE_HIGH));  
             if ( newNum == 1024 )
                square.setFill(COLOR_1024);
             else if ( newNum == 2048 )
                square.setFill(COLOR_2048);
             else square.setFill(COLOR_OTHER);
          }
       }
       
       // Method to update the whole grid
       public void update() {
          board.addRandomTile();          
          scoretxt.setText("Score:"+board.getScore());          
          for ( int x = 0; x < board.GRID_SIZE; x++ ) {
             for ( int y = 0; y < board.GRID_SIZE; y++ ) {
               changeTile(x,y,board.getGrid()[x][y]);
             }
          }
       }
                
       // Method to show game over when game is over
       public void showGameOver() {
          // "Game Over!"
          Text text = new Text();
          text.setText("Game Over!");
          text.setFont(Font.font("Times New Roman", FontWeight.BOLD,55 ));
          // background
          Rectangle rect = new Rectangle(gameover.getWidth(),gameover.getHeight());
          rect.setFill(COLOR_GAME_OVER);
          rect.widthProperty().bind(gameover.widthProperty());
          rect.heightProperty().bind(gameover.heightProperty());
          gameover.add(rect,0,0);
          gameover.add(text,0,0);
          gameover.setHalignment(text,HPos.CENTER);          
          }

          
       // myKeyHandler class extending eventHandler<KeyEvent>
       private class myKeyHandler implements EventHandler<KeyEvent>
	    {
   		@Override
   		public void handle(KeyEvent e) {
            /* KeyEvent Processing Code Goes Here */
            //up
            if ( e.getCode() == KeyCode.UP ) {
               if ( board.move(Direction.UP) ) {
                  System.out.println("Moving Up");
                  update();
               }
            }
            //down
            else if ( e.getCode() == KeyCode.DOWN ) {
               if ( board.move(Direction.DOWN) ){
                  System.out.println("Moving Down");
                  update();
               }
            }
            //left
            else if ( e.getCode() == KeyCode.LEFT) {
               if ( board.move(Direction.LEFT) ) {
                  System.out.println("Moving Left");
                  update();
               }
            }
            //right
            else if ( e.getCode() == KeyCode.RIGHT) {
               if ( board.move(Direction.RIGHT) ) {
                  System.out.println("Moving Right");
                  update();
               }
            }
            //save
            else if ( e.getCode() == KeyCode.S ) {
               System.out.println("Saving Board to"+outputBoard);
               try {
                  board.saveBoard(outputBoard);
               } catch (IOException excp) { 
                  System.out.println("saveBoard threw an Exception");
               }
            }
            //gameover
            if ( board.isGameOver() ) {
               if ( isGM == false ) {
                   showGameOver();
                   isGM = true;
               }
            }
               
               
      	}
       }

       






    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(new Random(), inputBoard);
            else
                board = new Board(new Random(), boardSize);
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() +
                    " was thrown while creating a " +
                    "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                    "Constructor is broken or the file isn't " +
                    "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+
                "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " +
                "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " +
                "used to save the 2048 board");
        System.out.println("                If none specified then the " +
                "default \"2048.board\" file will be used");
        System.out.println("  -s [size]  -> Specifies the size of the 2048" +
                "board if an input file hasn't been");
        System.out.println("                specified.  If both -s and -i" +
                "are used, then the size of the board");
        System.out.println("                will be determined by the input" +
                " file. The default size is 4.");
    }
}

