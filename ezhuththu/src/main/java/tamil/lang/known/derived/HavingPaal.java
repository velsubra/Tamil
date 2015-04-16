package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.PaalViguthi;

/**
 * <p>
 *     Anything that has a person (பால் ) associated.
 * </p>
 *
 * @author velsubra
 */
public interface HavingPaal {

    /**
     * Returns the பால்விகுதி
     * @return பால்விகுதி  of the word. non-null.
     */
    public PaalViguthi getPaalViguthi();
    /**
     * Tells if the word represented is உயர்திணைப்பெயர்
     * @return   true for an உயர்திணைப்பெயர்  false otherwise.
     */
    public boolean isUyarThinhai();

}
