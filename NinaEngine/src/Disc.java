import java.io.Serializable;

public class Disc implements Serializable{
    private char discSign;
    private Player player;
    private Position position;

    public char getDiscSign() {
        return discSign;
    }

    public void setDiscSign(char discSign) {
        this.discSign = discSign;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Disc(Player player, Position position) {
        this.discSign = player.getPlayerSign();
        this.player = player;
        this.position = position;
    }

    public void ScrollDown() {
        position = new Position (position.getRow() - 1, position.getCol());
    }
}
