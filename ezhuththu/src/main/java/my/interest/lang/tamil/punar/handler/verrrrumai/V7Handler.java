package my.interest.lang.tamil.punar.handler.verrrrumai;


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
 class V7Handler extends AbstractVearrrrumaiHandler {

    public static final V4Handler HANDLER = new V4Handler();

    @Override
    public String getName() {
        return "நான்காம்வேற்றுமை  . ";
    }

    public static final TamilWord KANH = TamilWord.from("கண்");

    public static final TamilWord IKANH = TamilWord.from("க்கண்");
    public static final TamilWord INKANH = TamilWord.from("ங்கண்");


    @Override
    public TamilWord getUrubu() {
        return KANH;
    }

    @Override
    public int getNumber() {
        return 7;
    }

    public boolean isOnRightViguthi(TamilWordPartContainer varum) {
        return varum.getWord().equals(KANH);
    }


    @Override
    public TamilWordPartContainer handleJoin(TamilWordPartContainer nilai, TamilWordPartContainer varum, boolean pronoun) {

        TamilWord v = varum.getWord();
        TamilWord n = nilai.getWord();
        AbstractPunarchiHandler handler = new VinaiMutruCreationHandler();
        if (nilai.size() == 1) {
            v = IKANH;
            handler = JustAddHandler.HANDLER;

        } else {
            if (nilai.getWord().endsWith(TamilCompoundCharacter.IM)) {

                if (nilai.size() <=3 && isUyarThinhaipPeyar(nilai.getWord())) {//E.gகலாம் -உயர்திணை
                    n = nilai.getWord();
                    v = IKANH;
                    handler = IyalbuPunarchiHandler.HANDLER;
                } else {
                    n = nilai.getWord().subWord(0, nilai.getWord().size() - 1);
                    v = INKANH;
                    handler = JustAddHandler.HANDLER;
                }

            } else {
                v = KANH;
                if (nilai.isEndingWithUyirMei()) {
                    if (nilai.isEndingWithYagaraUdambadumeiYuir()) {
                        v = IKANH;
                    }
                } else if (nilai.isEndingWithMei()) {
                    v = KANH;
                }

            }

        }
        return handler.join(new TamilWordPartContainer(n), new TamilWordPartContainer(v));
    }
}