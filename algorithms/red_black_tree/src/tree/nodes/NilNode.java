package tree.nodes;

public class NilNode implements Node {
  private TreeNode root;

  public NilNode() {
    this.root = null;
  }

  public void setRoot(TreeNode r) {
    this.root = r;
  }

  public Node getParent() {
    return this;
  }

  public TreeNode getRoot() {
    return this.root;
  }

  public boolean isBlack() {
    return true;
  }

  public boolean isTreeNode() {
    return false;
  }

  public boolean search(int k) {
    return false;
  }

  public StringBuilder sort(StringBuilder s) {
    return s;
  }

  public int getHeight() {
    return 0;
  }

  public int getBlackHeight() {
    return 1;
  }

  public TreeNode getNode(int k) {
    throw new IllegalArgumentException("No node has the key " + k + ".");
  }

  public TreeNode insert(int key, Node parent, NilNode root) {
    return new TreeNode(key, parent, this);
  }

  public void treeCheck() {
    return;
  }

}
