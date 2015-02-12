package my.interest.lang.tamil.punar;


import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface ThodarMozhiBuilder {

    /**
     *
      * @param know
     * @return
     */
    public ThodarMozhiBuilder accept(IKnownWord know, boolean  clone);

    public boolean  isAtLogicalCompletion();

    public List<IKnownWord>  getThodarMozhi();

    public    ThodarMozhiBuilder cloneContext();
}
