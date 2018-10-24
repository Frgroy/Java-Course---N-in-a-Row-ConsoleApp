public class Manager {

    private UI ninaUI = new UI();
    private Engine ninaEngine;
    private boolean isGameClosed = false;

    public boolean isGameClosed() {
        return isGameClosed;
    }

    public void setGameClosed(boolean gameClosed) {
        isGameClosed = gameClosed;
    }


    public UI getNinaUI() {
        return ninaUI;
    }

    public void setNinaUI(UI ninaUI) {
        this.ninaUI = ninaUI;
    }

    public Engine getNinaEngine() {
        return ninaEngine;
    }

    public void setNinaEngine(Engine ninaEngine) {
        this.ninaEngine = ninaEngine;
    }

    public void Run()
    {
        eMenuChoice userChoice;
        while (!isGameClosed){
            if (ninaEngine != null)
            {
                if (ninaEngine.getStatus() != eGameStatus.InProgress)
                {
                    userChoice = getAndConvertUserChoice();
                    if (legalUserChoice(userChoice)) {
                        handleUserChoice(userChoice);
                    }
                    else {
                        ninaUI.Alert(legalUserChoiceAsMessage(userChoice));
                    }
                }
                else{
                    ninaUI.PrintActivePlayer(ninaEngine.getCurrentPlayer().getPlayerName());
                    if (ninaEngine.getCurrentPlayer().getPlayerMode() == ePlayerMode.Human)
                    {
                        userChoice = getAndConvertUserChoice();
                        if (legalUserChoice(userChoice)) {
                            handleUserChoice(userChoice);
                        }
                        else {
                            ninaUI.Alert(legalUserChoiceAsMessage(userChoice));
                        }                    }
                    else {
                        handleComputerTurn();
                    }
                }
            }
            else{
                userChoice = getAndConvertUserChoice();
                if (legalUserChoice(userChoice)) {
                    handleUserChoice(userChoice);
                }
                else {
                    ninaUI.Alert(legalUserChoiceAsMessage(userChoice));
                }
            }
        }
    }


    private String legalUserChoiceAsMessage(eMenuChoice userChoice) {
        String reason = "it is ok";
        if (ninaEngine == null) {
            if (userChoice != eMenuChoice.readXML &&
                    userChoice != eMenuChoice.exit &&
                    userChoice != eMenuChoice.loadGame)
                reason = UIConstantsMessages.k_LoadXMLNecessary;
        }
        else {
            eGameStatus gameStatus = ninaEngine.getStatus();
            switch (gameStatus) {
                case NotInitialized:
                    if (userChoice != eMenuChoice.readXML &&
                            userChoice != eMenuChoice.exit &&
                            userChoice != eMenuChoice.loadGame)
                        reason = UIConstantsMessages.k_LoadXMLNecessary;
                    break;
                case NotStarted:
                    if (userChoice != eMenuChoice.startGame &&
                            userChoice != eMenuChoice.exit &&
                            userChoice != eMenuChoice.loadGame &&
                            userChoice != eMenuChoice.showDetails &&
                            userChoice != eMenuChoice.showHistory)
                    {
                        if (userChoice == eMenuChoice.readXML)
                            reason = UIConstantsMessages.k_LoadXMLUnNecessary;
                        else if (userChoice == eMenuChoice.undo)
                            reason = UIConstantsMessages.k_NoMoveToUndoError;
                        else if (userChoice == eMenuChoice.saveGame)
                            reason = UIConstantsMessages.k_NoneActiveGameSaveError;
                        else
                            reason = UIConstantsMessages.k_NoneActiveGameError;


                    }
                    break;
                case InProgress:
                    if (userChoice != eMenuChoice.showHistory &&
                            userChoice != eMenuChoice.showDetails &&
                            userChoice != eMenuChoice.executeTurn &&
                            userChoice != eMenuChoice.loadGame &&
                            userChoice != eMenuChoice.saveGame &&
                            userChoice != eMenuChoice.undo &&
                            userChoice != eMenuChoice.exit){
                        if (userChoice == eMenuChoice.readXML)
                            reason = UIConstantsMessages.k_LoadXMLUnNecessary;
                        else if (userChoice == eMenuChoice.startGame)
                            reason = UIConstantsMessages.k_GameStartedError;
                    }
                    break;
                case Closed:
                    break;
            }
        }
        return reason;
    }

    private boolean legalUserChoice(eMenuChoice userChoice) {
        boolean isLegalChoice = false;
        if (ninaEngine == null) {
            if (userChoice == eMenuChoice.readXML ||
                    userChoice == eMenuChoice.exit ||
                    userChoice == eMenuChoice.loadGame)
                isLegalChoice = true;
        }
        else {
            eGameStatus gameStatus = ninaEngine.getStatus();
            switch (gameStatus) {
                case NotInitialized:
                    if (userChoice == eMenuChoice.readXML ||
                            userChoice == eMenuChoice.loadGame ||
                            userChoice == eMenuChoice.exit)
                        isLegalChoice = true;
                    break;
                case NotStarted:
                    if (userChoice == eMenuChoice.startGame ||
                            userChoice == eMenuChoice.exit ||
                            userChoice == eMenuChoice.loadGame ||
                            userChoice == eMenuChoice.showDetails ||
                            userChoice == eMenuChoice.showHistory)
                        isLegalChoice = true;
                    break;
                case InProgress:
                    if (userChoice == eMenuChoice.showHistory ||
                            userChoice == eMenuChoice.showDetails ||
                            userChoice == eMenuChoice.executeTurn ||
                            userChoice == eMenuChoice.loadGame ||
                            userChoice == eMenuChoice.saveGame ||
                            userChoice == eMenuChoice.undo ||
                            userChoice == eMenuChoice.exit)
                        isLegalChoice = true;
                    break;
                case Closed:
                    break;
            }
        }
        return isLegalChoice;
    }


    private eMenuChoice getAndConvertUserChoice(){
        String userChoiceAsString;
        int userChoiceAsInt;
        eMenuChoice convertedUserChoice = null;
        boolean correctInput = false;
        while (!correctInput)
        {
            userChoiceAsString = ninaUI.getUserChoice();
            try{
                userChoiceAsInt = Integer.parseInt(userChoiceAsString);
                convertedUserChoice = toMenuChoice(userChoiceAsInt);
                if (convertedUserChoice == eMenuChoice.wrongInput){
                    ninaUI.informUserWrongInput();
                }
                else
                {
                    correctInput = true;
                }
            }
            catch (NumberFormatException notIntegerValueException) {
                ninaUI.informUserWrongInput();
            }
        }

        return convertedUserChoice;
    }

    private eMenuChoice toMenuChoice(int selectedOption) {
        eMenuChoice returnedChoice;
        switch (selectedOption)
        {
            case 1:
                returnedChoice = eMenuChoice.readXML;
                break;
            case 2:
                returnedChoice = eMenuChoice.startGame;
                break;
            case 3:
                returnedChoice = eMenuChoice.showDetails;
                break;
            case 4:
                returnedChoice = eMenuChoice.executeTurn;
                break;
            case 5:
                returnedChoice = eMenuChoice.showHistory;
                break;
            case 6:
                returnedChoice = eMenuChoice.exit;
                break;
            case 7:
                returnedChoice = eMenuChoice.undo;
                break;
            case 8:
                returnedChoice = eMenuChoice.saveGame;
                break;
            case 9:
                returnedChoice = eMenuChoice.loadGame;
                break;
            default:
                returnedChoice = eMenuChoice.wrongInput;
        }
        return returnedChoice;
    }

    private void handleComputerTurn() {
        this.getNinaEngine().PlayComputerTurn();
        getNinaUI().PrintBoard(getNinaEngine().getBoard());
        getNinaUI().PrintTurn(getNinaEngine().getMovesHistory().getLast());
        getNinaUI().PrintSeperator();
        if (getNinaEngine().getStatus() == eGameStatus.EndWithWin)
        {
            HandleWin();
        }
        else if(getNinaEngine().getStatus() == eGameStatus.EndWithDraw){
            HandleDraw();
        }
    }

    public void HandleDraw() {
        getNinaUI().PrintDrawMassage();
        StartNewGameRoutine();
    }

    private void StartNewGameRoutine() {
        ninaEngine.setStatus(eGameStatus.NotInitialized);

    }

    public void HandleWin() {
        getNinaUI().PrintWinnerMassage(getNinaEngine().getCurrentPlayer().getPlayerName());
        StartNewGameRoutine();
    }

    public void handleUserChoice(eMenuChoice userChoice) {
        Command cmd = null;
        switch (userChoice) {
            case readXML:
                cmd = new ReadXML();
                break;
            case startGame:
                cmd = new StartGame();
                break;
            case showDetails:
                cmd = new ShowDetails();
                break;
            case executeTurn:
                cmd = new ExecuteTurn();
                break;
            case showHistory:
                cmd = new ShowHistory();
                break;
            case exit:
                setGameClosed(true);
                break;
            case undo:
                cmd = new Undo();
                break;
            case loadGame:
                cmd = new LoadGame();
                break;
            case saveGame:
                cmd = new SaveGame();
                break;
        }
        if (cmd!=null){ cmd.Invoke(this);}
    }
}
