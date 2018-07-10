package containerPackage;

public class Heap<T>{ //小根堆
	Vector<T> v;
	public Heap(){
		v = new Vector<T>();
		v.pushBack(null);
	}
	public T top() {
		if(v.size() <= 1)return null;
		return v.get(1);
	}
	@SuppressWarnings("unchecked")
	public T pop() {
		if(v.size() <= 1)return null;
		T res = v.get(1);
		v.swap(1,v.size()-1);
		v.popBack();
		for(int r=1;r*2<v.size();) {
			Comparable<T> tn=(Comparable<T>)v.get(r);
			T ls=v.get(r*2),rs=v.get(r*2+((r*2+1<v.size())?1:0));
			if(tn.compareTo(ls) != 1&&tn.compareTo(rs) != 1)
				break;
			r = r*2+((((Comparable<T>)ls).compareTo(rs)!=1)?0:1);
			v.swap(r,r/2);
		}
		return res;
	}
	@SuppressWarnings("unchecked")
	public void push(T nt) {
		v.pushBack(nt);
		for(int r=v.size()-1;r>1;) {
			Comparable<T> tn=(Comparable<T>)v.get(r/2);
			T ts = v.get(r);
			if(tn.compareTo(ts) != 1)break;
			v.swap(r,r/2);
			r /= 2;
		}
	}
	public boolean isEmpty() {
		return v.size() <= 1;
	}
	public int size() {
		return v.size()-1;
	}
}
