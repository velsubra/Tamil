package tamil.lang.api.expression.model;

/**
 * Created by velsubra on 11/30/16.
 */
public abstract class PostFixExpressionItem {
    protected PostFixExpressionItem(String name, String text, int index) {
        this.name = name;
        this.text = text;
        this.sourceIndex = index;
    }

    public String getName() {
        return name;
    }



    private String name;

    public String getText() {
        return text == null ? name : text;
    }



    private String text;

    public int getSourceIndex() {
        return sourceIndex;
    }

    public String toString() {
        return getText();
    }

    private int sourceIndex;
}
