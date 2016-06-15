package my.interest.lang.tamil.punar.handler.nannool;

import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * னலமுன் றனவும் ணளமுன் டனவும்
 * ஆகுந் தநக்க ளாயுங் காலே
 * <p/>
 * நன்னூல் - 237
 * <p/>
 * ன முன் த – பொன் + தீது = பொற்றீது
 * ல முன் த – கல் + தீது = கற்றீது
 * <p/>
 * ன முன் ந – பொன் + ந்ன்று = பொன்னன்று
 * ல முன் ந – கல் + நன்று = கன்னன்று
 * <p/>
 * ண முன் த – மண் + தீது = மண்டீது
 * ள முன் த – முள் + தீது = முட்டீது
 * <p/>
 * ண முன் ந – மண் + நன்று = மண்ணன்று
 * ள முன் ந – முள் + நன்று = முண்ணன்று ??
 * </p>
 *
 * @author velsubra
 */
public class NannoolHandler237 extends AbstractPunharchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி-237 -  னலமுன் றனவும் ணளமுன் டனவும் ஆகும்  \"தந\" க்கள் ";
    }
    public static final NannoolHandler237 HANDLER = new NannoolHandler237();

    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaiPart, TamilWordPartContainer varum) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        if (varum.isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.IRR)) {
            if (varum.getWord().get(1).asTamilCharacter().isUyirMeyyezhuththu()) {
                TamilWord p1 = nilaiPart.getWord().duplicate();
                p1.add(TamilCompoundCharacter.IN);
                TamilWord p2 = new TamilWord(TamilCompoundCharacter.ITH.addUyir(varum.getWord().get(1).asTamilCharacter().getUyirPart()));
                if (varum.getWord().size() > 2) {
                    p2.addAll(varum.getWord().subWord(2, varum.getWord().size()));
                }

                list.add(new TamilWordSplitResult(p1, p2));

                p1 = nilaiPart.getWord().duplicate();
                p1.add(TamilCompoundCharacter.IL);
                p2 = new TamilWord(TamilCompoundCharacter.ITH.addUyir(varum.getWord().get(1).asTamilCharacter().getUyirPart()));
                if (varum.getWord().size() > 2) {
                    p2.addAll(varum.getWord().subWord(2, varum.getWord().size()));
                }

                list.add(new TamilWordSplitResult(p1, p2));
            }
        } else if (varum.isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.IN)) {
            if (varum.getWord().get(1).asTamilCharacter().isUyirMeyyezhuththu()) {
                TamilWord p1 = nilaiPart.getWord().duplicate();
                p1.add(TamilCompoundCharacter.IN);
                TamilWord p2 = new TamilWord(TamilCompoundCharacter.INTH.addUyir(varum.getWord().get(1).asTamilCharacter().getUyirPart()));
                if (varum.getWord().size() > 2) {
                    p2.addAll(varum.getWord().subWord(2, varum.getWord().size()));
                }

                list.add(new TamilWordSplitResult(p1, p2));

                p1 = nilaiPart.getWord().duplicate();
                p1.add(TamilCompoundCharacter.IL);
                p2 = new TamilWord(TamilCompoundCharacter.INTH.addUyir(varum.getWord().get(1).asTamilCharacter().getUyirPart()));
                if (varum.getWord().size() > 2) {
                    p2.addAll(varum.getWord().subWord(2, varum.getWord().size()));
                }

                list.add(new TamilWordSplitResult(p1, p2));
            }
        } else if (varum.isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.INNN)) {
            if (varum.getWord().get(1).asTamilCharacter().isUyirMeyyezhuththu()) {
                TamilWord p1 = nilaiPart.getWord().duplicate();
                p1.add(TamilCompoundCharacter.INNN);
                TamilWord p2 = new TamilWord(TamilCompoundCharacter.INTH.addUyir(varum.getWord().get(1).asTamilCharacter().getUyirPart()));
                if (varum.getWord().size() > 2) {
                    p2.addAll(varum.getWord().subWord(2, varum.getWord().size()));
                }

                list.add(new TamilWordSplitResult(p1, p2));

                p1 = nilaiPart.getWord().duplicate();
                p1.add(TamilCompoundCharacter.ILL);
                p2 = new TamilWord(TamilCompoundCharacter.INTH.addUyir(varum.getWord().get(1).asTamilCharacter().getUyirPart()));
                if (varum.getWord().size() > 2) {
                    p2.addAll(varum.getWord().subWord(2, varum.getWord().size()));
                }

                list.add(new TamilWordSplitResult(p1, p2));
            }
        } else if (varum.isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.IDD)) {
            if (varum.getWord().get(1).asTamilCharacter().isUyirMeyyezhuththu()) {
                TamilWord p1 = nilaiPart.getWord().duplicate();
                p1.add(TamilCompoundCharacter.ILL);
                TamilWord p2 = new TamilWord(TamilCompoundCharacter.ITH.addUyir(varum.getWord().get(1).asTamilCharacter().getUyirPart()));
                if (varum.getWord().size() > 2) {
                    p2.addAll(varum.getWord().subWord(2, varum.getWord().size()));
                }

                list.add(new TamilWordSplitResult(p1, p2));


            }
        } else if (varum.isStartingWithTwoConsonantsOfType(TamilCompoundCharacter.INNN, TamilCompoundCharacter.IDD)) {
            if (varum.getWord().get(1).asTamilCharacter().isUyirMeyyezhuththu()) {
                TamilWord p1 = nilaiPart.getWord().duplicate();
                p1.add(TamilCompoundCharacter.INNN);
                TamilWord p2 = new TamilWord(TamilCompoundCharacter.ITH.addUyir(varum.getWord().get(1).asTamilCharacter().getUyirPart()));
                if (varum.getWord().size() > 2) {
                    p2.addAll(varum.getWord().subWord(2, varum.getWord().size()));
                }

                list.add(new TamilWordSplitResult(p1, p2));

            }
        }
        return list;

    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilaiPart, TamilWordPartContainer varum) {
        if (!varum.isStartingWithUyirMei()) return null;
        if (!nilaiPart.isEndingWithMei()) return null;

        if (nilaiPart.getWord().endsWith(TamilCompoundCharacter.IN) || nilaiPart.getWord().endsWith(TamilCompoundCharacter.IL)) {
            if (varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.ITH)) {
                TamilWord w = nilaiPart.getWord().subWord(0, nilaiPart.size() - 1);
                w.add(TamilCompoundCharacter.IRR);
                w.add(TamilSimpleCharacter.RRA.addUyir(varum.getWord().get(0).asTamilCharacter().getUyirPart()));
                w.addAll(varum.getWord().subWord(1, varum.size()));

                return new TamilWordPartContainer(w);
            } else if (varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.INTH)) {

                    TamilWord w = nilaiPart.getWord().subWord(0, nilaiPart.size() - 1);
                    w.add(TamilCompoundCharacter.IN);
                    w.add(TamilSimpleCharacter.NA.addUyir(varum.getWord().get(0).asTamilCharacter().getUyirPart()));
                    w.addAll(varum.getWord().subWord(1, varum.size()));

                    return new TamilWordPartContainer(w);

            }
        } else if (nilaiPart.getWord().endsWith(TamilCompoundCharacter.INNN)) {
            if (varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.ITH)) {
                TamilWord w = nilaiPart.getWord().subWord(0, nilaiPart.size() - 1);
                w.add(TamilCompoundCharacter.INNN);
                w.add(TamilSimpleCharacter.DA.addUyir(varum.getWord().get(0).asTamilCharacter().getUyirPart()));
                w.addAll(varum.getWord().subWord(1, varum.size()));

                return new TamilWordPartContainer(w);
            } else if (varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.INTH)) {
                TamilWord w = nilaiPart.getWord().subWord(0, nilaiPart.size() - 1);
                w.add(TamilCompoundCharacter.INNN);
                w.add(TamilSimpleCharacter.NNNA.addUyir(varum.getWord().get(0).asTamilCharacter().getUyirPart()));
                w.addAll(varum.getWord().subWord(1, varum.size()));

                return new TamilWordPartContainer(w);
            }
        } else if (nilaiPart.getWord().endsWith(TamilCompoundCharacter.ILL)) {
            if (varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.ITH)) {
                TamilWord w = nilaiPart.getWord().subWord(0, nilaiPart.size() - 1);
                w.add(TamilCompoundCharacter.IDD);
                w.add(TamilSimpleCharacter.DA.addUyir(varum.getWord().get(0).asTamilCharacter().getUyirPart()));
                w.addAll(varum.getWord().subWord(1, varum.size()));

                return new TamilWordPartContainer(w);
            } else if (varum.isStartingWithOneConsonantOfType(TamilCompoundCharacter.INTH)) {
                TamilWord w = nilaiPart.getWord().subWord(0, nilaiPart.size() - 1);
                w.add(TamilCompoundCharacter.INNN);
                w.add(TamilSimpleCharacter.NNNA.addUyir(varum.getWord().get(0).asTamilCharacter().getUyirPart()));
                w.addAll(varum.getWord().subWord(1, varum.size()));

                return new TamilWordPartContainer(w);
            }

        }


        return null;

    }
}
