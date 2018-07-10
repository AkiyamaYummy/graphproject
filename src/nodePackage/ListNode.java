package nodePackage;

public class ListNode<T> {
	public ListNode<T> p,n;
	public T v;
	public ListNode(T V,ListNode<T> P,ListNode<T> N) {
		v = V;
		p = P;
		n = N;
	}
	public boolean remove() {
		if(p==null || n==null)return false;
		p.n = n;
		n.p = p;
		return true;
	}
	public ListNode<T> add(T nt) {
		if(p == null)return null;
		ListNode<T> nln = new ListNode<T>(nt,p,this);
		p.n = nln;
		this.p = nln;
		return nln;
	}
}
