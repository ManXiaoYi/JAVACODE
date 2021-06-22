package 栈;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/score-of-parentheses/ 
 * 给定一个平衡括号字符串S，按下述规则计算该字符串的分数：
 * 
 * () 得 1 分。
 * AB 得 A + B 分，其中 A 和 B 是平衡括号字符串。
 * (A) 得 2 * A 分，其中 A 是平衡括号字符串。
 */

public class _856_括号的分数 {

    public int scoreOfParentheses(String S) {
    	Stack<Character> stack = new Stack<>();
    	
    	int count = 0;
    	
    	int length = S.length();
    	for (int i = 0; i < length; i++) {
    		char c = S.charAt(i);
			if (c == '(') { // 左括号
				stack.push(c);
			} else { // 右括号
//				stack.pop();
//				count += 2 * stack.size();
			}
		}
    	
    	return count;
    }
}
