package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TokenMatcherResult {

    public TokenRecognizer tokenRecognizerCache = null;

    private TokenMatcherResult(List<TamilWordPartContainer> nilaiMozhi, MATCHING_STATUS status, List<IKnownWord> matchedWords) {
        this.nilaiMozhis = nilaiMozhi;
        this.status = status;
        this.matchedWords = matchedWords;

    }

    public static  TokenMatcherResult Continue() {
        return new TokenMatcherResult(null, MATCHING_STATUS.CONTINUE, null);
    }

    public static  TokenMatcherResult DisContinue() {
        return new TokenMatcherResult(null, MATCHING_STATUS.DISCONTINUE, null);
    }

    public static  TokenMatcherResult Matching(TamilWordPartContainer nilaiMozhi, List<IKnownWord> matchedWords) {
        List<TamilWordPartContainer> nilaiMozhis = new ArrayList<TamilWordPartContainer>();
         nilaiMozhis.add(nilaiMozhi);
        return new TokenMatcherResult(nilaiMozhis, MATCHING_STATUS.MATCHING, matchedWords);
    }

    public static  TokenMatcherResult Matching(List<TamilWordPartContainer> nilaiMozhis, List<IKnownWord> matchedWords) {

        return new TokenMatcherResult(nilaiMozhis, MATCHING_STATUS.MATCHING, matchedWords);
    }

    public boolean isMatching() {
        return status == MATCHING_STATUS.MATCHING.MATCHING;
    }



    public MATCHING_STATUS getStatus() {
        return status;
    }

    public List<TamilWordPartContainer> getNilaiMozhi() {
        return nilaiMozhis;
    }

    private List<TamilWordPartContainer> nilaiMozhis;


    private MATCHING_STATUS status;

    public List<IKnownWord> getMatchedWords() {
        return matchedWords;
    }

    private List<IKnownWord> matchedWords;

    public static enum MATCHING_STATUS {
          DISCONTINUE, CONTINUE, MATCHING;
    }
}
