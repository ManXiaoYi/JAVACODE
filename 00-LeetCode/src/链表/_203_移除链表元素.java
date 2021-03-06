package 链表;

/**
 * https://leetcode-cn.com/problems/remove-linked-list-elements/ 
 * 给你一个链表的头节点 head和一个整数 val ， 
 * 请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。 
 * 
 * 输入：head = [1,2,6,3,4,5,6], val = 6 
 * 输出：[1,2,3,4,5]
 *
 * 虚拟头节点
 */

public class _203_移除链表元素 {
    public ListNode removeElements(ListNode head, int val) {
    	// 虚拟头节点
    	ListNode newHead = new ListNode(-1);
    	// 虚拟头节点next指向head
    	newHead.next = head;
    	
        // 当前节点
    	ListNode currentNode = newHead;
    	
    	// 节点next不为null 循环
    	while (currentNode.next != null) {
			if (currentNode.next.val == val) {
				// 下一节点值为val，删除该节点
				currentNode.next = currentNode.next.next;
			} else {
				// 下一节点值不为val，指向下一节点
				currentNode = currentNode.next;
			}
		}
    	
    	return newHead.next;
    }
    
}
