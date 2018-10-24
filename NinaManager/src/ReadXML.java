import XsdClass.GameDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

public class ReadXML implements Command {
    @Override
    public void Invoke(Manager ninaManager){
        if (ninaManager.getNinaEngine() == null) {
            readXMLFile(ninaManager);
        }

        else if (ninaManager.getNinaEngine().getStatus() == eGameStatus.NotInitialized){
            readXMLFile(ninaManager);
        }
        else {
            ninaManager.getNinaUI().Alert(UIConstantsMessages.k_ActiveGameMessage);
        }

    }

    private void readXMLFile(Manager ninaManager){
        String XMLPath = ninaManager.getNinaUI().getXmlFileName();
        if (XMLPath.endsWith("\"") && XMLPath.startsWith("\"")){
            XMLPath = XMLPath.substring(1, (XMLPath.length() - 1));
        }
        if (getFileExtension(XMLPath).equals(UIConstantsMessages.k_XMLFinish))
        {
            GameDescriptor g;
            try{
                g = createGameDescriptor(XMLPath);
                String errorMsg = legalDescriptor(g);
                if (errorMsg == ""){
                    if (g.getGame().getVariant().equals("Regular")) {
                        ninaManager.setNinaEngine(new RegularEngine(g));
                    }
                    else if (g.getGame().getVariant().equals("Circular")) {
                        ninaManager.setNinaEngine(new CircularEngine(g));
                    }
                    else {
                        ninaManager.setNinaEngine(new PopoutEngine(g));
                    }
                    ninaManager.getNinaUI().Alert(UIConstantsMessages.k_XMLFileLoadedSuccessfully);
                    ninaManager.getNinaUI().PrintBoard(ninaManager.getNinaEngine().getBoard());
                    ninaManager.getNinaUI().PrintGameStatus(ninaManager.getNinaEngine().getStatus());
                    ninaManager.getNinaUI().PrintSeperator();
                }
                else
                {
                    ninaManager.getNinaUI().Alert(errorMsg);
                }
            }
            catch (Exception e) {
                ninaManager.getNinaUI().Alert(UIConstantsMessages.k_InCorrectXmlName);
            }
        }
        else
        {
            ninaManager.getNinaUI().Alert(UIConstantsMessages.k_InCorrectFileType);
        }
    }
    private String legalDescriptor(GameDescriptor g) {
        String errorMsg = "";
        boolean legalData = true;
        if (g.getGame().getBoard().getRows() > 50 || g.getGame().getBoard().getRows() < 5) {
            errorMsg += UIConstantsMessages.k_IllegalRows + "\n";
        }
        if (g.getGame().getBoard().getColumns().intValue() > 30 || g.getGame().getBoard().getColumns().intValue() < 6) {
            errorMsg += UIConstantsMessages.k_IllegalCols + "\n";
        }
        if ((g.getGame().getTarget().intValue() >= g.getGame().getBoard().getRows() ||
                g.getGame().getTarget().intValue() >= g.getGame().getBoard().getColumns().intValue()) ||
                g.getGame().getTarget().intValue() < 2) {
            errorMsg += UIConstantsMessages.k_IllegalTarget + "\n";
        }

        if ((g.getGame().getTarget().intValue() >= g.getGame().getBoard().getRows() ||
                g.getGame().getTarget().intValue() >= g.getGame().getBoard().getColumns().intValue()) ||
                g.getGame().getTarget().intValue() < 2) {
            errorMsg += UIConstantsMessages.k_IllegalTarget + "\n";
        }

        if (g.getPlayers().getPlayer().size() < 2 ||
                g.getPlayers().getPlayer().size() > 6){
            errorMsg += UIConstantsMessages.k_IllegalNumOfPlayers + "\n";
        }

        for (int i = 0; i < g.getPlayers().getPlayer().size() - 1 && legalData == true ; i++){
            for (int j = i+1; j < g.getPlayers().getPlayer().size() && legalData == true; j++){
                if (g.getPlayers().getPlayer().get(i).getId() ==
                        g.getPlayers().getPlayer().get(j).getId()) {
                    legalData = false;
                    errorMsg += UIConstantsMessages.k_IllegalIDOfPlayers + "\n";
                }
            }
        }

        if (errorMsg != ""){
            errorMsg = UIConstantsMessages.k_IllegalXMLContent + "\n" + errorMsg;
        }
        return errorMsg;
    }

    public String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }


    private GameDescriptor createGameDescriptor(String xmlPath) throws JAXBException, FileNotFoundException {
        GameDescriptor g;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            File file = new File(xmlPath);
            if (file.exists() == false) {

                throw new FileNotFoundException();
            }
            g = (GameDescriptor) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            throw e;
        }

        return g;
    }
}