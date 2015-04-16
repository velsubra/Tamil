package my.interest.lang.tamil.punar;

import my.interest.lang.tamil.internal.api.IPropertyFinder;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class EnglishToTamilPropertyFinder implements IPropertyFinder {

    public static final EnglishToTamilPropertyFinder FINDER = new EnglishToTamilPropertyFinder();
    @Override
    public String findProperty(String english) {
        return EnglishToTamilCharacterLookUpContext.getBestMatch(english).toString();
    }
}
