import java.util.HashMap;
import java.util.Map;

/**
 * Builder class to build the board
 * @author Jeneerthan Pageerathan 101153531
 */
public final class BoardBuilder {
    private String currency;
    private Map<Integer, Estate> estates = new HashMap<>();
    private Railroad[] railroads = new Railroad[4];
    private Utility[] utilities = new Utility[2];
    private IncomeTax incomeTax;
    private Jail jail;
    private Go go;
    private ParkingSpace ps = new ParkingSpace();
    private Property communityChest = new Property("COMMUNITY CHEST", 0, 0, "");
    private Property chanceSpace = new Property("CHANCE", 0, 0, "");
    private Property jailSpace = new Property("Just Visiting - Jail", 0, 0, "");

    /**
     * Method to set currency to be used in the board
     * @param currency currency to be used
     * @return boardBuilder
     */
    public BoardBuilder currency(String currency) {
        this.currency = currency;
        return this;
    }

    /**
     * Method to put estates to the list of estates
     * @param index int, the position in the list
     * @param estate Estate, the estate to be added
     * @return boardBuilder
     */
    public BoardBuilder estate(int index, Estate estate) {
        this.estates.put(index, estate);
        return this;
    }

    /**
     * Method to put utility to the list of utilities
     * @param index int, position iin the list
     * @param utility Utility, the Utility to be added
     * @return boardBuilder
     */
    public BoardBuilder utility(int index, Utility utility) {
        this.utilities[index] = utility;
        return this;
    }

    /**
     * Method to put railroad to the list of railroads
     * @param index int, position in the list
     * @param railroad Railroad, the railroad to be added
     * @return boardBuilder
     */
    public BoardBuilder railroad(int index, Railroad railroad) {
        this.railroads[index] = railroad;
        return this;
    }

    /**
     * Method to initialize income tax space
     * @param tax double, the income tax amount
     * @return boardBuilder
     */
    public BoardBuilder incomeTax(double tax) {
        this.incomeTax = new IncomeTax(tax, ps);
        return this;
    }

    /**
     * Method to intitialize the jail space
     * @param bailFee double, the bail fees
     * @return boardBuilder
     */
    public BoardBuilder jail(double bailFee) {
        this.jail = new Jail(bailFee);
        return this;
    }

    /**
     * Method to initialize the go space
     * @param goSum double, the salary
     * @return boardBuilder
     */
    public BoardBuilder go(double goSum) {
        this.go = new Go(goSum);
        return this;
    }

    /**
     * Method to build the board with all the given spaces
     * @return map of spaces with positions
     */
    public Map<Integer, Space> build() {
        Map<Integer, Space> spaces = new HashMap<>();
        for (Integer i : estates.keySet()) {
            spaces.put(i, estates.get(i));
        }
        spaces.put(0, go);
        spaces.put(2, communityChest);
        spaces.put(4, incomeTax);
        spaces.put(5, railroads[0]);
        spaces.put(7, chanceSpace);
        spaces.put(10, jailSpace);
        spaces.put(12, utilities[0]);
        spaces.put(15, railroads[1]);
        spaces.put(17, communityChest);
        spaces.put(20, ps);
        spaces.put(22, chanceSpace);
        spaces.put(25, railroads[2]);
        spaces.put(28, utilities[1]);
        spaces.put(30, jail);
        spaces.put(33, communityChest);
        spaces.put(35, railroads[3]);
        spaces.put(36, chanceSpace);
        spaces.put(38, incomeTax);
        return spaces;
    }

    /**
     * Getter for the parking space
     * @return ParkingSpace
     */
    public ParkingSpace getPs() {
        return ps;
    }

    /**
     * Getter for the go space
     * @return Go
     */
    public Go getGo() {
        return go;
    }

    /**
     * Getter for the income tax space
     * @return IncomeTax
     */
    public IncomeTax getIncomeTax() {
        return incomeTax;
    }

    /**
     * Getter for the jail space
     * @return Jail
     */
    public Jail getJail() {
        return jail;
    }
}
