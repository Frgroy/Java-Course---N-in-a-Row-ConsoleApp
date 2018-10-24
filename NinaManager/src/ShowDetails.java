import com.sun.webkit.Timer;

import java.sql.Time;

public class ShowDetails implements Command{
    @Override
    public void Invoke (Manager ninaMngr) {
        if (ninaMngr.getNinaEngine().getStatus() != eGameStatus.NotInitialized) {
            ninaMngr.getNinaUI().PrintBoard(ninaMngr.getNinaEngine().getBoard());
            ninaMngr.getNinaUI().PrintGameStatus(ninaMngr.getNinaEngine().getStatus());
            if (!ninaMngr.getNinaEngine().getPlayerList().isEmpty()){
                ninaMngr.getNinaUI().PrintActivePlayer(ninaMngr.getNinaEngine().getCurrentPlayer().getPlayerName());
                ninaMngr.getNinaUI().PrintPlayersDetails(ninaMngr.getNinaEngine().getPlayerList());
            }
            ninaMngr.getNinaUI().PrintTime(ninaMngr.getNinaEngine().getTimer().toString());
            ninaMngr.getNinaUI().PrintSeperator();
        }
        else {
            ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_NoneActiveGameError);
        }
    }
}
