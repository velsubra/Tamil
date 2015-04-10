package tamil.lang.known.thodar;

import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 *     Interface for denoting a word is a compound word.
 * </p>
 *
 * @author velsubra
 */
public interface IThodarMozhi extends  IKnownWord {

    /**
     * Gets the list of its components.
     * @return  the list of its parts each of which could be a {@link tamil.lang.known.thodar.IThodarMozhi}
     */
    public List<?extends IKnownWord> getWords();


//    public List<TYPE_SIG> getTypes();
//
//
//
//    //[class tamil.common.lang.lang.known.derived.PanhpupPeyarthThiribu,
//    // class tamil.common.lang.lang.known.derived.KurrippupPeyarechcham,
//    // class tamil.common.lang.lang.known.non.derived.Peyarchchol,
//    // class tamil.common.lang.lang.known.derived.Kaddalhai,
//    // class tamil.common.lang.lang.known.derived.VinaiMuttu,
//    // class tamil.common.lang.lang.known.derived.Peyarechcham,
//    // class tamil.common.lang.lang.known.non.derived.NonStartingIdaichchol,
//    // class tamil.common.lang.lang.known.non.derived.AtomicIsolatedIdai,
//    // class tamil.common.lang.lang.known.derived.Vinaiyechcham,
//    // class tamil.common.lang.lang.known.non.derived.Kalh,
//    // class tamil.common.lang.lang.known.non.derived.Ottu,
//    // class tamil.common.lang.lang.known.derived.ThozhirrPeyar,
//    // class tamil.common.lang.lang.known.derived.EthirmarraipPeyarechcham,
//    // class tamil.common.lang.lang.known.non.derived.NonEndingIdaichChol,
//    // class tamil.common.lang.lang.known.derived.EthirmarraiVinaiyaalanhaiyumPeyar,
//    // class tamil.common.lang.lang.known.non.derived.Vinaiyadi,
//    // class tamil.common.lang.lang.known.derived.PeyarchCholThiribu,
//    // class tamil.common.lang.lang.known.derived.EthirmarraithozhirPeyar,
//    // class tamil.common.lang.lang.known.derived.KurrippupVinaiyarechcham,
//    // class tamil.common.lang.lang.known.derived.VinaiyaalanhaiyumPeyar]
//
//
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
