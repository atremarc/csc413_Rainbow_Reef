package GameObject;

import java.awt.*;

//The Bricks class is an abstract class which is extended by all the brick objects in the game
public abstract class Bricks {

    public abstract void setPosition (int x, int y);

    public abstract void drawImage(Graphics g);

    public abstract Rectangle getBoxCollider ();

    public abstract void setBoxCollider(int x, int y, int w, int h);

    public abstract int getXpos ();

    public abstract int getYpos ();

    public abstract int getRank ();

    public abstract void sleepBrick();

    //Method used to return a specific type of brick, based on the rank of the desired brick
    public static Bricks getBrick (int rank) {
        Bricks brick = null;
        if (rank == 1) {
            try {
                Class c = Class.forName("GameObject.UnBreakableBrick");
                brick = (Bricks) c.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Tile failed to load. Rank: " + rank);
            }
        } else if (rank == 2) {
            try {
                Class c = Class.forName("GameObject.BreakableBrick");
                brick = (Bricks) c.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Tile failed to load. Rank: " + rank);
            }
        } else if (rank == 3) {
            try {
                Class c = Class.forName("GameObject.BonusBrick");
                brick = (Bricks) c.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Tile failed to load. Rank: " + rank);
            }
        } else if (rank == 4) {
            try {
                Class c = Class.forName("GameObject.TargetBrick");
                brick = (Bricks) c.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Tile failed to load. Rank: " + rank);
            }
        }
        return brick;
    }
}
