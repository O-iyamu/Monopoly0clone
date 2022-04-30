/**
 * A class to represent the railroad space on the board
 * @author Jeneerthan Pageerathan 101153531
 * @author Vishesh Dasani 101162185
 */
public class Railroad extends Property {
    /**
     * The constructor of the Railroad class
     * @param name, the name of the railroad
     * @param price, the price of the railroad
     * @param rent, the rent of the railroad
     */
    public Railroad(String name, double price, double rent) {
        super(name, price, rent, "Black");
    }

    /**
     * A method to get the rent of the railroad
     * @param owner
     * @return
     */
    public double getRent(Player owner) {
        return (super.getRent() * owner.checkMonopoly("Black"));
    }

    /**
     * Method to convert string to create a railroad object
     * @param param String, the string to be converted
     * @return Railroad
     */
    public static Railroad toRailroad(String param){
        String[] info = param.split("#");
        Railroad railroad = new Railroad(info[0],Double.parseDouble(info[1]) ,Double.parseDouble(info[2]));
        return railroad;
    }
}
