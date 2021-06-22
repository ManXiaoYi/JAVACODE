package 链表;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle/ 
 * 给定一个链表，判断链表中是否有环。
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
 *
 * 输入：head = [3,2,0,-4], pos = 1 输出：true 解释：链表中有一个环，其尾部连接到第二个节点。
 * 
 * 利用快慢指针
 */

public class _141_环形链表 {

	public boolean hasCycle(ListNode head) {
		// 无节点 or 只有一个节点
		if (head == null || head.next == null) {
			return false;
		}

		// 慢指针
		ListNode slow = head;
		// 快指针
		ListNode fast = head.next;

		// 快指针没有指向null时 循环
		while (fast != null && fast.next != null) {
			// 慢指针节点变换
			slow = slow.next;
			// 快指针节点变换
			fast = fast.next.next;
			
			// 快慢指针相等
			if (slow == fast) {
				return true;
			}
		}

		return false;
	}
}
