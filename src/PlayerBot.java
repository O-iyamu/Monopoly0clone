import java.io.Serializable;
import java.util.ArrayList;

/**
 * PlayerBot class which creates artificial players to play the game
 * @author Jeneerthan Pageerathan 101153531
 */
public class PlayerBot extends Player implements Serializable {
    /**
     * The constructor of the PlayerBot class
     * @param numb, the id of the robot
     * @param balance, the balance of the robot
     */
    public PlayerBot(int numb, double balance) {
        super(("Robot" + numb), balance);
    }

    /**
     * A method to return an ArrayList of commands 
     * @return ArrayList of commands
     */
    public ArrayList<String> pathWay() {
        ArrayList newPath = new ArrayList();
        newPath.add("Roll");
        newPath.add("Buy");
        newPath.add("Pass");
        newPath.add("Quit");
        return newPath;
    }
}