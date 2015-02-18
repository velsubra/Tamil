package my.interest.lang.tamil.bean;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TranslitResult {
    private String tamilWord;

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    private String actual;

    public void setKnown(boolean known) {
        this.known = known;
    }

    public boolean isParsed() {
        return parsed;
    }

    public void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

    private boolean parsed;


    private boolean known;

    public boolean isKnown() {
        return known;
    }




    public String getTamilWord() {
        return tamilWord;
    }

    public void setTamilWord(String tamilWord) {
        this.tamilWord = tamilWord;
    }
}
