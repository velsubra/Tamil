package my.interest.lang.tamil.punar.handler.verrrrumai;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;
import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.JustAddHandler;
import my.interest.lang.tamil.punar.handler.udambadu.UadambaduMeiHandler;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
abstract class CommonHandler extends AbstractVearrrrumaiHandler {

    public static final TamilWord ITHTHU = TamilWord.from("த்து");



    @Override
    public boolean isOnRightViguthi(TamilWordPartContainer varum) {
        return varum.getWord().startsWith(getUrubu(), false);
    }

    @Override
    public TamilWordPartContainer handleJoin(TamilWordPartContainer nilai, TamilWordPartContainer varum, boolean pronouwn) {
        TamilWord v = varum.getWord();
        TamilWord n = nilai.getWord();
        AbstractPunharchiHandler handler = new VinaiMutruCreationHandler();
        if (nilai.size() == 1) {
            v = getUrubu();
            handler = UadambaduMeiHandler.HANDLER;

        } else {
            if (nilai.getWord().endsWith(TamilCompoundCharacter.IM)) {




                if ( nilai.size() <=2  ||endsWithNedilAndIM(nilai)) {//E.gகலாம் -உயர்திணை
                    n = nilai.getWord();
                } else {
                    n = nilai.getWord().subWord(0, nilai.getWord().size() - 1);
                    n =  JustAddHandler.HANDLER.join(new TamilWordPartContainer(n), new TamilWordPartContainer(ITHTHU)).getWord();

                }

            }
        }

        return handler.join(new TamilWordPartContainer(n), new TamilWordPartContainer(v));


    }
}
