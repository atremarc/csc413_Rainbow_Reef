/*

Code written by: Adam Tremarche
Date Submitted: 12/12/2018
Class: CSC 413
Instructor: Anthony Souza

*/

package Core;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;

//The Map class is used to read a text file containing a matrix, that matrix is used to create the tilemap of the game
//world.
public class Map {

    private int[][] grid;       //2d array used to store values relating to the specific tiles to be loaded in

    //Constructor:
    public Map () {
    }

    //Constructor:
    //used in fromFile function to build a tile map of a certain size
    public Map (int w, int h) {
        grid = new int[h][w];
    }

    //code for this method adapted from XNAGameDevelopment on Youtube
    //this method reads a text file and uses it to generate a 2d array which is used to build the tile map of the game
    public static Map fromFile(String fileName) {

        //ArrayList of ArrayLists used to store tile values
        ArrayList<ArrayList<Integer>> tempMap = new ArrayList<>();

        //BufferedReader reads each line of the file and then parses the line into an ArrayList of integers. Then each
        //ArrayList is added to the master ArrayList.
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.isEmpty()) {
                    continue;
                }
                ArrayList<Integer> row = new ArrayList<>();
                String[] values = currentLine.trim().split(" ");

                for(String s : values) {
                    if (!s.isEmpty()) {
                        int id = Integer.parseInt(s);
                        row.add(id);
                    }
                }
                tempMap.add(row);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //get 2d array width and height
        int width = tempMap.get(0).size();
        int height = tempMap.size();

        //create new Map of specific size and then fill the 2d array from the ArrayList of ArrayLists
        Map gameMap = new Map(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gameMap.grid[i][j] = tempMap.get(i).get(j);
            }
        }
        return gameMap;
    }

    //method for returning the completed tile map
    public int[][] getGrid () {
        return grid;
    }
}
