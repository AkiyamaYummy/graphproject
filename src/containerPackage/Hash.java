package containerPackage;

import nodePackage.HashNode;

public class Hash<K,V> {
	static final int totSize = 11;
	public HashNode<K,V>[] a;
	public final int size; 
	@SuppressWarnings("unchecked")
	public Hash(){
		size = totSize;
		a = new HashNode[size];
		for(int i=0;i<size;i++) {
			a[i] = new HashNode<K,V>();
		}
	}
	public boolean set(K s,V v) {
		int k = Math.abs(getHashKey(s))%size;
		for(int i=1;i<=size;i++) {
			int d = ((i%2==1)?-1:1)*(i/2);
			int tk = ((k+d)%size+size)%size;
			if(!a[tk].u) {
				a[tk] = new HashNode<K,V>(s,v,true);
				return true;
			}
		}
		return false;
	}
	public V get(K s) {
		int k = Math.abs(getHashKey(s))%size;
		for(int i=1;i<=size;i++) {
			int d = ((i%2==1)?-1:1)*(i/2);
			int tk = ((k+d)%size+size)%size;
			if(!a[tk].u)return null;
			if(a[tk].k.equals(s))return a[tk].v;
		}
		return null;
	}
	public boolean remove(K s) {
		int k = Math.abs(getHashKey(s))%size;
		for(int i=1;i<=size;i++) {
			int d = ((i%2==1)?-1:1)*(i/2);
			int tk = ((k+d)%size+size)%size;
			if(!a[tk].u)return false;
			if(a[tk].k.equals(s)) {
				a[tk].u = false;
				return true;
			}
		}
		return false;
	}
	public int getHashKey(K s) {
		int k = s.hashCode();
		k ^= (k>>>20)^(k>>>12);
		return k^(k>>>7)^(k>>>4);
	}
}
