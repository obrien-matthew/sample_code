import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * A JUnit test class for the PostfixExpression class.
 */
public class PostfixExpressionTest {

  PostfixExpression single;
  PostfixExpression twoTerm;
  PostfixExpression twoNested;
  PostfixExpression operatorPrecedence;
  PostfixExpression alpha;
  PostfixExpression largeInts;
  PostfixExpression allOperators;
  PostfixExpression alphabet;
  PostfixExpression singleAlpha;


  @Before
  public void setUp() {
    single = new PostfixExpression("5");
    twoTerm = new PostfixExpression("1 5 +");
    twoNested = new PostfixExpression("1 5 + 3 4 + *");
    operatorPrecedence = new PostfixExpression("1 5 6 * +");
    alpha = new PostfixExpression("a b +");
    largeInts = new PostfixExpression("1564890 2147483647 +");
    allOperators = new PostfixExpression("1 5 + 3 4 + * 2 * 9 / 3 -");
    alphabet = new PostfixExpression("a b + c /");
    singleAlpha = new PostfixExpression("a");
  }


  @Test
  public void testConstructorException() {
    PostfixExpression test;
    try {
      test = new PostfixExpression(" 1 5 +");
      fail();
    } catch (IllegalArgumentException e) {
      try {
        test = new PostfixExpression("1 5 + ");
        fail();
      } catch (IllegalArgumentException f) {
        try {
          test = new PostfixExpression("1 5 +3 4 + *");
          fail();
        } catch (IllegalArgumentException g) {
          try {
            test = new PostfixExpression("1 5 + 3 4 + * +");
            fail();
          } catch (IllegalArgumentException h) {
            try {
              test = new PostfixExpression("1 + 5");
              fail();
            } catch (IllegalArgumentException i) {
              try {
                test = new PostfixExpression("1 +5");
                fail();
              } catch (IllegalArgumentException j) {
                try {
                  test = new PostfixExpression("1 -5 +");
                  fail();
                } catch (IllegalArgumentException k) {
                  try {
                    test = new PostfixExpression("1 5 + 2 3 +");
                    fail();
                  } catch (IllegalArgumentException l) {
                    try {
                      test = new PostfixExpression("");
                      fail();
                    } catch (IllegalArgumentException m) {
                      try {
                        test = new PostfixExpression(" ");
                        fail();
                      } catch (IllegalArgumentException n) {
                        try {
                          test = new PostfixExpression("-");
                          fail();
                        } catch (IllegalArgumentException o) {
                          try {
                            test = new PostfixExpression("4; 2 -");
                            fail();
                          } catch (IllegalArgumentException p) {
                            try {
                              test = new PostfixExpression("%");
                              fail();
                            } catch (IllegalArgumentException q) {
                              // exceptions caught
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }


  @Test
  public void testEvaluate() {
    assertEquals(5, single.evaluate(), .001);
    assertEquals(6, twoTerm.evaluate(), .001);
    assertEquals(42, twoNested.evaluate(), .001);
    assertEquals(31, operatorPrecedence.evaluate(), .001);
  }


  @Test(expected = ArithmeticException.class)
  public void testEvaluateException() {
    double d = alpha.evaluate();
  }


  @Test
  public void testToString() {
    assertEquals("1 5 +", twoTerm.toString());
    assertEquals("1 5 + 3 4 + *", twoNested.toString());
    assertEquals("a b +", alpha.toString());
    assertEquals("1 5 6 * +", operatorPrecedence.toString());
    assertEquals("1564890 2147483647 +", largeInts.toString());
    assertEquals("1 5 + 3 4 + * 2 * 9 / 3 -", allOperators.toString());
    assertEquals("a b + c /", alphabet.toString());
    assertEquals("a", singleAlpha.toString());
  }
}
