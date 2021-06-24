package 二叉树;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 *
 * 二叉树：[3,9,20,null,null,15,7],
 * [ [3], [9,20], [15,7] ]
 */

public class _102_二叉树的层序遍历 {
	// 利用队列实现
    public List<List<Integer>> levelOrder(TreeNode root) {
    	if (root == null) {
			return new ArrayList<>();
		}
    	
    	// 返回二维数组
    	List<List<Integer>> res = new ArrayList<>();
    	
    	// 生成队列
    	Queue<TreeNode> queue = new LinkedList<>();
    	// root入队
    	queue.offer(root);
    	
    	// 队列非空循环
    	while (!queue.isEmpty()) {
    		// 队列大小
    		int size = queue.size();
    	
    		// 数组 - 存值
    		List<Integer> list = new ArrayList<>();
    	
    		// 循环 - 所有节点出队
    		while (size > 0) {
    			// 节点出队
    			TreeNode node = queue.poll();
    			// 添加到数组
    			list.add(node.val);
    			
    			// 左节点有值入队
    			if (node.left != null) {
    				queue.offer(node.left);
    			}
    			
    			// 右节点有值入队
    			if (node.right != null) {
    				queue.offer(node.right);
    			}
    			
    			size--;
			}
    		
    		res.add(list);
		}
    	
    	return res;
    }
}
