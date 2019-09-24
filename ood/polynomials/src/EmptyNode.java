/**
 * An EmptyNode which implements PolynomialNode. EmptyNodes end each PolynomialImpl.
 */
public class EmptyNode implements PolynomialNode {
  /**
   * Gets the power of a polynomial. Calling on an EmptyNode results in an exception.
   *
   * @return none
   * @throws IllegalStateException if called on an EmptyNode.
   */
  @Override
  public int getPower() throws IllegalStateException {
    throw new IllegalStateException("Cannot get power of undefined polynomial.");
  }

  /**
   * Recursively returns the coefficient for the given power, if found. An EmptyNode
   * always throws an exception.
   *
   * @param p an int, the power.
   * @return an int, the coefficient.
   * @throws IllegalArgumentException if it reaches an EmptyNode.
   */
  @Override
  public int getCoefficient(int p) throws IllegalArgumentException {
    throw new IllegalArgumentException("No term found with the specified power.");
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
    // Must be present because it is declared in the interface, but doesn't need to do anything.
  }

  /**
   * Adds a node to the polynomial with coefficient c and power p. An EmptyNode adds a new
   * TermNode and sets itself as the "rest" of the TermNode.
   *
   * @param c an int, the coefficient.
   * @param p an int, the power.
   * @return the head of the list with the added node.
   */
  @Override
  public PolynomialNode addNode(int c, int p) {
    return new TermNode(c, p, this);
  }

  /**
   * Removes the node with the given power. If it reaches an EmptyNode, an exception is thrown.
   *
   * @param power an int, the specified power.
   * @return none
   */
  @Override
  public PolynomialNode removeNode(int power) {
    throw new IllegalArgumentException("No such power found.");
  }

  /**
   * Plugs a double, x, into the polynomial and returns the result. An EmptyNode always
   * returns 0.
   *
   * @param x a double, to be evaluated in the polynomial.
   * @return a double, the result.
   */
  @Override
  public double evaluate(double x) {
    return 0;
  }

  /**
   * A toString method for the EmptyNode. An EmptyNode always returns a string of "0".
   *
   * @return an int, 0.
   */
  @Override
  public String toString() {
    return "0";
  }

}
