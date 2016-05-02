package client;

import gposition.CPosition;
import gposition.GPosition;
import java.util.ArrayList;
import main.Fen;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GStartTest {

    private final GStart gStart;
    private  GPosition gposition;
    private  CPosition cposition;
    private ArrayList<String> lg_coups;
    private ArrayList<String> lcp_coups;

    public GStartTest() {
        gStart = new GStart();
        gposition = gStart.getGposition();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCoupsValides() {
        for (String f : Fen.getFenList()) {
            gposition.init(f);
            cposition = gposition.getCp_position();
            ArrayList<String> diff = getTest().getDiffStringList();
            
            assert (diff.isEmpty());

            String result = cposition.getPosition().getFEN() + '\n'
                    + "Coups ChessPresso:" + "\n"
                    + lg_coups + '\n'
                    + "Coups GCLE:" + "\n"
                    + lcp_coups + "\n"
                    + "Diff:" + "\n"
                    + diff + "\n";

        }
    }

    private GPositionTest getTest() {
        GPositionTest valid = new GPositionTest();
        lg_coups = gposition.getLAN();
        lcp_coups = cposition.getLAN();

        if (lg_coups.size() <= lcp_coups.size()) {
            valid.setDiffStringList(getDiff(lg_coups, lcp_coups));
        } else {
            valid.setDiffStringList(getDiff(lcp_coups, lg_coups));
        }

        return valid;
    }

    private ArrayList<String> getDiff(ArrayList<String> L1, ArrayList<String> L2) {
        L2.removeAll(L1);
        return L2;

    }
}
