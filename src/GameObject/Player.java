/*

Code written by: Adam Tremarche
Date Submitted: 12/12/2018
Class: CSC 413
Instructor: Anthony Souza

*/

package GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static javax.imageio.ImageIO.read;

import Core.Game;

public class Player {

    private final int MOVEMENTSPEED = 2;            //Movement speed of the player

    private int xPos;                               //x coordinate of the player
    private int xCenter;                            //center of the player's paddle
    private int yPos;                               //y coordinate of the player
    private int xVol;                               //x velocity of the player

    private int lives;                              //remaining lives of the player
    private int score;                              //current score of the player

    private boolean RightPressed;                   //used to signal that right has been pressed
    private boolean LeftPressed;                    //used to signal that left has been pressed
    private boolean control;                        //used to remove or grant control to the player

    private Rectangle boxCollider;                  //rectangle used to detect collisions
    private BufferedImage sprite;                   //image of player object

    //Constructor:
    //Assigns: coordinates, lives, score, boxCollider, and image
    public Player (int x, int y) {

        this.xPos = x;
        this.yPos = y;
        this.xCenter = x + 24;
        this.boxCollider = new Rectangle ();
        this.control = true;
        this.lives = 3;
        this.score = 0;
        try {
            this.sprite = read( new File ("paddle.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Getters and Setters
    public int getxPos() {
        return xPos;
    }

    public void setxPos(int x) {
        xPos = x;
    }

    public int getyPos() {
        return yPos;
    }

    public int getxCenter () {
        return xCenter;
    }

    public boolean getControl () {
        return control;
    }

    public int getScore () {
        return score;
    }

    public void incLives (int x) {
        lives += x;
    }

    public void incScore (int x) {
        score += x;
    }

    private void setBoxCollider(int x, int y, int w, int h){
        this.boxCollider.x = this.xPos + x;
        this.boxCollider.y = this.yPos + y;
        this.boxCollider.width = w;
        this.boxCollider.height = h;
    }

    public Rectangle getBoxCollider () {
        return boxCollider;
    }

    //Method sets RightPressed to true
    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    //Method sets LeftPressed to true
    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    //Method sets RightPressed to false
    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    //Method sets LeftPressed to false
    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    //Keeps the paddle in the game world
    private void checkBorder() {
        if (xPos < 16) {
            xPos = 16;
        }
        if (xPos >= Game.SCREEN_WIDTH - 70) {
            xPos = Game.SCREEN_WIDTH - 70;
        }
    }

    //Method called every tick of the game, moves the paddle and boxCollider based on the players inputs as well as
    //checks to see if the player is still alive
    public void update() {
        this.setBoxCollider(-20, -12, 54, 8);
        this.xCenter = xPos + 24;
        if (control) {
            if (this.RightPressed) {
                this.moveForwards();
            }
            if (this.LeftPressed) {
                this.moveBackwards();
            }
        }
        if (this.lives <= 0) {
            try {
                sprite = read(new File("blank.png"));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            this.control = false;
        }
    }

    //Moves the paddle to the left
    private void moveBackwards() {
        xVol = MOVEMENTSPEED;
        xPos -= xVol;
        xCenter = xPos + 24;
        checkBorder();
    }

    //Moves the paddle to the right
    private void moveForwards() {
        xVol = MOVEMENTSPEED;
        xPos += xVol;
        xCenter = xPos + 24;
        checkBorder();
    }

    //Used to draw the Player's score and lives to the display window
    @Override
    public String toString() {
        return "Lives: " + this.lives + "  Score: " + this.score;
    }

    //Used to draw the paddle to the window
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, this.xPos, this.yPos, null);
    }
}
