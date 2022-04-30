/**
 * A class to reperesent an Estate
 * @author Jeneerthan Pageerathan 101153531
 * @author Vishesh Dasani 101162185
 */
public class Estate extends Property {
    private int numHouses; // 5 houses = hotel
    private boolean hotel;

    /**
     * The Constructor method for the Estate class
     * @param name, the name of the estate
     * @param price, the price of the estate
     * @param rent, the rent of the estate
     * @param colour, the colour assigned to the estate
     */
    public Estate(String name, double price, double rent, String colour) {
        super(name, price, rent, colour);
    }

    /**
     * A method that returns the rent of the estate
     * @return double, the rent of the estate
     */
    @Override
    public double getRent() {
        return super.getRent() * rentModifier();
    }

    /**
     * A method to add a house to the estate
     */
    @Override
    public void addHouse() {
        if (checkHotel()) {
            return;
        }
        numHouses += 1;
    }

    /**
     * A method to check if a hotel can b e built from the number of houses
     * @return boolean, true if it can and false if it cannot
     */
    private boolean checkHotel() {
        return (numHouses == 5);
    }

    /**
     * A method to modify the rent of an estate
     * @return double, the new rent
     */
    private double rentModifier() {
        switch (numHouses) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 5;
            case 4:
                return 10;
            case 5:
                return 20;
        }
        return 1;
    }

    /**
     * A method to get the build price of an estate
     * @return double, the price of the Original Estate
     */
    public double getBuildPrice() {
        return (super.getPrice() / 2);
    }

    /**
     * Method to convert String to create an estate object
     * @param param String
     * @return Estate
     */
    public static Estate toEstate(String param){
        String[] info = param.split("#");
        Estate estate = new Estate(info[0],Double.parseDouble(info[1]) ,Double.parseDouble(info[2]),info[3]);
        return estate;
    }


    /**
     * A getter method to return the number of houses in a specific property
     * @return int, number of houses
     */
    public int getNumHouses(){ return this.numHouses; }
}
