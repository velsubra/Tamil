package tamil.lang.api.persist.manager;

import tamil.lang.api.regex.RxDescription;
import tamil.lang.exception.TamilPlatformException;

import java.util.Map;
import java.util.Set;

/**
 * Created by velsubra on 6/12/16.
 */
public interface CustomRegXManager {

    /**
     * Gets all the custom defined expressions
     *
     * @return empty list if none is defined.
     */
    public Set<? extends RxDescription> getRegXDescriptions();


    /**
     * Gets all the definitions.
     * key - the expression name ; This may be defined as a top level expression through {@link #addExpression(String, String)}
     * value - the value of the expression; this can contain patterns such as ${previously defined or known expression}
     *
     * @return empty map if nothing is defined
     */
    public Map<String, String> getDefinitions();

    /**
     * Adds and exposes a expression. Please note that the expression should have been defined using the method {@link #addExpression(String, String)}
     *
     * @param expressionName the expression name that the method {@link #getRegXDescriptions()} would return
     * @param description    the description of the RX
     */
    public void addExpression(String expressionName, String description);

    /**
     * Deletes and expression
     *
     * @param expressionName
     */
    public void deleteExpression(String expressionName);


    /**
     * Saves all the changes after compiling all the expression.
     */
    public void save() throws TamilPlatformException;
}
