package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import tamil.lang.TamilSimpleCharacter;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import tamil.lang.known.AbstractVinaiyadiPeyarechcham;
import tamil.lang.known.non.derived.IEthirmarrai;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *     A known எதிர்மறைப்பெயரெச்சம்  e.g) தெரியா, தெரியாத   <br/>
 *     ஈறுகெட்ட எதிர்மறைப்பெயரெச்சம்  has the property  EERRUKEDDATHU  set to true. Please  see {@link tamil.lang.known.IKnownWord#getProperty(String)}
 * </p>
 *
 * @author velsubra
 */
public final class EthirmarraipPeyarechcham extends AbstractVinaiyadiPeyarechcham implements IEthirmarrai {
    public EthirmarraipPeyarechcham(TamilWord word, Vinaiyadi vinaiyadi) {
        super(word, vinaiyadi);
       if (word.endsWith(TamilSimpleCharacter.THA) && word.size() > 1) {
           TamilWord withoutLast = word.subWord(0, word.size() -1);
           EthirmarraipPeyarechcham kedda = new EthirmarraipPeyarechcham(withoutLast, vinaiyadi);
           kedda.addProperty("EERRUKEDDATHU", "true");
           PersistenceInterface.addKnown(kedda);

//
//           PersistenceInterface.addKnown(new VinaiMuttu(withoutLast,vinaiyadi, SimpleTense.FUTURE, PaalViguthi.THU));
//           PersistenceInterface.addKnown(new VinaiMuttu(withoutLast,vinaiyadi,SimpleTense.FUTURE, PaalViguthi.A));
           //செல்லாமை
           TamilWord t =  kedda.getWord().duplicate();
           t.addLast(TamilCompoundCharacter.IM_I);
           EthirmarraithozhirPeyar thozhir = new EthirmarraithozhirPeyar(t, vinaiyadi);
           PersistenceInterface.addKnown(thozhir);
       }
    }
}
