package my.interest.lang.tamil.bean;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SingleWordSplit {
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public SimpleSplitResult getSplit() {
        return split;
    }

    public void setSplit(SimpleSplitResult split) {
        this.split = split;
    }

    private SimpleSplitResult split;
}
