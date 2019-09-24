public class Edge {
  private Vertex parent;
  private Vertex child;
  private int c;
  private int f;
  private boolean original;

  public Edge(Vertex u, Vertex v, int capacity, int flow) {
    this.parent = u;
    this.child = v;
    this.c = capacity;
    this.f = flow;
    this.original = true;
  }

  public Edge(Vertex u, Vertex v, int flow) {
    this.parent = u;
    this.child = v;
    this.c = Integer.MAX_VALUE;
    this.f = flow;
    this.original = false;
  }

  public Vertex getParent() {
    return this.parent;
  }

  public Vertex getChild() {
    return this.child;
  }

  public int getC() {
    return this.c;
  }

  public int getF() {
    return this.f;
  }

  public void setC(int x) {
    this.c = x;
  }

  public void setF(int x) {
    this.f = x;
  }

  public boolean isOriginal() {
    return this.original;
  }

}
