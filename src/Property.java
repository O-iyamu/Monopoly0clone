/**
 * The Property class used to represent a property in the Monopoly Game
 *
 * @author Iyamu Osaretinmwen 101157386
 * @version 1.0
 */
public class Property extends Space {
    private String name;
    private double price;
    private double rent;
    private Player owner;
    private String colour;

    /**
     * The constructor of the class
     *
     * @param name  String, the name of the property
     * @param price double, the price of the property
     * @param rent  double, the rent of the property
     * @param colour  String, the colour of property
     */
    public Property(String name, double price, double rent, String colour) {
        this.name = name;
        this.price = price;
        this.rent = rent;
        this.owner = null;
        this.colour = colour;
    }

    /**
     * A method to set the owner of the property
     *
     * @param player Player, the owner of the property
     */
    @Override
    public void setOwner(Player player) {
        this.owner = player;
    }

    /**
     * A method to get the name of the property
     *
     * @return String, the name of the property
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * A method to get the rent of the property
     *
     * @return double, the rent of the property
     */
    @Override
    public double getRent() {
        return this.rent;
    }

    /**
     * A method to get the price of the property
     *
     * @return double, the price of the property
     */
    @Override
    public double getPrice() {
        return this.price;
    }

    /**
     * A method to get the owner of the property
     *
     * @return Player, the owner of the property
     */
    @Override
    public Player getOwner() {
        return this.owner;
    }

    /**
     * A method to get the type of the property
     *
     * @return String, the type of the property
     */
    @Override
    public String getColour() {
        return this.colour;
    }


    public void addHouse() {
        return;
    }

    @Override
    public int getNumHouses() {
        return 0;
    }


}
