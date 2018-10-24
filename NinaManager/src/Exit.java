public class Exit implements Command{
    @Override
    public void Invoke(Manager ninaManager){
        ninaManager.getNinaEngine().setStatus(eGameStatus.Closed);
    }
}
