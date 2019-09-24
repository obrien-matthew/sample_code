package tree;

import tree.nodes.NilNode;
import tree.nodes.Node;
import tree.nodes.TreeNode;

public interface IRBTree {
  int min();
  int max();

  String search(int k);
  String sort();

  int height();
  int blackHeight();

  int successor(int key);
  int predecessor(int key);

  void leftRotate(TreeNode z);
  void rightRotate(TreeNode z);

  void insertFixup(Node z);
  void insert(int key);

  void treeCheck();
}
