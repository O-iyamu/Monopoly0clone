import java.io.Serializable;

/**
 * Class for Spaces on the Monopoly Board
 * @author Jeneerthan Pageerathan 101153531
 * @author Vishesh Dasani 101162185
 * @version 1.0
 */
public abstract class Space implements Serializable {

    /**
     * Getter for the name
     *
     * @return String name
     */
    public abstract String getName();

    /**
     * Getter for the rent
     *
     * @return double rent
     */
    public abstract double getRent();

    /**
     * Getter for the price
     *
     * @return Double price
     */
    public abstract double getPrice();

    /**
     * Getter for the owner
     *
     * @return Player owner
     */
    public abstract Player getOwner();

    /**
     * Setter for the owner
     *
     * @param p Player owner
     */
    public abstract void setOwner(Player p);

    /**
     * Getter for the type of property
     *
     * @return String type
     */
    public abstract String getColour();

    /**
     * Method to add house to property
     */
    public abstract void addHouse();

    /**
     *
     */
    public abstract int getNumHouses();
}
