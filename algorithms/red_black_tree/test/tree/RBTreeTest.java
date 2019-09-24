package tree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RBTreeTest {

  RBTree t;

  @Before
  public void setUp() {
    t = new RBTree();
    int[] nums = {3, 8, 4, 9, 2, 6, 12, 20, 7, 1, 16, 10, 17, 13, 5, 14, 11};
    for (int n : nums) {
      t.insert(n);
    }
  }

  @Test
  public void testMin() {
    assertEquals(1, t.min());
  }

  @Test
  public void testMax() {
    assertEquals(20, t.max());
  }

  @Test
  public void testSearch() {
    assertEquals("Node found.\n", t.search(7));
    assertEquals("Node not found.\n", t.search(18));
  }

  @Test
  public void testSort() {
    assertEquals("1 2 3 4 5 6 7 8 9 10 11 12 13 14 16 17 20", t.sort());
  }

  @Test
  public void testHeight() {
    assertEquals(5, t.height());
  }

  @Test
  public void testBlackHeight() {
    assertEquals(4, t.blackHeight());
  }

  @Test
  public void testInsert() {
    t.insert(15);
    assertEquals("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 20", t.sort());
    assertEquals(5, t.height());
    assertEquals(4, t.blackHeight());
    t.treeCheck();
  }
}