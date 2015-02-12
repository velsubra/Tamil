package my.interest.lang.tamil.punar.handler.verrrrumai;


import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;
import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import my.interest.lang.tamil.punar.handler.WordsJoinHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.IyalbuPunarchiHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.JustAddHandler;
import my.interest.lang.tamil.punar.handler.udambadu.UadambaduMeiHandler;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
 class V4Handler extends AbstractVearrrrumaiHandler {

    public static final V4Handler HANDLER = new V4Handler();

    @Override
    public String getName() {
        return "நான்காம்வேற்றுமை  . ";
    }

    public static final TamilWord KU = TamilWord.from("கு");

    public static final TamilWord IRRKU = TamilWord.from("இற்கு ");
    public static final TamilWord UKKU = TamilWord.from("உக்கு");
    public static final TamilWord ITHIRRKU = TamilWord.from("த்திற்கு");
    public static final TamilWord IKKU = TamilWord.from("க்கு");



    @Override
    public TamilWordPartContainer translateForProNoun(TamilWordPartContainer nilai) {
        return IyalbuPunarchiHandler.HANDLER.join(nilai, new TamilWordPartContainer(new TamilWord(TamilSimpleCharacter.a)));
    }

    public boolean isOnRightViguthi(TamilWordPartContainer varum) {
        return varum.getWord().equals(KU);
    }


    @Override
    public TamilWordPartContainer handleJoin(TamilWordPartContainer nilai, TamilWordPartContainer varum, boolean pronoun) {


        TamilWord v = varum.getWord();
        TamilWord n = nilai.getWord();
        AbstractPunarchiHandler handler = new VinaiMutruCreationHandler();
        if (nilai.size() == 1) {
            v = IRRKU;
            handler = UadambaduMeiHandler.HANDLER;

        } else {
            if (nilai.getWord().endsWith(TamilCompoundCharacter.IM)) {

                if (nilai.size() <=3 && isUyarThinhaipPeyar(nilai.getWord())) {//E.gகலாம் -உயர்திணை
                    n = nilai.getWord();
                    v = IRRKU;
                    handler = IyalbuPunarchiHandler.HANDLER;
                } else {
                    n = nilai.getWord().subWord(0, nilai.getWord().size() - 1);
                    v = ITHIRRKU;
                    handler = JustAddHandler.HANDLER;
                }

            } else {
                v = IRRKU;
                if (nilai.isEndingWithUyirMei()) {
                    if (nilai.isEndingWithYagaraUdambadumeiYuir() || pronoun) {
                        v = IKKU;
                    }
                } else if (nilai.isEndingWithMei()) {
                    v = UKKU;
                }

            }

        }
        return handler.join(new TamilWordPartContainer(n), new TamilWordPartContainer(v));
    }

}