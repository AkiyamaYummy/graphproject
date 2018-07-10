package graphPackage;

import nodePackage.ListNode;

public class Edge implements Comparable<Edge>{
	public int len;
	public Vertex fo,to;//fo为该边起点，一般为null
	public Edge inv;
	public ListNode<Edge> ln;
	public boolean inT;//是否在最小生成树中
	public String pixes;//pixes代表所占有的像素
	public Edge(int l,Vertex t) {
		len = l;
		to = t;
		inv = null;
		ln = null;
		inT = false;
		fo = null;
		pixes = null;
	}
	public int compareTo(Edge that) {
		return (this.len>that.len)?1:(this.len==that.len?0:-1);
	}
}
