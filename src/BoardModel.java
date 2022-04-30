import java.io.*;
import java.util.*;



/**
 * BoardModel class which is the model of the game
 *
 * @author Vishesh Dasani 101162185
 * @author Jeneerthan Pageerathan 101153531
 * @version 3.0
 */
public class BoardModel implements Serializable {
    private List<BoardView> views;
    private List<Player> players;
    public static int numPlayers;
    public Player currentPlayer;
    public Player winner;
    private Map<Integer, Space> spaces;
    private Map<Player, Integer> positions;
    private ParkingSpace parkingSpace;
    private IncomeTax incomeTax;
    private Jail jail;
    private Go go;
    public Dice die1;
    public Dice die2;
    private String selectedColor;

    /**
     * Constructor for the BoardModel Class
     */
    public BoardModel(int numPlayers, int faces, double startingBalance, List<String> names, BoardBuilder bb) {
        this.views = new ArrayList<>();
        this.players = new ArrayList<>();
        this.positions = new HashMap<>();

        die1 = new Dice(faces);
        die2 = new Dice(faces);

        this.numPlayers = numPlayers;
        for (int i = 0; i < numPlayers; i++) {
            this.players.add(new Player(names.get(i), startingBalance));
            positions.put(players.get(i), 0);
        }
        this.currentPlayer = this.players.get(0);

        this.spaces = new HashMap<>();
        this.spaces.putAll(bb.build());
        this.parkingSpace = bb.getPs();
        this.go = bb.getGo();
        this.incomeTax = bb.getIncomeTax();
        this.jail = bb.getJail();
    }

    /**
     * Method to add a view to the game
     *
     * @param view GameFrame, the view
     */
    public void addView(BoardView view) {
        this.views.add(view);
    }

    /**
     * Method to remove a view from the game
     *
     * @param view GameFrame, the view
     */
    public void removeView(BoardView view) {
        this.views.remove(view);
    }

    /**
     * Getter for the views of the game
     * @return views of the game
     */
    public List<BoardView> getViews(){
        return this.views;
    }

    /**
     * Getter for list of players
     *
     * @return list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Getter for list of properties
     *
     * @return list of properties
     */
    public Map<Integer, Space> getSpaces() {
        return spaces;
    }

    /**
     * Getter for list of positions
     *
     * @return list of positions
     */
    public Map<Player, Integer> getPositions() {
        return positions;
    }


    /**
     * Method to change turns of the players
     */
    public void changeTurn() {
        int index = players.indexOf(currentPlayer);
        index = (index + 1) % numPlayers;
        currentPlayer = players.get(index);
        if (currentPlayer instanceof PlayerBot) {
            if (jail.getArrestedPlayers().containsKey(currentPlayer)) {
                if (currentPlayer.getBalance() >= (jail.getFee() * 2)) {
                    payBail();
                }
                else {
                    rollInJail();
                }
            }
            else {
                rollCommand();
            }
        }
    }

    /**
     * Method to change player positions in the game according the amount rolled on the dice
     *
     * @param roll int, the amount rolled
     */
    public void changePlayerPosition(int roll) {
        int newPosition = 0;
        if ((newPosition + roll) > 40) {
            currentPlayer.addBalance(go.passGo());
        }
        newPosition = (positions.get(currentPlayer) + roll) % 40;
        positions.replace(currentPlayer, newPosition);
    }

    /**
     * Behaviour if current position is 'go'
     * @param currentPosition int, current position of the player
     */
    private void positionGo(int currentPosition){
        currentPlayer.addBalance(go.passGo());
        for (BoardView view : views) {
            view.handleRollCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
        }
        for (BoardView view : views) {
            view.handleLandGo(new BoardEvent(this, die1, die2, currentPlayer, currentPosition, spaces.get(currentPosition)));
        }

    }

    /**
     * Behaviour if current position is chance,community chest, parking space, just visiting (do nothing)
     * @param currentPosition int, current position of the player
     */
    private void positionExtraSpace(int currentPosition){
        for (BoardView view : views) {
            view.handleRollCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
        }
        for (BoardView view : views) {
            view.handleExtraSpace(new BoardEvent(this, die1, die2, currentPlayer, currentPosition, spaces.get(currentPosition)));
        }
    }
    /**
     * Behaviour if current position is 'Income Tax'
     * @param currentPosition int, current position of the player
     */
    private void positionIncomeTax(int currentPosition){
        currentPlayer.subtractBalance(incomeTax.payTax());
        for (BoardView view : views) {
            view.handleRollCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
        }
        for (BoardView view : views) {
            view.handleIncomeTax(new BoardEvent(this, die1, die2, currentPlayer, currentPosition, spaces.get(currentPosition)));
        }
    }
    /**
     * Behaviour if current position is 'Free Parking'
     * @param currentPosition int, current position of the player
     */
    private void positionParkingSpace(int currentPosition){
        currentPlayer.addBalance(parkingSpace.getCollectedSum());
        for (BoardView view : views) {
            view.handleRollCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
        }
        for (BoardView view : views) {
            view.handleParkingSpace(new BoardEvent(this, die1, die2, currentPlayer, currentPosition, spaces.get(currentPosition), parkingSpace));
        }
    }
    /**
     * Behaviour if current position is 'GO TO JAIL'
     * @param currentPosition int, current position of the player
     */
    private void positionJail(int currentPosition){
        jail.arrestPlayer(currentPlayer);
        for (BoardView view : views) {
            view.handleRollCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
        }
        for (BoardView view : views) {
            view.handleGoToJail(new BoardEvent(this, die1, die2, currentPlayer, currentPosition, spaces.get(currentPosition)));
        }
        positions.replace(currentPlayer, 10);

    }

    /**
     * Behaviour if current property is owned by someone else
     * @param currentPosition int, current position of the player
     * @param currentProperty Property, current property that the current player is on
     */
    private void propertyOwnedByAnother(int currentPosition, Space currentProperty){
        if (die1.getValue() == die2.getValue()) {
            if (!currentPlayer.propertyOwned(currentProperty) && currentProperty.getOwner() != null) {
                for (BoardView view : views) {
                    view.handlePropertyOwnedByAnother(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
                }
                if (currentPosition == 12 || currentPosition == 28) {
                    Utility temp = new Utility("temp", 0, 0);
                    double rent = temp.getRent(die1, die2, currentPlayer);
                    if (currentPlayer.getBalance() < rent) {
                        currentPlayer.declareBankruptcy(currentProperty.getOwner());
                        this.players.remove(currentPlayer);
                        numPlayers -= 1;
                    } else {
                        currentPlayer.subtractBalance(rent);
                        currentProperty.getOwner().addBalance(rent);
                        if (currentPlayer.getBalance() < 0) {
                            currentPlayer.declareBankruptcy(currentProperty.getOwner());
                            this.players.remove(currentPlayer);
                            numPlayers -= 1;
                        }
                    }
                }
                else if (currentPlayer.getBalance() < currentProperty.getRent()) {
                    currentPlayer.declareBankruptcy(currentProperty.getOwner());
                    this.players.remove(currentPlayer);
                    numPlayers -= 1;
                } else {
                    currentPlayer.payRent(currentProperty);
                }
            }
        }
        else {
            if (!currentPlayer.propertyOwned(currentProperty) && currentProperty.getOwner() != null) {
                for (BoardView view : views) {
                    view.handlePropertyOwnedByAnother(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
                }
                if (currentPosition == 12 || currentPosition == 28) {
                    Utility temp = new Utility("temp", 0, 0);
                    double rent = temp.getRent(die1, die2, currentPlayer);
                    if (currentPlayer.getBalance() < rent) {
                        currentPlayer.declareBankruptcy(currentProperty.getOwner());
                        this.players.remove(currentPlayer);
                        numPlayers -= 1;
                    } else {
                        currentPlayer.subtractBalance(rent);
                        currentProperty.getOwner().addBalance(rent);
                        if (currentPlayer.getBalance() < 0) {
                            currentPlayer.declareBankruptcy(currentProperty.getOwner());
                            this.players.remove(currentPlayer);
                            numPlayers -= 1;
                        }
                        else if (currentPlayer.getBalance() < currentProperty.getRent()) {
                            currentPlayer.declareBankruptcy(currentProperty.getOwner());
                            this.players.remove(currentPlayer);
                            numPlayers -= 1;
                        } else {
                            currentPlayer.payRent(currentProperty);
                        }

                    }
                }
            }
        }
    }

    /**
     * Behaviour if property is owned by self
     * @param currentProperty Property, current property that the current player is on
     */
    private void propertyOwnedBySelf(Space currentProperty){
        if (die1.getValue() == die2.getValue()) {
            if (currentPlayer.propertyOwned(currentProperty)) {
                for (BoardView view : views) {
                    view.handlePropertyOwnedBySelf(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
                }
            }
        }
        else{
            if (currentPlayer.propertyOwned(currentProperty)) {
                for (BoardView view : views) {
                    view.handlePropertyOwnedBySelf(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
                }
            }
        }
    }

    /**
     * Behavious if property is not owned by anyone
     * @param currentProperty Property, current property that the current player is on
     */
    private void propertyUnowned(Space currentProperty){
        if (die1.getValue() == die2.getValue()) {
            if (currentProperty.getOwner() == null) {
                for (BoardView view : views) {
                    view.handlePropertyUnowned(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
                }
            }
        }
        else{
            if (currentProperty.getOwner() == null) {
                for (BoardView view : views) {
                    view.handlePropertyUnowned(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
                }
            }
        }
    }

    /**
     * Method for functionality of the roll button
     */
    public void rollCommand() {
        currentPlayer.rollDice(die1, die2);
        changePlayerPosition(die1.getValue() + die2.getValue());
        int currentPosition = positions.get(currentPlayer);

        if (currentPlayer instanceof PlayerBot) {
            Space space = spaces.get(currentPosition);
            Boolean buyable = space instanceof Estate || space instanceof Railroad || space instanceof Utility;
            if (buyable && space.getOwner() == null && space.getPrice() < currentPlayer.getBalance()) {
                buyCommand();
            }
        }

        switch(currentPosition){
            case 0:
                positionGo(currentPosition);
                break;
            case 4:
            case 38:
                positionIncomeTax(currentPosition);
                break;
            case 20:
                positionParkingSpace(currentPosition);
                break;
            case 30:
                positionJail(currentPosition);
                break;
            case 2:
            case 7:
            case 10:
            case 17:
            case 22:
            case 33:
            case 36:
                positionExtraSpace(currentPosition);
                break;
            default:
                for (BoardView view : views) {
                    view.handleRollCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
                }

                Space currentProperty = spaces.get(currentPosition);
                propertyOwnedByAnother(currentPosition,currentProperty);
                propertyOwnedBySelf(currentProperty);
                propertyUnowned(currentProperty);

        }

    }

    /**
     * Method for functionality of the quit button
     */
    public void quitCommand() {
        currentPlayer.quit();
        changeTurn();
        if (players.indexOf(currentPlayer) == 0) {
            this.players.remove(players.size() - 1);
        } else {
            this.players.remove(players.indexOf(currentPlayer) - 1);
        }
        numPlayers -= 1;
        if (players.size() == 1) {
            for (BoardView view : views) {
                view.handleQuitCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
            }
        } else {
            for (BoardView view : views) {
                view.handleQuitCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
            }
        }
    }

    /**
     * Method for functionality of the pass button
     */
    public void passCommand() {
        changeTurn();
        if(currentPlayer.getSets().size() > 0) {
            for (BoardView view : views) {
                view.handleSetContained(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
            }
        }

        if (jail.getArrestedPlayers().containsKey(currentPlayer)) {
            for (BoardView view : views) {
                view.handlePlayerInJail(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
            }
        }
        for (BoardView view : views) {
            view.handlePassCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
        }
    }

    /**
     * Method for functionality of the buy property button
     */
    public void buyCommand() {
        int currentPosition = positions.get(currentPlayer);
        Space currentProperty = spaces.get(currentPosition);
        if (currentPlayer.getBalance() < currentProperty.getPrice()) {
            for (BoardView view : views) {
                view.handleBuyCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
            }
        } else {
            currentPlayer.buyProperty(currentProperty);
            for (BoardView view : views) {
                view.handleBuyCommand(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
            }
        }
        if (currentPlayer instanceof PlayerBot) {
            passCommand();
        }
        return;
    }

    /**
     * Method for functionality of the declare winner button
     */
    public void declareWinner() {
        List<Double> netWorths = new ArrayList<>();
        for (Player p : this.players) {
            netWorths.add(p.getNetWorth());
        }
        double highestNW = Collections.max(netWorths);
        winner = getPlayerFromNW(highestNW);
        for (BoardView view : views) {
            view.handleWinnerDeclaration(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
        }
    }


    /**
     * Helper method to get the player who has the given net worth
     *
     * @param netWorth double, the given net worth
     * @return the player with the given net worth
     */
    private Player getPlayerFromNW(double netWorth) {
        Player highNW = new Player("", 0);
        for (Player p : this.players) {
            if (p.getNetWorth() == netWorth) {
                highNW = p;
                break;
            }
        }
        return highNW;
    }

    /**
     * Method for functionality of the Pay Bail button
     */
    public void payBail() {
        currentPlayer.subtractBalance(jail.getFee());
        jail.freePlayer(currentPlayer);
        for (BoardView view : views) {
            view.handlePlayerPaidBail(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer))));
        }
        if (currentPlayer instanceof PlayerBot) {
            rollCommand();
        }
    }

    /**
     * Method for functionality of the Attempt to Roll double button
     */
    public void rollInJail() {
        if (jail.checkAttempts(currentPlayer)) {
            payBail();
            for (BoardView view : views) {
                view.handlePlayerAttemptedRoll(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer)), jail));
            }
        } else {
            if (jail.rollForDoubles(die1, die2, currentPlayer)) {
                jail.freePlayer(currentPlayer);
                for (BoardView view : views) {
                    view.handlePlayerAttemptedRoll(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer)), jail));
                }
                if (currentPlayer instanceof PlayerBot) {
                    rollCommand();
                }
            } else {
                for (BoardView view : views) {
                    view.handlePlayerAttemptedRoll(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer)), jail));
                }
                if (currentPlayer instanceof PlayerBot) {
                    passCommand();
                }
            }
        }
    }

    /**
     * Behaviour when the person selects the color set to which house is to be added
     * @param color String, chosen color
     */
    public void addHouseToColor(String color){
        selectedColor = color;
        for (BoardView view : views) {
            view.handleSelectedColor(new BoardEvent(this, die1, die2, currentPlayer, positions.get(currentPlayer), spaces.get(positions.get(currentPlayer)),color));
        }
    }

    /**
     * Method for functionality of the First Property to add the house button
     */
    public void addHouseToFirstP() {
        currentPlayer.getPropertyByColor(selectedColor).get(0).addHouse();
    }

    /**
     * Method for functionality of the Second Property to add the house button
     */
    public void addHouseToSecondP() {
        currentPlayer.getPropertyByColor(selectedColor).get(1).addHouse();
    }

    /**
     * Method for functionality of the Third Property to add the house button
     */
    public void addHouseToThirdP() {
        currentPlayer.getPropertyByColor(selectedColor).get(2).addHouse();
    }

    /**
     * Method to add AI to the game
     * @param i int, number of bots
     * @param startBalance double, starting balance of the bots
     */
    public void addBot(int i, double startBalance) {
        PlayerBot bot = new PlayerBot(i, startBalance);
        players.add(bot);
        positions.put(bot, 0);
        numPlayers++;
    }

    /**
     * Method to save the game at its current state
     * @param o Object, the game object to be saved (in this case BoardModel)
     * @throws IOException
     */
    public static void saveGame(Object o) throws IOException {
        try(ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(new File("myFile.txt")))) {
            out.writeObject(o);
        }
    }

    /**
     * Method to load the game from a saved text file
     * @return BoardModel, game object created from the file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static BoardModel loadGame() throws IOException, ClassNotFoundException{
        FileInputStream fileInputStream = new FileInputStream("myFile.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        BoardModel model =  (BoardModel) objectInputStream.readObject();
        return model;

    }

}
