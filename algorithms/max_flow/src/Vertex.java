import java.util.ArrayList;
import java.util.List;

public class Vertex {
  private int h;
  private int e;
  private List<Vertex> forward;
  private List<Vertex> back;
  private List<Edge> fedge;
  private List<Edge> bedge;

  public Vertex() {
    this.h = 0;
    this.e = 0;
    this.forward = new ArrayList<>();
    this.back = new ArrayList<>();
    this.fedge = new ArrayList<>();
    this.bedge = new ArrayList<>();
  }

  public boolean isOverflow() {
    if (this.e > 0) {
      return true;
    }

    return false;
  }

  public Edge getFEdge(Vertex v) {
    for (Edge e : fedge) {
      if (e.getChild() == v) {
        return e;
      }
    }
    throw new IllegalArgumentException("Error getting fedge.");
  }

  public Edge getBEdge(Vertex v) {
    for (Edge e : bedge) {
      if (e.getChild() == v) {
        return e;
      }
    }
    throw new IllegalArgumentException("Error getting bedge.");
  }

  public void relabel() {
    int min = Integer.MAX_VALUE;
    boolean gotmin = false;
    for (Edge e : fedge) {
      if (e.getChild().getH() == this.h - 1 && e.getC() - e.getF() != 0) {
        return;
      } else if (e.getChild().getH() < min && e.getC() - e.getF() > 0) {
        min = e.getChild().getH();
        gotmin = true;
      }
    }
    for (Edge e : bedge) {
      if (e.getChild().getH() == this.h - 1 && e.getChild().getFEdge(this).getF() > 0) {
        return;
      } else if (e.getChild().getH() < min && e.getChild().getFEdge(this).getF() > 0) {
        min = e.getChild().getH();
        gotmin = true;
      }
    }
    if (gotmin) {
      this.h = min + 1;
    }
  }

  public void adjE(int x) {
    this.e = this.e + x;
  }

  public int getE() {
    return this.e;
  }

  public int getH() {
    return this.h;
  }

  public List<Edge> getFedges() {
    return this.fedge;
  }

  public List<Edge> getBedges() {
    return this.bedge;
  }

  public void setH(int i) {
    this.h = i;
  }

  public void addForward(Vertex v) {
    this.forward.add(v);
  }

  public void addBack(Vertex v) {
    this.back.add(v);
  }

  public void addFEdge(Edge e) {
    this.fedge.add(e);
  }

  public void addBEdge(Edge e) {
    this.bedge.add(e);
  }

  public int getFlowIn() {
    int sum = 0;
    for (Vertex v : this.back) {
      Edge e = v.getFEdge(this);
      sum += e.getF();
    }
    return sum;
  }

  public void initializeEdges() {
    for (Edge e : this.fedge) {
      e.setF(e.getC());
      this.adjE(-1*e.getC());
      e.getChild().adjE(e.getC());
    }
  }
}
