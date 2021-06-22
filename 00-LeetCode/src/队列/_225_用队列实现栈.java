package 队列;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/implement-stack-using-queues/
 * 请你仅使用两个队列实现一个后入先出（LIFO）的栈
 * 并支持普通队列的全部四种操作（push、top、pop 和 empty）。
 *
 */

public class _225_用队列实现栈 {
	Queue<Integer> inQueue = new LinkedList<>();
	Queue<Integer> outQueue = new LinkedList<>();
	
    /** Initialize your data structure here. */
    public _225_用队列实现栈() {

    }
    
    /** Push element x onto stack. */
    public void push(int x) {
    	// 入列
    	inQueue.offer(x);
    	
    	// 将outQueue队列中所有元素 入列到inQueue
    	while (!outQueue.isEmpty()) {
			inQueue.offer(outQueue.poll());
		}
    	
    	// 交换inQueue和outQueue，确保inQueue在元素入列前为空队列
    	Queue<Integer> temp = inQueue;
    	inQueue = outQueue;
    	outQueue = temp;
    	
    }
    
    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
    	return outQueue.poll();
    }
    
    /** Get the top element. */
    public int top() {
    	return outQueue.peek();
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
    	return outQueue.isEmpty();
    }
}
