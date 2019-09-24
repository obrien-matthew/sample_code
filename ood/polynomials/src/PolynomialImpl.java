import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A PolynomialImpl class. Each PolynomialImpl stores the head of its corresponding polynomial and
 * an int representing the number of terms in the polynomial.
 */
public class PolynomialImpl implements Polynomial {
  /**
   * A PolynomialNode, the head of the polynomial.
   */
  private PolynomialNode head;

  /**
   * an int, the count of terms.
   */
  private int count;

  /**
   * A constructor for the polynomial. When called without an input string, this constructor creates
   * a polynomial with only an EmptyNode.
   */
  public PolynomialImpl() {
    this.head = new EmptyNode();
    this.count = 0;
  }

  /**
   * A constructor for the polynomial. When called with a string, parses the string and adds the
   * terms to the polynomial. Throws exceptions for illegal inputs.
   *
   * @param str a String, formatted as such: "ax^b +cx^d -e"
   * @throws IllegalArgumentException if the string is not properly formatted.
   */
  public PolynomialImpl(String str) throws IllegalArgumentException {
    if (str.length() > 0) {
      this.head = new EmptyNode();
      this.count = 0;

      if (str.charAt(0) != '-') {
        str = "+" + str;
      }

      String[] split = str.split(" ");
      int splitLen = split.length;

      for (int i = 0; i < splitLen; i++) {
        int[] nums = scanNums(split[i]);
        if (nums[0] != 0) {
          this.addTerm(nums[0], nums[1]);
        }
      }
    } else {
      throw new IllegalArgumentException("String cannot be empty.");
    }
  }

  /**
   * Called by the string constructor. parses the terms after they are split according to spaces.
   * Returns a list of ints, the coefficient and power.
   *
   * @param str the input term.
   * @return a list of ints, the coefficient and the power.
   * @throws IllegalArgumentException if the input is illegal.
   */
  private int[] scanNums(String str) throws IllegalArgumentException {
    int c = 0;
    int p = 0;

    Scanner s = new Scanner(str);

    Pattern p1 = Pattern.compile("^[+-].*[+-]");
    Matcher multiterm = p1.matcher(str);

    Pattern p2 = Pattern.compile("[a-wyzA-WYZ\\*\\/]");
    Matcher illegalchars = p2.matcher(str);

    // check for more than one term in str
    if (multiterm.find()) {
      throw new IllegalArgumentException("Terms must be separated with a space.");
    }

    // check for multiplication/division
    if (illegalchars.find()) {
      throw new IllegalArgumentException("Illegal characters passed to constructor.");
    }

    try {
      // check if string is just an int
      c = Integer.parseInt(str);
      p = 0;
    } catch (NumberFormatException e) {
      try {
        // split check if string is just in kx format (will throw exception if so)
        String[] testChars = str.split("(?!^)");
        Integer.parseInt(testChars[testChars.length - 1]);

        // find coefficient/power
        s.findInLine("([+\\-]\\d+)x\\^(\\d+)");
        MatchResult result = s.match();
        c = Integer.parseInt(result.group(1));
        p = Integer.parseInt(result.group(2));

      } catch (NumberFormatException | IllegalStateException f) {
        try {
          s.findInLine("([+\\-]\\d+)x");
          MatchResult result = s.match();
          c = Integer.parseInt(result.group(1));
          p = 1;
        } catch (NumberFormatException | IllegalStateException g) {
          throw new IllegalArgumentException("Constructor input invalid.");
        }
      }
    }

    s.close();

    return new int[]{c, p};
  }

  /**
   * Adds a term to the polynomial if the given power is postiive. If not, it throws an exception.
   *
   * @param coefficient an int, the coefficient of the new term.
   * @param power       an int, the power of the new term.
   * @throws IllegalArgumentException if the power is negative.
   */
  @Override
  public void addTerm(int coefficient, int power) throws IllegalArgumentException {
    if (power < 0) {
      throw new IllegalArgumentException("Power must be positive.");
    } else if (coefficient != 0) {
      this.head = this.head.addNode(coefficient, power);
      this.count++;
    }
  }

  /**
   * Removes a term from the polynomial if it has the given power. If not, returns the same
   * polynomial.
   *
   * @param power an int, the given power of the term to be removed.
   */
  @Override
  public void removeTerm(int power) {
    try {
      this.head = this.head.removeNode(power);
      this.count--;
    } catch (IllegalArgumentException e) {
      // no node found.
    }
  }

  /**
   * Gets the degree of the polynomial (the power of its first element).
   *
   * @return an int, the degree of the polynomial.
   */
  @Override
  public int getDegree() {
    int p = 0;
    try {
      p = this.head.getPower();
    } catch (IllegalStateException e) {
      // caught, polynomial consists only of an EmptyNode.
    }
    return p;
  }

  /**
   * Gets the coefficient for the term with the given power. If no such power is found, returns 0.
   *
   * @param power an int, the power.
   * @return an int, the coefficient.
   */
  @Override
  public int getCoefficient(int power) {
    int p = 0;
    try {
      p = this.head.getCoefficient(power);
    } catch (IllegalArgumentException e) {
      // caught, no such term.
    }
    return p;
  }

  /**
   * Evaluates the polynomial based on the given double.
   *
   * @param x a double, x.
   * @return a double, the result.
   */
  @Override
  public double evaluate(double x) {
    return this.head.evaluate(x);
  }

  /**
   * Adds two polynomials together if they are of the same concrete class. Returns a new polynomial
   * (i.e., the originals have not been altered).
   *
   * @param other the second polynomial.
   * @return a PolynomialImpl, the new polynomial.
   * @throws IllegalArgumentException if the second polynomial is not of the same concrete class.
   */
  @Override
  public Polynomial add(Polynomial other) throws IllegalArgumentException {
    if (!(other instanceof PolynomialImpl)) {
      throw new IllegalArgumentException("Can only add polynomials of the same concrete class.");
    }

    PolynomialImpl otherPol = (PolynomialImpl) other;
    PolynomialImpl newPol = new PolynomialImpl();

    PolynomialNode otherNode = otherPol.head;
    PolynomialNode thisNode = this.head;

    int newCount = this.count + otherPol.count;

    int[] accNum = new int[newCount];
    int[] accPower = new int[newCount];

    otherNode.getAll(accNum, accPower, 0);
    thisNode.getAll(accNum, accPower, otherPol.count);

    for (int i = 0; i < newCount; i++) {
      int num = accNum[i];
      int power = accPower[i];

      newPol.head = newPol.head.addNode(num, power);
    }

    return newPol;
  }

  /**
   * A toString method for the polynomial. Simply calls the toString method of its head.
   *
   * @return a String representing the polynomial in the following format: "ax^b + cx^d -e"
   */
  @Override
  public String toString() {
    return this.head.toString();
  }
}
