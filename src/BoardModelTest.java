import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit test class for BoardModel
 * @author Iyamu Osaretinmwen 101157386
 */

class BoardModelTest {
    private BoardModel board;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        List<String> players;
        players = new ArrayList<>();
        players.add("a");
        players.add("b");
        players.add("c");
        players.add("d");
        players.add("e");
        players.add("f");

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

        board = new BoardModel(6, 12, 1500.0, players, bb);
    }

    @org.junit.jupiter.api.Test
    void getPlayers() {
        Player a = new Player("a", 1500.0);
        Player b = new Player("b", 1500.0);
        Player c = new Player("c", 1500.0);
        Player d = new Player("d", 1500.0);
        Player e = new Player("e", 1500.0);
        Player f = new Player("f", 1500.0);
        List<Player> playersCheck = new ArrayList<>();
        playersCheck.add(a);
        playersCheck.add(b);
        playersCheck.add(c);
        playersCheck.add(d);
        playersCheck.add(e);
        playersCheck.add(f);
        for(int i =0;i<playersCheck.size();i++){
            assertEquals(playersCheck.get(i).getName(), board.getPlayers().get(i).getName());
        }
    }

    @Test
    void getSpaces() {
        Map<Integer, Space> propertiesCheck = new HashMap<>();
        ParkingSpace ps = new ParkingSpace();
        IncomeTax incomeTax = new IncomeTax(200, ps);
        propertiesCheck.put(1, new Estate("MD AVENUE", 60, 10,"Grey"));

        propertiesCheck.put(2,new Property("COMMUNITY CHEST",0,0,""));

        propertiesCheck.put(3, new Estate("BALL AVENUE", 60, 10,"Grey"));

        propertiesCheck.put(4, incomeTax);

        propertiesCheck.put(5,new Railroad("UNION STATION",150,25));

        propertiesCheck.put(6, new Estate("ORIENT AVENUE", 100, 14,"Cyan"));

        propertiesCheck.put(7,new Property("CHANCE",0,0,""));

        propertiesCheck.put(8, new Estate("MAPLE AVENUE", 100, 14,"Cyan"));
        propertiesCheck.put(9, new Estate("CONNECT AVENUE", 120, 15,"Cyan"));

        propertiesCheck.put(10,new Property("Just Visiting - Jail",0,0,""));

        propertiesCheck.put(11, new Estate("CHARLIE PLACE", 140, 19,"Pink"));

        propertiesCheck.put(12,new Utility("ELECTRIC COMPANY",150,0));

        propertiesCheck.put(13, new Estate("STATUE AVENUE", 140, 19,"Pink"));
        propertiesCheck.put(14, new Estate("VIRGO AVENUE", 160, 20,"Pink"));

        propertiesCheck.put(15,new Railroad("THOMAS RAILWAY",150,25));

        propertiesCheck.put(16, new Estate("JAMIE PLACE", 180, 22,"Orange"));

        propertiesCheck.put(17,new Property("COMMUNITY CHEST",0,0,""));

        propertiesCheck.put(18, new Estate("CHATTANOOGA AVENUE", 180, 22,"Orange"));
        propertiesCheck.put(19, new Estate("TORONTO AVENUE", 200, 25,"Orange"));

        propertiesCheck.put(20, ps);

        propertiesCheck.put(21, new Estate("LOUIS AVENUE", 220, 30,"Red"));

        propertiesCheck.put(22,new Property("CHANCE",0,0,""));

        propertiesCheck.put(23, new Estate("ROCKY AVENUE", 220, 30,"Red"));
        propertiesCheck.put(24, new Estate("CHICAGO AVENUE", 240, 32,"Red"));

        propertiesCheck.put(25,new Railroad("PARADISE STATION",150,25));

        propertiesCheck.put(26, new Estate("ATLANTIS AVENUE", 260, 37,"Yellow"));
        propertiesCheck.put(27, new Estate("LENTIL AVENUE", 260, 37,"Yellow"));

        propertiesCheck.put(28,new Utility("WATER WORKS",150,0));

        propertiesCheck.put(29, new Estate("MAC GARDENS", 280, 40,"Yellow"));

        propertiesCheck.put(30,new Jail(50));

        propertiesCheck.put(31, new Estate("OCEAN AVENUE", 300, 50,"Green"));
        propertiesCheck.put(32, new Estate("NORTHERN AVENUE", 300, 50,"Green"));

        propertiesCheck.put(33,new Property("COMMUNITY CHEST",0,0,""));

        propertiesCheck.put(34, new Estate("PHILLY AVENUE", 320, 60,"Green"));

        propertiesCheck.put(35,new Railroad("LEEDS RAILWAY",150,25));

        propertiesCheck.put(36,new Property("CHANCE",0,0,""));

        propertiesCheck.put(37, new Estate("GARDEN PLACE", 350, 80,"Blue"));

        propertiesCheck.put(38,incomeTax);

        propertiesCheck.put(39, new Estate("OCEANVIEW PROMENADE", 400, 100,"Blue"));

        for(Integer position : propertiesCheck.keySet()){
            assertEquals(propertiesCheck.get(position).getName(), board.getSpaces().get(position).getName());
        }
    }

    @org.junit.jupiter.api.Test
    void getPositions() {
        board.changePlayerPosition(1);
        board.changeTurn();
        board.changePlayerPosition(2);
        board.changeTurn();
        board.changePlayerPosition(3);
        board.changeTurn();

        assertEquals(1, board.getPositions().get(board.getPlayers().get(0)));
        assertEquals(2, board.getPositions().get(board.getPlayers().get(1)));
        assertEquals(3, board.getPositions().get(board.getPlayers().get(2)));
    }

    @org.junit.jupiter.api.Test
    void changeTurn() {
        board.changeTurn();
        assertEquals(board.getPlayers().get(1), board.currentPlayer);
    }

    @org.junit.jupiter.api.Test
    void changePlayerPosition() {
        board.changePlayerPosition(6);
        assertEquals(6, board.getPositions().get(board.currentPlayer));
    }

    @org.junit.jupiter.api.Test
    void quitCommand() {
        board.currentPlayer.buyProperty(board.getSpaces().get(1));
        Player check = board.currentPlayer;
        board.quitCommand();
        assertEquals(false, board.getPlayers().contains(check));
        assertEquals(null, board.getSpaces().get(1).getOwner());
        assertEquals(5, board.numPlayers);
    }

    @org.junit.jupiter.api.Test
    void passCommand() {
        board.passCommand();
        assertEquals(board.getPlayers().get(1), board.currentPlayer);
    }

    @org.junit.jupiter.api.Test
    void buyCommand() {
        board.changePlayerPosition(1);
        board.buyCommand();
        assertEquals(board.currentPlayer, board.getSpaces().get(1).getOwner());
    }

    @org.junit.jupiter.api.Test
    void declareWinner() {
        board.currentPlayer.buyProperty(board.getSpaces().get(1));
        board.changeTurn();
        board.currentPlayer.payRent(board.getSpaces().get(1));
        board.declareWinner();
        assertEquals(board.getPlayers().get(0), board.winner);
    }

    @org.junit.jupiter.api.Test
    void payBail() {
        board.changePlayerPosition(30);
        board.payBail();
        assertEquals(1450.0, board.currentPlayer.getBalance());
    }

    @org.junit.jupiter.api.Test
    void addHouseToFirstP() {
        board.currentPlayer.buyProperty(board.getSpaces().get(1));
        board.currentPlayer.buyProperty(board.getSpaces().get(3));
        board.addHouseToColor("Grey");
        board.addHouseToFirstP();
        assertEquals(1, board.getSpaces().get(1).getNumHouses());
    }

    @org.junit.jupiter.api.Test
    void addHouseToSecondP() {
        board.currentPlayer.buyProperty(board.getSpaces().get(1));
        board.currentPlayer.buyProperty(board.getSpaces().get(3));
        board.addHouseToColor("Grey");
        board.addHouseToSecondP();
        assertEquals(1, board.getSpaces().get(3).getNumHouses());
    }

    @org.junit.jupiter.api.Test
    void addHouseToThirdP() {
        board.currentPlayer.buyProperty(board.getSpaces().get(6));
        board.currentPlayer.buyProperty(board.getSpaces().get(8));
        board.currentPlayer.buyProperty(board.getSpaces().get(9));
        board.addHouseToColor("Cyan");
        board.addHouseToThirdP();
        assertEquals(1, board.getSpaces().get(9).getNumHouses());
    }

    @org.junit.jupiter.api.Test
    void addBot() {
        PlayerBot bot = new PlayerBot(1, 1500.0);
        board.getPlayers().add(bot);
        assertTrue(board.getPlayers().contains(bot));
    }
}