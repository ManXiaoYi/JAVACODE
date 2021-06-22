package stack;

import List.ArrayList;

public class Stack<E> {
	
	ArrayList<E> list = new ArrayList<>();

	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void clear() {
		list.clear();
	}
	
	public void push(E element) {
		list.add(element);
	}
	
	public E pop() {
		return list.remove(list.size() - 1);
	}
	
	public E tap() {
		return list.get(list.size() - 1);
	}
	
}
