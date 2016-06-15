package my.interest.lang.tamil.punar.handler.verrrrumai;


import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;
import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.IyalbuPunarchiHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.JustAddHandler;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
 class V7Handler extends AbstractVearrrrumaiHandler {

    public static final V7Handler HANDLER = new V7Handler();

    @Override
    public String getName() {
        return "ஏழாம்வேற்றுமை  . ";
    }

    public static final TamilWord KANH = TamilWord.from("கண்");

    public static final TamilWord IKKANH = TamilWord.from("க்கண்");
    public static final TamilWord INGANH = TamilWord.from("ங்கண்");


    @Override
    public TamilWord getUrubu() {
        return KANH;
    }

    @Override
    public int getNumber() {
        return 7;
    }

    public boolean isOnRightViguthi(TamilWordPartContainer varum) {
        return varum.getWord().startsWith(getUrubu(), true);
    }


    @Override
    public TamilWordPartContainer handleJoin(TamilWordPartContainer nilai, TamilWordPartContainer varum, boolean pronoun) {

        TamilWord v = getUrubu();
        TamilWord n = nilai.getWord();
        AbstractPunharchiHandler handler = new VinaiMutruCreationHandler();
        if (nilai.size() == 1) {
            v = IKKANH;
            handler = JustAddHandler.HANDLER;

        } else {
            if (nilai.getWord().endsWith(TamilCompoundCharacter.IM)) {

                if (nilai.size() <=3 && endsWithNedilAndIM(nilai)) {//E.gகலாம் -உயர்திணை
                    n = nilai.getWord();
                    v = IKKANH;
                    handler = IyalbuPunarchiHandler.HANDLER;
                } else {
                    n = nilai.getWord().subWord(0, nilai.getWord().size() - 1);
                    v = INGANH;
                    handler = JustAddHandler.HANDLER;
                }

            } else {
                v = KANH;
                if (nilai.isEndingWithUyirMei()) {
                    if (nilai.isEndingWithYagaraUdambadumeiYuir()) {
                        v = IKKANH;
                    }
                } else if (nilai.isEndingWithMei()) {
                    v = KANH;
                }

            }

        }
        TamilWord  torepalce  = varum.getWord().duplicate();
        torepalce.replace(getUrubu(),v,true);
        return handler.join(new TamilWordPartContainer(n), new TamilWordPartContainer(torepalce));
    }
}