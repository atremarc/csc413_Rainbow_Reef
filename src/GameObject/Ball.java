/*

Code written by: Adam Tremarche
Date Submitted: 12/12/2018
Class: CSC 413
Instructor: Anthony Souza

*/

package GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static javax.imageio.ImageIO.read;

import Core.Game;

//The Ball Class handles the bouncing ball object
public class Ball {

    private int xPos;                       //x coordinate of the ball
    private int yPos;                       //y coordinate of the ball
    private int xCenter;                    //center of the ball
    private int xVol;                       //x velocity of the ball
    private int yVol;                       //y velocity of the ball
    private int angle;                      //angle of the ball
    private double speed;                   //speed of the ball

    private BufferedImage img;              //image of the ball
    private Rectangle boxCollider;          //rectangle used to detect collisions
    private Player PC;                      //player object

    //Constructor:
    //sets the coordinates and angle of the ball, creates a boxCollider, as well as loads the image for ball
    public Ball (int x, int y, int ang, Player PC) {

        this.xPos = x;
        this.yPos = y;
        this.xCenter = x + 4;
        this.angle = ang;
        this.PC = PC;
        this.speed = 2.0;
        boxCollider = new Rectangle();
        try {
            this.img = read( new File ("ball.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Method moves bullet forward at the angle it is currently traveling. Also, this method moves the boxCollider with
    //the object
    public void update () {
        this.xVol = (int) Math.round(speed * Math.cos(Math.toRadians(angle)));
        this.yVol = (int) Math.round(speed * Math.sin(Math.toRadians(angle)));
        this.xPos += xVol;
        this.yPos += yVol;
        this.xCenter = xPos + 20;
        setBoxCollider(7,7,1,1);
        checkBorder();
    }

    //Getters and Setters
    public int getxPos () {
        return xPos;
    }

    public int getyPos () {
        return yPos;
    }

    public void setAngle (int ang) {
        this.angle = ang;
    }

    public int getAngle () {
        return angle;
    }

    public int getxCenter () {
        return xCenter;
    }

    public void setSpeed (double spd) {
        speed = spd;
    }

    public void incSpeed (double x) {
        speed += x;
    }

    //resets the ball and paddle to the start positions of the level
    public void reset () {
        this.xPos = 300;
        this.yPos = Game.SCREEN_HEIGHT - 512;
        this.angle = 90;
        this.speed = 2.0;
        PC.setxPos(296);

    }

    //Checks to make sure the ball bounces off the edge of the world
    private void checkBorder() {
        if (xPos < 8) {
            if((angle < 180) && (angle > 90)) {
                angle -= 90;
            } else if (angle == 180) {
                angle = 45;
            } else {
                angle += 90;
            }
        }
        if (xPos >= Game.SCREEN_WIDTH - 48) {
            if ((angle > 0) && (angle < 90)) {
                angle += 90;
            } else if (angle == 0) {
                angle = 135;
            } else {
                angle -= 90;
            }
        }
        if (yPos < 8) {
            if ((angle < 360) && (angle > 270)) {
                angle += 90;
            } else if (angle == 270) {
                angle = 90;
            } else {
                angle -= 90;
            }
        }
        if (yPos >= Game.SCREEN_HEIGHT - 64) {
            PC.incLives(-1);
            reset();
        }
        if ((xPos < 0) || (xPos > Game.SCREEN_WIDTH) || (yPos < 0)){
            reset();
        }
    }

    //used to determine which kind of bounce the ball will perform when colliding with a brick
    public void bounce (Bricks brick) {
        int brickX = brick.getXpos();
        int brickY = brick.getYpos();
        if ((xPos < brickX) && (yPos < brickY + 16)) {
            if (angle <= 90) {
                angle += 90;
            } else {
                angle -= 90;
            }
        } else if ((xPos > brickX) && (yPos < brickY)) {
            if ((angle > 0) && (angle <= 90)) {
                angle -= 90;
            } else {
                angle += 90;
            }
        } else if ((xPos > brickX + 30) && (yPos < brickY + 16)) {
            if (angle <= 180) {
                angle -= 90;
            } else {
                angle += 90;
            }
        } else if ((xPos > brickX) && (yPos > brickY  + 16)) {
            if (angle <= 270) {
                angle -= 90;
            } else {
                angle += 90;
            }
        }
    }

    //Method sets the position and dimensions of the boxCollider
    private void setBoxCollider(int x, int y, int w, int h){
        this.boxCollider.x = this.xPos + x;
        this.boxCollider.y = this.yPos + y;
        this.boxCollider.width = w;
        this.boxCollider.height = h;
    }

    //Method returns the boxCollieder
    public Rectangle getBoxCollider () {
        return boxCollider;
    }

    //Method draws the bullet to a graphics object
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(xPos + 16, yPos + 16);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }
}
