package gposition;

import chesspresso.*;
import chesspresso.position.Position;
import gposition.generateur.*;
import java.util.*;
import org.apache.commons.collections.iterators.*;

public class GPosition implements ICodage {

    private final static GPosition INSTANCE = new GPosition();

    private String fen;
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

    public void execGCoups(GCoups coups) {
        int couleur = trait;
        int caseO = coups.getCaseO();
        int caseX = coups.getCaseX();
        caseEP = -1;
        if (coups.getPiece() == PION && Math.abs(caseX - caseO) == 24) {// avance de 2 cases
            caseEP = couleur == NOIR ? caseX + 12 : caseX - 12;
        }
        if (coups.getTypeDeCoups()
                == ICodage.TYPE_DE_COUPS.Deplacement
                || coups.getTypeDeCoups() == ICodage.TYPE_DE_COUPS.Prise) {
            etats[caseX] = etats[caseO];
            etats[caseO] = VIDE;
        } else if (coups.getTypeDeCoups()
                == ICodage.TYPE_DE_COUPS.EnPassant) {
            // caseX == caseEP
            etats[caseX] = etats[caseO];
            etats[caseO] = VIDE;
            if (couleur == BLANC) {
                etats[caseX + sud] = VIDE;
            } else if (couleur == NOIR) {
                etats[caseX + nord] = VIDE;
            }
        } else if (coups.getTypeDeCoups() == ICodage.TYPE_DE_COUPS.Promotion) {
            etats[caseX] = coups.getPiecePromotion();
            etats[caseO] = VIDE;

        } else if (coups.getTypeDeCoups() == ICodage.TYPE_DE_COUPS.Roque) {

        }
        pseudocoups = new Generateur(this).getCoups();

    }

    public final void init(final String f) throws IllegalArgumentException {
        fen = f;
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

    public Position getPosition() {
        return cp_position.getPosition();
    }

    public int getTrait() {
        return trait;
    }

    public void setTrait(int trait) {
        this.trait = trait;
    }

    public int getCaseEP() {
        return caseEP;
    }

    public String getFen() {
        return fen;
    }

    public ArrayList<GCoups> getPseudocoups() {
        return pseudocoups;
    }

    public void setPseudocoups(ArrayList<GCoups> pseudocoups) {
        this.pseudocoups = pseudocoups;
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
