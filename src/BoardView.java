/**
 * An interface to handle events sent by the GameModel class
 * @author Vishesh Dasani 101162185
 */
public interface BoardView {

    void handleRollCommand(BoardEvent event);

    void handlePropertyOwnedByAnother(BoardEvent event);

    void handlePropertyOwnedBySelf(BoardEvent event);

    void handlePropertyUnowned(BoardEvent event);

    void handleQuitCommand(BoardEvent event);

    void handlePassCommand(BoardEvent event);

    void handleBuyCommand(BoardEvent event);

    void handleWinnerDeclaration(BoardEvent event);

    void handleLandGo(BoardEvent event);

    void handleExtraSpace(BoardEvent event);

    void handleIncomeTax(BoardEvent event);

    void handleGoToJail(BoardEvent event);

    void handlePlayerInJail(BoardEvent event);

    void handlePlayerPaidBail(BoardEvent event);

    void handlePlayerAttemptedRoll(BoardEvent event);

    void handleParkingSpace(BoardEvent event);

    void handleSetContained(BoardEvent event);

    void handleSelectedColor(BoardEvent boardEvent);
}
