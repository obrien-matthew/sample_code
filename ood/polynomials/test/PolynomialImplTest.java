import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static java.lang.Math.pow;

/**
 * A JUnit test class for PolynomialImpl.
 */
public class PolynomialImplTest {

  PolynomialImpl pI0;
  PolynomialImpl pI1;
  PolynomialImpl pI2;
  PolynomialImpl pI3;
  PolynomialImpl pI4;
  PolynomialImpl pI5;
  PolynomialImpl pI6;
  PolynomialImpl pI7;
  PolynomialImpl pI8;
  PolynomialImpl pI9;
  PolynomialImpl pI10;
  PolynomialImpl pI11;

  @Before
  public void setUp() {
    pI0 = new PolynomialImpl("-4x^1");
    pI1 = new PolynomialImpl("2x^3");
    pI8 = new PolynomialImpl("2x");
    pI2 = new PolynomialImpl("2x^5 +3x^2");
    pI3 = new PolynomialImpl("9x^7 -2x^3");
    pI4 = new PolynomialImpl("-3x^5 +4x^3");
    pI5 = new PolynomialImpl("-3x^5 +4x^3 +6");
    pI6 = new PolynomialImpl("4x^3 +6 -3x^5");
    pI7 = new PolynomialImpl("40x^700");
    pI9 = new PolynomialImpl("3x^2 +4x^1 -2x^0");

  }

  @Test
  public void testConstructor() {
    PolynomialImpl pT1 = new PolynomialImpl("4x^3 +3x^1 -5");
    PolynomialImpl pT2 = new PolynomialImpl("-3x^4 -2x^5 -5 +11x^1");
    assertEquals("4x^3 +3x^1 -5", pT1.toString());
    assertEquals("-2x^5 -3x^4 +11x^1 -5", pT2.toString());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException1() {
    pI10 = new PolynomialImpl("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException2() {
    pI11 = new PolynomialImpl("dsf");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException3() {
    pI11 = new PolynomialImpl("4x^3+3x^1-5");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException4() {
    pI11 = new PolynomialImpl("2f^3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException5() {
    pI11 = new PolynomialImpl("4x^3 *3x^1 +5");
  }

  @Test
  public void testAddTerm() {
    pI2.addTerm(9, 4);
    assertEquals("2x^5 +9x^4 +3x^2", pI2.toString());
    pI2.addTerm(9, 0);
    assertEquals("2x^5 +9x^4 +3x^2 +9", pI2.toString());

    assertEquals("0", pI0.toString());
    pI0.addTerm(-17, 9);
    assertEquals("-17x^9", pI0.toString());
    pI0.addTerm(4, 2);
    assertEquals("-17x^9 +4x^2", pI0.toString());
    pI0.addTerm(19, 29);
    assertEquals("19x^29 -17x^9 +4x^2", pI0.toString());
    pI0.addTerm(-5, 0);
    assertEquals("19x^29 -17x^9 +4x^2 -5", pI0.toString());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTermException() {
    pI2.addTerm(9, -1);
    assertEquals("2x^5 +3x^2", pI2.toString());

  }

  @Test
  public void testRemoveTerm() {
    pI2.removeTerm(2);
    assertEquals("2x^5", pI2.toString());
    pI2.removeTerm(8);
    assertEquals("2x^5", pI2.toString());
    pI5.removeTerm(0);
    assertEquals("-3x^5 +4x^3", pI5.toString());

  }

  @Test
  public void testGetDegree() {
    assertEquals(3, pI1.getDegree());
    assertEquals(0, pI0.getDegree());

  }

  @Test
  public void testGetCoefficient() {
    assertEquals(2, pI1.getCoefficient(3));
    assertEquals(0, pI4.getCoefficient(4));

  }

  @Test
  public void testEvaluate() {
    assertEquals(16, pI1.evaluate(2), .001);
//    assertEquals(-2, pI9.evaluate(0), .001);
    assertEquals(
            89998000, pI3.evaluate(10), .001);
    double d = 40 * pow(4, 700);
    assertEquals(d, pI7.evaluate(4), .001);
    assertEquals(-54, pI3.evaluate(-3), .001);

  }

  @Test
  public void testToString() {
    assertEquals("0", pI0.toString());
    assertEquals("-3x^5 +4x^3 +6", pI6.toString());
    assertEquals("2x^1", pI8.toString());
    assertEquals("3x^2 +4x^1 -2", pI9.toString());

  }

  @Test
  public void testAdd() {
    PolynomialImpl testPol = (PolynomialImpl)pI2.add(pI1);
    assertEquals("2x^5 +2x^3 +3x^2", testPol.toString());

    Polynomial newPol = pI9.add(pI0);
    assertEquals("3x^2 +4x^1 -2", newPol.toString());
    newPol = pI0.add(pI9);
    assertEquals("3x^2 +4x^1 -2", newPol.toString());
    assertEquals("3x^2 +4x^1 -2", pI9.toString());
    assertEquals("0", pI0.toString());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTermNegException() {
    pI1.addTerm(2, -1);

  }

}