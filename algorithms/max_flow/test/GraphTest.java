import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {
  private Graph g;
  private Graph h;
  private Graph t;

  @Before
  public void setUp() {
    g = new Graph(6, 0, 5);

    g.addEdge(0, 1, 16);
    g.addEdge(0, 2, 13);
    g.addEdge(2, 1, 4);
    g.addEdge(1, 3, 12);
    g.addEdge(2, 4, 14);
    g.addEdge(3, 2, 9);
    g.addEdge(3, 5, 20);
    g.addEdge(4, 3, 7);
    g.addEdge(4, 5, 4);

    h = new Graph(6, 0, 5);

    h.addEdge(0, 1, 3);
    h.addEdge(0, 2, 3);
    h.addEdge(1, 2, 2);
    h.addEdge(1, 3, 3);
    h.addEdge(3, 4, 4);
    h.addEdge(2, 4, 2);
    h.addEdge(4, 5, 3);
    h.addEdge(3, 5, 2);

    t = new Graph(4, 0, 3);

    t.addEdge(0, 1, 1);
    t.addEdge(0, 2, 1);
    t.addEdge(1, 3, 2);
    t.addEdge(2, 3, 2);
    t.addEdge(1, 2, 2);

  }

  @Test
  public void testGetMaxFlow() {
    assertEquals(23, g.getMaxFlow());
    assertEquals(5, h.getMaxFlow());
    assertEquals(5, t.getMaxFlow());

  }
}