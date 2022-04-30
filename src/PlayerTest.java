import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * JUnit test class for BoardModel
 * @author Iyamu Osaretinmwen 101157386
 */

class PlayerTest {

    Player player;
    Property p;
    @BeforeEach
    void setUp() {
        player = new Player("Monopoly", 1500.0);
        p = new Property("ORIENT AVENUE", 100, 14,"Estate");
    }

    @Test
    void rollDice() {
    }

    @Test
    void getName() {
        assertEquals("Monopoly", player.getName());
    }

    @Test
    void getBalance() {
        assertEquals(1500.0, player.getBalance());
    }

    @Test
    void setBalance() {
        player.setBalance(2000.0);
        assertEquals(2000.0, player.getBalance());
    }

    @Test
    void addBalance() {
        player.addBalance(1000.0);
        assertEquals(2500.0, player.getBalance());
    }

    @Test
    void buyProperty() {
        player.buyProperty(p);
        assertEquals(true, player.propertyOwned(p));
    }

    @Test
    void payRent() {
        Player player2 = new Player("Game", 1500);
        player2.buyProperty(p);
        player.payRent(p);
        assertEquals(1486, player.getBalance());
    }

    @Test
    void declareBankruptcy() {
        Player player2 = new Player("Game", 1500);
        player.buyProperty(p);
        player.declareBankruptcy(player2);
        assertEquals("Game", p.getOwner().getName());
    }

    @Test
    void quit() {
        player.buyProperty(p);
        player.quit();
        assertEquals(null, p.getOwner());
    }

    @Test
    void propertyOwned() {
        player.buyProperty(p);
        assertEquals(true, player.propertyOwned(p));
    }

    @Test
    void printInfo() {
    }

    @Test
    void getNetWorth() {
        player.buyProperty(p);
        assertEquals(1500.0, player.getNetWorth());
    }
}