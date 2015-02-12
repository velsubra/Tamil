package my.interest.lang.tamil.internal.api;

import tamil.lang.TamilCharacter;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface TamilCharacterParserListener<T extends List> {
    /**
     *  Called when a tamil character is encountered
     * @param tamil
     * @return  true when parsing is to be finished, false otherwise
     */
    public boolean tamilCharacter(TamilCharacter tamil);
    public boolean nonTamilCharacter(int nonTamil);
    public T get();




}
