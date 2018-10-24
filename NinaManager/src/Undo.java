public class Undo implements Command{
    @Override
    public void Invoke(Manager ninaMngr){
        if (!ninaMngr.getNinaEngine().getMovesHistory().isEmpty()) {
            Move move = ninaMngr.getNinaEngine().getMovesHistory().getLast();
            ninaMngr.getNinaEngine().UndoMove(move);
            ninaMngr.getNinaUI().PrintBoard(ninaMngr.getNinaEngine().getBoard());
            ninaMngr.getNinaUI().PrintUndoMoveMassage(move);
            ninaMngr.getNinaUI().PrintSeperator();

        }
        else {
            ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_NoMoveToUndoError);
        }
    }
}