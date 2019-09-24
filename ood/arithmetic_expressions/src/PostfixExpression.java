/**
 * The PostfixExpression class. In postfix expressions, the operator comes after the two terms upon
 * which it operates (e.g. 2 2 +). Extends AbstractExpression.
 */
public class PostfixExpression extends AbstractExpression {

  /**
   * A constructor for a postfix expression. It must be properly ordered and formatted, and can only
   * consist of single alphabetic variables or numbers.
   */
  public PostfixExpression(String expression) throws IllegalArgumentException {
    super(expression);
  }
}
