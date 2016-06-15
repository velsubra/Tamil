package my.interest.lang.tamil.punar.handler.verrrrumai;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;
import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.IyalbuPunarchiHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.JustAddHandler;
import my.interest.lang.tamil.punar.handler.udambadu.UadambaduMeiHandler;
import my.interest.lang.tamil.punar.handler.uyirvarin.UyirvarinUkkuralMeiVittodumHandler;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;

/**
 * Created by velsubra on 6/14/16.
 */
public abstract class V4HandlerBase extends AbstractVearrrrumaiHandler {



    @Override
    public String getName() {
        return "நான்காம்வேற்றுமை  . ";
    }

    public static final TamilWord KU = TamilWord.from("கு");

    public static final TamilWord IRRKU = TamilWord.from("இற்கு ");
    public static final TamilWord UKKU = TamilWord.from("உக்கு");
   // public static final TamilWord ITHIRRKU = TamilWord.from("த்திற்கு");
    public static final TamilWord IKKU = TamilWord.from("க்கு");


    @Override
    public TamilWordPartContainer translateForProNoun(TamilWordPartContainer nilai) {
        //உங்கள்
        //எனக்கு
        if (!nilai.getWord().endsWith(TamilCompoundCharacter.ILL)) {
            return IyalbuPunarchiHandler.HANDLER.join(nilai, new TamilWordPartContainer(new TamilWord(TamilSimpleCharacter.a)));
        } else {
            return super.translateForProNoun(nilai);
        }
    }


    @Override
    public int getNumber() {
        return 4;
    }

    public boolean isOnRightViguthi(TamilWordPartContainer varum) {
        return varum.getWord().startsWith(getUrubu(), true);

    }


    @Override
    public TamilWordPartContainer handleJoin(TamilWordPartContainer nilai, TamilWordPartContainer varum, boolean pronoun) {


        TamilWord v = getUrubu().duplicate();
        TamilWord n = nilai.getWord();
        AbstractPunharchiHandler handler = new VinaiMutruCreationHandler();
        if (nilai.size() == 1) {

            handler = UadambaduMeiHandler.HANDLER;

        } else {
            if (nilai.getWord().endsWith(TamilCompoundCharacter.IM)) {

                if (endsWithNedilAndIM(nilai)) {//E.gகலாம் -உயர்திணை
                    n = nilai.getWord();

                    handler = IyalbuPunarchiHandler.HANDLER;
                } else {
                    TamilWord withoutM = nilai.getWord().subWord(0, nilai.getWord().size() - 1);
                    n = n.duplicate();
                    //add thth
                    n.clear();
                    n.add(TamilCompoundCharacter.ITH);
                    n.add(TamilCompoundCharacter.ITH_U);
                    withoutM.addAll(n);
                    n = withoutM;
                    handler = UyirvarinUkkuralMeiVittodumHandler.HANDLER;
                }

            } else {

                if (nilai.isEndingWithUyirMei()) {
                    if (nilai.isEndingWithYagaraUdambadumeiYuir() || pronoun) {
                        v = IKKU;
                    }
                }

            }

        }
        TamilWord torepalce = varum.getWord().duplicate();
        torepalce.replace(getUrubu(), v, true);

        return handler.join(new TamilWordPartContainer(n), new TamilWordPartContainer(torepalce));
    }

}