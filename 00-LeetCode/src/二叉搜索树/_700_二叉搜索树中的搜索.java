package 二叉搜索树;

/**
 * https://leetcode-cn.com/problems/search-in-a-binary-search-tree/
 * 给定二叉搜索树（BST）的根节点和一个值。
 * 你需要在BST中找到节点值等于给定值的节点。 
 * 返回以该节点为根的子树。 如果节点不存在，则返回 NULL。
 */

public class _700_二叉搜索树中的搜索 {
	// 递归1
    public TreeNode searchBST4(TreeNode root, int val) {    	
    	if (root == null || root.val == val) {
			return root;
		}
    	
    	return val < root.val ? searchBST4(root.left, val) : searchBST4(root.right, val);
    }
    
	// 递归1
    public TreeNode searchBST3(TreeNode root, int val) {    	
    	if (root == null) {
			return null;
		}
    	
    	if (val < root.val) {
			return searchBST3(root.left, val);
		} else if (val > root.val) {
			return searchBST3(root.right, val);
		} else {
	    	return root;
		}
    }
    
	// 迭代2
    public TreeNode searchBST2(TreeNode root, int val) {
    	while (root != null && root.val != val) {
			root = val < root.val ? root.left : root.right;
		}
    	return root;
    }
    
	// 迭代1
    public TreeNode searchBST(TreeNode root, int val) {
    	while (root != null) {
			if (val > root.val) {
				// 节点在右子树
				root = root.right;
			} else if (val < root.val) {
				// 节点在左子树
				root = root.left;
			} else {
				// 找到节点
				return root;
			}
		}
    	
    	return root;
    }

}
