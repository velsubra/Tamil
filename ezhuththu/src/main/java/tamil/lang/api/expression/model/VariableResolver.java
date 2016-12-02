package tamil.lang.api.expression.model;

import tamil.lang.api.expression.Operand;
import tamil.lang.exception.service.ServiceException;

/**
 * Created by velsubra on 12/1/16.
 */
public interface VariableResolver<T> {

    public Operand<T> resolve(String variable) throws ServiceException;
}
