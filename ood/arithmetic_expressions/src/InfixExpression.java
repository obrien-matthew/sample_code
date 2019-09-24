import java.util.EmptyStackException;
import java.util.Stack;

/**
 * The InfixExpression class. An infix expression is formatted classically (e.g. 2 + 2). Extends
 * AbstractExpression.
 */
public class InfixExpression extends AbstractExpression {

  /**
   * A constructor for an InfixExpression. The expression must be properly formatted and may only
   * consist of single alphabetic variables, numbers, and operators.
   *
   * @param expression A String, the expression.
   * @throws IllegalArgumentException if the String is not properly formatted.
   */
  public InfixExpression(String expression) throws IllegalArgumentException {
    super(expression);
  }


  /**
   * Called by the toPostfix method, evaluates the precedence of operators.
   *
   * @param str a String, the operator.
   * @return an int, the precedence of the operator.
   */
  private int getPrecedence(String str) {
    int precedence = 0;
    if (str.equals("*") || str.equals("/")) {
      precedence = 1;
    } else if (str.equals("-") || str.equals("+")) {
      precedence = 2;
    }
    return precedence;
  }


  /**
   * Converts an InfixExpression into a PostfixExpression.
   *
   * @return a PostfixExpression.
   */
  public PostfixExpression toPostfix() {
    StringBuilder postfix = new StringBuilder();
    Stack<String> terms_stack = new Stack<String>();
    int i = 0;

    for (; i < this.length; i++) {
      String[] term_info = this.terms.get(i);
      String term = term_info[0];
      String type = term_info[1];

      if (type.equals("operand")) {
        postfix.append(term);
        postfix.append(" ");

      } else if (type.equals("operator")) {
        if (terms_stack.empty()) {
          terms_stack.push(term);
        } else {
          String popped = terms_stack.pop();
          int popped_precedence = getPrecedence(popped);
          int op_precedence = getPrecedence(term);
          while (popped_precedence <= op_precedence && !popped.equals("(")) {
            postfix.append(popped);
            postfix.append(" ");
            if (terms_stack.empty()) {
              break;
            }
            popped = terms_stack.pop();
            popped_precedence = getPrecedence(popped);
          }
          if (popped_precedence > op_precedence || popped.equals("(")) {
            terms_stack.push(popped);
          }
          terms_stack.push(term);
        }
      } else if (type.equals("close parentheses")) {
        String popped = terms_stack.pop();
        while (!popped.equals("(")) {
          postfix.append(popped);
          postfix.append(" ");
          try {
            popped = terms_stack.pop();
          } catch (EmptyStackException e) {
            throw new IllegalArgumentException("EmptyStackException thrown while looking"
                    + " for open parentheses.");
          }
        }
      } else if (type.equals("open parentheses")) {
        terms_stack.push(term);
      }
    }

    while (!terms_stack.empty()) {
      postfix.append(terms_stack.pop());
      postfix.append(" ");
    }

    return new PostfixExpression(postfix.deleteCharAt((postfix.length()) - 1).toString());
  }
}
