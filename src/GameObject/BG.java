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

//The BG class simply draws a background image to the game world
public class BG {

    private BufferedImage image;          //image for displaying

    public BG () {

        try {
            this.image = read(new File("bg.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Draws background to a graphics object
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, 0,0, null);
    }
}
