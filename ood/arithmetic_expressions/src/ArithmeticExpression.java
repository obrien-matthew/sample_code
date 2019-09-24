/**
 * An interface for ArithmeticExpressions. Implemented by AbstractExpression, which is extended by
 * Infix- and PostfixExpression classes. Thus both must implement an evaluate function and a
 * toString function.
 */
public interface ArithmeticExpression {

  /**
   * Evaluates the expression and returns a double.
   *
   * @return a double, the result of the evaluation.
   * @throws ArithmeticException if the expression cannot be evaluated (e.g., a b +).
   */
  double evaluate() throws ArithmeticException;

  /**
   * Returns a string representing the data making up an arithmetic expression.
   *
   * @return a String, the  expression.
   */
  String toString();

}
