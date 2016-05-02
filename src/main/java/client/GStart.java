package client;

import gposition.GPosition;
import main.Fen;
import main.Ui;

public class GStart {

    private final GPosition gposition;

    public GStart() {
        gposition = GPosition.getInstance();

        initFen();

    }

    private void initFen() {
        String[] command = new String[3];
        command[0] = "-cli";
        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\bird.pgn";//353
//        command[1] = "F:/ProgmEchecsNotes/Tartakower.pgn";//1290
        Ui.main(command);
    }

    public GPosition getGposition() {
        return gposition;
    }

}