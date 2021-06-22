package 链表;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list/ 反转一个单链表。
 * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
 * 
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 * 
 * 递归方法 or 迭代方法
 */

public class _206_反转链表 {
	
	// 递归方法
	public ListNode reverseList(ListNode head) {
		// 空节点 or 一个节点 -> 返回head
		if (head == null || head.next == null) {
			return head;
		}
		
		// head后节点翻转
		ListNode newHead = reverseList(head.next);
		// 翻转后最后节点next指向head
		head.next.next = head;
		// head指向null
		head.next = null;
		
		return newHead;
    }
	

	// 	迭代方法
	public ListNode reverseList2(ListNode head) {
		ListNode newHead = null;
		
		while (head != null) {
			// 临时变量 - 存储head.next
			ListNode tmp = head.next;
			// head.next指向newHead
			head.next = newHead;
			// newHead指向head
			newHead = head;
			// head指向下一个节点
			head = tmp;
		}		
		
		return newHead;
	}

}
