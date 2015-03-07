package tamil.lang;

import common.lang.impl.AbstractCharacter;
import common.lang.impl.AbstractWord;
import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.parser.impl.TamilWordListener;

/**
 * <p>
 * Represents a word/String. In fact, it just represents a character sequence. It may not be a recognized word (ie with a meaning).
 * Also, all the characters in the word may not be recognized to a Tamil character.  Please use {@link #isPure()} to know if the word is formed with Tamil characters(#247) only.
 * <p/>
 * So, the word represented by this object may not be a tamil word (சொல் ) at all. See {@link tamil.lang.api.dictionary.TamilDictionary#lookup(TamilWord)}
 * <p/>
 * <p/>
 * </p>
 * <p/>
 * <p>
 * This should be treated as Tamil Sting equivalent of  java.common.lang.lang.String. ie) it can potentially contain spaces and non tamil characters
 * </p>
 *
 * @author velsubra
 * @see #from(String)
 * @see #isPure()
 */
public final class TamilWord extends AbstractWord<AbstractCharacter> implements Comparable {

    private int suggestionCode = 0;

    /**
     * Returns if all the characters in the word are pure.
     *
     * @return true when all characters are pure Tamil characters, false otherwise
     */
    public boolean isPure() {
        for (int i = 0; i < size(); i++) {
            AbstractCharacter ch = get(i);
            if (!ch.isPureTamilLetter()) {
                return false;
            }
        }
        return true;
    }

    /**
     * The suggestion hash code for the given word.
     * This could be same for multiple words that look alike.
     *
     * @return suggestion hash code for this word
     */
    public int suggestionHashCode() {

        if (suggestionCode == 0 && this.size() > 0) {

            suggestionCode = EzhuththuUtils.suggestionHashCode(this);
        }
        return suggestionCode;
    }


    /**
     * Checks if there is any tamil character in the word.
     *
     * @return true, if there is any else false.
     */
    public boolean containsTamilCharacter() {
        for (int i = 0; i < size(); i++) {
            AbstractCharacter ch = get(i);
            if (ch.isPureTamilLetter()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ignores unknown characters and limit the characters to pure tamil word.
     *
     * @return word containing only tamil letters.
     */
    public TamilWord filterToPure() {
        TamilWord ret = new TamilWord();
        for (int i = 0; i < size(); i++) {
            AbstractCharacter ch = get(i);
            if (ch.isPureTamilLetter()) {
                ret.add(ch);
            }
        }
        return ret;
    }

    /**
     * Creates a new tamil word from characters.
     *
     * @param ch tamil characters.
     */
    public TamilWord(AbstractCharacter... ch) {
        for (AbstractCharacter c : ch) {
            add(c);
        }
    }



    /**
     * Checks if the  word starts with  the given word.
     *
     * @param start the given word
     * @return true, if the current word starts with the given word, false otherwise.
     */
    public boolean startsWith(TamilWord start) {
        return startsWith(start, true);
    }

    /**
     * Checks if the current word starts with the given word.
     *
     * @param start     the given word
     * @param fullMatch flag indicating if fullMatch is to be performed while comparing character to character. See {@link TamilCharacter#equals(TamilCharacter, boolean)} for details on full match.
     * @return true if the current word starts wit the given word, false otherwise.
     */
    public boolean startsWith(TamilWord start, boolean fullMatch) {
        if (start.size() > size()) {
            return false;
        }
        for (int i = 0; i < start.size(); i++) {
            AbstractCharacter sh = start.get(i);
            AbstractCharacter ch = get(i);
            if (ch.isTamilLetter()) {
                if (sh.isTamilLetter()) {
                    if (!((TamilCharacter) ch).equals((TamilCharacter) sh, fullMatch)) {

                        return false;
                    }

                } else {
                    return false;
                }
            } else {
                if (sh.isTamilLetter()) {
                    return false;
                } else {
                    continue;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the current word ends with the given set of characters in the order.
     *
     * @param end the array of characters.
     * @return true if the array of characters appear last in the current word in the same order, false otherwise.
     */
    public boolean endsWith(TamilCharacter... end) {
        return endsWith(new TamilWord(end));
    }

    /**
     * Checks if the current word ends with the given word.
     *
     * @param end the given word
     * @return true if it ends with, false otherwise.
     */
    public boolean endsWith(String end) {
        return endsWith(TamilWord.from(end));
    }

    /**
     * Checks if the current word starts with the given word.
     *
     * @param start the given word
     * @return true if it starts with, false otherwise.
     */
    public boolean startsWith(String start) {
        return startsWith(TamilWord.from(start));
    }

    /**
     * Checks if the current word starts with the given set of characters in the order.
     *
     * @param start the array of characters.
     * @return true if the array of characters appear first in the current word in the same order, false otherwise.
     */
    public boolean startsWith(TamilCharacter... start) {
        return startsWith(new TamilWord(start));
    }


    public boolean endsWith(TamilWord end) {
        return endsWith(end, true);
    }

    public boolean endsWith(TamilWord end, boolean fullMatch) {
        if (end.size() > size()) {
            return false;
        }
        return reverse(this.subWord(this.size() - end.size(), this.size())).startsWith(reverse(end), fullMatch);
    }

    public TamilWord subWord(int start, int end) {
        TamilWord word = new TamilWord();
        word.addAll(subList(start, end));
        return word;
    }


    /**
     * Reverses a given word.
     *
     * @param t the word to be reversed.
     * @return the reversed word.
     */
    public static TamilWord reverse(TamilWord t) {
        if (t == null) {
            return null;
        }
        TamilWord sub = new TamilWord();
        for (int i = 0; i < t.size(); i++) {
            sub.add(0, t.get(i));
        }
        return sub;


    }

    /**
     * Create an empty word.
     */
    public TamilWord() {
    }


    /**
     * Duplicates (clones) the current word.
     *
     * @return the cloned word.
     */
    public TamilWord duplicate() {
        TamilWord w = new TamilWord();
        w.addAll(this);
        return w;
    }


    /**
     * Creates a {@link TamilWord} from a String.
     * Please note the reading stops with a space. Consider using {@link TamilSentence} if you need to read multiple words separated with spaces.
     *
     * @param word the string
     * @return the word read.
     */
    public static TamilWord from(String word) {
        if (word == null) {
            return new TamilWord();
        } else {
            return TamilWordListener.readUTF8(word);
        }
    }


    /**
     * Can be used to read along with space. So, it will read the whole java String     if  dontStopAtSpace is ture
     *
     * @param word            the string
     * @param dontStopAtSpace flag to indicate if the reading should stop at space.
     * @return the word read.
     */
    public static TamilWord from(String word, boolean dontStopAtSpace) {
        if (word == null) {
            return new TamilWord();
        } else {
            return TamilWordListener.readUTF8(word, dontStopAtSpace);
        }
    }


    public CharacterDigest getConsonantDigest() {
        CharacterDigest buffer = new CharacterDigest();
        for (AbstractCharacter c : this) {
            buffer.add(c.getConsonantDigest());
        }
        return buffer;
    }


    /**
     * Returns all the consonants (மெய்யெழுத்துகள் )
     *
     * @return word of consonants.
     */
    public TamilWord getConsonants() {
        TamilWord buffer = new TamilWord();
        for (AbstractCharacter c : this) {
            if (c.isTamilLetter()) {
                TamilCharacter t = c.asTamilCharacter();
                if (t.isMeyyezhuththu() || t.isUyirMeyyezhuththu()) {
                    buffer.add(t.getMeiPart());
                }
            }
        }
        return buffer;
    }

    /**
     * Returns all the vowels in the the word.
     *
     * @return word of vowels
     */
    public TamilWord getVowels() {
        TamilWord buffer = new TamilWord();
        for (AbstractCharacter c : this) {
            if (c.isTamilLetter()) {
                TamilCharacter t = c.asTamilCharacter();
                if (t.isUyirezhuththu() || t.isUyirMeyyezhuththu()) {
                    buffer.add(t.getUyirPart());
                }
            }
        }
        return buffer;
    }


    /**
     * Returns vowel digest. Please see {@link TamilCharacter.DIGEST_VOWEL}
     *
     * @return vowel digest
     */
    public CharacterDigest getVowelDigest() {


        CharacterDigest buffer = new CharacterDigest();
        for (AbstractCharacter c : this) {
            buffer.add(c.getVowelDigest());
        }
        return buffer;
    }

    /**
     * Returns Character digest. Please see {@link TamilCharacter.DIGEST_CHAR_TYPE}
     *
     * @return character digest
     */
    public CharacterDigest getCharacterTypeDigest() {


        CharacterDigest buffer = new CharacterDigest();
        for (AbstractCharacter c : this.toArray(new AbstractCharacter[0])) {
            buffer.add(c.getCharacterTypeDigest());
        }
        return buffer;
    }

    /**
     * Builds character digest on sound size(மாத்திரை)
     *
     * @return the sound size digest
     */
    public CharacterDigest getSoundSizeDigest() {


        CharacterDigest buffer = new CharacterDigest();
        for (AbstractCharacter c : this.toArray(new AbstractCharacter[0])) {
            buffer.add(c.getSoundSizeDigest());
        }
        return buffer;
    }

    /**
     * Builds Character Digest on  sound strength.
     *
     * @return the sounds strength digest.
     */
    public CharacterDigest getSoundStrengthDigest() {

        CharacterDigest buffer = new CharacterDigest();
        for (AbstractCharacter c : this.toArray(new AbstractCharacter[0])) {
            buffer.add(c.getSoundStrengthDigest());
        }
        return buffer;
    }


    @Override
    public int compareTo(Object o) {

        TamilWord w = (TamilWord) o;
        if (w.isPure()) {
            if (!isPure()) {
                return -1;
            }
        } else {
            if (isPure()) {
                return 1;
            } else {
                return 0;
            }
        }
        for (int i = 0; i < size(); i++) {
            if (w.size() == i) {
                return 1;
            }
            int comp = get(i).compareTo(w.get(i));
            if (comp != 0) return comp;
        }
        if (w.size() > size()) {
            return -1;
        } else {
            return 0;
        }
    }


    /**
     * Adds space character at the end.
     */
    public void addSpace() {
        add(UnknownCharacter.SPACE);
    }


}
