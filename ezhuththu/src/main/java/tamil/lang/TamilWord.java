package tamil.lang;

import common.lang.impl.AbstractCharacter;
import common.lang.impl.AbstractWord;
import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.parser.impl.TamilWordListener;
import tamil.lang.exception.TamilPlatformException;

import java.util.Collection;

/**
 * <p>
 * Represents a word/String. In fact, it just represents a character sequence. It may not be a recognized word (ie with a meaning).
 * Also, all the characters in the word may not be recognized to a Tamil character.  Please use {@link #isPure()} to know if the word is formed with Tamil characters(#247) only.
 * <p/>
 * So, the word represented by this object may not be a tamil word (சொல் ) at all. See {@link tamil.lang.api.dictionary.TamilDictionary#lookup(TamilWord)}
 * <p/>
 * <p>
 * This should be treated as Tamil String equivalent of  java.lang.String. ie) it can potentially contain spaces and non tamil characters
 * </p>
 *
 * @author velsubra
 * @see #from(String)
 * @see #from(java.lang.String,boolean)
 * @see #isPure()
 */
public final class TamilWord extends AbstractWord<AbstractCharacter> implements Comparable {

    private int suggestionCode = 0;

    public void setLocked() {
        this.locked = true;
    }

    private boolean locked = false;


    /**
     * Replaces the text by starting the search with 0. Please see {@link #replace(int, TamilWord, TamilWord, boolean)}
     */
    public int replace(TamilWord what, TamilWord with, boolean fullmatch) {
        return replace(0, what, with, fullmatch);
    }


    /**
     * Replaces all the occurrences of a text with another text
     *
     * @param what      the text to be found
     * @param with      the text to be replaced with
     * @param fullmatch flag to indicate if the exact match has to be done.
     * @return the number of replacements done.
     */
    public int replaceAll(TamilWord what, TamilWord with, boolean fullmatch) {
        return replaceAll(0, what, with, fullmatch,false);
    }

    /**
     * Replaces all the occurrences of a text with another text
     *
     * @param searchIndex the index to start searching from
     * @param what        the text to be found
     * @param with        the text to be replaced with
     * @param fullmatch   flag to indicate if the exact match has to be done.
     * @param mergeLastOnSingleChar When fulmatch is false , the text which is replacing is merged (இயல்புபுணர்ச்சியுடன்  ) in both the ends
     *                              However when a single character is replacing, this value indicates where that should get merged. When true, the character is merged last, otherwise at the start of the index where the initial text to be found was found..
     * @return the number of replacements done.
     */
    public int replaceAll(int searchIndex, TamilWord what, TamilWord with, boolean fullmatch, boolean mergeLastOnSingleChar) {
        int count = 0;
        int index = replace(searchIndex, what, with, fullmatch, mergeLastOnSingleChar);
        while (index >= 0) {
            count++;
            index = replace(index + with.size(), what, with, fullmatch,mergeLastOnSingleChar);
        }
        return count;


    }

    public int replace(int searchIndex, TamilWord what, TamilWord with, boolean fullmatch) {
        return replace(searchIndex, what, with, fullmatch, false);
    }

    /**
     * Replaces Tamil word with another without any parsing.
     *
     * @param what                  the text to be found
     * @param with                  the text that replaces the found text
     * @param fullmatch             flag to indicate if full match should be done while finding
     * @param mergeLastOnSingleChar When fulmatch is false , the text which is replacing is merged (இயல்புபுணர்ச்சியுடன்  ) in both the ends
     *                              However when a single character is replacing, this value indicates where that should get merged. When true, the character is merged last, otherwise at the start of the index where the initial text to be found was found..
     * @return the index where  the text to be found was found. -1 when nothing was found.
     */
    public int replace(int searchIndex, TamilWord what, TamilWord with, boolean fullmatch, boolean mergeLastOnSingleChar) {
        if (isEmpty()) return -1;
        if (what == null || what.isEmpty())
            throw new TamilPlatformException("Text to be found can not be null or empty");
        int index = indexOf(searchIndex, what, fullmatch);
        if (index < 0) {
            return index;
        } else {
            TamilWord sub = subWord(index, index + what.size());
            if (!fullmatch) {
                TamilWord withoriginal = with;
                with = with.duplicate();
                AbstractCharacter whatFirst = sub.getFirst();
                if (!with.isEmpty()) {
                    if (with.size() != 1 || !mergeLastOnSingleChar ) {
                        AbstractCharacter withFirst = with.getFirst();
                        if (whatFirst != withFirst && whatFirst.isTamilLetter()) {
                            with.removeFirst();
                            with.addFirst(merge((TamilCharacter) whatFirst, (TamilCharacter) withFirst));

                        }
                    }
                } else {
                    AbstractCharacter searchedFirst = what.getFirst();
                    if (whatFirst != searchedFirst && whatFirst.isTamilLetter()) {
                        with.addFirst(remove((TamilCharacter) whatFirst, (TamilCharacter) searchedFirst));
                    }
                }

                if (withoriginal.size() != 1 || mergeLastOnSingleChar) {
                    AbstractCharacter whatLast = sub.getLast();
                    if (!withoriginal.isEmpty()) {
                        if (with.size() != 1 || mergeLastOnSingleChar ) {
                            AbstractCharacter withLast = with.getLast();
                            if (whatLast != withLast && whatLast.isTamilLetter()) {
                                with.removeLast();
                                with.addLast(merge((TamilCharacter) whatLast, (TamilCharacter) withLast));
                            }
                        }
                    } else {
                        if (what.size() > 1) {
                            AbstractCharacter searchedLast = what.getLast();
                            if (whatLast != searchedLast && whatLast.isTamilLetter()) {

                                with.addLast(remove((TamilCharacter) whatLast, (TamilCharacter) searchedLast));
                            }
                        }
                    }
                }
            }
            removeRange(index, index + what.size());
            addAll(index, with);
            return index;
        }

    }


    private static TamilCharacter remove(TamilCharacter what, TamilCharacter with) {
        if (with.isUyirezhuththu() && what.isUyirMeyyezhuththu()) {
            return what.getMeiPart();
        }

        if (with.isMeyyezhuththu() && what.isUyirMeyyezhuththu()) {
            return what.getUyirPart();
        }
        return what;
    }

    private static TamilCharacter merge(TamilCharacter what, TamilCharacter with) {
        if (with.isUyirezhuththu() && what.isUyirMeyyezhuththu()) {
            return what.getMeiPart().addUyir((TamilSimpleCharacter) with);
        }

        if (with.isMeyyezhuththu() && what.isUyirMeyyezhuththu()) {
            return with.addUyir(what.getUyirPart());
        }
        return with;
    }


    /**
     * Returns the starting index of part in the given word.
     * Note: There is no parsing done while searching for the index.
     *
     * @param part      the word to be located
     * @param fullmatch flag to do full match.
     * @return the starting index  of the word when the part is found; -1 otherwise.
     */
    public int indexOf(TamilWord part, boolean fullmatch) {
        return indexOf(0, part, fullmatch);
    }


    /**
     * Returns the starting index of part in the given word.
     * Note: There is no parsing done while searching for the index.
     *
     * @param index     the index from which the search is started.
     * @param part      the word to be located
     * @param fullmatch flag to do full match.
     * @return the starting index  of the word when the part is found; -1 otherwise.
     */
    public int indexOf(int index, TamilWord part, boolean fullmatch) {
        if (isEmpty()) return -1;
        if (part.isEmpty()) return 0;

        while (part.size() <= size() - index) {
            if (startsWith(part, index, fullmatch)) {
                return index;
            } else {
                index++;
            }
        }
        return -1;
    }

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
        return startsWith(start, 0, fullMatch);
    }

    /**
     * Checks if the current word starts with the given word.
     *
     * @param start     the given word
     * @param from      the index to start matching from ; should be >=0
     * @param fullMatch flag indicating if fullMatch is to be performed while comparing character to character. See {@link TamilCharacter#equals(TamilCharacter, boolean)} for details on full match.
     * @return true if the current word starts wit the given word, false otherwise.
     */
    public boolean startsWith(TamilWord start, int from, boolean fullMatch) {
        if (from < 0) {
            from = 0;
        }
        if (start.size() > size() - from) {
            return false;
        }
        for (int i = from; i < size(); i++) {
            if (i - from == start.size()) {
                return true;
            }
            AbstractCharacter sh = start.get(i - from);
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


    public boolean endsWith(TamilCharacter end, boolean full) {
        return endsWith(new TamilWord(end), full);
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

    public boolean startsWith(TamilCharacter start) {
        return startsWith(new TamilWord(start));
    }


    public boolean endsWith(TamilWord end) {
        return endsWith(end, true);
    }


    public boolean endsWith(TamilCharacter end) {
        return endsWith(new TamilWord(end), true);
    }

    public boolean endsWith(TamilWord end, boolean fullMatch) {
        if (end.size() > size()) {
            return false;
        }
        return reverse(this.subWord(this.size() - end.size(), this.size())).startsWith(reverse(end), fullMatch);
    }

    /**
     * Gets the sub word that is contained between two indices.
     * Exception will be raised if start and end indices are not in the valid bound.
     *
     * @param start the start index, inclusive
     * @param end   the end index exclusive
     * @return the sub word. Empty word when the indices are valid and  start=end
     */
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
        for (AbstractCharacter c : this) {
            w.add(c);
        }
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

    private void checkLocked() {
        if (locked) {
            throw new TamilPlatformException("Dictionary word cannot be modified.");
        }
    }

    @Override
    public boolean add(AbstractCharacter character) {
        checkLocked();
        return super.add(character);
    }

    @Override
    public boolean addAll(int index, Collection<? extends AbstractCharacter> c) {
        checkLocked();
        return super.addAll(index, c);
    }

    @Override
    public void clear() {
        checkLocked();
        super.clear();
    }

    @Override
    public AbstractCharacter remove(int index) {
        checkLocked();
        return super.remove(index);
    }

    @Override
    public AbstractCharacter remove() {
        checkLocked();
        return super.remove();
    }

    @Override
    public boolean remove(Object o) {
        checkLocked();
        return super.remove(o);
    }

    @Override
    public AbstractCharacter removeFirst() {
        checkLocked();
        return super.removeFirst();
    }


    @Override
    public AbstractCharacter removeLast() {
        checkLocked();
        return super.removeLast();
    }


    @Override
    public void removeRange(int fromIndex, int toIndex) {
        checkLocked();
        super.removeRange(fromIndex, toIndex);
    }


    /**
     * Returns the total number of codepoints used by all the characters in the word
     *
     * @return the count >=0, by counting all the characters size in terms of number of unicode codepoints used.
     */
    public int getCodePointsTotalCount() {

        int count = 0;
        for (AbstractCharacter c : this.toArray(new AbstractCharacter[0])) {
            count += c.getCodePointsCount();
        }
        return count;
    }


}
