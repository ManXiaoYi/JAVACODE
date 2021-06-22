package 队列;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/implement-queue-using-stacks/
 * 请你仅使用两个栈实现先入先出队列。
 * 
 * 队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：
 *
 * 两个队列，一个inStack，一个outStack
 * 入队时：push到inStack中
 * 出队时：
 * 如果outStack为空，将inStack所有元素逐一弹出，push到outStack，outStack弹出栈顶元素
 * 如果outStack不为空，outStack弹出栈顶元素
 */

public class _232_用栈实现队列 {
	
	Stack<Integer> inStack = new Stack<>();
	Stack<Integer> outStack = new Stack<>();

	/** Initialize your data structure here. */
    public _232_用栈实现队列() {

    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
    	inStack.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
    	checkOutStack();
    	return outStack.pop();
    }
    
    /** Get the front element. */
    public int peek() {
    	checkOutStack();
    	return outStack.peek();
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
    	return inStack.isEmpty() && outStack.isEmpty();
    }
    
    private void checkOutStack() {
    	if (outStack.isEmpty()) {
    		while (!inStack.isEmpty()) {
    			outStack.push(inStack.pop());
			}
		}
	}
}
