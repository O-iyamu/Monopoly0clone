import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for BoardModel
 * @author Iyamu Osaretinmwen 101157386
 */

class PropertyTest {

    Property property;
    Player player;
    @BeforeEach
    void setUp() {
        property = new Property("ORIENT AVENUE", 100, 14,"Estate");
        player = new Player("Monopoly", 1500.0);
    }

    @Test
    void setOwner() {
        property.setOwner(player);
        assertEquals(player, property.getOwner());
    }

    @Test
    void getName() {
        assertEquals("ORIENT AVENUE", property.getName());
    }

    @Test
    void getRent() {
        assertEquals(14, property.getRent());
    }

    @Test
    void getPrice() {
        assertEquals(100, property.getPrice());
    }

    @Test
    void getOwner() {
        player.buyProperty(property);
        assertEquals(player, property.getOwner());
    }
}