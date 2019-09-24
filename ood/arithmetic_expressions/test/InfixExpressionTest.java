import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the InfixExpression class.
 */
public class InfixExpressionTest {

  InfixExpression simple;
  InfixExpression operatorPrecedence;
  InfixExpression frontParen;
  InfixExpression backParen;
  InfixExpression allOps;
  InfixExpression complex;
  InfixExpression variables;


  @Before
  public void setUp() {
    simple = new InfixExpression("2 + 3");
    operatorPrecedence = new InfixExpression("2 + 3 * 4");
    frontParen = new InfixExpression("( 2 + 3 ) * 4");
    backParen = new InfixExpression("4 * ( 3 + 5 )");
    allOps = new InfixExpression("( 3 + 4 * 8 ) / 3");
    complex = new InfixExpression("3 - ( 2 + 4 * 1 - ( 7 / 8 ) ) + 9");
    variables = new InfixExpression("a + ( b / c ) - d");
  }


  @Test
  public void testConstructorException() {
    InfixExpression test;
    try {
      test = new InfixExpression(" 1 + 5");
      fail();
    } catch (IllegalArgumentException e) {
      try {
        test = new InfixExpression("1 + 5 ");
        fail();
      } catch (IllegalArgumentException f) {
        try {
          test = new InfixExpression("1 + 5 *3");
          fail();
        } catch (IllegalArgumentException g) {
          try {
            test = new InfixExpression("1 + 5 + 3 * 4 +");
            fail();
          } catch (IllegalArgumentException h) {
            try {
              test = new InfixExpression("1 5 +");
              fail();
            } catch (IllegalArgumentException i) {
              try {
                test = new InfixExpression("1+ 5");
                fail();
              } catch (IllegalArgumentException j) {
                try {
                  test = new InfixExpression("1 + -5");
                  fail();
                } catch (IllegalArgumentException k) {
                  try {
                    test = new InfixExpression("1 + 5 2 + 3");
                    fail();
                  } catch (IllegalArgumentException l) {
                    try {
                      test = new InfixExpression("");
                      fail();
                    } catch (IllegalArgumentException m) {
                      try {
                        test = new InfixExpression(" ");
                        fail();
                      } catch (IllegalArgumentException n) {
                        try {
                          test = new InfixExpression("-");
                          fail();
                        } catch (IllegalArgumentException o) {
                          try {
                            test = new InfixExpression("4; - 2");
                            fail();
                          } catch (IllegalArgumentException p) {
                            try {
                              test = new InfixExpression("%");
                              fail();
                            } catch (IllegalArgumentException q) {
                              try {
                                test = new InfixExpression("ab + 5");
                                fail();
                              } catch (IllegalArgumentException r) {
                                try {
                                  test = new InfixExpression("-");
                                  fail();
                                } catch (IllegalArgumentException s) {
                                  try {
                                    test = new InfixExpression(") 3 - 4 (");
                                    fail();
                                  } catch (IllegalArgumentException t) {
                                    try {
                                      test = new InfixExpression("( ( ( 3 + 3 )");
                                      fail();
                                    } catch (IllegalArgumentException u) {
                                      try {
                                        test = new InfixExpression("( )");
                                        fail();
                                      } catch (IllegalArgumentException v) {
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
          }
        }
      }
    }
  }


  @Test
  public void testEvaluate() {
    assertEquals(5, simple.evaluate(), .001);
    assertEquals(14, operatorPrecedence.evaluate(), .001);
    assertEquals(20, frontParen.evaluate(), .001);
    assertEquals(32, backParen.evaluate(), .001);
    assertEquals(11.6666, allOps.evaluate(), .001);
    assertEquals(6.875, complex.evaluate(), .001);

  }


  @Test(expected = ArithmeticException.class)
  public void testEvaluateException() {
    double d = variables.evaluate();
  }


  @Test
  public void testToPostfix() {
    assertEquals("2 3 +", simple.toPostfix().toString());
    assertEquals("2 3 4 * +", operatorPrecedence.toPostfix().toString());
    assertEquals("2 3 + 4 *", frontParen.toPostfix().toString());
    assertEquals("4 3 5 + *", backParen.toPostfix().toString());
    assertEquals("3 4 8 * + 3 /", allOps.toPostfix().toString());
    assertEquals("3 2 4 1 * + 7 8 / - - 9 +", complex.toPostfix().toString());
    assertEquals("a b c / + d -", variables.toPostfix().toString());


  }


  @Test
  public void testToString() {
    assertEquals("2 + 3", simple.toString());
    assertEquals("2 + 3 * 4", operatorPrecedence.toString());
    assertEquals("( 2 + 3 ) * 4", frontParen.toString());
    assertEquals("4 * ( 3 + 5 )", backParen.toString());
    assertEquals("( 3 + 4 * 8 ) / 3", allOps.toString());
    assertEquals("3 - ( 2 + 4 * 1 - ( 7 / 8 ) ) + 9", complex.toString());
    assertEquals("a + ( b / c ) - d", variables.toString());
  }


}