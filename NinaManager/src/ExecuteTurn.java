public class ExecuteTurn implements Command {

    @Override
    public void Invoke(Manager ninaMngr) { ///to trangsger to UI
        if (ninaMngr.getNinaEngine().getStatus() == eGameStatus.InProgress) {
            ninaMngr.getNinaUI().PrintBoard(ninaMngr.getNinaEngine().getBoard());
            int chosenColumn = getChoosenColumnToInsertDiscFromUser(ninaMngr);
            if (ninaMngr.getNinaEngine().getVarient() == eGameVarient.Popout){
                String inOrOut = ninaMngr.getNinaUI().inOrOut();
                ninaMngr.getNinaEngine().PlayAMove(chosenColumn, inOrOut);
            }
            else {
                ninaMngr.getNinaEngine().PlayAMove(chosenColumn, "1");
            }
            if (ninaMngr.getNinaEngine().getStatus() == eGameStatus.EndWithWin) {
                ninaMngr.HandleWin();
            } else if (ninaMngr.getNinaEngine().getStatus() == eGameStatus.EndWithDraw) {
                ninaMngr.HandleDraw();
            }
        } else {
            ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_NoneActiveGameError);
        }

    }

    private int getChoosenColumnToInsertDiscFromUser(Manager ninaMngr) {
        String ChoosenColumn = ninaMngr.getNinaUI().ChooseColumn();
        int ChoosenColumnAsInt = -1;
        while (ChoosenColumnAsInt == -1) {
            try {
                ChoosenColumnAsInt = Integer.parseInt(ChoosenColumn);
                if (ChoosenColumnAsInt <= 0 || ChoosenColumnAsInt > ninaMngr.getNinaEngine().getBoard().getCols()) {
                    throw new NumberFormatException();
                }
                else {
                    ChoosenColumnAsInt = ChoosenColumnAsInt - 1;
                    if (!ninaMngr.getNinaEngine().getBoard().IsColumnVacant(ChoosenColumnAsInt)) {
                        throw new IndexOutOfBoundsException();
                    }
                }
            }
            catch (NumberFormatException notIntegerValueException) {
                ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_informUserChoosenColumnOutOfRange + ninaMngr.getNinaEngine().getBoard().getCols());
                ChoosenColumnAsInt = -1;
                ChoosenColumn = ninaMngr.getNinaUI().ChooseColumn();
            }
            catch (IndexOutOfBoundsException er){
                ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_FullColumn);
                ChoosenColumnAsInt = -1;
                ChoosenColumn = ninaMngr.getNinaUI().ChooseColumn();
            }
        }

        return ChoosenColumnAsInt;
    }
}
