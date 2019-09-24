/**
 * An interface for a PolynomialNode object. TermNodes, ConstantNodes, and EmptyNodes all implement
 * this interface. It creates a linked list of nodes which in sum form a polynomial.
 */
public interface PolynomialNode {

  /**
   * Returns the power for this node.
   *
   * @return an int, the node's power.
   * @throws IllegalStateException if called on a ConstantNode or EmptyNode.
   */
  int getPower() throws IllegalStateException;

  /**
   * Recursively returns the coefficient for the given power, if found. If not found, throws an
   * IllegalArgumentException.
   *
   * @param p an int, the power.
   * @return an int, the coefficient.
   * @throws IllegalArgumentException if power not found.
   */
  int getCoefficient(int p) throws IllegalArgumentException;

  /**
   * Adds a node to the polynomial with coefficient c and power p. Recursively find where it should
   * go in the order. If a node with this power already exists, it adds the coefficients together.
   *
   * @param c an int, the coefficient.
   * @param p an int, the power.
   * @return the head of the list with the added node.
   */
  PolynomialNode addNode(int c, int p);


  /**
   * Removes the node with the given power. If no node with that power exists, returns the same
   * polynomial.
   *
   * @param power an int, the specified power.
   * @return the head of the list with the node removed.
   */
  PolynomialNode removeNode(int power);

  /**
   * Plugs a double, x, into the polynomial and returns the result.
   *
   * @param x a double, to be evaluated in the polynomial.
   * @return a double, the result.
   */
  double evaluate(double x);

  /**
   * Adds the coefficient and powers of the calling polynomial to a list.
   *
   * @param accNum   a list of ints, for the coefficients.
   * @param accPower a list of ints, for the powers.
   * @param i        an iterator.
   */
  void getAll(int[] accNum, int[] accPower, int i);

}
