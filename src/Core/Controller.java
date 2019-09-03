/*

Code written by: Adam Tremarche
Date Submitted: 12/12/2018
Class: CSC 413
Instructor: Anthony Souza

*/

package Core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import GameObject.Player;


//The Controller class handles all keyboard presses used to control the player Tanks in the game.
//The majority of this code was adapted from Prof. Souza's Tank demo.
public class Controller implements KeyListener{

    private Player PC;              //player controlled character object
    private final int right;        //used to store key value for move right
    private final int left;         //used to store key value for move left

    //Constructor:
    //Assigns a player to the controller as well as establishes keys that player uses to control their tank
    public Controller(Player PC, int left, int right) {
        this.PC = PC;
        this.right = right;
        this.left = left;
    }


    //Included by Prof. Souza so I left it in
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    //Method sends signal to the Tank object to trigger action for each key press
    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();

        if (keyPressed == left) {
            this.PC.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.PC.toggleRightPressed();
        }
    }

    //Method sends signal to the Tank object to trigger end of action for each key press
    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();

        if (keyReleased  == left) {
            this.PC.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.PC.unToggleRightPressed();
        }
    }
}