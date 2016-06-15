package my.interest.lang.tamil.punar.handler.udambadu;

import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunharchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
class YagaraUadambaduMeiHandler extends AbstractPunharchiHandler {
    @Override
    public String getName() {
        return "யகர உடம்படுமெய்த்தோன்றல் ";
    }


    @Override
    public List<TamilWordSplitResult> split(TamilWordPartContainer nilai, TamilWordPartContainer varumPart) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();


        if (!varumPart.isStartingWithOneConsonantOfType(TamilCompoundCharacter.IY) || varumPart.isStartingWithMei()) return null;
        if (!nilai.isEndingWithYagaraUdambadumeiYuir()) return null;
        TamilWord varumPartWithUyir = new TamilWord();
        varumPartWithUyir.add(varumPart.getWord().get(0).asTamilCharacter().getUyirPart());
        varumPartWithUyir.addAll(varumPart.getWord().subList(1, varumPart.size()));

        TamilWordSplitResult split1 = new TamilWordSplitResult();
        split1.add(nilai);
        split1.add(new TamilWordPartContainer(varumPartWithUyir));
        list.add(split1);


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        if (!varum.isStartingWithUyir() || !nilai.isEndingWithYagaraUdambadumeiYuir()) return null;
        TamilWord ret = nilai.getWord().duplicate();
        ret.add(TamilCompoundCharacter.IY.addUyir(varum.getWord().get(0).asTamilCharacter().getUyirPart()));
        ret.addAll(varum.getWord().subList(1, varum.getWord().size()));
        return new TamilWordPartContainer(ret);
    }
}