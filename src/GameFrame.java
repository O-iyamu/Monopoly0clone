import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The GameFrame class used to represent the frame of the Monopoly Board Game
 *
 * @author Christina Dang 101141609
 * @author Jeneerthan Pageerathan 101153531
 * @author Vishesh Dasani 101162185
 * @version 2.0
 */

public class GameFrame extends JFrame implements BoardView, Serializable {
    private BoardModel boardModel;
    private BoardController controller;
    private ArrayList<JButton> actions;
    private JPanel actionPanel;
    private JPanel infoPanel;
    private JPanel centerPanel;
    private JButton rollButton, buyButton, quitButton, passButton, declareButton, payFeeButton, rollInJailButton,saveButton;
    private JPanel topProperties, leftProperties, rightProperties, bottomProperties;
    private JLabel playerInfoLabel, diceLabel, ownPropertyLabel, landedLabel;
    private JButton[]colorButtons = new JButton[10];
    JRadioButton firstProperty,secondProperty,thirdProperty;
    private Map<Integer, Space> properties;
    List<JPanel> subPanels = new ArrayList();
    public JButton player1, player2, player3, player4, player5, player6;

    /**
     *The default constructor of the GameFrame class
     */
    public GameFrame() throws IOException, ClassNotFoundException, ParseException {
        super("Monopoly");
        actions = new ArrayList<>();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500, 1500);

        int loadResult = JOptionPane.showConfirmDialog(this, "Would you like to " +
                "load a previously saved game?");
        switch (loadResult) {
            case JOptionPane.YES_OPTION:
                boardModel = BoardModel.loadGame();
                boardModel.addView(this);
                controller = new BoardController(boardModel);
                break;
            case JOptionPane.NO_OPTION:
                String[] playerOptions = {"2", "3", "4", "5", "6"};
                int playerNum = (JOptionPane.showOptionDialog(this, "How many players are playing?",
                        "Monopoly Set Up", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        playerOptions, playerOptions[0])) + 2;

                String[] botOptions = {"0", "1", "2", "3", "4"};
                int botNum = (JOptionPane.showOptionDialog(this, "How many robot players are playing?",
                        "Monopoly Set Up", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        botOptions, botOptions[0]));

                String[] diceOptions = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                int diceNum = (JOptionPane.showOptionDialog(this, "How many faces on your dice?",
                        "Monopoly Set Up", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        diceOptions, diceOptions[0])) + 2;

                String[] balanceOptions = {"500", "1000", "1500", "2000", "2500", "3000", "3500", "4000", "4500", "5000"};
                int balanceIndex = (JOptionPane.showOptionDialog(this, "What is your starting balance?",
                        "Monopoly Set Up", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        balanceOptions, balanceOptions[0]));
                double startingBalance = Double.parseDouble(balanceOptions[balanceIndex]);


                ArrayList<String> playerNames = new ArrayList<>();
                for (int i = 0; i < playerNum; i++) {
                    int pos = i + 1;
                    String pName = JOptionPane.showInputDialog("Enter name for Player " + pos + ":");
                    playerNames.add(pName);
                }

                BoardBuilder bb = new BoardBuilder();

                int boardChoice = JOptionPane.showConfirmDialog(this, "Would you like to " +
                        "customize the board?");
                switch(boardChoice){
                    case JOptionPane.YES_OPTION:
                        bb = parseJSON();
                        break;
                    case JOptionPane.NO_OPTION:
                        bb = classicBoardSetUp();
                        break;
                    default:
                        System.exit(0);
                        break;
                }

                boardModel = new BoardModel(playerNum, diceNum, startingBalance, playerNames, bb);
                boardModel.addView(this);
                controller = new BoardController(boardModel);

                for (int i = 0; i < botNum; i++) {
                    boardModel.addBot(i+1, startingBalance);
                }
                break;
            default:
                System.exit(0);
                break;
        }



        actionPanel = new JPanel();
        infoPanel = new JPanel();
        centerPanel = new JPanel();

        createBoardPanels();
        initialize();
        updateInfoPanel();
        this.setVisible(true);
    }



    /**
     * Method to set button actions
     */
    public void setActions() {
        rollButton = new JButton("Roll");
        rollButton.setActionCommand("roll");
        buyButton = new JButton("Buy");
        buyButton.setActionCommand("buy");
        quitButton = new JButton("Quit");
        quitButton.setActionCommand("quit");
        passButton = new JButton("Pass Turn");
        passButton.setActionCommand("pass");
        declareButton = new JButton("Declare Winner");
        declareButton.setActionCommand("declare");
        payFeeButton = new JButton("Pay Bail Fee of $50");
        payFeeButton.setActionCommand("payF");
        rollInJailButton = new JButton("Attempt Rolling Double");
        rollInJailButton.setActionCommand("rollJ");
        saveButton = new JButton("Save Game");
        for(int i=0;i<8;i++){
            colorButtons[i] = new JButton();
            colorButtons[i].setText(colorButtons[i].toString());
        }
        firstProperty = new JRadioButton();
        secondProperty = new JRadioButton();
        thirdProperty = new JRadioButton();

        rollButton.addActionListener(controller);
        buyButton.addActionListener(controller);
        quitButton.addActionListener(controller);
        passButton.addActionListener(controller);
        declareButton.addActionListener(controller);
        payFeeButton.addActionListener(controller);
        rollInJailButton.addActionListener(controller);
        saveButton.addActionListener(e-> {
            try {
                BoardModel.saveGame(boardModel);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        for(int i=0;i<8;i++){
            String color = colorButtons[i].toString();
            colorButtons[i].addActionListener(e->boardModel.addHouseToColor(color));
        }
        firstProperty.addActionListener(e->boardModel.addHouseToFirstP());
        secondProperty.addActionListener(e->boardModel.addHouseToSecondP());
        thirdProperty.addActionListener(e->boardModel.addHouseToThirdP());

        actions.add(rollButton);
        actions.add(buyButton);
        actions.add(quitButton);
        actions.add(passButton);
        actions.add(declareButton);
        actions.add(payFeeButton);
        actions.add(rollInJailButton);
        actions.add(saveButton);
        for(int i=0;i<8;i++){
            actions.add(colorButtons[i]);
        }

        actionPanel.add(rollButton);
        rollButton.setEnabled(true);
        actionPanel.add(buyButton);
        buyButton.setEnabled(false);
        actionPanel.add(quitButton);
        quitButton.setEnabled(true);
        actionPanel.add(passButton);
        passButton.setEnabled(false);
        actionPanel.add(declareButton);
        declareButton.setEnabled(false);
        actionPanel.add(payFeeButton);
        payFeeButton.setEnabled(false);
        actionPanel.add(rollInJailButton);
        rollInJailButton.setEnabled(false);
        actionPanel.add(saveButton);
        saveButton.setEnabled(true);
        for(int i=0;i<8;i++){
            actionPanel.add(colorButtons[i]);
            colorButtons[i].setVisible(false);
        }
        actionPanel.add(firstProperty);
        firstProperty.setVisible(false);
        secondProperty.setVisible(false);
        thirdProperty.setVisible(false);
        actionPanel.add(secondProperty);
        actionPanel.add(thirdProperty);
    }

    /**
     * A method to create the info spaces
     */
    public void createInfoSpace() {
        playerInfoLabel = new JLabel();
        diceLabel = new JLabel();
        ownPropertyLabel = new JLabel();
        landedLabel = new JLabel();
        infoPanel.setLayout(new GridLayout(4, 1));
        infoPanel.add(playerInfoLabel);
        infoPanel.add(diceLabel);
        infoPanel.add(ownPropertyLabel);
        infoPanel.add(landedLabel);
    }

    /**
     * Method to initialize panels and group them
     */
    public void createBoardPanels() {


        for (int i = 0; i < 40; i++) {
            Space s = boardModel.getSpaces().get(i);
            JPanel jPanel = new JPanel();
            if (s.getColour() == null || s.getColour().equals("")) {
                createSubPanels(jPanel, i, s.getColour(), false, s.getName());
            }
            else {
                createSubPanels(jPanel, i, s.getColour(), true, s.getName());
            }
            subPanels.add(jPanel);
        }
        topProperties = new JPanel(new GridLayout(1, 11));
        for (int i = 20; i < 31; i++) {
            topProperties.add(subPanels.get(i));
        }

        leftProperties = new JPanel(new GridLayout(9, 1));
        for (int i = 19; i >= 11; i--) {
            leftProperties.add(subPanels.get(i));
        }

        bottomProperties = new JPanel(new GridLayout(1, 11));
        for (int i = 10; i >= 0; i--) {
            bottomProperties.add(subPanels.get(i));
        }

        rightProperties = new JPanel(new GridLayout(9, 1));
        for (int i = 31; i < 40; i++) {
            rightProperties.add(subPanels.get(i));
        }
    }

    /**
     * Method to create the sub panels for the properties and things on the board
     */
    public void createSubPanels(JPanel panel, int propNum, String col, boolean property, String placeName) {

        if (property) {
            properties = boardModel.getSpaces();
            panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            panel.setLayout(new BorderLayout());
            panel.add(new JLabel(properties.get(propNum).getName()), BorderLayout.NORTH);
            panel.add(new JLabel("Price: " + properties.get(propNum).getPrice()), BorderLayout.CENTER);
            panel.add(new JLabel("Rent: " + properties.get(propNum).getRent()), BorderLayout.SOUTH);

        } else {
            panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            panel.add(new JLabel(placeName));
        }
        if (col == "White") {
            panel.setBackground(Color.white);
        }
        if (col == "Black") {
            panel.setBackground(Color.black);
        }
        if (col == "Gray") {
            panel.setBackground(Color.gray);
        }
        if (col == "Blue") {
            panel.setBackground(Color.blue);
        }
        if (col == "Pink") {
            panel.setBackground(Color.pink);
        }
        if (col == "Yellow") {
            panel.setBackground(Color.yellow);
        }
        if (col == "Red") {
            panel.setBackground(Color.red);
        }
        if (col == "Orange") {
            panel.setBackground(Color.orange);
        }
        if (col == "Green") {
            panel.setBackground(Color.green);
        }
        if (col == "Cyan") {
            panel.setBackground(Color.cyan);
        }
    }

    /**
     * Method to move player icons
     */

    public void changePlayer(int newPos,int oldPos, int playerNum){
        int i = playerNum+1;

        if (i==1){
            if(properties.get(oldPos).getPrice()==0 && oldPos != 0){
                subPanels.get(oldPos).remove(1);
            }
            subPanels.get(oldPos).remove(player1);
            subPanels.get(newPos).add(player1, BorderLayout.EAST);
        }
        if (i==2){
            if(properties.get(oldPos).getPrice()==0 && oldPos != 0){
                subPanels.get(oldPos).remove(1);
            }
            subPanels.get(oldPos).remove(player2);
            subPanels.get(newPos).add(player2, BorderLayout.EAST);
        }
        if (i==3){
            if(properties.get(oldPos).getPrice()==0 && oldPos != 0){
                subPanels.get(oldPos).remove(1);
            }
            subPanels.get(oldPos).remove(player3);
            subPanels.get(newPos).add(player3, BorderLayout.EAST);
        }
        if (i==4){
            if(properties.get(oldPos).getPrice()==0 && oldPos != 0){
                subPanels.get(oldPos).remove(1);
            }
            subPanels.get(oldPos).remove(player4);
            subPanels.get(newPos).add(player4, BorderLayout.EAST);
        }
        if (i==5){
            if(properties.get(oldPos).getPrice()==0 && oldPos != 0){
                subPanels.get(oldPos).remove(1);
            }
            subPanels.get(oldPos).remove(player5);
            subPanels.get(newPos).add(player5, BorderLayout.EAST);
        }
        if (i==6){
            if(properties.get(oldPos).getPrice()==0 && oldPos != 0){
                subPanels.get(oldPos).remove(1);
            }
            subPanels.get(oldPos).remove(player6);
            subPanels.get(newPos).add(player6, BorderLayout.EAST);
        }

    }


    /**
     * Method to update the player information panel panel
     */
    public void updateInfoPanel() {
        Space currentProperty = boardModel.getSpaces().get(boardModel.getPositions().get(boardModel.currentPlayer));
        playerInfoLabel.setText(boardModel.currentPlayer.printInfo());
        int totalRoll = boardModel.die1.getValue() + boardModel.die2.getValue();
        diceLabel.setText("Dice 1:" + boardModel.die1.getValue() + " Dice 2:" + boardModel.die2.getValue() + " Total Roll:" + totalRoll);
        ownPropertyLabel.setText("You own this property");
        landedLabel.setText("You landed on " + currentProperty.getName() + " Price: " + currentProperty.getPrice() + " Rent: " + currentProperty.getRent());

        playerInfoLabel.setEnabled(true);
        diceLabel.setVisible(false);
        ownPropertyLabel.setVisible(false);
        landedLabel.setVisible(false);
    }

    /**
     * Method to set the board layout and panels
     */
    public void initialize() {
        centerPanel.setLayout(new BorderLayout());
        actionPanel.setLayout(new FlowLayout());
        infoPanel.setLayout(new GridBagLayout());

        setActions();
        createInfoSpace();

        this.setLayout(new BorderLayout());
        this.add(topProperties, BorderLayout.PAGE_START);
        this.add(leftProperties, BorderLayout.LINE_START);
        this.add(rightProperties, BorderLayout.LINE_END);
        this.add(bottomProperties, BorderLayout.PAGE_END);
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(actionPanel, BorderLayout.NORTH);
        centerPanel.add(infoPanel, BorderLayout.SOUTH);

        //player icons on go
        player1 = new JButton();
        player2 = new JButton();
        player3 = new JButton();
        player4 = new JButton();
        player5 = new JButton();
        player6 = new JButton();

        for(int i=1; i<=boardModel.getPlayers().size();i++){
            if (i==1){
                player1.setBackground(Color.red);
                subPanels.get(0).add(player1, BorderLayout.EAST);
            }
            if (i==2){
                player2.setBackground(Color.orange);
                subPanels.get(0).add(player2, BorderLayout.EAST);
            }
            if (i==3){
                player3.setBackground(Color.yellow);
                subPanels.get(0).add(player3, BorderLayout.EAST);
            }
            if (i==4){
                player4.setBackground(Color.green);
                subPanels.get(0).add(player4, BorderLayout.EAST);
            }
            if (i==5){
                player5.setBackground(Color.cyan);
                subPanels.get(0).add(player5, BorderLayout.EAST);
            }
            if (i==6){
                player6.setBackground(Color.pink);
                subPanels.get(0).add(player6, BorderLayout.EAST);
            }
        }

        updateInfoPanel();
    }

    /**
     * A method to handle the roll command
     * @param e, the event to be handled
     */
    @Override
    public void handleRollCommand(BoardEvent e) {

        int die1Value = e.getDie1().getValue();
        int die2Value = e.getDie2().getValue();

        int newPosition = e.getCurrentPlayerPosition();
        int oldPosition = (e.getCurrentPlayerPosition() - (die1Value + die2Value));
        Player currentPlayer = e.getCurrentPlayer();
        changePlayer(newPosition, oldPosition, boardModel.getPlayers().indexOf(currentPlayer));

        updateInfoPanel();
        diceLabel.setVisible(true);
        landedLabel.setVisible(true);
        if (die1Value == die2Value) {
            JFrame popupFrame = new JFrame();
            popupFrame.setSize(200, 200);
            JOptionPane.showMessageDialog(popupFrame, "You rolled a double!Please roll again before passing your turn!");
        }

    }

    /**
     * A method to handle property owned by another player
     * @param e, the event to be handled
     */
    @Override
    public void handlePropertyOwnedByAnother(BoardEvent e) {
        int die1Value = e.getDie1().getValue();
        int die2Value = e.getDie2().getValue();
        Player currentPlayer = e.getCurrentPlayer();
        int currentPosition = e.getCurrentPlayerPosition();
        Space currentProperty = e.getCurrentProperty();
        if (die1Value == die2Value) {
            if (currentPosition == 12 || currentPosition == 28) {
                Utility temp = new Utility("temp", 0, 0);
                double rent = temp.getRent(e.getDie1(), e.getDie2(), currentPlayer);
                if (currentPlayer.getBalance() < rent) {
                    JFrame popupFrame = new JFrame();
                    popupFrame.setSize(200, 200);
                    JOptionPane.showMessageDialog(popupFrame, "You went bankrupt lol!");
                    updateInfoPanel();
                    return;
                } else {
                    JFrame popupFrame = new JFrame();
                    popupFrame.setSize(200, 200);
                    JOptionPane.showMessageDialog(popupFrame, "You paid $" + rent + " as rent to " + currentProperty.getOwner().getName());
                    updateInfoPanel();
                    return;
                }
            } else if (currentPlayer.getBalance() < currentProperty.getRent()) {
                JFrame popupFrame = new JFrame();
                popupFrame.setSize(200, 200);
                JOptionPane.showMessageDialog(popupFrame, "You went bankrupt lol!");
                updateInfoPanel();
                return;
            } else {
                JFrame popupFrame = new JFrame();
                popupFrame.setSize(200, 200);
                JOptionPane.showMessageDialog(popupFrame, "You paid $" + currentProperty.getRent() + " as rent to " + currentProperty.getOwner().getName());
                rollButton.setEnabled(true);
                quitButton.setEnabled(true);
                buyButton.setEnabled(false);
                passButton.setEnabled(false);
                declareButton.setEnabled(false);
                return;
            }
        } else {
            if (currentPlayer.getBalance() < currentProperty.getRent()) {
                JFrame popupFrame = new JFrame();
                popupFrame.setSize(200, 200);
                JOptionPane.showMessageDialog(popupFrame, "You went bankrupt lol!");
                updateInfoPanel();
                return;
            } else {
                JFrame popupFrame = new JFrame();
                popupFrame.setSize(200, 200);
                JOptionPane.showMessageDialog(popupFrame, "You paid $" + currentProperty.getRent() + " as rent to " + currentProperty.getOwner().getName());
                rollButton.setEnabled(false);
                quitButton.setEnabled(true);
                buyButton.setEnabled(false);
                passButton.setEnabled(true);
                declareButton.setEnabled(false);
                return;
            }
        }
    }

    /**
     * A method to handle property owned by current player
     * @param e, the event to be handled
     */
    @Override
    public void handlePropertyOwnedBySelf(BoardEvent e) {
        int die1Value = e.getDie1().getValue();
        int die2Value = e.getDie2().getValue();
        ownPropertyLabel.setVisible(true);
        if (die1Value == die2Value) {
            rollButton.setEnabled(true);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(false);
        } else {
            rollButton.setEnabled(false);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(true);
        }
        declareButton.setEnabled(false);
        return;
    }

    /**
     * A method to handle property unowned
     * @param e, the event to be handled
     */
    @Override
    public void handlePropertyUnowned(BoardEvent e) {
        int die1Value = e.getDie1().getValue();
        int die2Value = e.getDie2().getValue();
        if (die1Value == die2Value) {
            rollButton.setEnabled(true);
            quitButton.setEnabled(true);
            buyButton.setEnabled(true);
            passButton.setEnabled(false);
        } else {
            rollButton.setEnabled(false);
            quitButton.setEnabled(true);
            buyButton.setEnabled(true);
            passButton.setEnabled(true);
        }
        declareButton.setEnabled(false);
        return;
    }

    /**
     * A method to handle the quit command
     * @param e, the event to be handled
     */
    @Override
    public void handleQuitCommand(BoardEvent e) {
        BoardModel boardModel = (BoardModel) e.getSource();
        JFrame popupFrame = new JFrame();
        popupFrame.setSize(200, 200);
        if (boardModel.numPlayers == 1) {
            JOptionPane.showMessageDialog(popupFrame, "1 player remaining, game ended");
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(popupFrame, "You quit the game! LOSER");
            updateInfoPanel();
            return;
        }
    }

    /**
     * A method to handle the pass command
     * @param e, the event to be handled
     */
    @Override
    public void handlePassCommand(BoardEvent e) {
        BoardModel boardModel = (BoardModel) e.getSource();
        Player currentPlayer = e.getCurrentPlayer();
        if (boardModel.getPlayers().indexOf(currentPlayer) == (boardModel.numPlayers - 1)) {
            updateInfoPanel();
            rollButton.setEnabled(true);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(false);
            declareButton.setEnabled(true);
            return;
        } else {
            updateInfoPanel();
            rollButton.setEnabled(true);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(false);
            declareButton.setEnabled(false);
            return;
        }
    }

    /**
     * A method to handle the buy command
     * @param e, the event to be handled
     */
    @Override
    public void handleBuyCommand(BoardEvent e) {
        Player currentPlayer = e.getCurrentPlayer();
        Space currentProperty = e.getCurrentProperty();
        if (currentPlayer.getBalance() < currentProperty.getPrice()) {
            JFrame popupFrame = new JFrame();
            popupFrame.setSize(200, 200);
            JOptionPane.showMessageDialog(popupFrame, "LMAO TOO BROKE BUDDY");
        } else {
            updateInfoPanel();
        }
        buyButton.setEnabled(false);
        return;
    }

    /**
     * A method to handle the declaration of a winner
     * @param e, the event to be handled
     */
    @Override
    public void handleWinnerDeclaration(BoardEvent e) {
        BoardModel boardModel = (BoardModel) e.getSource();
        JFrame popupFrame = new JFrame();
        popupFrame.setSize(200, 200);
        JOptionPane.showMessageDialog(popupFrame, boardModel.winner.getName() + " has won the game! Game ended");
        System.exit(0);
    }

    /**
     * A method to handle the event of a player landing on go
     * @param e, the event to be handled
     */
    @Override
    public void handleLandGo(BoardEvent e) {
        JFrame popupFrame = new JFrame();
        popupFrame.setSize(200, 200);
        JOptionPane.showMessageDialog(popupFrame, "Landed on GO. Nothing to do here, collect $200 as salary!");
        updateInfoPanel();
    }

    /**
     * A method to handle the extra space like chance, community chest, free parking, just visiting.
     * @param e, the event to be handled
     */
    @Override
    public void handleExtraSpace(BoardEvent e) {
        int die1Value = e.getDie1().getValue();
        int die2Value = e.getDie2().getValue();
        JFrame popupFrame = new JFrame();
        popupFrame.setSize(200, 200);
        JOptionPane.showMessageDialog(popupFrame, "Nothing to do here!");
        updateInfoPanel();
        if (die1Value == die2Value) {
            rollButton.setEnabled(true);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(false);
        } else {
            rollButton.setEnabled(false);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(true);
        }
    }

    /**
     * A method to handle income tax
     * @param e, the event to be handled
     */
    @Override
    public void handleIncomeTax(BoardEvent e) {
        int die1Value = e.getDie1().getValue();
        int die2Value = e.getDie2().getValue();
        JFrame popupFrame = new JFrame();
        popupFrame.setSize(200, 200);
        JOptionPane.showMessageDialog(popupFrame, "You landed on 'INCOME TAX'. Pay $200.");
        updateInfoPanel();
        if (die1Value == die2Value) {
            rollButton.setEnabled(true);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(false);
        } else {
            rollButton.setEnabled(false);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(true);
        }
    }

    /**
     * A method to handle go to jail
     * @param e, the event to be handled
     */
    @Override
    public void handleGoToJail(BoardEvent e) {
        JFrame popupFrame = new JFrame();
        popupFrame.setSize(200, 200);
        JOptionPane.showMessageDialog(popupFrame, "You landed on 'GO TO JAIL'. You have been arrested!");
        updateInfoPanel();
        rollButton.setEnabled(false);
        quitButton.setEnabled(true);
        buyButton.setEnabled(false);
        passButton.setEnabled(true);
    }

    /**
     * A method to handle PLayer in jail
     * @param e, the event to be handled
     */
    @Override
    public void handlePlayerInJail(BoardEvent e) {
        JFrame popupFrame = new JFrame();
        popupFrame.setSize(200, 200);
        JOptionPane.showMessageDialog(popupFrame, "You are in jail, please choose to pay the bail fee or attempt " +
                "to roll a double. You have 3 attempts");
        updateInfoPanel();
        rollButton.setEnabled(false);
        quitButton.setEnabled(true);
        buyButton.setEnabled(false);
        passButton.setEnabled(false);
        payFeeButton.setEnabled(true);
        rollInJailButton.setEnabled(true);

    }

    /**
     * A method to handle the Player Paying Bail
     * @param e, the event to be handled
     */
    @Override
    public void handlePlayerPaidBail(BoardEvent e) {
        JFrame popupFrame = new JFrame();
        popupFrame.setSize(200, 200);
        JOptionPane.showMessageDialog(popupFrame, "You paid the bail fee of $50. You are now out of jail!");
        updateInfoPanel();
        rollButton.setEnabled(true);
        quitButton.setEnabled(true);
        buyButton.setEnabled(false);
        passButton.setEnabled(false);
        payFeeButton.setEnabled(false);
        rollInJailButton.setEnabled(false);
    }

    /**
     * A method to handle Player attempting to roll in jail
     * @param e, the event to be handled
     */
    @Override
    public void handlePlayerAttemptedRoll(BoardEvent e) {
        Player currentPlayer = e.getCurrentPlayer();
        Jail jail = e.getJail();
        if (jail.checkAttempts(currentPlayer)) {
            JFrame popupFrame = new JFrame();
            popupFrame.setSize(200, 200);
            JOptionPane.showMessageDialog(popupFrame, "You have exceeded roll attempts. You paid the bail fee of $50. You are now out of jail!");
            updateInfoPanel();
            rollButton.setEnabled(true);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(false);
            payFeeButton.setEnabled(false);
            rollInJailButton.setEnabled(false);
        } else {
            if (jail.rollForDoubles(e.getDie1(), e.getDie2(), currentPlayer)) {
                JFrame popupFrame = new JFrame();
                popupFrame.setSize(200, 200);
                JOptionPane.showMessageDialog(popupFrame, "You rolled a double. You are now out of jail!");
                updateInfoPanel();
                rollButton.setEnabled(true);
                quitButton.setEnabled(true);
                buyButton.setEnabled(false);
                passButton.setEnabled(false);
                payFeeButton.setEnabled(false);
                rollInJailButton.setEnabled(false);
            } else {
                JFrame popupFrame = new JFrame();
                popupFrame.setSize(200, 200);
                JOptionPane.showMessageDialog(popupFrame, "You did not roll a double. Try again next time!");
                updateInfoPanel();
                rollButton.setEnabled(false);
                quitButton.setEnabled(true);
                buyButton.setEnabled(false);
                passButton.setEnabled(true);
                payFeeButton.setEnabled(false);
                rollInJailButton.setEnabled(false);
            }
        }

    }

    /**
     * A method to handle parking space
     * @param e, the event to be handled
     */
    @Override
    public void handleParkingSpace(BoardEvent e) {
        int die1Value = e.getDie1().getValue();
        int die2Value = e.getDie2().getValue();
        ParkingSpace parkingSpace = e.getPs();
        JFrame popupFrame = new JFrame();
        popupFrame.setSize(200, 200);
        JOptionPane.showMessageDialog(popupFrame, "You landed on 'FREE PARKING'. Collect " + parkingSpace.getCollectedSum());
        updateInfoPanel();
        if (die1Value == die2Value) {
            rollButton.setEnabled(true);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(false);
        } else {
            rollButton.setEnabled(false);
            quitButton.setEnabled(true);
            buyButton.setEnabled(false);
            passButton.setEnabled(true);
        }
    }

    /**
     * A method to handle behaviour if a set is contained
     * @param e, the event to be handled
     */
    @Override
    public void handleSetContained(BoardEvent e) {
        Player currentPlayer = e.getCurrentPlayer();
        JFrame popupFrame = new JFrame();
        popupFrame.setSize(200, 200);
        String s = "Colors: ";
        for(String color:currentPlayer.getSets()){
            s+=color + ", ";
        }
        JOptionPane.showMessageDialog(popupFrame, "You have sets for the following colors, please click on" +
                "the color button for which you would like to build your house, or continue with your turn!\n"+s);
        updateInfoPanel();
        for(String color:currentPlayer.getSets()){
            for(int i=0;i<8;i++){
                if(colorButtons[i].toString().equals(color)){
                    colorButtons[i].setVisible(true);
                }
            }
        }
    }

    /**
     * Method to handle the behaviour after user  chooses the color set to add houses in
     * @param e the event to be handled
     */
    @Override
    public void handleSelectedColor(BoardEvent e) {
        Player currentPlayer = e.getCurrentPlayer();
        String color = e.getColor();
        ArrayList<Space> properties = currentPlayer.getPropertyByColor(color);
        if(properties.size()==2){
            firstProperty.setText(properties.get(0).getName());
            firstProperty.setVisible(true);
            secondProperty.setText(properties.get(1).getName());
            secondProperty.setVisible(true);
        }
        else if(properties.size()==3){
            firstProperty.setText(properties.get(0).getName());
            firstProperty.setVisible(true);
            secondProperty.setText(properties.get(1).getName());
            secondProperty.setVisible(true);
            thirdProperty.setText(properties.get(2).getName());
            thirdProperty.setVisible(true);
        }
    }

    public BoardBuilder classicBoardSetUp() {
        PropertyBuilder pb = new PropertyBuilder();
        BoardBuilder bb = new BoardBuilder().currency("dollars").go(200).incomeTax(200).jail(50);
        bb.estate(1, pb.name("MD AVENUE").price(60).rent(10).colour("Grey").buildEstate());
        bb.estate(3, pb.name("BALL AVENUE").buildEstate());
        bb.railroad(0, pb.name("UNION STATION").price(150).rent(25).buildRailroad());
        bb.estate(6, pb.name("ORIENT AVENUE").price(100).rent(14).colour("Cyan").buildEstate());
        bb.estate(8, pb.name("MAPLE AVENUE").buildEstate());
        bb.estate(9, pb.name("CONNECT AVENUE").price(120).rent(15).buildEstate());
        bb.estate(11, pb.name("CHARLIE PLACE").price(140).rent(19).colour("Pink").buildEstate());
        bb.utility(0, pb.name("ELECTRIC COMPANY").price(150).buildUtility());
        bb.estate(13, pb.name("STATUE AVENUE").price(140).buildEstate());
        bb.estate(14, pb.name("VIRGO AVENUE").price(160).rent(20).buildEstate());
        bb.railroad(1, pb.name("THOMAS RAILWAY").price(150).rent(25).buildRailroad());
        bb.estate(16, pb.name("JAMIE PLACE").price(180).rent(22).colour("Orange").buildEstate());
        bb.estate(18, pb.name("CHATTANOOGA AVENUE").buildEstate());
        bb.estate(19, pb.name("TORONTO AVENUE").price(200).rent(25).buildEstate());
        bb.estate(21, pb.name("LOUIS AVENUE").price(220).rent(30).colour("Red").buildEstate());
        bb.estate(23, pb.name("ROCKY AVENUE").buildEstate());
        bb.estate(24, pb.name("CHICAGO AVENUE").price(240).rent(32).buildEstate());
        bb.railroad(2, pb.name("PARADISE STATION").price(150).rent(25).buildRailroad());
        bb.estate(26, pb.name("ATLANTIS AVENUE").price(260).rent(37).colour("Yellow").buildEstate());
        bb.estate(27, pb.name("LENTIL AVENUE").buildEstate());
        bb.utility(1, pb.name("WATER WORKS").price(150).buildUtility());
        bb.estate(29, pb.name("MAC GARDENS").price(280).rent(40).buildEstate());
        bb.estate(31, pb.name("OCEAN AVENUE").price(300).rent(50).colour("Green").buildEstate());
        bb.estate(32, pb.name("NORTHERN AVENUE").buildEstate());
        bb.estate(34, pb.name("PHILLY AVENUE").price(320).rent(60).buildEstate());
        bb.railroad(3, pb.name("LEEDS RAILWAY").price(150).rent(25).buildRailroad());
        bb.estate(37, pb.name("GARDEN PLACE").price(350).rent(80).colour("Blue").buildEstate());
        bb.estate(39, pb.name("OCEANVIEW PROMENADE").price(400).rent(100).buildEstate());
       
        return bb;
    }

    /**
     * Method to parse the JSON file.
     * @return boardBuilder, which will be passed as an argument to the boardModel
     * @throws IOException
     * @throws ParseException
     */
    private BoardBuilder parseJSON() throws IOException, ParseException {
        String customFilename = JOptionPane.showInputDialog("Please insert JSON filename");
        Object obj = new JSONParser().parse(new FileReader(customFilename));
        JSONObject jsonObject = (JSONObject) obj;

        String currency = (String)jsonObject.get("currency");
        Double salary = (Double)jsonObject.get("salary");
        Double tax = (Double)jsonObject.get("incomeTax");
        Double bailFee = (Double)jsonObject.get("bailFee");
        Map properties =(Map)jsonObject.get("properties");
        PropertyBuilder propertyBuilder = new PropertyBuilder();
        BoardBuilder boardBuilder = new BoardBuilder().currency(currency).go(salary).incomeTax(tax).jail(bailFee);
        Iterator<Map.Entry> itr = properties.entrySet().iterator();
        String type = (String)jsonObject.get("type");

        while (itr.hasNext()) {
            Map.Entry pair = itr.next();
            if(type.equals("estate")){
                Integer key = (Integer) pair.getKey();
                String estate = (String)pair.getValue();
                Estate e = Estate.toEstate(estate);
                boardBuilder.estate(key,propertyBuilder.name(e.getName()).price(e.getPrice()).rent(e.getRent()).colour(e.getColour()).buildEstate());
            }
            else if(type.equals("railroad")){
                Integer key = (Integer) pair.getKey();
                String railroad = (String)pair.getValue();
                Railroad r = Railroad.toRailroad(railroad);
                boardBuilder.railroad(key,propertyBuilder.name(r.getName()).price(r.getPrice()).rent(r.getRent()).buildRailroad());
            }
            else if(type.equals("utility")){
                Integer key = (Integer) pair.getKey();
                String utility = (String)pair.getValue();
                Utility u = Utility.toUtility(utility);
                boardBuilder.utility(key,propertyBuilder.name(u.getName()).price(u.getPrice()).rent(u.getRent()).buildUtility());
            }
        }
        return boardBuilder;
    }

    /**
     * Main method
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws ParseException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
       new GameFrame();
    }


}
