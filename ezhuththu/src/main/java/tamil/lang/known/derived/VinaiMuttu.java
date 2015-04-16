package tamil.lang.known.derived;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;

import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import tamil.lang.*;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 * வினைமுற்று எ.கா) வந்தேன், வருவேன் ,வந்துகொண்டிருந்தேன், வந்திருப்பேன்          <br/>
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public final class VinaiMuttu extends VinaiMuttuBase {

    public VinaiMuttu(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, PaalViguthi viguthi) {
        this(word,vinaiyadi,tense,viguthi,false);
    }
    public VinaiMuttu(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, PaalViguthi viguthi,  boolean implicit) {
        super(word, vinaiyadi, tense, viguthi, implicit);

         if (implicit) return;
        //vinaiyaalanhayumpeyar

        if (TamilUtils.isVinaiMuttuAsNoun(tense, viguthi)) {
            VinaiyaalanhaiyumPeyar vp = new VinaiyaalanhaiyumPeyar(word,vinaiyadi,tense, viguthi);
            TamilFactory.getSystemDictionary().add(vp);
        }


        //Add EthirMarrai
        if (tense == SimpleTense.FUTURE && isSimple()) {

            TamilWord[] vas = vinaiyadi.getFutureVigaaram(PaalViguthi.THU);
            if (vas == null || vas.length == 0) {
                vas = new TamilWord[]{vinaiyadi.getWord().duplicate()};
            }
            for (TamilWord va : vas) {
                VinaiMutruCreationHandler handler = new VinaiMutruCreationHandler();
                TamilCharacter last = va.getLast().asTamilCharacter();

                boolean remove_fist = false;
                if (last.isUyirMeyyezhuththu()) {

                    if (va.size() == 2 && va.getFirst().asTamilCharacter().isKurilezhuththu() && last.isUyirMeyyezhuththu() && last.getUyirPart().equals(TamilSimpleCharacter.U)) {
                        //To avoid udambadu mei ; to remove ukkurralh.
                        va.add(0, TamilSimpleCharacter.a);
                        remove_fist = true;
                    }
                }


                handler.add(va);

                //போகாய்          - Should not be udambadu mei!
                //சாகாய்
                if (va.size() == 1 ) { //&& last.getUyirPart().equals(TamilSimpleCharacter.OO)) {
                    handler.add(new TamilWord(TamilCompoundCharacter.IK));
                }

                String v = viguthi.toString().toLowerCase();
                if (viguthi == PaalViguthi.A) {
                    v = PaalViguthi.THU.toString().toLowerCase();

                }
                if (viguthi == PaalViguthi.A || viguthi == PaalViguthi.THU) {
                    handler.add(new TamilWord(TamilSimpleCharacter.aa));
                }

                handler.add(EnglishToTamilCharacterLookUpContext.getArrayValue(v));
                TamilWord vm = handler.getVinaiMutru();
                if (remove_fist) {
                    vm = vm.subWord(1, vm.size());
                }
                TamilFactory.getSystemDictionary().add(new EthirMarraiVinaiMuttu(vm, vinaiyadi, SimpleTense.FUTURE, viguthi, true));
                if (viguthi == PaalViguthi.A || viguthi == PaalViguthi.THU) {
                    //Remove thu.
                    TamilFactory.getSystemDictionary().add(new EthirMarraiVinaiMuttu(vm.subWord(0, vm.size() - 1), vinaiyadi, SimpleTense.FUTURE, viguthi, true));
                }

                if (vm.equals(word)) {
                    System.out.println("-------------Warning VinaiMuttu=EthirMarraiVinaiMuttu:" + word);
                }
            }

        }
    }


}
