public class ShowHistory implements Command{
    @Override
    public void Invoke (Manager ninaMngr){
        if (ninaMngr.getNinaEngine().getStatus() == eGameStatus.InProgress) {
            if (ninaMngr.getNinaEngine().getMovesHistory().size() == 0) {
                ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_NoHistoryError);
            } else {
                ninaMngr.getNinaUI().PrintMovesHistory(ninaMngr.getNinaEngine().getMovesHistory());
            }
            ninaMngr.getNinaUI().PrintSeperator();
        }
        else {
            ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_NoneActiveGameError);
        }
    }
}
