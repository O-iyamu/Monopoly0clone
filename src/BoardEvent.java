import java.util.EventObject;

/**
 * A class to Instantiate an Event
 */

public class BoardEvent extends EventObject {
    private Dice die1;
    private Dice die2;
    private Player currentPlayer;
    private int currentPlayerPosition;
    private Space currentProperty;
    private Jail jail;
    private ParkingSpace ps;
    private String color;

    /**
     * A constructor for the Board Event
     * @param boardModel, the model
     * @param die1, the first dice
     * @param die2, the second dice
     * @param currentPlayer, the current player
     * @param currentPlayerPosition, the position of the current player
     * @param currentProperty, the current property
     */
    public BoardEvent(BoardModel boardModel, Dice die1, Dice die2, Player currentPlayer, int currentPlayerPosition, Space currentProperty) {
        super(boardModel);
        this.die1 = die1;
        this.die2 = die2;
        this.currentPlayer = currentPlayer;
        this.currentPlayerPosition = currentPlayerPosition;
        this.currentProperty = currentProperty;
    }

    /**
     * A constructor for the Board Event
     * @param boardModel, the model
     * @param die1, the first dice
     * @param die2, the second dice
     * @param currentPlayer, the current player
     * @param currentPlayerPosition, the position of the current player
     * @param currentProperty, the current property
     * @param jail, the jail
     */
    public BoardEvent(BoardModel boardModel, Dice die1, Dice die2, Player currentPlayer, int currentPlayerPosition, Space currentProperty, Jail jail) {
        this(boardModel,die1,die2,currentPlayer,currentPlayerPosition,currentProperty);
        this.jail = jail;
    }

    /**
     * A constructor for the Board Event
     * @param boardModel, the model
     * @param die1, the first dice
     * @param die2, the second dice
     * @param currentPlayer, the current player
     * @param currentPlayerPosition, the position of the current player
     * @param currentProperty, the current property
     * @param ps, the Parking Space
     */
    public BoardEvent(BoardModel boardModel, Dice die1, Dice die2, Player currentPlayer, int currentPlayerPosition, Space currentProperty, ParkingSpace ps) {
        this(boardModel,die1,die2,currentPlayer,currentPlayerPosition,currentProperty);
        this.ps = ps;
    }

    public BoardEvent(BoardModel boardModel, Dice die1, Dice die2, Player currentPlayer, int currentPlayerPosition, Space currentProperty, String color) {
        this(boardModel,die1,die2,currentPlayer,currentPlayerPosition,currentProperty);
        this.color = color;
    }

    /**
     * Getter method for first die
     * @return Die
     */
    public Dice getDie1() {
        return die1;
    }

    /**
     * Getter method for second die
     * @return Die
     */
    public Dice getDie2() {
        return die2;
    }

    /**
     * Getter method for the current player
     * @return Player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Getter method for position of the current player
     * @return int
     */
    public int getCurrentPlayerPosition() {
        return currentPlayerPosition;
    }

    /**
     * Getter method for current property
     * @return Space
     */
    public Space getCurrentProperty() {
        return currentProperty;
    }

    /**
     * Getter method for the jail
     * @return Jail
     */
    public Jail getJail() {
        return jail;
    }

    /**
     * Getter method for parking space
     * @return ParkingSpace
     */
    public ParkingSpace getPs() {
        return ps;
    }

    /**
     * Getter for the color of the sets
     * @return color
     */
    public String getColor() {
        return color;
    }
}
