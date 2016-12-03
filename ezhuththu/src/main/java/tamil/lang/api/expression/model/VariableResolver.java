package tamil.lang.api.expression.model;

import tamil.lang.api.expression.Operand;
import tamil.lang.exception.service.ServiceException;

/**
 * Created by velsubra on 12/1/16.
 * Resolves a variable to an operand of type T
 *
 * @see tamil.lang.api.expression.Evaluator#setVariableResolver(VariableResolver)
 */

public interface VariableResolver<T> {

    /**
     * Callback to resolve a variable to an operand
     * @param variable the variable
     * @return the operand value when resolved
     * @throws ServiceException when the variable is not resolved.
     */
    public Operand<T> resolve(String variable) throws ServiceException;
}
