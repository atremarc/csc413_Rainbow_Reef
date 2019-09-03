/*

Code written by: Adam Tremarche
Date Submitted: 12/12/2018
Class: CSC 413
Instructor: Anthony Souza

*/

package GameObject;

import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import static javax.imageio.ImageIO.read;

//The BonusBrick class handles the gold brick objects which give player extra points when destroyed
public class BonusBrick extends Bricks {

    private int xPos;                   //x coordinate of the brick
    private int yPos;                   //y coordinate of the brick
    private final int RANK = 3;         //rank of the brick (used to ID the tile "type" outside this class
    private BufferedImage image;        //image of the brick
    private Rectangle boxCollider;      //rectangle used to detect collisions

    //Constructor:
    //Assigns: coordinates, boxCollider, and image
    public BonusBrick () {
        this.xPos = 0;
        this.yPos = 0;
        this.boxCollider =  new Rectangle();

        try {
            this.image = read(new File("bonus.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Method used to set the position of the brick
    public void setPosition (int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    //draws the brick to a graphics object
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, this.xPos, this.yPos, null);
    }

    //returns the boxCollider rectangle
    public Rectangle getBoxCollider () {
        return boxCollider;
    }

    //returns the x coordinate of the brick
    public int getXpos () {
        return this.xPos;
    }

    //returns the y coordinate of the brick
    public int getYpos () {
        return this.yPos;
    }

    //returns the rank of the brick
    public int getRank () {
        return RANK;
    }

    //Method used to set the position and dimensions of the boxCollider
    public void setBoxCollider(int x, int y, int w, int h){
        this.boxCollider.x = this.xPos + x;
        this.boxCollider.y = this.yPos + y;
        this.boxCollider.width = w;
        this.boxCollider.height = h;
    }

    //Method used to hide a brick which has been destroyed
    public void sleepBrick () {
        try {
            this.image = read(new File("blank.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        boxCollider.x = 800;
        boxCollider.y = 800;
    }
}
