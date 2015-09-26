package tamil.util.regex;

import tamil.lang.TamilWord;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;

/**
 * <p>
 *     The Tamil matcher that returns the indices that point the Tamil character.
 *     That way, it is different from standard Java {@link java.util.regex.Matcher}.
 *
 * </p>
 *
 * @author velsubra
 */
public final class TamilMatcher implements MatchResult {
    Matcher javaMatcher = null;
    TamilWord tamilWord = null;

    public TamilMatcher(Matcher javaMatcher, TamilWord tamilWord) {
        this.javaMatcher = javaMatcher;
        this.tamilWord = tamilWord;
    }

    public int start() {
        int start = javaMatcher.start();
        return tamilWord.getIndexForCodepointIndex(start);
    }


    public int start(int group) {
        int start = javaMatcher.start(group);
        return tamilWord.getIndexForCodepointIndex(start);
    }


    public int end() {
        int end = javaMatcher.end();
        return tamilWord.getIndexForCodepointIndex(end -1) + 1;
    }

    public boolean find() {
        return javaMatcher.find();
    }

    public boolean find(int start) {
        start = tamilWord.getCodepointIndexForIndex(start);
        return javaMatcher.find(start);
    }


    public int end(int group) {
        int end = javaMatcher.end(group);
        return tamilWord.getIndexForCodepointIndex(end -1 ) + 1;
    }


    public String group() {
        return javaMatcher.group();
    }


    public String group(int group) {
        return javaMatcher.group(group);
    }


    public int groupCount() {
        return javaMatcher.groupCount();
    }
}
