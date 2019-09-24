package tree.nodes;

import static java.lang.Math.max;


public class TreeNode implements Node {
  private int key;
  private Node lchild;
  private Node rchild;
  private Node parent;
  private boolean black;

  /**
   * Constructor
   *
   * @param key value in node
   */
  public TreeNode(int key, Node p, Node r) {
    this.key = key;
    this.lchild = r;
    this.rchild = r;
    this.parent = p;
    this.black = false;
  }



  // Setters

  public void setLChild(Node n) {
    this.lchild = n;
  }

  public void setRChild(Node n) {
    this.rchild = n;
  }

  public void setParent(Node n) {
    this.parent = n;
  }

  public void setBlack(boolean b) {
    this.black = b;
  }



  // Getters

  public boolean isBlack() {
    return this.black;
  }

  public boolean isTreeNode() {
    return true;
  }

  public int getKey() {
    return this.key;
  }

  public Node getLChild() { return this.lchild; }

  public Node getRChild() { return this.rchild; }

  public Node getParent() { return this.parent; }


  // tree Functions

  public int getMax() {
    if (this.rchild.isTreeNode()) {
      TreeNode n = (TreeNode) this.rchild;
      return n.getMax();
    }

    return this.key;
  }

  public int getMin() {
    if (this.lchild.isTreeNode()) {
      TreeNode n = (TreeNode) this.lchild;
      return n.getMin();
    }

    return this.key;
  }

  public TreeNode getNode(int k) {
    if (this.key == k) {
      return this;
    } else if (this.key > k) {
      return this.lchild.getNode(k);
    } else {
      return this.rchild.getNode(k);
    }
  }

  public boolean search(int k) {
    if (this.key == k) {
      return true;
    }

    if (k > this.key) {
      return this.rchild.search(k);
    } else {
      return this.lchild.search(k);
    }
  }

  public StringBuilder sort(StringBuilder s) {
    this.lchild.sort(s);
    s.append(this.key);
    s.append(" ");
    this.rchild.sort(s);
    return s;
  }

  public int getSuccessor() {
    if (this.rchild.getClass() != NilNode.class) {
      TreeNode n = (TreeNode) this.rchild;
      return n.getMin();
    }

    TreeNode x = this;
    Node y = this.parent;
    while (y.getClass() != NilNode.class) {
      TreeNode j = (TreeNode) y;
      if (j.getLChild() == x) {
        return j.getKey();
      }
      x = j;
      y = j.getParent();
    }
    return this.key;
  }

  public int getPredecessor() {
    if (this.lchild.getClass() != NilNode.class) {
      TreeNode n = (TreeNode) this.lchild;
      return n.getMax();
    }

    TreeNode x = this;
    Node y = this.parent;
    while (y.getClass() != NilNode.class) {
      TreeNode j = (TreeNode) y;
      if (j.getRChild() == x) {
        return j.getKey();
      }
      x = j;
      y = j.getParent();
    }
    return this.key;
  }

  public int getHeight() {
    int l = this.lchild.getHeight();
    int r = this.rchild.getHeight();
    return max(l, r) + 1;
  }

  public int getBlackHeight() {
    int h = this.getRChild().getBlackHeight();


    if (this.black) {
      return h + 1;
    }

    return h;
  }

  public TreeNode insert(int k, Node parent, NilNode root) {
    if (k < this.key) {
      this.setLChild(this.lchild.insert(k, this, root));
    } else if (k > this.key) {
      this.setRChild(this.rchild.insert(k, this, root));
    }

    return this;
  }

  public void treeCheck() {
    if (!this.black) {
      if (!this.getLChild().isBlack() || !this.getRChild().isBlack()) {
        throw new IllegalStateException("Nodes under red node " + this.key + " are also red.");
      }
    }

    int l = this.lchild.getBlackHeight();
    int r = this.rchild.getBlackHeight();
    if (l != r) {
      throw new IllegalStateException("Paths unbalanced under node "
              + this.key + " (" + l + "/" + r + ")");
    }

    this.lchild.treeCheck();
    this.rchild.treeCheck();
  }
}
