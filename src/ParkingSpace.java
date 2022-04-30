/**
 * A class to represent the parking space on the board
 * @author Jeneerthan Pageerathan 101153531
 */
public class ParkingSpace extends Space {
    private double collectedSum;

    /**
     * The default constructor of the ParkingSpace class
     */
    public ParkingSpace() {
        collectedSum = 0;
    }

    /**
     * A method to get the collected sum and reset it to zero
     * @return double, the collected sum
     */
    public double getCollectedSum() {
        double sum = collectedSum;
        collectedSum = 0;
        return sum;
    }

    /**
     * A method to add to the collected sum
     * @param additionalSum, the amount to add
     */
    public void addSum(double additionalSum) {
        collectedSum += additionalSum;
    }

    /**
     * A method to get the name of the class
     * @return String, the name
     */
    public String getName(){
        return "FREE PARKING";
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
