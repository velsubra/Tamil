package tamil.lang.known.thodar;

import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface IThodarMozhi extends  IKnownWord {
    public List<?extends IKnownWord> getWords();
    public List<TYPE_SIG> getTypes();



    //[class tamil.lang.known.derived.PanhpupPeyarthThiribu,
    // class tamil.lang.known.derived.KurrippupPeyarechcham,
    // class tamil.lang.known.non.derived.Peyarchchol,
    // class tamil.lang.known.derived.Kaddalhai,
    // class tamil.lang.known.derived.VinaiMuttu,
    // class tamil.lang.known.derived.Peyarechcham,
    // class tamil.lang.known.non.derived.NonStartingIdaichchol,
    // class tamil.lang.known.non.derived.AtomicIsolatedIdai,
    // class tamil.lang.known.derived.Vinaiyechcham,
    // class tamil.lang.known.non.derived.Kalh,
    // class tamil.lang.known.non.derived.Ottu,
    // class tamil.lang.known.derived.ThozhirrPeyar,
    // class tamil.lang.known.derived.EthirmarraipPeyarechcham,
    // class tamil.lang.known.non.derived.NonEndingIdaichChol,
    // class tamil.lang.known.derived.EthirmarraiVinaiyaalanhaiyumPeyar,
    // class tamil.lang.known.non.derived.Vinaiyadi,
    // class tamil.lang.known.derived.PeyarchCholThiribu,
    // class tamil.lang.known.derived.EthirmarraithozhirPeyar,
    // class tamil.lang.known.derived.KurrippupVinaiyarechcham,
    // class tamil.lang.known.derived.VinaiyaalanhaiyumPeyar]


    public static enum TYPE_SIG {
        RV, //Root Verb
        N, //Noun
        VM, //Vinai muttu
        I,  //Idaichchol
        PE,  //Peyarechcham
        VE,  //vinaiyechcham
        PT,  //PanhpupPeyarthThiribu
        O,  //Ottu
        UN,  //UNKNOWN
    }

}
