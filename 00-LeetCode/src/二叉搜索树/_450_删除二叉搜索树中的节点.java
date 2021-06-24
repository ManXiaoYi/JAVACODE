package 二叉搜索树;

/**
 * https://leetcode-cn.com/problems/delete-node-in-a-bst/
 * 给定一个二叉搜索树的根节点 root 和一个值 key，
 * 删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。
 * 返回二叉搜索树（有可能被更新）的根节点的引用。
 */

public class _450_删除二叉搜索树中的节点 {
    public TreeNode deleteNode(TreeNode root, int key) {
    	if (root == null) {
			return null;
		}
    	
    	
    	
    	return root;
    }

    public boolean hasTwoChilds(TreeNode node) {
		return node.left != null && node.right != null;
	}
    
    // 前驱节点
    public TreeNode predecessor(TreeNode root) {
    	root = root.left;
    	while (root != null) {
			root = root.right;
		}
    	return root;
	}
    
    // 后继节点
    public TreeNode successor(TreeNode root) {
    	root = root.right;
    	while (root != null) {
			root = root.left;
		}
    	return root;
	}
}
