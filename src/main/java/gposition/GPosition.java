package gposition;

import chesspresso.*;
import gposition.generateur.*;
import java.util.*;
import org.apache.commons.collections.iterators.*;

public class GPosition implements ICodage{

    private final static GPosition INSTANCE = new GPosition();

//    private String fen;
    private final int[] etats;
    private int trait;
    private ArrayList<GCoups> pseudocoups;
    private CPosition cp_position;
    private boolean droitPetitRoqueBlanc;
    private boolean droitGrandRoqueNoir;
    private boolean droitGrandRoqueBlanc;
    private boolean droitPetitRoqueNoir;
    private int caseEP;

    private GPosition() {
        etats = new int[NB_CELLULES];
    }

    public static GPosition getInstance() {
        return INSTANCE;
    }

    public final void init(final String fen) throws IllegalArgumentException {
//        this.fen = fen;
        cp_position = CPosition.getInstance();
        cp_position.init(fen);

        for (int caseO = 0; caseO < NB_CELLULES; caseO++) {
            etats[caseO] = OUT;
        }

        ArrayIterator itetats = new ArrayIterator(cp_position.getEtats());
        int indice = 0;

        while (itetats.hasNext()) {
            Integer e = (Integer) itetats.next();

            etats[CASES[indice]] = e;
            indice++;
        }

        if (cp_position.getTrait() == Chess.WHITE) {
            trait = BLANC;
        } else {
            trait = NOIR;
        }

        droitPetitRoqueBlanc = cp_position.getDroitPetitRoqueBlanc();
        droitPetitRoqueNoir = cp_position.getDroitPetitRoqueNoir();
        droitGrandRoqueBlanc = cp_position.getDroitGrandRoqueBlanc();
        droitGrandRoqueNoir = cp_position.getDroitGrandRoqueNoir();

        if (cp_position.getCaseEP() == PAS_DE_CASE) {
            caseEP = -1;
        } else {
            caseEP = CASES[cp_position.getCaseEP()];
        }

        pseudocoups = new Generateur(this).getCoups();

    }

    public int[] getEtats() {
        return etats;
    }

    public int getTrait() {
        return trait;
    }

    public int getCaseEP() {
        return caseEP;
    }

    public ArrayList<GCoups> getPseudocoups() {
        return pseudocoups;
    }

    public CPosition getCp_position() {
        return cp_position;
    }

    public boolean getDroitPetitRoqueBlanc() {
        return droitPetitRoqueBlanc;
    }

    public boolean getDroitPetitRoqueNoir() {
        return droitPetitRoqueNoir;
    }

    public boolean getDroitGrandRoqueNoir() {
        return droitGrandRoqueNoir;
    }

    public boolean getDroitGrandRoqueBlanc() {
        return droitGrandRoqueBlanc;
    }

    public GPosition copie() {
        GPosition position = new GPosition();

        System.arraycopy(etats, 0, position.etats, 0, NB_CELLULES);

        return position;
    }

    public ArrayList<String> getLAN() {
        ArrayList<String> result = new ArrayList();
        for (GCoups c : pseudocoups) {
            result.add(GCoups.getString(c));
        }
//        Collections.sort(result);
        return result;
    }

    @Override
    public String toString() {
        return getLAN().toString();

    }


}
