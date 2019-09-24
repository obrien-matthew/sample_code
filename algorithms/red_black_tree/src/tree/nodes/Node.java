package tree.nodes;

public interface Node {
  boolean isBlack();
  boolean isTreeNode();
  Node getParent();
  boolean search(int k);
  StringBuilder sort(StringBuilder s);
  int getHeight();
  int getBlackHeight();
  TreeNode getNode(int k);
  TreeNode insert(int k, Node parent, NilNode root);
  void treeCheck();
}
