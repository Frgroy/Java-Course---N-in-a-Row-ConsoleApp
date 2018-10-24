import XsdClass.GameDescriptor;

import java.io.Serializable;
import java.util.LinkedList;

public class PopoutEngine extends Engine implements Serializable {
    public PopoutEngine(GameDescriptor g) {
        super(g);
    }

    @Override
    public void PlayAMove(int chosenColumn, String inOrOut)
    {
        if (inOrOut.equals("1")){
            InsertDiscToColumn(chosenColumn);
        }
        else {
            PopDiscOutOfColumn(chosenColumn);
        }

        super.handleEndOfTurn();
    }

    private void PopDiscOutOfColumn(int chosenColumn) {
        Disc discToRemove = super.getBoard().getDisc(getBoard().getRows()-1, chosenColumn);
        super.getCurrentPlayer().RemoveDisc(discToRemove);
        super.getBoard().RemoveDiscFromColumn(getBoard().getRows()-1, chosenColumn);
        super.getMovesHistory().add(new Move (super.getCurrentPlayer(), getBoard().getRows()-1, chosenColumn, true));
        Disc discToScroll = super.getBoard().getDisc(getBoard().getRows()-2, chosenColumn);
        super.getBoard().ScrollDownDiscs(chosenColumn);
    }

    public LinkedList<String> CheckIfWinAchieved() {
        LinkedList<String> winners = new LinkedList<>();
        Move actionedMove = super.getMovesHistory().getLast();
        int i = super.getBoard().getRows() - 1;
        Disc checkedDisc = super.getBoard().getDisc(i, actionedMove.getColumn());
        while (checkedDisc != null) {
            if (checkNinaRow(checkedDisc, new Direction().new Horizontal()) ||
                    checkNinaRow(checkedDisc, new Direction().new Vertical()) ||
                    checkNinaRow(checkedDisc, new Direction().new FirstObliqueLineStartDownLeft()) ||
                    checkNinaRow(checkedDisc, new Direction().new SecondObliqueLineStartDownRight())) {
                super.setStatus(eGameStatus.EndWithWin);
                if (!winners.contains(checkedDisc.getPlayer().getPlayerName())) {
                    winners.add(checkedDisc.getPlayer().getPlayerName());
                }
            }
            i--;
            checkedDisc = super.getBoard().getDisc(i, actionedMove.getColumn());
        }
        return winners;
    }
}
