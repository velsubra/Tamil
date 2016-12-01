package tamil.lang.api.expression;

import tamil.lang.exception.service.ServiceException;

/**
 * Created by velsubra on 11/30/16.
 */
public interface BinaryOperator<T> {
    public OperatorDefinition getOperator();
    public Operand<T> perform(Operand<T> left, Operand<T> right) throws ServiceException;
}
