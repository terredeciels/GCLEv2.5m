package client;

import chesspresso.move.IllegalMoveException;
import chesspresso.move.Move;
import chesspresso.position.Position;
import gposition.CPosition;
import gposition.GCoups;
import gposition.GPosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            CPosition cposition = gposition.getCp_position();

            //verifier les coups valides non les positions genere ?
            ArrayList<String> lg_coups_str = gposition.getLAN();
            ArrayList<String> lg_coups_str_tri = (ArrayList<String>) lg_coups_str.clone();
            ArrayList<String> lcp_coups_str = cposition.getLAN();
            ArrayList<String> lcp_coups_str_tri = (ArrayList<String>) lcp_coups_str.clone();
            Collections.sort(lg_coups_str_tri);
            Collections.sort(lcp_coups_str_tri);
            assertEquals(lg_coups_str_tri, lcp_coups_str_tri);

            ArrayList<GCoups> lg_coups = gposition.getPseudocoups();
            short[] lcp_coups = cposition.getMoves();

            if (!lg_coups_str.isEmpty()) {
                GCoups gcoups = lg_coups.get(0);// premier coups de la liste
                assertNotNull(gcoups);
                String gcoups_str = GCoups.getString(gcoups);
                assertTrue(lg_coups_str.contains(gcoups_str));
                assertTrue(lcp_coups_str.contains(gcoups_str));

                int index = lcp_coups_str.indexOf(gcoups_str);//non trié
                assertTrue(index != -1);
                short coups = lcp_coups[index];//non trié
                assertNotNull(coups);
                String coups_str = Move.getString(coups);
                assertEquals(coups_str, gcoups_str);

                gposition.setTrait(-cposition.getTrait());
                //GCLE
                gposition.execGCoups(gcoups);// ==> coups des noirs ? trait ?
                //ChessPresso
                cposition.doMove(coups);//==>coups des blancs ? trait ?

                //verifier les coups valides non les positions genere ?
                ArrayList<String> lg_coups_str1 = gposition.getLAN();
                ArrayList<String> lcp_coups_str1 = cposition.getLAN();

                ArrayList<String> lg_coups_str_tri1 = (ArrayList<String>) lg_coups_str1.clone();
                ArrayList<String> lcp_coups_str_tri1 = (ArrayList<String>) lcp_coups_str1.clone();

                Collections.sort(lg_coups_str_tri1);
                Collections.sort(lcp_coups_str_tri1);

                System.out.println(cposition.getPosition().getFEN());
                System.out.println("GCLE");
                System.out.println(lg_coups_str_tri1);
                System.out.println("ChessPresso");
                System.out.println(lcp_coups_str_tri1);

                assertEquals(lg_coups_str_tri1, lcp_coups_str_tri1);
            }
        }
    }
}
