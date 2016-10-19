package tamil.util.regex;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.impl.rx.RxRegistry;
import tamil.lang.TamilWord;

import java.util.HashMap;
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
public final class TamilMatcher implements MatchResult, SimpleMatcher {
    Matcher javaMatcher = null;
    TamilWord tamilWord = null;
    TamilPattern tamilPattern = null;

    public TamilMatcher( TamilPattern tamilPattern, Matcher javaMatcher, TamilWord tamilWord) {
        this.javaMatcher = javaMatcher;
        this.tamilWord = tamilWord;
        this.tamilPattern = tamilPattern;
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

    public String getPattern() {
        return tamilPattern.getTamilPattern();
    }

    public boolean isTransposed() {
        return false;
    }


    public int getSourceLength() {
        return tamilWord.size();
    }



    public boolean matches() {
        return javaMatcher.matches();
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

    public String group(String name) {
        return javaMatcher.group(name);
    }


    public String group(int group) {
        return javaMatcher.group(group);
    }

    public MatchingModel buildMatchingModel() {
        return TamilUtils.buildMatchingModel(tamilPattern,javaMatcher);
    }


    public int groupCount() {
        return javaMatcher.groupCount();
    }



}
