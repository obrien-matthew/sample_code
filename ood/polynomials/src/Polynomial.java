/**
 * An interface for Polynomials. Implemented by PolynomialImpl, and consists
 * only of the required methods for assignment 4.
 */
public interface Polynomial {
  /**
   * Adds a term to an existing polynomial. Recursive. Adds terms in descending
   * order of power.
   *
   * @param coefficient an int, the coefficient of the new term.
   * @param power an int, the power of the new term.
   * @throws IllegalArgumentException if the coefficient is 0 or power is negative.
   */
  void addTerm(int coefficient, int power) throws IllegalArgumentException;

  /**
   * Removes the term that has the given power. If no term is found, returns
   * the same list.
   *
   * @param power an int, the given power of the term to be removed.
   */
  void removeTerm(int power);

  /**
   * Returns the degree of the polynomial.
   *
   * @return an int, the degree of the polynomial.
   */
  int getDegree();

  /**
   * Returns the coefficient with the specified power. Calls exit(1) if no
   * term found.
   *
   * @param power an int, the power.
   * @return an int, the coefficient.
   */
  int getCoefficient(int power);

  /**
   * Plugs a double, x, into a polynomial and evaluates the polynomial.
   *
   * @param x a double, x.
   * @return a double, the result of the evaluation.
   */
  double evaluate(double x);

  /**
   * Adds the terms of two polynomials to a new one. Combines terms with the same
   * power and constants. Adds terms recursively, in descending order of power.
   * Throws an IllegalArgumentException if the two polynomials are not of the
   * same concrete class.
   *
   * @param other the second polynomial.
   * @return a new polynomial, with the added terms.
   * @throws IllegalArgumentException if the two polynomials are of different
   *     concrete classes.
   */
  Polynomial add(Polynomial other) throws IllegalArgumentException;

}
