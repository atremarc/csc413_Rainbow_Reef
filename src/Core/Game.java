/*

Code written by: Adam Tremarche
Date Submitted: 12/12/2018
Class: CSC 413
Instructor: Anthony Souza

*/

package Core;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

import GameObject.*;

//Game is the Main class of the game. It contains the main() method, handles everything having to do with the game
//world, as well as the drawing the game to the jFrame window.
public class Game extends JPanel {

    public static final int SCREEN_WIDTH = 646;                 //Width of the display window
    public static final int SCREEN_HEIGHT = 704;                //Height of the display window
    private static final int STARTX_PC = 296;                   //player starting position
    private static final int STARTY_PC = SCREEN_HEIGHT - 160;   //player starting position

    private BufferedImage world;                                //holds an image of the whole game world
    private Graphics2D buffer;                                  //used to draw to the game world image
    private JFrame jf;                                          //jFrame for displaying the game
    private Map gameMap1;                                       //holds the tile map for level one
    private Map gameMap2;                                       //holds the tile map for level two
    private Map gameMap3;                                       //holds the tile map for level three
    private ArrayList<Bricks> bricks = new ArrayList<>();       //holds all the bricks
    private Player PC;                                          //holds the player object
    private Ball ball;                                          //holds the ball object
    private BG bg;                                              //holds the background image
    private Controller PCcontrol;                               //holds the player controller object

    private int level;                                          //indicates current level of the game
    private int goalCount;                                      //used to trigger end level state

    //*** Main method of the game. ***
    //In this method the gameInstance is initialized and then the game-loop is executed in perpetuity, until the game
    //window is closed.
    public static void main(String [] args) {
        Thread x;
        Game gameInstance = new Game();
        gameInstance.initGame();

        //There are 3 game loops for each level of the game
        gameInstance.startLevel1();
        while(gameInstance.level == 1) {
            gameInstance.gameLoop(gameInstance);
            if (gameInstance.goalCount == 10) {
                gameInstance.level = 2;
            }
            if (!gameInstance.PC.getControl()) {
                break;
            }
        }
        gameInstance.startLevel2();
        while(gameInstance.level == 2) {
            gameInstance.gameLoop(gameInstance);
            if (gameInstance.goalCount == 11) {
                gameInstance.level = 3;
            }
            if (!gameInstance.PC.getControl()) {
                break;
            }
        }
        gameInstance.startLevel3();
        while(gameInstance.level == 3) {
            gameInstance.gameLoop(gameInstance);
            if (gameInstance.goalCount == 3) {
                gameInstance.level = 4;
            }
            if (!gameInstance.PC.getControl()) {
                break;
            }
        }

        //End game: Displays Game over or Congrats and then handles the high score table
        if (gameInstance.PC.getControl()) {
            System.out.println("CONGRATULATIONS! YOU WON!");
        } else {
            System.out.println("GAME OVER! Try Again");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        String name = sc.next();

        String [] newScores = new String [6];
        try(BufferedReader reader = new BufferedReader(new FileReader("highScores.txt"))) {
            String currentLine;

            currentLine = reader.readLine();
            String[] currScores = currentLine.trim().split(" ");
            if( Integer.parseInt(currScores[1]) <= gameInstance.PC.getScore()) {
                newScores[0] = name;
                newScores[1] = String.valueOf(gameInstance.PC.getScore());
                newScores[2] = currScores[0];
                newScores[3] = currScores[1];
                newScores[4] = currScores[2];
                newScores[5] = currScores[3];
            } else if (Integer.parseInt(currScores[3]) <= gameInstance.PC.getScore()) {
                newScores[0] = currScores[0];
                newScores[1] = currScores[1];
                newScores[2] = name;
                newScores[3] = String.valueOf(gameInstance.PC.getScore());
                newScores[4] = currScores[2];
                newScores[5] = currScores[3];
            } else if (Integer.parseInt(currScores[5]) <= gameInstance.PC.getScore()) {
                newScores[0] = currScores[0];
                newScores[1] = currScores[1];
                newScores[2] = currScores[2];
                newScores[3] = currScores[3];
                newScores[4] = name;
                newScores[5] = String.valueOf(gameInstance.PC.getScore());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            PrintWriter fw = new PrintWriter("highScores.txt");
            fw.print(" ");
            for (int i = 0; i < 6; i++) {
                fw.append(newScores[i]);
                fw.append(" ");
            }
            fw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("HIGH SCORES");
        for(int i = 0;i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(newScores[j + i] + " ");
            }
            i++;
            System.out.println();
        }
    }

    //Method called by main(). initGame() runs once before the game-loop and takes care of most of the object creation
    //and initialization for the game. This includes:
    //
    //          -creating and setting up the jFrame
    //          -creating the game world objects objects
    //          -loading the maps for each level
    //          -creating Controller objects for each player
    private void initGame() {

        //creating and setting up the jFrame
        this.jf = new JFrame("Rainbow Reef");
        this.jf.setSize(SCREEN_WIDTH,SCREEN_HEIGHT + 29);
        this.jf.setResizable(false);
        this.jf.setLocationRelativeTo(null);
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setLayout(new BorderLayout());

        //loading the tile maps for each level
        this.gameMap1 = gameMap1.fromFile("map1.txt");
        this.gameMap2 = gameMap2.fromFile("map2.txt");
        this.gameMap3 = gameMap3.fromFile("map3.txt");

        //initializing image to draw game world to
        this.world = new BufferedImage(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);

        //initialize player object
        this.PC = new Player(STARTX_PC, STARTY_PC);

        //initialize the ball object
        this.ball = new Ball (300, SCREEN_HEIGHT - 512, 90, PC);

        //initializing the controller
        this.PCcontrol = new Controller(PC, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        this.jf.addKeyListener(PCcontrol);

        //initialize the BG image
        this.bg = new BG ();

        //start the game with level one
        this.level = 1;

        //finalize and display jFrame
        this.jf.add(this);
        this.jf.setVisible(true);
    }

    //Game Loop, holds all the update() methods
    private void gameLoop (Game gameInstance) {
        try {
            gameInstance.collisionDetection();
            gameInstance.PC.update();
            gameInstance.ball.update();
            gameInstance.repaint();
            Thread.sleep(1000 / 144);
        } catch (InterruptedException ignored) {
        }
    }

    //called to build level one bricks
    private void startLevel1 () {
        buildBricks(gameMap1.getGrid());
    }

    //called to reset world and build level two bricks
    private void startLevel2 () {
        goalCount = 0;
        bricks.clear();
        buildBricks(gameMap2.getGrid());
        ball.reset();
    }

    //called to reset world and build level three bricks
    private void startLevel3 () {
        goalCount = 0;
        bricks.clear();
        buildBricks(gameMap3.getGrid());
        ball.reset();
    }

    //Method called by initGame(). Used to create Brick objects to fit a 2D Array
    private void buildBricks (int[][] grid) {
        for (int i = 1; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Bricks newBrick = Bricks.getBrick(grid[i][j]);
                if (newBrick != null) {
                    newBrick.setPosition((16 + j*32), (i*16));
                    newBrick.setBoxCollider(0, 0, 32, 16);
                    bricks.add(newBrick);
                }
            }
        }
    }

    //Method called by repaint() in main(). Iterates through all game Tiles and calls drawImage() from the Tiles class
    private void drawBricks () {
        Bricks b;
        for (int i = 0; i < bricks.size(); i++) {
            b = bricks.get(i);
            b.drawImage(buffer);
        }
    }

    //Collision Detection method. Handles paddle w/ ball and ball w/ brick collisions
    private void collisionDetection () {

        //ball w/ paddle
        if(PC.getBoxCollider().intersects(ball.getBoxCollider())) {
            if(ball.getxCenter() < PC.getxCenter()) {
                ball.setAngle(270 - (PC.getxCenter() - ball.getxCenter()));
            } else {
                ball.setAngle(270 + (ball.getxCenter() - PC.getxCenter()));
            }
            ball.incSpeed(0.2);
        }
        //ball w/ bricks
        for (int i = 0; i < bricks.size(); i++) {
            if(ball.getBoxCollider().intersects(bricks.get(i).getBoxCollider())) {
                if (bricks.get(i).getRank() == 1) {
                    ball.bounce(bricks.get(i));
                } else if (bricks.get(i).getRank() == 2) {
                    ball.bounce(bricks.get(i));
                    bricks.get(i).sleepBrick();
                    PC.incScore(100);
                } else if (bricks.get(i).getRank() == 3) {
                    ball.bounce(bricks.get(i));
                    bricks.get(i).sleepBrick();
                    PC.incScore(500);
                } else if (bricks.get(i).getRank() == 4) {
                    ball.bounce(bricks.get(i));
                    bricks.get(i).sleepBrick();
                    PC.incScore(1000);
                    goalCount++;
                }
            }
        }
    }

    //Draws game world
    @Override
    public void paintComponent(Graphics g) {

        //Create graphics object for drawing to the jFrame and set buffer equal to world
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);

        //Draw game objects to the world
        this.bg.drawImage(buffer);
        this.PC.drawImage(buffer);
        this.ball.drawImage(buffer);
        this.drawBricks();


        //Draw the world to the jFrame
        g2.drawImage(world,0,0, null);

        //Write text to the jFrame to display the player's lives and score
        g2.setFont(new Font("Helvetica",Font.BOLD,24));
        g2.drawString(PC.toString(), 16, SCREEN_HEIGHT - 16);
    }

}
