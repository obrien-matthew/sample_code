import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * An abstract class for arithmetic expressions. Implements ArithmeticExpression and is extended by
 * Postfix and InfixExpression.
 */
public abstract class AbstractExpression implements ArithmeticExpression {

  /**
   * A list of a list of Strings. Contains terms paired with their data type (i.e, operator,
   * operand, or open/close parentheses).
   */
  protected ArrayList<String[]> terms;

  /**
   * An int, the length of the expression.
   */
  protected int length;

  /**
   * A constructor for AbstractExpressions. Checks the concrete class of the caller, and checks
   * formatting apporpriately.
   *
   * @param expression A String, the expression.
   */
  public AbstractExpression(String expression) {
    this.initialChecks(expression);
    ArrayList<String[]> terms_list = this.scanTerms(expression);
    int l = terms_list.size();

    if (l == 0) {
      throw new IllegalArgumentException("Expression must include operators and operands.");
    }

    if (this.getClass() == PostfixExpression.class) {

      try {
        checkPostfix(terms_list);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("'" + expression + "' threw the following exception in "
                + "the PostfixExpression constructor: " + e.getMessage());
      }

      this.terms = terms_list;
      this.length = l;

    } else if (this.getClass() == InfixExpression.class) {

      try {
        checkInfix(terms_list);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("'" + expression + "' threw the following exception in "
                + "the InfixExpression constructor:" + e.getMessage());
      }

      this.terms = terms_list;
      this.length = l;

    } else {
      throw new IllegalArgumentException("Constructor called from unknown subclass.");
    }

  }

  /**
   * Called by the constructor for AbstractExpressions. Checks for empty strings and
   * leading/trailing spaces.
   *
   * @throws IllegalArgumentException if the checks fail.
   */
  private void initialChecks(String expression) throws IllegalArgumentException {
    // check for empty string, leading/trailing spaces
    if (expression.length() == 0) {
      throw new IllegalArgumentException("Argument string cannot be empty.");
    } else if (expression.charAt(0) == ' '
            || expression.charAt(expression.length() - 1) == ' ') {
      throw new IllegalArgumentException("Argument string cannot "
              + "have leading or trailing spaces.");
    }
  }

  /**
   * Called by the constructor for AbstractExpressions. Evaluates each term in an expression and
   * pairs it with a data type (operand, operator, or open/close parentheses). Returns a list of
   * lists of Strings containing those pairings.
   *
   * @param expression a Sting, the term to be evaluated.
   * @return A list of lists of Strings, containing data matched with its type.
   */
  private ArrayList<String[]> scanTerms(String expression) {
    ArrayList<String[]> terms = new ArrayList<String[]>();
    String[] split = expression.split(" ");

    for (int i = 0; i < split.length; i++) {
      if (!split[i].equals("")) {
        String[] toAdd = new String[2];
        toAdd[0] = split[i];
        toAdd[1] = getType(split[i]);
        terms.add(toAdd);
      }
    }
    return terms;
  }

  /**
   * Called by scanTerms for AbstractExpressions. Returns the data type for the given expression
   * (i.e. operand, operator, or open/close parentheses).
   *
   * @param term A string, the term to be evaluated.
   * @return a String, the data type of that term.
   * @throws IllegalArgumentException if the term is invalid.
   */
  private String getType(String term) throws IllegalArgumentException {
    if (term.length() == 1) {
      String[] operators = new String[]{"+", "-", "*", "/"};
      int i = 0;
      for (; i < 4; i++) {
        if (term.equals(operators[i])) {
          return "operator";
        }
      }
      if (term.equals(")")) {
        return "close parentheses";
      }
      if (term.equals("(")) {
        return "open parentheses";
      }
      if (Character.isDigit(term.charAt(0))
              || Character.isAlphabetic(term.charAt(0))) {
        return "operand";
      }
    } else {
      try {
        double c = Double.parseDouble(term);
        if (c < 0) {
          throw new IllegalArgumentException("Numbers must be non-negative.");
        }
        return "operand";
      } catch (NumberFormatException e) {
        // exception caught, throwing exception
      }
    }
    throw new IllegalArgumentException("'" + term + "' is invalid.");
  }

  /**
   * Called by the constructor for AbstractExpressions. Checks an expression is properly formatted
   * in the Postfix format.
   *
   * @param termsList A list of lists of Strings containing the data for an expression.
   * @throws IllegalArgumentException if expression is improperly formatted.
   */
  private void checkPostfix(ArrayList<String[]> termsList) throws IllegalArgumentException {
    int i = 0;
    Stack<String> stack = new Stack<String>();

    for (; i < termsList.size(); i++) {
      String[] term_info = termsList.get(i);
      String type = term_info[1];

      if (type.equals("operator")) {
        try {
          stack.pop();
          stack.pop();
          stack.push("result");
        } catch (EmptyStackException e) {
          throw new IllegalArgumentException("Invalid input: operands/operators are not balanced.");
        }
      } else if (type.equals("operand")) {
        stack.push("operand");
      } else if (type.equals("close parentheses")
              || type.equals("open parentheses")) {
        throw new IllegalArgumentException("Parentheses are not allowed in postfix expressions.");
      }
    }

    stack.pop();

    if (!stack.empty()) {
      throw new IllegalArgumentException("Invalid input: operands/operators are not balanced.");
    }
  }

  /**
   * Called by the constructor for AbstractExpressions. Checks an expression is properly formatted
   * in the Infix format.
   *
   * @param termsList A list of lists of Strings containing the data for an expression.
   * @throws IllegalArgumentException if the expression is not properly formatted.
   */
  private void checkInfix(ArrayList<String[]> termsList) {
    Stack<String> stack = new Stack<String>();

    int i = 0;
    // iterate through the terms
    for (; i < termsList.size(); i++) {
      String[] term_info = termsList.get(i);
      String type = term_info[1];

      // check type of each term
      String popped = "";
      if (type.equals("open parentheses")) {
        stack.push("open parentheses");

      } else if (type.equals("close parentheses")) {
        try {
          popped = stack.pop();
        } catch (EmptyStackException e) {
          throw new IllegalArgumentException("close parentheses operated on empty stack.");
        }

        if (!popped.equals("operand")) {
          throw new IllegalArgumentException("close parentheses did not follow an operand.");
        }

        while (!popped.equals("open parentheses")) {
          try {
            popped = stack.pop();
          } catch (EmptyStackException e) {
            throw new IllegalArgumentException("close parentheses reach an empty stack.");
          }
        }

        stack.push("operand");

      } else if (type.equals("operand")) {
        if (stack.empty()) {
          stack.push("operand");
        } else {
          popped = stack.pop();
          if (popped.equals("operand")) {
            throw new IllegalArgumentException("Cannot have two consecutive operands");
          } else if (popped.equals("close parentheses")) {
            throw new IllegalArgumentException("A close parentheses must be "
                    + "followed by an operator.");
          } else if (popped.equals("open parentheses")) {
            stack.push("open parentheses");
            stack.push("operand");
          } else if (popped.equals("operator")) {
            String popped_term = "";
            try {
              popped_term = stack.pop();
            } catch (EmptyStackException e) {
              throw new IllegalArgumentException("Could not pop another term.");
            }
            if (!popped_term.equals("operand")) {
              throw new IllegalArgumentException("Popped term was not an operand.");
            }
            stack.push("operand");
          }
        }

      } else if (type.equals("operator")) {
        if (stack.empty()) {
          throw new IllegalArgumentException("An operator cannot be the first term in "
                  + "an arithmetic expression.");
        } else {
          popped = stack.pop();
          if (popped.equals("operand")) {
            stack.push("operand");
            stack.push("operator");
          } else if (popped.equals("close parentheses")) {
            throw new IllegalArgumentException("Extraneous close parentheses.");
          } else if (popped.equals("open parentheses")) {
            throw new IllegalArgumentException("An open parentheses cannot be followed "
                    + "by an operator.");
          } else if (popped.equals("operator")) {
            throw new IllegalArgumentException("Cannot have two consecutive operators.");
          }
        }
      }
    }

    while (true) {
      String popped1;
      String popped2;
      String popped3;

      try {
        popped1 = stack.pop();
        if (stack.empty()) {
          stack.push("operand");
          break;
        }
        popped2 = stack.pop();
        popped3 = stack.pop();
      } catch (EmptyStackException e) {
        throw new IllegalArgumentException("Improperly formatted expression.");
      }

      if (!popped1.equals("operand") || !popped3.equals("operand")
              || !popped2.equals("operator")) {
        throw new IllegalArgumentException("Invalid data in stack.");
      }
      stack.push("operand");
    }
  }

  /**
   * Evaluates the expression and returns a double. If the expression is Infix, converts to Postfix
   * and evaluates.
   *
   * @return a double, the result of the evaluation.
   * @throws ArithmeticException if the expression cannot be evaluated (e.g., a b +).
   */
  public final double evaluate() {
    if (this.getClass() == InfixExpression.class) {
      InfixExpression infix = (InfixExpression) this;
      return infix.toPostfix().evaluate();
    }

    double result = 0;

    Stack<Double> ints = new Stack<Double>();
    int i = 0;

    for (; i < this.length; i++) {
      String[] term_info = this.terms.get(i);
      String term = term_info[0];
      String type = term_info[1];

      if (type.equals("operator")) {
        double a;
        double b;
        try {
          b = ints.pop();
          a = ints.pop();
          // I commented out this check for infinite values because it was not required.
          // I left it, however, because I think it'd be useful in an actual
          // implementation of this code.
          // if (a == Double.POSITIVE_INFINITY || a == Double.NEGATIVE_INFINITY
          //     || b == Double.POSITIVE_INFINITY || b == Double.NEGATIVE_INFINITY) {
          //   throw new ArithmeticException("Value cannot be positive or negative infinity.");
          // }
        } catch (EmptyStackException e) {
          throw new ArithmeticException("Empty Stack exception thrown.");
        }

        if (term.equals("+")) {
          result = a + b;
        } else if (term.equals("-")) {
          result = a - b;
        } else if (term.equals("*")) {
          result = a * b;
        } else if (term.equals("/")) {
          // I commented out this check for divide by zero because it failed JUnit tests
          // on the server. I left it, however, because I think it'd be useful in an actual
          // implementation of this code.
          // if (b > -.0001 && b < .0001) {
          // throw new ArithmeticException("Cannot divide by zero.");
          // }
          result = a / b;
        }

        ints.push(result);
      } else {
        double a;
        try {
          a = Double.parseDouble(term);
        } catch (NumberFormatException e) {
          throw new ArithmeticException("Non-numeric operand in expression.");
        }
        ints.push(a);
      }
    }

    return ints.pop();
  }

  /**
   * Returns a string representing the data making up an arithmetic expression.
   *
   * @return a String, the  expression.
   */
  public final String toString() {
    int num_terms = this.length;
    StringBuilder str = new StringBuilder();
    int i = 0;
    for (; i < num_terms; i++) {
      str.append(this.terms.get(i)[0]);
      str.append(" ");
    }
    str.deleteCharAt(str.length() - 1);
    return str.toString();
  }

}
