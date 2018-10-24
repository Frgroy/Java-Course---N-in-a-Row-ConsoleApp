import java.io.*;

public class LoadGame implements Command{
    @Override
    public void Invoke(Manager ninaMngr){
        String fileName = ninaMngr.getNinaUI().GetLoadFileNameFromUser();
            try {
                Engine newEngine = loadGame(fileName);
                ninaMngr.setNinaEngine(newEngine);
                ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_LoadedSuccesfully);
                ninaMngr.getNinaUI().PrintSeperator();
            } catch (IOException e) {
                ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_LoadFailed);
            }
            catch (ClassNotFoundException e) {
                ninaMngr.getNinaUI().Alert(UIConstantsMessages.k_LoadFailed);
            }
        }

        private Engine loadGame(String fileLocation) throws IOException, ClassNotFoundException{
        Engine g;

        try(ObjectInputStream load =
            new ObjectInputStream(
                    new FileInputStream(fileLocation)))
        {
             g = (Engine)load.readObject();
        }

        return g;
    }
}
