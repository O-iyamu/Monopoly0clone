/**
 * Class to build properties to be added to the board. Properties include Estates,Railroads and Utilities
 * @author Jeneerthan Pageerathan 101153531
 */
public final class PropertyBuilder {
    private String name;
    private double price;
    private double rent;
    private Player owner = null;
    private String colour;

    /**
     * Method to add name of the property
     * @param name String, name of the property
     * @return propertyBuilder
     */
    public PropertyBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Method to add price of the property
     * @param price double, price of the property
     * @return propertyBuilder
     */
    public PropertyBuilder price(double price) {
        this.price = price;
        return this;
    }

    /**
     * Method to add rent of the property
     * @param rent double, rent of the property
     * @return propertyBuilder
     */
    public PropertyBuilder rent(double rent) {
        this.rent = rent;
        return this;
    }

    /**
     * Method to add colour of the property
     * @param colour String, colour of the property
     * @return propertyBuilder
     */
    public PropertyBuilder colour(String colour) {
        this.colour = colour;
        return this;
    }

    /**
     * Method to buildProperty
     * @return Property
     */
    public Property buildProperty() {
        return new Property(name, price, rent, colour);
    }

    /**
     * Method to build estate
     * @return Estate
     */
    public Estate buildEstate() {
        return new Estate(name, price, rent, colour);
    }

    /**
     * Method to build railroad
     * @return Railroad
     */
    public Railroad buildRailroad() {
        return new Railroad(name, price, rent);
    }

    /**
     * Method to build Utility
     * @return Utility
     */
    public Utility buildUtility() {
        return new Utility(name, price, rent);
    }
}
