import java.io.Serializable;

/**
 * The Dice class implementing a die for the Monopoly Game
 *
 * @author Christina Dang 101141609
 */
public class Dice implements Serializable {
    private int faces;
    private int rolledVal;

    /**
     * The constructor of the Dice class
     * @param faces int, the amount of faces on the die
     */
    public Dice(int faces){
        this.faces = faces;
    }

    /**
     * A method to get the number on the die
     * @return rolledVal int, the number on the die
     */
    public int getValue(){
        return rolledVal;
    }

    /**
     * A method to roll the die
     */
    public void roll() {
        rolledVal = (int) Math.floor(Math.random() * (faces) + 1);
    }
}

