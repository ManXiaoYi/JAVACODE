package 栈;

import java.util.HashMap;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/valid-parentheses/
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 
 * 有效字符串需满足：
 * 1、左括号必须用相同类型的右括号闭合。
 * 2、左括号必须以正确的顺序闭合。
 *
 */

public class _020_有效的括号 {
	
	private static HashMap<Character, Character> hashMap = new HashMap<>();
	
	static {
		hashMap.put('(', ')');
		hashMap.put('[', ']');
		hashMap.put('{', '}');
	}
	
	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<>();
		
		int length = s.length();
		
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			
			if (c == '(' || c == '[' || c == '{') { // 左括号
				stack.push(c);
			} else { // 右括号
				// 栈空
				if (stack.isEmpty()) return false;
				
				if (c != hashMap.get(stack.pop())) return false;
			}
		}
		
		return stack.isEmpty();
    }

	public boolean isValid2(String s) {
		Stack<Character> stack = new Stack<>();
		
		int length = s.length();
		
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			
			if (c == '(' || c == '[' || c == '{') { // 左括号
				stack.push(c);
			} else { // 右括号
				// 栈空
				if (stack.isEmpty()) return false;
				
				char left = stack.pop();
				if (left == '(' && c != ')') return false;
				if (left == '[' && c != ']') return false;
				if (left == '{' && c != '}') return false;
			}
		}
		
		return stack.isEmpty();
    }
}
