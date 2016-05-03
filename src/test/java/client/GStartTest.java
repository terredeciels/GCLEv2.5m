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
    private final GPosition gposition;
    private CPosition cposition;
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
            lg_coups = gposition.getLAN();
            lcp_coups = cposition.getLAN();

            assertEquals(lg_coups, lcp_coups);

            String result = cposition.getPosition().getFEN() + '\n'
                    + "Coups ChessPresso:" + "\n"
                    + lg_coups + '\n'
                    + "Coups GCLE:" + "\n"
                    + lcp_coups + "\n";

            System.out.println(result);

        }
    }

}
