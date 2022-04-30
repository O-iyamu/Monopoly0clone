
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for BoardModel
 * @author Iyamu Osaretinmwen 101157386
 */

class JailTest {

    private Player player;
    private Jail jail;
    @BeforeEach
    void setUp() {
        player = new Player("player", 1500.0);
        jail = new Jail(50.0);
    }

    @Test
    void getFee() {
        assertEquals(50, jail.getFee());
    }

    @Test
    void freePlayer() {
        jail.arrestPlayer(player);
        jail.freePlayer(player);
        assertEquals(false, jail.getArrestedPlayers().containsKey(player));
    }

    @Test
    void arrestPlayer() {
        jail.arrestPlayer(player);
        assertTrue(jail.getArrestedPlayers().keySet().contains(player));
    }

    @Test
    void checkAttempts() {
        jail.arrestPlayer(player);
        assertEquals(false, jail.checkAttempts(player));
    }

    @Test
    void getArrestedPlayers() {
        jail.arrestPlayer(player);
        assertEquals(player, jail.getArrestedPlayers().keySet().toArray()[0]);
    }
}