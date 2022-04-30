import java.util.HashMap;
import java.util.Map;

/**
 * A class to represent the jail space on the board
 * @author Jeneerthan Pageerathan 101153531
 */
public class Jail extends Space {
    private Map<Player, Integer> arrestedPlayers;
    private double fee;

    /**
     * The constructor of the jail class
     * @param fee, the fee to pay for jail
     */
    public Jail(double fee) {
        this.fee = fee;
        arrestedPlayers = new HashMap<>();
    }

    /**
     * A method to return the fee of jail
     * @return double, the fee of the jail
     */
    public double getFee() {
        return fee;
    }

    /**
     * A method to check for rolls of doubles by a specific player
     * @param d1, the first die
     * @param d2, the second die
     * @param player, the player to check
     * @return boolean, if a player rolled a double or not
     */
    public boolean rollForDoubles(Dice d1, Dice d2, Player player) {
        if (!arrestedPlayers.containsKey(player)) { // Should implement equals method in Player
            return false;
        }

        Integer attempts = arrestedPlayers.get(player);
        arrestedPlayers.replace(player, (attempts + 1));
        d1.roll();
        d2.roll();
        return (d1.getValue() == d2.getValue());
    }

    /**
     * A method to release a player from jail
     * @param player, the player to remove
     */
    public void freePlayer(Player player) {
        if (!arrestedPlayers.containsKey(player)) {
            return;
        }
        arrestedPlayers.remove(player);
    }

    /**
     * A method to arrest a player
     * @param player, the player to arrest
     */
    public void arrestPlayer(Player player) {
        arrestedPlayers.put(player, 0);
    }

    /**
     * A method to check if the number of attempts of an arrested player is 3
     * @param player, the player to check
     * @return boolean, if a player has made 3 attempts yet
     */
    public boolean checkAttempts(Player player) {
        if (!arrestedPlayers.containsKey(player)) {
            return false;
        }
        return arrestedPlayers.get(player) == 3;
    }

    /**
     * A method to return the list of arrested players
     * @return Map of arrested players
     */
    public Map<Player,Integer> getArrestedPlayers(){
        return arrestedPlayers;
    }

    @Override
    public String getName() {
        return "Go To Jail";
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
