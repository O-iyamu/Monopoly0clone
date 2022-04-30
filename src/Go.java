/**
 * A class to represent the go space on the Board
 * @author Jeneerthan Pageerathan 101153531
 */
public class Go extends Space {
    private double taxReturn;

    /**
     * The constructor for the Go class
     * @param taxReturn, the tax
     */    
    public Go(double taxReturn) {
        this.taxReturn = taxReturn;
    }

    /**
     * A method to return the tax
     * @return double, the tax
     */
    public double passGo() {
        return taxReturn;
    }

    /**
     * A method to return the name of the class
     * @return String, the name of the class
     */
    public String getName(){
        return "GO";
    }

    @Override
    public double getRent() {
        return 0;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public Player getOwner() {
        return null;
    }

    @Override
    public void setOwner(Player p) {

    }

    @Override
    public String getColour() {
        return null;
    }

    @Override
    public void addHouse() {

    }

    @Override
    public int getNumHouses() {
        return 0;
    }
}
