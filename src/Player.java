import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Player class represents a player of the monopoly game
 * @author Jeneerthan Pageerathan 101153531
 */
public class Player implements Serializable {
    private String name;
    private double balance;
    private List<Space> propertiesOwned;

    /**
     * Constructor for Player object
     *
     * @param name String, the name of the player
     */
    public Player(String name, double balance) {
        this.name = name;
        this.balance = balance;
        propertiesOwned = new ArrayList<>();
    }

    /**
     * Method to roll dice
     *
     * @param d1 Dice, first die to roll
     * @param d2 Dice, second die to roll
     */
    public void rollDice(Dice d1, Dice d2) {
        d1.roll();
        d2.roll();
    }

    /**
     * Getter method for the player's name
     *
     * @return String, the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for the player's balance
     *
     * @return double, the balance of the player
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Setter method for the player's balance
     * Intended for starting balances
     *
     * @param balance double, the starting balance of each player
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Method to increase the player's balance
     *
     * @param amount double, the amount to add to the balance
     */
    public void addBalance(double amount) {
        balance += amount;
    }

    /**
     * Method to decrease the player's balance
     *
     * @param amount double, the amount to subtract from the balance
     */
    public void subtractBalance(double amount) {
        balance -= amount;
    }

    /**
     * Method to add property to player's ownership
     *
     * @param p Property, property to be bought
     */
    public void buyProperty(Space p) {
        this.subtractBalance(p.getPrice());
        this.propertiesOwned.add(p);
        p.setOwner(this);
        if (balance < 0) {
            this.quit();
        }
    }

    /**
     * Method to pay rent to a property owner
     *
     * @param p Property, property that the player landed on
     */
    public void payRent(Space p) {
        this.subtractBalance(p.getRent());
        p.getOwner().addBalance(p.getRent());
        if (balance < 0) {
            this.declareBankruptcy(p.getOwner());
        }
    }

    /**
     * Method to transfer properties to another player
     *
     * @param player Player, player who receives properties
     */
    public void declareBankruptcy(Player player) {
        for (Space p : propertiesOwned) {
            p.setOwner(player);
        }
    }

    /**
     * Method to quit and remove ownership of all owned properties
     */
    public void quit() {
        for (Space p : propertiesOwned) {
            p.setOwner(null);
        }
    }

    /**
     * Method to check if property is owned by this player
     *
     * @param p Property, the property being checked for ownership
     * @return true, if property is owned by this player object
     * @return false, if property is not owned by this player object
     */
    public boolean propertyOwned(Space p) {
        return this.propertiesOwned.contains(p);
    }

    /**
     * Method to provide this player object's info
     * Including name, balance, and properties owned
     *
     * @return String, for the player's information
     */
    public String printInfo() {
        String info = "";
        info += ("Player Info: \n");
        info += (" Name: " + name + "\n");
        info += (" Balance: " + balance + "\n");
        info += (" Properties Owned: \n");
        for (Space p : propertiesOwned) {
            info += (p.getName() + " \n");
        }
        return info;
    }

    /**
     * Method to calculate this player object's net worth
     *
     * @return double, this player's net worth
     */
    public double getNetWorth() {
        double netWorth = this.balance;
        for (Space p : propertiesOwned) {
            netWorth += p.getPrice();
        }
        return netWorth;
    }

    /**
     * Method to check how many of a property type is owned
     *
     * @param type, String for the type of Property to be checked
     * @return int, the number of properties owned of the specified type
     */
    public int checkMonopoly(String type) {
        int owned = 0;
        for (Space p : propertiesOwned) {
            if (p.getColour().equals(type)) {
                owned++;
            }
        }
        return owned;
    }

    public List<String> getSets() {
        List<String> type = new ArrayList<>();
        String[]color = {"Grey","Cyan","Pink","Orange","Red","Yellow","Green","Blue"};
        for(int i=1;i<7;i++){
            if(checkMonopoly(color[i])==3){
                type.add(color[i]);
            }
        }
        if(checkMonopoly(color[0])==2){
            type.add(color[0]);
        }
        if(checkMonopoly(color[7])==2){
            type.add(color[07]);
        }
        return type;
    }

    public ArrayList<Space> getPropertyByColor(String color){
        ArrayList<Space> properties = new ArrayList<>();
        for(Space p: propertiesOwned){
            if(p.getColour().equals(color)){
                properties.add(p);
            }
        }
        return properties;
    }
}
