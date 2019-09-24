import java.util.Stack;

public class BiHeap {
  private HeapNode head;

  // static methods
  public static BiHeap union(BiHeap a, BiHeap b) {
    BiHeap toReturn = new BiHeap();
    toReturn.setHead(binomialMerge(a, b));
    if (toReturn.getHead() != null) {
      HeapNode prev = null;
      HeapNode x =  toReturn.getHead();
      HeapNode next = x.getSibling();
      while (next != null) {
        if (x.getDegree() != next.getDegree()
                || (next.getSibling() != null
                && (next.getSibling()).getDegree() == x.getDegree())) {
          prev = x;
          x = next;
          next = x.getSibling();
        } else {
          if (x.getKey() <= next.getKey()) {
            x.setSibling(next.getSibling());
            binomialLink(next, x);
            next = x.getSibling();
          } else {
            if (prev == null) {
              toReturn.setHead(next);
            } else {
              prev.setSibling(next);
            }
            binomialLink(x, next);
            x = next;
            next = x.getSibling();
          }
        }
      }
//      heapCheck(toReturn.getHead());
    }
    return toReturn;
  }
  public static void binomialLink(HeapNode larger, HeapNode smaller) {
    larger.setP(smaller);
    larger.setSibling(smaller.getChild());
    smaller.setChild(larger);
    smaller.setDegree(smaller.getDegree()+1);
  }
  public static HeapNode binomialMerge(BiHeap a, BiHeap b) {
    // check for empty heaps
    if (a.getHead() == null) {
      return b.getHead();
    } else if (b.getHead() == null) {
      return a.getHead();
    }

    // set variables
    BiHeap h = new BiHeap();
    HeapNode aNode =  a.getHead();
    HeapNode bNode =  b.getHead();
    boolean aBool = true;
    boolean bBool = true;

    // set head
    if (aNode.getDegree() <= bNode.getDegree()) {
      h.setHead(aNode);
      if (aNode.getSibling() == null) {
        aBool = false;
      } else {
        aNode =  aNode.getSibling();
      }
    } else {
      h.setHead(bNode);
      if (bNode.getSibling() == null) {
        bBool = false;
      } else {
        bNode =  bNode.getSibling();
      }
    }

    HeapNode index =  h.getHead();

    // initial merge
    while (true) {
      // still nodes in a and b
      if (aBool && bBool) {
        if (aNode.getDegree() <= bNode.getDegree()) {
          index.setSibling(aNode);
          index = aNode;
          if (aNode.getSibling() == null) {
            aBool = false;
          } else {
            aNode =  aNode.getSibling();
          }
        } else {
          index.setSibling(bNode);
          index = bNode;
          if (bNode.getSibling() == null) {
            bBool = false;
          } else {
            bNode =  bNode.getSibling();
          }
        }

        // still nodes in a
      } else if (aBool) {
        index.setSibling(aNode);
        break;

        // still nodes in b
      } else  if (bBool) {
        index.setSibling(bNode);
        break;

      } else {
        break;
      }
    }

    // merge fixup
    index =  h.getHead();
    HeapNode prev = null;
    HeapNode next = index.getSibling();
    while (next != null) {
      if (index.getDegree() == next.getDegree()) {
        if (index.getKey() < next.getKey()) {
          index.setSibling(next.getSibling());
          binomialLink(next, index);
          next = index.getSibling();
        } else {
          binomialLink(index, next);
          if (prev != null) {
            prev.setSibling(next);
          } else {
            h.setHead(next);
          }
          index = next;
          next = next.getSibling();
        }
      } else {
        prev = index;
        index = next;
        next = next.getSibling();
      }
    }

    return h.getHead();
  }
  public static HeapNode reverseKids(HeapNode n) {
    HeapNode child = n.getChild();
    if (child == null) {
      return child;
    }
    Stack<HeapNode> nodes = new Stack<>();
    while (child != null) {
      nodes.push(child);
      child = child.getSibling();
    }
    HeapNode first = nodes.pop();
    nodes.push(first);
    while (!nodes.empty()) {
      HeapNode a = nodes.pop();
      a.setP(null);
      if (nodes.size() > 1) {
        HeapNode b = nodes.pop();
        a.setSibling(b);
        nodes.push(b);
      } else if (nodes.size() == 1) {
        a.setSibling(nodes.pop());
        a.getSibling().setP(null);
        a.getSibling().setSibling(null);
      }
    }
    return first;
  }

  // constructor
  public BiHeap() {   // equivalent to makeHeap()
    this.head = null;
  }

  // setters
  private void setHead(HeapNode n) {
    this.head = n;
  }

  // getters
  public HeapNode getHead() { return this.head; }

  // heap operations
  public void insert(HeapNode n) {
    BiHeap temp = new BiHeap();
    n.setP(null);
    n.setChild(null);
    n.setSibling(null);
    n.setDegree(0);
    temp.setHead(n);
    this.setHead((union(this, temp).getHead()));
  }
  public HeapNode minimum() {
    HeapNode toReturn = null;
    HeapNode index = this.head;
    int min = Integer.MAX_VALUE;

    while (index != null) {
      HeapNode n =  index;
      if (n.getKey() < min) {
        min = n.getKey();
        toReturn = n;
      }
      index = n.getSibling();
    }

    if (toReturn == null) {
      throw new IllegalStateException("Binomial heap with no nodes has no minimum.");
    }
    return  toReturn;
  }
  public HeapNode extractMin() {
    HeapNode minNode = this.getHead();
    int minVal = Integer.MAX_VALUE;
    if (minNode != null) {
      minVal = minNode.getKey();
    } else {
      throw new IllegalStateException("Cannot extract from an empty heap.");
    }
    HeapNode prev = minNode;
    HeapNode minPrev = null;
    HeapNode index = minNode.getSibling();
    while (index != null) {
      if (index.getKey() < minVal) {
        minVal = index.getKey();
        minPrev = prev;
        minNode = index;
      };
      prev = index;
      index = index.getSibling();
    }
    if (minPrev == null) {
      this.setHead(minNode.getSibling());
    } else {
      minPrev.setSibling(minNode.getSibling());
    }

    BiHeap temp = new BiHeap();
    temp.setHead(reverseKids(minNode));

    this.setHead(union(this, temp).getHead());
    return minNode;
  }
  public void decreaseKey(HeapNode n, int k) {
    if (k > n.getKey()) {
      throw new IllegalArgumentException("New key " + k + " larger than old key for node " + n.getKey());
    }
    n.setKey(k);
    HeapNode p = n.getP();
    while (p != null && n.getKey() < p.getKey()) {
      int temp = n.getKey();
      n.setKey(p.getKey());
      p.setKey(temp);
      n = p;
      p = p.getP();
    }
  }
  public void delete(HeapNode n) {
    this.decreaseKey(n, Integer.MIN_VALUE);
    this.extractMin();
  }
  public boolean heapCheck() {
    HeapNode i = this.getHead();
    HeapNode n = i.getSibling();
    Stack<HeapNode> children = new Stack<>();
    while (n != null) {
      if (i.getDegree() >= n.getDegree()) {
        throw new IllegalStateException("Root " + i.getKey() + ", " + n.getKey()
                + " nodes improperly ordered.");
      }
      if (i.getChild() != null) {
        children.push(i.getChild());
      }
      i = n;
      n = n.getSibling();
    }

    while (!children.empty()) {
      HeapNode c = children.pop();
      i = c;
      n = c.getSibling();
      while (n != null) {
        if (i.getDegree() <= n.getDegree()) {
          throw new IllegalStateException("Children " + i.getKey() + ", " + n.getKey()
                  + " nodes improperly ordered.");
        }
        if (i.getChild() != null) {
          children.push(i.getChild());
        }
        if (i.getP().getKey() > i.getKey()) {
          throw new IllegalStateException("Parent node " + i.getP().getKey()
                  + " larger than child " + i.getKey());
        }
        i = n;
        n = n.getSibling();
      }
    }
    return true;
  }
  public int height() {
    HeapNode n = this.getHead();
    HeapNode max = n;
    int degree = n.getDegree();
    n = n.getSibling();
    while (n != null) {
      if (n.getDegree() > degree) {
        max = n;
        degree = max.getDegree();
      }
      n = n.getSibling();
    }

    int height = 1;
    if (max.getChild() != null) {
      HeapNode c = max.getChild();
      while (c != null) {
        height += 1;
        c = c.getChild();
      }
    }

    return height;
  }
}
