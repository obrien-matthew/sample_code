public class HeapNode {
  private int key;        // data
  private int degree;     // # of children
  private HeapNode p;   // p node
  private HeapNode sibling;      // sibling to the right
  private HeapNode child;   // leftmost child

  // constructor
  public HeapNode(int k) {
    this.key = k;
    this.p = null;
    this.child = null;
    this.sibling = null;
    this.degree = 0;
  }

  // setters
  public void setP(HeapNode n) {
    this.p = n;
  }
  public void setChild(HeapNode n) {
    this.child = n;
  }
  public void setSibling(HeapNode n) {
    this.sibling = n;
  }
  public void setDegree(int i) {
    this.degree = i;
  }
  public void setKey(int i) { this.key = i; }

  // getters
  public int getKey() {
    return this.key;
  }
  public HeapNode getSibling() {
    return this.sibling;
  }
  public HeapNode getChild() {
    return this.child;
  }
  public HeapNode getP() { return this.p; }
  public int getDegree() {
    return this.degree;
  }

}
