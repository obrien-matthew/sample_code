import static java.lang.Math.pow;

/**
 * A TermNode that implements PolynomialNode. Each TermNode has a coefficient, a power, and the rest
 * of the polynomial. The variable, x, is raised to the power then multiplied by the coefficient
 * when evaluating. If the power is 0, the coefficient acts essentially as a constant.
 */
public class TermNode implements PolynomialNode {
  /**
   * An int, the coefficient.
   */
  private int coefficient;

  /**
   * An int, the power.
   */
  private int power;

  /**
   * A PolynomialNode, the rest of the polynomial.
   */
  private PolynomialNode rest;

  /**
   * The constructor for a TermNode. Adds a node to the polynomial with coefficient
   * c and power p. Recursively finds where it should go in the order. If a node with
   * this power already exists, it adds the coefficients together.
   *
   * @param c an int, the coefficient.
   * @param p an int, the power.
   * @param r a Polynomial, the next node in the polynomial.
   */
  public TermNode(int c, int p, PolynomialNode r) {
    this.coefficient = c;
    this.power = p;
    this.rest = r;
  }

  /**
   * Returns the power for this node.
   *
   * @return an int, the node's power.
   * @throws IllegalStateException if called on a ConstantNode or EmptyNode.
   */
  @Override
  public int getPower() throws IllegalStateException {
    return this.power;
  }

  /**
   * Recursively returns the coefficient for the given power, if found. If not
   * found, throws an IllegalArgumentException.
   *
   * @param p an int, the power.
   * @return an int, the coefficient.
   * @throws IllegalArgumentException if power not found.
   */
  @Override
  public int getCoefficient(int p) throws IllegalArgumentException {
    if (this.power == p) {
      return this.coefficient;
    } else {
      return this.rest.getCoefficient(p);
    }
  }

  /**
   * Adds a node to the polynomial with coefficient c and power p. Recursively find
   * where it should go in the order. If a node with this power already exists,
   * it adds the coefficients together.
   *
   * @param c an int, the coefficient.
   * @param p an int, the power.
   * @return the head of the list with the added node.
   */
  @Override
  public PolynomialNode addNode(int c, int p) {
    if (this.power > p) {
      this.rest = this.rest.addNode(c, p);
      return this;
    } else if (this.power == p) {
      this.coefficient += c;
      return this;
    } else {
      return new TermNode(c, p, this);
    }
  }

  /**
   * Removes the node with the given power. If no node with that power exists,
   * returns the same polynomial.
   *
   * @param p an int, the specified power.
   * @return the head of the list with the node removed.
   */
  @Override
  public PolynomialNode removeNode(int p) {
    if (this.power == p) {
      return this.rest;
    } else {
      this.rest = this.rest.removeNode(p);
      return this;
    }
  }

  /**
   * Plugs a double, x, into the polynomial and returns the result.
   *
   * @param x a double, to be evaluated in the polynomial.
   * @return a double, the result.
   */
  @Override
  public double evaluate(double x) {
    return (this.coefficient * pow(x, this.power)) + this.rest.evaluate(x);
  }

  /**
   * Adds the coefficient and powers of the calling polynomial to a list.
   *
   * @param accNum a list of ints, for the coefficients.
   * @param accPower a list of ints, for the powers.
   * @param i an iterator.
   */
  @Override
  public void getAll(int[] accNum, int[] accPower, int i) {
    accNum[i] = this.coefficient;
    accPower[i] = this.power;
    this.rest.getAll(accNum, accPower, i + 1);
  }

  /**
   * A toString method for a polynomial. Checks the next node, and formats the returning
   * string accordingly. All polynomials are returned in the following format: ax^b +cx^d +e
   *
   * @return a String, this node + the strings of the rest of the nodes.
   */
  @Override
  public String toString() {
    String plus = "+";
    String power = "x^" + this.power;
    String str = "";
    String space = " ";

    if (this.rest.getClass() == EmptyNode.class) {
      str = "";
      space = "";
      plus = "";

    } else {
      str = this.rest.toString();
      if (str.charAt(0) == '-') {
        plus = "";
      }
    }

    if (this.power == 0) {
      power = "";
    }

    return this.coefficient + power + space + plus + str;
  }
}
