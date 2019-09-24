import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import java.util.Stack;

import static org.junit.Assert.*;

public class BiHeapTest {
  private BiHeap h;

  @Before
  public void setUp() {
    h = new BiHeap();

    // generate random numbers
    Stack<Integer> nums = new Stack<>();
    Random r = new Random();
    int i = 0;
    while (i < 35) {
      int num = r.nextInt(150);
      num += 5;
      if (!nums.contains(num)) {
        nums.push(num);
        i++;
      }
    }

    // insert into binomial heap
    while (!nums.empty()) {
      h.insert(new HeapNode(nums.pop()));
    }
  }

  @Test
  public void testInsert() {
    h.insert(new HeapNode(155));
    h.insert(new HeapNode(3));
    h.insert(new HeapNode(5000));
    h.insert(new HeapNode(1));
    h.insert(new HeapNode(158));
    h.insert(new HeapNode(157));
    assertTrue(h.heapCheck());
  }

  @Test
  public void testMinimum() {
    h.insert(new HeapNode(4));
    assertEquals(4, h.minimum().getKey());
    h.insert(new HeapNode(2));
    assertEquals(2, h.minimum().getKey());
    h.insert(new HeapNode(3));
    assertEquals(2, h.minimum().getKey());
    assertTrue(h.heapCheck());
  }

  @Test
  public void testExtractMin() {
    h.insert(new HeapNode(2));
    assertEquals(2, h.minimum().getKey());
    HeapNode m = h.extractMin();
    assertEquals(2, m.getKey());
    assertNotEquals(2, h.minimum().getKey());
    assertTrue(h.heapCheck());
  }

  @Test
  public void testDelete() {
    assertNotEquals(1, h.minimum().getKey());
    h.insert(new HeapNode(1));
    assertEquals(1, h.minimum().getKey());
    h.delete(h.minimum());
    assertNotEquals(1, h.minimum().getKey());
    assertTrue(h.heapCheck());
  }

  @Test
  public void testDecreaseKey() {
    h.insert(new HeapNode(4));
    h.decreaseKey(h.minimum(), 2);
    assertEquals(2, h.minimum().getKey());
    assertTrue(h.heapCheck());
  }

  @Test
  public void testUnion() {
    BiHeap a = new BiHeap();
    a.insert(new HeapNode(2));
    a.insert(new HeapNode(4));
    a.insert(new HeapNode(160));
    a.insert(new HeapNode(155));
    a.insert(new HeapNode(225));
    a.insert(new HeapNode(3));
    assertEquals(2, a.minimum().getKey());
    assertNotEquals(2, h.minimum().getKey());
    BiHeap b = BiHeap.union(a, h);
    assertEquals(2, b.minimum().getKey());
    assertTrue(b.heapCheck());
  }

  @Test
  public void testHeight() {
    assertEquals(5, h.height());
  }
}