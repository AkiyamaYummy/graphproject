package graphPackage;

import containerPackage.List;
import nodePackage.ListNode;

public class Vertex implements Comparable<Vertex>{
	public static final int maxd = 31415926; 
	public List<Edge> es;
	public String name,content;
	public ListNode<Vertex> ln;
	public int d;
	public Vertex p,v;//d p v用于迪杰斯特拉算法，d在求哈回路近似值的时候也用于标记节点是否被遍历过
	public String pixes;//pixes代表所占有的像素
	public Vertex(String n,String c) {
		name = n;
		content = c;
		es = new List<Edge>();
		ln = null;
		d = maxd;
		p = null;
		v = null;
		pixes = null;
	}
	public Vertex(Vertex V,int D) {//用于迪杰斯特拉算法
		v = V;
		d = D;
		name = null;
		es = null;
		ln = null;
		p = null;
	}
	public void addEdge(Edge ne) {
		ne.ln = es.pushBack(ne);//设置ne的ln
	}
	public int compareTo(Vertex that) {
		return (this.d>that.d)?1:(this.d==that.d?0:-1);
	}
}
