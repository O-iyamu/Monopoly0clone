/**
 * A class to represent the Utility on the board
 * @author Jeneerthan Pageerathan 101153531
 * @author Vishesh Dasani 101162185
 */
public class Utility extends Property {

    /**
     * The constructor of the Utility class
     * @param name, the name of the utility
     * @param price, the price of the utility
     * @param rent, the rent of the utility
     */
    public Utility(String name, double price, double rent) {
        super(name, price, rent, "White");
    }

    /**
     * A method to get the rent of a utility
     * @param d1, the first die
     * @param d2, the second die
     * @param owner the owner of the utility
     * @return the rent to pay
     */
    public double getRent(Dice d1, Dice d2, Player owner) {
        int totalRoll = d1.getValue() + d2.getValue();
        if (owner.checkMonopoly("White") == 2) {
            return totalRoll * 10;
        }
        return totalRoll * 4;
    }

    /**
     * Method to convert string to create a utility object
     * @param param String, the string to be converted
     * @return Utility
     */
    public static Utility toUtility(String param){
        String[] info = param.split("#");
        Utility utility = new Utility(info[0],Double.parseDouble(info[1]) ,Double.parseDouble(info[2]));
        return utility;
    }
}
