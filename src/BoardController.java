import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Class for Controller for the Monopoly Game
 *
 * @author Vishesh Dasani 101162185
 * @version 2.0
 */
public class BoardController implements ActionListener, Serializable {

    BoardModel model;

    /**
     * Constructor for BoardController
     *
     * @param model BoardModel, the model of the game
     */
    public BoardController(BoardModel model) {
        this.model = model;
    }

    /**
     * Action Performed method for the buttons in the view. Used enhanced switch cases.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "roll" -> model.rollCommand();
            case "quit" -> model.quitCommand();
            case "pass" -> model.passCommand();
            case "buy" -> model.buyCommand();
            case "declare" -> model.declareWinner();
            case "payF" -> model.payBail();
            case "rollJ" -> model.rollInJail();
        }


    }

}
