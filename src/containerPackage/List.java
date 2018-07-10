package containerPackage;

import nodePackage.ListNode;

public class List<T> {
	public ListNode<T> h,t;
	public List(){
		h = new ListNode<T>(null, null, null);
		t = new ListNode<T>(null, null, null);
		h.n = t;
		t.p = h;
	}
	public ListNode<T> pushBack(T nt) {
		return t.add(nt);
	}
	public ListNode<T> pushFront(T nt) {
		return h.n.add(nt);
	}
	public ListNode<T> popFront(){
		if(isEmpty())return null;
		ListNode<T> res = h.n;
		h.n.remove();
		return res;
	}
	public boolean isEmpty() {
		return h.n == t;
	}
}
