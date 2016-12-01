package tamil.lang.api.expression;

import tamil.lang.exception.service.ServiceException;

/**
 * Created by velsubra on 11/28/16.
 */
public interface UnaryOperator<T> {
    public OperatorDefinition getOperator();
    public Operand<T> perform(Operand<T> right) throws ServiceException;
}
