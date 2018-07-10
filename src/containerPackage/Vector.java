package containerPackage;

public class Vector<T> {
	public static final int initSize = 2;
	private Object[] a;
	private int _size,capa;
	public Vector() {
		a = new Object[initSize];
		_size = 0;
		capa = initSize;
	}
	public void pushBack(T nt) {
		if(_size+1 > capa) {
			Object[] na = new Object[capa=_size*2];
			for(int i=0;i<_size;i++)
				na[i] = a[i];
			a = na;
		}
		a[_size++] = nt;
	}
	public void popBack() {
		_size--;
		if(_size <= capa/2) {
			Object[] na = new Object[capa=capa/2];
			for(int i=0;i<_size;i++)
				na[i] = a[i];
			a = na;
		}
	}
	public int size() {
		return _size;
	}
	@SuppressWarnings("unchecked")
	public T get(int r) {
		if(r >= _size)return null;
		return (T)a[r];
	}
	public void swap(int ra,int rb) {
		if(ra >= size() || rb >= size())return;
		T nt = get(ra);
		a[ra] = a[rb];
		a[rb] = nt;
	}
}
