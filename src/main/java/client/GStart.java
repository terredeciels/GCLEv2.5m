package client;

import gposition.GPosition;
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
        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Bird100.pgn";
//        command[1] = "D:\\Documents\\CHESS\\MAVEN\\parties\\Tartakower.pgn";//1290
        Ui.main(command);
    }

    public GPosition getGposition() {
        return gposition;
    }

}
