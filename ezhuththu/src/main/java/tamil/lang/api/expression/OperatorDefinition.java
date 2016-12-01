package tamil.lang.api.expression;

/**
 * Created by velsubra on 11/28/16.
 */
public class OperatorDefinition {

    private String name;
    private float precedence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrecedence() {
        return precedence;
    }

    public void setPrecedence(float precedence) {
        this.precedence = precedence;
    }
}
