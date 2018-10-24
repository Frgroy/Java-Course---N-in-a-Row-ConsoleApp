import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class UI {

    public String getXmlFileName() {
        Scanner in = new Scanner(System.in);

        System.out.println(UIConstantsMessages.k_AskForFileName);
        String fileName = in.nextLine();
        //fileName = fileName + UIConstantsMessages.k_XMLFinish;
        return fileName;
    }

    public String getUserChoice() {
        Scanner in = new Scanner(System.in);
        String selectedOption;
        System.out.println(UIConstantsMessages.k_Menu);

        selectedOption = in.next();

        return selectedOption;
    }


    public String ChooseColumn() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter selected column to insert Disc: ");
        String selectedColAsString = in.next();

        return selectedColAsString;


        //int selectedCol = in.nextInt();
        //while (selectedCol <= 0 || selectedCol > numberOfColumns) {
        //    System.out.println("Illegal choice. Enter number between 1 to " + (numberOfColumns) + ":");
         //   selectedCol = in.nextInt();
        //}

        //return (selectedCol - 1);

    }


    public void informUserWrongInput() {
        System.out.println("You entered a wrong input!" + "\n"+
                "Please insert a NUMBER value from the suggested menu");
    }


    public String getPlayerName( int playerNumber) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter player's " + playerNumber + " name: ");
        String playerName = in.next();
        while (playerName.length() < 1 || playerName.length() > 20)
        {
            System.out.println("Illegal name. Valid name contains 1 to 20 characters");
            System.out.println("Enter player's " + playerNumber + " valid name: ");
            playerName = in.next();
        }

        return playerName;
    }

    public String getPlayerMode() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter player mode: \n [1] Human \n [2] Computer ");
        String selectedColAsString = in.next();
        return selectedColAsString;
    }

    public void Alert(String s) {
        System.out.println(s);
    }

    public void PrintBoard(Board b) {
        int rows = b.getRows() + 1;
        int cols = b.getCols() + 2;

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j<cols; j++)
            {
                if (i == 0 && (j == 0 || j == 1))
                {
                    System.out.print(' ');
                }

                if (i == 0 && (j>1 && j<cols)) {
                    if (j > 10) {
                        System.out.print("  ");
                    } else {
                        System.out.print("   ");
                    }
                    System.out.print(j - 1);
                    if (j > 10) {
                        System.out.print("  ");
                    } else {
                        System.out.print("  ");
                    }
                }

                if ((i>0) && (j == 0)) {
                    if (i<10) {
                        System.out.print(' ');
                    }
                    System.out.print(i);
                }

                if ((i>0) && (j == 1)) {

                    System.out.print('|');
                }

                if ((i>0) && (j>1 && j<cols)) {
                    System.out.print(" _");
                    if ((b.getDisc(i - 1, j - 2) != null)) {
                        System.out.print(b.getDisc(i - 1, j - 2).getDiscSign());
                    } else {
                        System.out.print("_");
                    }
                    System.out.print("_ |");
                }
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    public void PrintMovesHistory(LinkedList<Move> movesHistory) {
        for(Move move : movesHistory)
        {
            System.out.println(move.toString());
        }
    }

    public void PrintDrawMassage() {
        this.Alert(UIConstantsMessages.k_GameDrawed);
    }

    public void PrintWinnerMassage(String winningPlayer){
        this.Alert(UIConstantsMessages.k_PlayerWon + winningPlayer);
    }

    public void PrintGameStatus(eGameStatus status) {
        if (status == eGameStatus.InProgress)
            this.Alert("Game in progress.");
        else if (status == eGameStatus.EndWithWin || status == eGameStatus.EndWithDraw ) {
            this.Alert("Game finished"); }
        else {this.Alert("Game not started."); }
    }

    public void PrintActivePlayer(String playerName) {
        this.Alert("Active player: " + playerName);
    }

    public void PrintPlayersDetails(List<Player> playerList) {
        for (Player p : playerList)
        {
            this.Alert("Disc of " + p.getPlayerName() + ": " + p.getPlayerSign());
        }
        for (Player p : playerList)
        {
            this.Alert(p.getPlayerName() + " played " + p.getNumOfPlays() + " turns");
        }
    }

    public void PrintTime(String timer) {
        this.Alert(timer + " from game started");
    }

    public void PrintSeperator() {this.Alert("======================================================");}

    public void PrintTurn(Move move) {
        this.Alert(move.getExecutedPlayer().getPlayerName() + " Inserted disc to: " +
                (move.getColumn()+1));
    }

    public void PrintUndoMoveMassage(Move move) {
        this.Alert("Disc removed from column " + (move.getColumn()+1));
    }

    public String GetSaveFileNameFromUser() {
        Scanner in = new Scanner(System.in);
        String fileName;
        this.Alert("Enter save file name:");
        fileName = in.nextLine();

        return fileName;
    }

    public String GetLoadFileNameFromUser() {
        Scanner in = new Scanner(System.in);
        String fileName;
        this.Alert("Enter file path to the exist saved game:");
        fileName = in.nextLine();

        return fileName;
    }

    public String inOrOut() {
        Scanner in = new Scanner(System.in);
        this.Alert("Inorout");
        return in.nextLine();
    }
}