package nodePackage;

public class HashNode<K,V> {
	public V v;
	public K k;
	public boolean u;
	public HashNode() {
		v = null;
		k = null;
		u = false;
	}
	public HashNode(K kk,V vv,boolean uu) {
		v = vv;
		k = kk;
		u = uu;
	}
}
