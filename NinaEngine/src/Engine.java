import java.io.Serializable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class Engine implements Serializable{
    private String REGULAR = "REGULAR";
    private String CIRCULAR = "CIRCULAR";
    private String POPOUT = "POPOUT";
    private Board board;
    private LinkedList<Player> playerList;
    private Player currentPlayer;
    private int target;
    private eGameStatus status;
    private LinkedList<Move> movesHistory;
    private eGameVarient varient;
    private Timer timer;

    public Engine(XsdClass.GameDescriptor g) {
        int rows = g.getGame().getBoard().getRows();
        int columns = g.getGame().getBoard().getColumns().intValue();
        board = new Board (rows, columns);
        status = eGameStatus.NotStarted;
        target = g.getGame().getTarget().intValue();
        createPlayersList(g);
        varient = convertFromStringToVarient(g.getGame().getVariant());
        movesHistory = new LinkedList<>();
    }

    private eGameVarient convertFromStringToVarient(String variant) {
        eGameVarient returnedVarient;
        variant = variant.toUpperCase();
        if(variant.equals(REGULAR)){
            returnedVarient = eGameVarient.Regular;
        }
        else if(variant.equals(CIRCULAR))
        {
            returnedVarient = eGameVarient.Circular;
        }
        else {
            returnedVarient = eGameVarient.Popout;
        }

        return returnedVarient;
    }

    private void createPlayersList(XsdClass.GameDescriptor g) {
        playerList = new LinkedList<>();
        Random rand = new Random();
        for (XsdClass.Player player : g.getPlayers().getPlayer()){
            char c = (char)(rand.nextInt(26) + 'a');
            playerList.add(new Player(player, c));
        }
        currentPlayer = null;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public eGameStatus getStatus() {
        return status;
    }

    public void setStatus(eGameStatus status) {
        this.status = status;
    }

    public LinkedList<Move> getMovesHistory() {
        return movesHistory;
    }

    public Timer getTimer() {
        return timer;
    }

    public eGameVarient getVarient() {
        return varient;
    }

    public void setVarient(eGameVarient varient) {
        this.varient = varient;
    }

    public void PlayAMove(int chosenColumn, String inOrOut)
    {
        InsertDiscToColumn(chosenColumn);
        handleEndOfTurn();
    }

    public void InsertDiscToColumn(int chosenColumn) {
        int row = endOfColumn(chosenColumn);
        Disc insertedDisc = new Disc (currentPlayer, new Position(row, chosenColumn));
        board.setDisc(row, chosenColumn, insertedDisc);
        currentPlayer.getDiscList().add(insertedDisc);
        currentPlayer.incNumOfPlays();
        Move executedMove = new Move (currentPlayer, row, chosenColumn, false);
        movesHistory.add(executedMove);
    }

    private int endOfColumn(int chosenColumn) {
        int i = 0;
        boolean reached = false;
        while (i < board.getRows() && !reached)
        {
           if (board.getDisc(i, chosenColumn) == null) { i++; }
           else { reached = true; }
        } return i - 1;
    }

    public void SwapPlayers() {
        Player firstPlayer = playerList.removeFirst();
        playerList.addLast(firstPlayer);
        currentPlayer = playerList.getFirst();
    }

    public LinkedList<String> CheckIfWinAchieved() {
        LinkedList<String> winner = new LinkedList<String>();
        Disc insertedDisc = currentPlayer.getDiscList().get(currentPlayer.getDiscList().size() - 1);
        if (checkNinaRow(insertedDisc, new Direction().new Horizontal()) ||
                checkNinaRow(insertedDisc, new Direction().new Vertical()) ||
                checkNinaRow(insertedDisc, new Direction().new FirstObliqueLineStartDownLeft()) ||
                checkNinaRow(insertedDisc, new Direction().new SecondObliqueLineStartDownRight())) {
            winner.add(insertedDisc.getPlayer().getPlayerName());
            this.status = eGameStatus.EndWithWin;
        }

        return winner;
    }

    public boolean checkNinaRow(Disc insertedDiscToCompare, Direction direction) {
        boolean weGotNinaInARow;
        int targetInARow = 1;
        targetInARow += countOccupiedSquares(insertedDiscToCompare, direction.getFirstX(), direction.getFirstY());
        weGotNinaInARow = checkIfWeGotTargetInARow(targetInARow);
        if(!weGotNinaInARow){
            targetInARow += countOccupiedSquares(insertedDiscToCompare, direction.getSecondX(), direction.getSecondY());
            weGotNinaInARow = checkIfWeGotTargetInARow(targetInARow);
        }

        return weGotNinaInARow;
    }

    public int countOccupiedSquares(Disc insertedDiscToCompare, int addToRow, int addToColumn )
    {
        int tempXPosition = insertedDiscToCompare.getPosition().getRow();
        int tempYPosition = insertedDiscToCompare.getPosition().getCol();
        boolean stillCurrentPlayerDiscSign = true;
        int countedMatchSigns = 0;
        tempXPosition += addToRow;
        tempYPosition += addToColumn;
        while (isInRange(tempXPosition, tempYPosition) && stillCurrentPlayerDiscSign) {
            if (board.getDisc(tempXPosition, tempYPosition) != null)
            {
                if (board.getDisc(tempXPosition,tempYPosition).getPlayer() == insertedDiscToCompare.getPlayer()){
                    countedMatchSigns++;
                    tempXPosition += addToRow;
                    tempYPosition += addToColumn;
                }
                else
                    stillCurrentPlayerDiscSign = false;
            }
            else{
                stillCurrentPlayerDiscSign = false;
            }
        }

        return countedMatchSigns;
    }

    private boolean checkIfWeGotTargetInARow(int targetInARow) {
        if (targetInARow >= target){
            return true;
        }
        else
            return false;
    }

    public boolean isInRange(int xPositionCur, int yPositionCur)
    {
        if(xPositionCur < board.getRows() && xPositionCur >= 0 && yPositionCur >= 0 && yPositionCur <board.getCols())
        {
            return true;
        }
        return false;
    }

    public boolean CheckIfBoardFulled() {
        for (int i = 0; i < board.getCols() ; i ++)
        {
            if (board.IsColumnVacant(i))
            {
                return false;
            }
        }

        return true;
    }

    public void PlayComputerTurn() {
        boolean turnPlayed = false;
        Random randomCol = new Random();
        int selectedColumn;

        do {
            selectedColumn = randomCol.nextInt(board.getCols());
            if (board.IsColumnVacant(selectedColumn)){
                turnPlayed = true;
                PlayAMove(selectedColumn, "1");
            }
        }while(!turnPlayed);
    }

    public void UndoMove(Move moveToRemove) {
        Disc discToRemove = board.getDisc(moveToRemove.getRow(), moveToRemove.getColumn());
        moveToRemove.getExecutedPlayer().RemoveDisc(discToRemove);
        board.RemoveDiscFromColumn(moveToRemove.getRow(), moveToRemove.getColumn());
        movesHistory.remove(moveToRemove);
        SwapPlayers();
    }

    public void StartTimer() {
        timer = new Timer();
    }

    protected void handleEndOfTurn() {
        if (!CheckIfWinAchieved().isEmpty()){
            setStatus(eGameStatus.EndWithWin);
        } else if (CheckIfBoardFulled()){
            setStatus(eGameStatus.EndWithDraw);
        } else{
            SwapPlayers();
        }
    }
}

