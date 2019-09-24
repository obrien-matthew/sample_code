package tree;

import tree.nodes.NilNode;
import tree.nodes.Node;
import tree.nodes.TreeNode;

public class RBTree implements IRBTree {
  private NilNode root;

  public RBTree() {
    this.root = new NilNode();
  }

  public int min() {
    if (this.root.getRoot() == null) {
      throw new IllegalStateException("Empty tree has no min value.");
    }

    return this.root.getRoot().getMin();
  }

  public int max() {
    if (this.root.getRoot() == null) {
      throw new IllegalStateException("Empty tree has no max value.");
    }

    return this.root.getRoot().getMax();
  }

  public String search(int k) {
    if (this.root.getRoot() == null) {
      throw new IllegalStateException("Empty tree has no values.");
    }

    if (this.root.getRoot().search(k)) {
      return "Node found.\n";
    } else {
      return "Node not found.\n";
    }
  }

  public String sort() {
    if (this.root.getRoot() == null) {
      throw new IllegalStateException("Empty tree has no values.");
    }

    StringBuilder s = new StringBuilder();
    s = this.root.getRoot().sort(s);
    s.deleteCharAt(s.length() - 1); // delete extra space at end
    return s.toString();
  }

  public int height() {
    if (this.root.getRoot() == null) {
      throw new IllegalStateException("Empty tree has no height.");
    }

    return this.root.getRoot().getHeight();
  }

  public int blackHeight() {
    if (this.root.getRoot() == null) {
      throw new IllegalStateException("Empty tree has no black height.");
    }

    return this.root.getRoot().getBlackHeight();
  }

  public int successor(int key) {
    if (this.root.getRoot() == null) {
      throw new IllegalStateException("Empty tree has no nodes.");
    }

    TreeNode n = (TreeNode) this.root.getRoot().getNode(key);
    return n.getSuccessor();
  }

  public int predecessor(int key) {
    if (this.root.getRoot() == null) {
      throw new IllegalStateException("Empty tree has no nodes.");
    }

    TreeNode n = (TreeNode) this.root.getRoot().getNode(key);
    return n.getPredecessor();
  }

  public void leftRotate(TreeNode z) {
    if (z.getRChild().isTreeNode()) {
      TreeNode rc = (TreeNode) z.getRChild();
      Node rclc = rc.getLChild();

      rc.setParent(z.getParent());
      if (z == this.root.getRoot()) {
        this.root.setRoot(rc);
      } else {
        TreeNode p = (TreeNode) z.getParent();
        if (p.getLChild() == z) {
          p.setLChild(rc);
        } else {
          p.setRChild(rc);
        }
      }

      z.setParent(rc);
      z.setRChild(this.root);
      rc.setLChild(z);
      if (rclc.isTreeNode()) {
        TreeNode rclc_cast = (TreeNode) rclc;
        z.setRChild(rclc);
        rclc_cast.setParent(z);
      }
    }
  }

  public void rightRotate(TreeNode z) {
    if (z.getLChild().isTreeNode()) {
      TreeNode lc = (TreeNode) z.getLChild();
      Node lcrc = lc.getRChild();

      lc.setParent(z.getParent());
      if (z == this.root.getRoot()) {
        this.root.setRoot(lc);
      } else {
        TreeNode p = (TreeNode) z.getParent();
        if (p.getRChild() == z) {
          p.setRChild(lc);
        } else {
          p.setLChild(lc);
        }
      }

      z.setParent(lc);
      z.setLChild(this.root);
      lc.setRChild(z);
      if (lcrc.getClass() != NilNode.class) {
        TreeNode lcrc_cast = (TreeNode) lcrc;
        z.setLChild(lcrc);
        lcrc_cast.setParent(z);
      }
    }
  }

  public void insertFixup(Node n) {
    if (!n.isTreeNode()) {
      return;
    }

    TreeNode x = (TreeNode) n;
    x.setBlack(false);

    if (this.root.getRoot() != x && !x.getParent().isBlack()) {
      TreeNode p = (TreeNode) x.getParent();
      TreeNode pp = (TreeNode) p.getParent();
      if (p == pp.getLChild()) {                      // if parent is lchild
        if (!pp.getRChild().isBlack()) {                // if p.bro is red
          TreeNode y = (TreeNode) pp.getRChild();
          p.setBlack(true);
          y.setBlack(true);
          pp.setBlack(false);
          insertFixup(pp);
        } else {                                        // if p.bro is black
          if (x == p.getRChild()) {                       // if x is rchild
            x = p;
            leftRotate(x);
          }
          ((TreeNode) x.getParent()).setBlack(true);
          pp.setBlack(false);
          rightRotate(pp);
        }
      } else {                                          // if parent is rchild
        if (!pp.getLChild().isBlack()) {                  // if p.bro is red
          TreeNode y = (TreeNode) pp.getLChild();
          p.setBlack(true);
          y.setBlack(true);
          pp.setBlack(false);
          insertFixup(pp);
        } else {                                          // if p.bro is black
          if (x == p.getLChild()) {                         // if x is lchild
            x = p;
            rightRotate(x);
          }
          ((TreeNode) x.getParent()).setBlack(true);
          pp.setBlack(false);
          leftRotate(pp);
        }
      }
    }
    //}
    if (this.root.getRoot().isTreeNode()) {
      this.root.getRoot().setBlack(true);
    }
    return;
  }

  public void treeCheck() throws IllegalStateException {
    if (!this.root.getRoot().isBlack()) {
      throw new IllegalStateException("Root node is not black.");
    }
    this.root.getRoot().treeCheck();
  }

  public void insert(int key) throws IllegalStateException {
    // In the case of the first node being added:
    if (this.root.getRoot() == null) {
      this.root.setRoot(new TreeNode(key, this.root, this.root));
      this.root.getRoot().setBlack(true);
    } else {   // Otherwise
      this.root.setRoot(this.root.getRoot().insert(key, this.root, this.root));
      this.insertFixup(this.root.getRoot().getNode(key));
    }
    this.treeCheck();
  }


}
