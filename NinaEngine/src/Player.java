import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

public class Player implements Serializable{

    private String HUMAN = "HUMAN";
    private String COMPUTER = "COMPUTER";
    private char playerSign; //todo: change to disc colour
    private ePlayerMode playerMode;
    private String playerID;
    private String playerName;
    private List<Disc> discList;
    private int numOfPlays;

    public int getNumOfPlays() {
        return numOfPlays;
    }

    public void incNumOfPlays() {
        numOfPlays++;
    }

    public char getPlayerSign() {
        return playerSign;
    }

    public void setPlayerSign(char playerSign) {
        this.playerSign = playerSign;
    }

    public ePlayerMode getPlayerMode() {
        return playerMode;
    }

    public void setPlayerMode(ePlayerMode playerMode) {
        this.playerMode = playerMode;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Player(char playerSign, ePlayerMode playerMode, String playerName) {
        this.playerSign = playerSign;
        this.playerMode = playerMode;
        this.playerName = playerName;
        this.playerID = null;
        this.numOfPlays = 0;
        discList = new LinkedList<>();
    }

    public Player(XsdClass.Player player, char playerSign) {
    //    this.playerSign = playerSign; //todo: create random player colour
        this.playerMode =  convertFromStringToPlayerMode(player.getType());
        this.playerName = player.getName();
        this.playerID = String.valueOf(player.getId());
        this.numOfPlays = 0;
        this.playerSign = playerSign;
        discList = new LinkedList<>();
    }

    private ePlayerMode convertFromStringToPlayerMode(String playerMode) {
        ePlayerMode returnedPlayerMode;
        playerMode = playerMode.toUpperCase();
        if(playerMode.equals(HUMAN)){
            returnedPlayerMode = ePlayerMode.Human;
        }
        else // if(playerMode == COMPUTER)
        {
            returnedPlayerMode = ePlayerMode.Computer;
        }

        return returnedPlayerMode;
    }

    public List<Disc> getDiscList() {
        return discList;
    }

    public void RemoveDisc(Disc discToRemove) {
        numOfPlays--;
        discList.remove(discToRemove);
    }
}


