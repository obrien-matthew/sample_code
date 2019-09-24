import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;

public class Graph {

  private List<Vertex> vertices;
  private Vertex s;
  private Vertex t;

  public Graph(int size, int s_, int t_) {
    this.vertices = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      this.vertices.add(new Vertex());
    }
    this.s = this.vertices.get(s_);
    this.t = this.vertices.get(t_);
  }

  public void addEdge(int u, int v, int c) {
    this.vertices.get(u).addForward(this.vertices.get(v));
    this.vertices.get(u).addFEdge(new Edge(vertices.get(u), vertices.get(v), c, 0));
    this.vertices.get(v).addBack(this.vertices.get(u));
    this.vertices.get(v).addBEdge(new Edge(vertices.get(v), vertices.get(u), 0));

  }

  private void initialize() {
    this.s.setH(vertices.size());
    this.s.initializeEdges();

  }

  private Vertex getOverflowV() {
    for (Vertex v : vertices) {
      if (v.isOverflow() && v != this.t && v != this.s) {
        return v;
      }
    }
    return null;
  }

  private void push(Vertex u, Vertex v) {
    boolean f = true;
    Edge e = null;
    try {
      e = u.getFEdge(v);
    } catch (IllegalArgumentException o) {
      f = false;
    }

    int net = 0;

    if (f) {
      net = min(e.getC() - e.getF(), u.getE());
      e.setF(e.getF() + net);
    } else {
      try {
        e = v.getFEdge(u);
      } catch (IllegalStateException h) {
        throw new IllegalStateException("Error pushing");
      }
      net = min(e.getF(), u.getE());
      e.setF(e.getF() - net);
      e = u.getBEdge(v);
      e.setF(e.getF() + net);
    }

    u.adjE(net * (-1));
    v.adjE(net);
  }

  private void relabel(Vertex u) {
    u.relabel();
  }

  public int getMaxFlow() {
    this.initialize();

    Vertex x = this.getOverflowV();
    while (x != null) {
      boolean pushed = false;

      for (Edge e : x.getFedges()) {
        Vertex c = e.getChild();
        if (c.getH() + 1 == x.getH() && e.getC() - e.getF() > 0) {
          pushed = true;
          push(x, c);
          break;
        }
      }

      if (!pushed) {
        for (Edge e : x.getBedges()) {
          Vertex c = e.getChild();
          if (c.getH() + 1 == x.getH() && c.getFEdge(x).getF() > 0) {
            pushed = true;
            push(x, c);
            break;
          }
        }
      }

      if (!pushed) {
        relabel(x);
      }

      x = this.getOverflowV();
    }

    return this.t.getFlowIn();
  }

}
