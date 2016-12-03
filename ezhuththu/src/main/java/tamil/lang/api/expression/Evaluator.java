package tamil.lang.api.expression;

import tamil.lang.TamilFactory;
import tamil.lang.api.expression.model.*;
import tamil.lang.exception.service.ServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by velsubra on 11/28/16.
 * <p>
 * Generic evaluator for an post-fix generic expression!
 */
public abstract class Evaluator<T> {

    private Map<String, UnaryOperator<T>> unaryOperators = new HashMap();
    private Map<String, BinaryOperator<T>> binaryOperators = new HashMap();
    protected Map<String, OperatorDefinition> operatorDefinitionMap = new HashMap<String, OperatorDefinition>();
    protected Map<String, Operand<T>> knownOperands = new HashMap();
    VariableResolver<T> resolver = null;


    /**
     * Sets an variable resolver
     * @param resolver the resolver
     */
    public void setVariableResolver(VariableResolver<T> resolver) {
        this.resolver = resolver;
    }

    /**
     * Registers an Unary operator
     * @param operator the operator
     */
    public void registerOperator(UnaryOperator<T> operator) {
        operatorDefinitionMap.put(operator.getOperator().getName(), operator.getOperator());
        unaryOperators.put(operator.getOperator().getName(), operator);
    }

    /**
     * Registers a binary operator
     * @param operator the operator
     */
    public void registerOperator(BinaryOperator<T> operator) {
        operatorDefinitionMap.put(operator.getOperator().getName(), operator.getOperator());
        binaryOperators.put(operator.getOperator().getName(), operator);
    }

    /**
     * Registers known constant values for a name
     * @param name the name of the constance
     * @param value the value of the constant
     *
     * @see #setVariableResolver(VariableResolver)  for all dynamic resolution.
     */
    public void registerKnownOperand(String name, Operand<T> value) {
        knownOperands.put(name, value);
    }


    /**
     * Method to override for sub classes that are responsible for converting the expression to the post-fix form.
     * @param infix the expression typically in the in-fix form
     * @return the post-fix expression as a list of items
     * @throws ServiceException
     */
    public abstract List<? extends PostFixExpressionItem> generatePostFix(String infix) throws ServiceException;


    /**
     * The evaluator
     * @param ex  the expression
     * @return the evaluated value.
     * @throws ServiceException
     */
    public Operand<T> evaluate(String ex) throws ServiceException {
        List<? extends PostFixExpressionItem> items = generatePostFix(ex);
        if (items.isEmpty()) {
            throw new ServiceException("Null expression!");
        }
        Stack<Operand<T>> s = new Stack<Operand<T>>();
        for (PostFixExpressionItem item : items) {
            if (VariableItem.class.isAssignableFrom(item.getClass())) {
                Operand<T> value = knownOperands.get(item.getText());
                if (value == null) {
                    value = knownOperands.get(TamilFactory.getTransliterator(null).transliterate(item.getText()).toString());
                }
                if (value == null && resolver != null) {
                    value = resolver.resolve(item.getText());
                }

                if (value == null) {
                    throw new ServiceException("Unrecognized operand:" + item.getText() + "  at:" + item.getSourceIndex());
                }

                s.push(value);
            } else if (UnaryOperatorItem.class.isAssignableFrom(item.getClass())) {
                if (s.isEmpty()) {
                    throw new ServiceException("Illegal Unary operator location" + item.getText() + "  at:" + item.getSourceIndex());
                }
                UnaryOperator<T> operator = unaryOperators.get(item.getName());
                if (operator == null) {
                    operator = unaryOperators.get(item.getText());
                }
                if (operator == null) {
                    throw new ServiceException("Unrecognized operator:" + item.getText() + "  at:" + item.getSourceIndex());
                }
                s.push(operator.perform(s.pop()));
            } else if (BinaryOperatorItem.class.isAssignableFrom(item.getClass())) {
                if (s.isEmpty()) {
                    throw new ServiceException("Illegal Binary operator location" + item.getText() + "  at:" + item.getSourceIndex());
                }
                BinaryOperator<T> operator = binaryOperators.get(item.getName());
                if (operator == null) {
                    operator = binaryOperators.get(item.getText());
                }
                if (operator == null) {
                    throw new ServiceException("Unrecognized operator:" + item.getText() + "  at:" + item.getSourceIndex());
                }
                Operand<T> right = s.pop();
                if (s.isEmpty()) {
                    throw new ServiceException("Illegal Binary operator location" + item.getText() + "  at:" + item.getSourceIndex());
                }
                Operand<T> left = s.pop();
                s.push(operator.perform(left, right));
            } else {
                throw new ServiceException("Unrecognized item " + item.getClass().getName());
            }

        }
        Operand<T> ret = s.pop();
        if (!s.isEmpty()) {
            throw new ServiceException("Evaluation encountered an illegal state! Stack is not empty.");
        }
        return ret;
    }
}
