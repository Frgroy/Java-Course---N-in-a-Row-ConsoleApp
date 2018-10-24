public class StartGame implements Command{
    @Override
   public void Invoke(Manager ninaManager){
        ninaManager.getNinaEngine().StartTimer();
        ninaManager.getNinaEngine().setStatus(eGameStatus.InProgress);
        ninaManager.getNinaEngine().setCurrentPlayer(ninaManager.getNinaEngine().getPlayerList().get(0));
    }

}