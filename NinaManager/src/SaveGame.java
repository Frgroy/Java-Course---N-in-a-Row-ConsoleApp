import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class SaveGame implements Command {
    @Override
    public void Invoke(Manager ninaMngr){
        if (ninaMngr.getNinaEngine().getStatus() != eGameStatus.NotInitialized &&
                ninaMngr.getNinaEngine().getStatus() != eGameStatus.NotStarted) {
            String fileName = ninaMngr.getNinaUI().GetSaveFileNameFromUser();
            try {
                saveGame(ninaMngr.getNinaEngine(), fileName);
                ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_SavedSuccesfully);
                ninaMngr.getNinaUI().PrintSeperator();
            } catch (IOException e) {
               ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_SaveFailed);
            }
        }
        else {
              ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_NoneActiveGameError);
        }
    }


    private void saveGame(Engine g, String fileName) throws IOException{
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(g);
            out.flush();
        }
        catch (IOException e) {
            throw e;
        }
    }
}
