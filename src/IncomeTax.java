/**
 * A class to represent the income tax space on the board
 * @author Jeneerthan Pageerathan 101153531
 */
public class IncomeTax extends Space {
    private double tax;
    private ParkingSpace ps;

    /**
     * Tyhe constructor of the IncomeTax class
     * @param tax, the tax
     * @param ps, the parking space
     */
    public IncomeTax(double tax, ParkingSpace ps) {
        this.tax = tax;
        this.ps = ps;
    }

    /**
     * A method to add tax to the bill of the parking space
     * @return double, the modified tax
     */
    public double payTax() {
        ps.addSum(tax);
        return tax;
    }

    /**
     * A method to get the name of the class
     * @return String, the name of the class
     */
    public String getName() {
        return "INCOME TAX";
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
