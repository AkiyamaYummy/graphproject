package graphPackage;

import containerPackage.Hash;
import containerPackage.Heap;
import containerPackage.List;
import nodePackage.ListNode;

public class Hamilton extends Graph{//因为我对求NP难问题的近似解没兴趣，所以专门建了个类来收容垃圾算法
	public Hamilton(Graph g) {
		vs = g.vs;
		vHash = g.vHash;
		eHash = g.eHash;
	}
	class UnionFind{
		int[] f;
		UnionFind(int size){
			f = new int[size];
			for(int i=0;i<size;i++)f[i] = i;
		}
		int find(int n) {
			return (f[n] == n)?n:(f[n]=find(f[n]));
		}
	}
	public List<Vertex> getPath() {
		Heap<Edge> que = new Heap<Edge>();
		int size = 0;
		Hash<Vertex,Integer> v_i = new Hash<Vertex,Integer>(); 
		for(ListNode<Vertex> i=vs.h.n;i!=vs.t;i=i.n) {
			v_i.set(i.v,size++);//节点映射到编号
			i.v.d = 0;//0代表尚未被dfs遍历到
			for(ListNode<Edge> j=i.v.es.h.n;j!=i.v.es.t;j=j.n) {
				j.v.inT = false;
				j.v.fo = i.v;
				que.push(j.v);
			}
		}
		UnionFind uf = new UnionFind(size);
		for(int i=0;i<size-1;i++) {
			while(!que.isEmpty()) {
				Edge e = que.pop();
				//C.debug.println("2 "+e.fo.name+" "+e.to.name+" "+que.size());
				Vertex v1 = e.fo,v2 = e.to;
				int i1 = v_i.get(v1),i2 = v_i.get(v2);
				int r1 = uf.find(i1),r2 = uf.find(i2);
				if(r1 != r2) {
					uf.f[r1] = r2;
					e.inT = e.inv.inT = true;
					break;
				}
			}
		}
		List<Vertex> reslist = new List<Vertex>();
		dfs(vs.h.n.v,reslist);
		/*
		for(ListNode<Vertex> i=reslist.h.n;i!=reslist.t;i=i.n) {
			C.debug.print(i.v.name+" ");
		}C.debug.println();
		*/
		if(reslist.isEmpty() || reslist.h.n==reslist.t.p)return null;
		reslist.pushBack(reslist.h.n.v);
		ListNode<Vertex> b=reslist.h.n,e=reslist.t.p;
		for(ListNode<Vertex> i=b;i!=e;) {
			ListNode<Vertex> ni = i.n;
			Vertex sv=i.v,ev=i.n.v;
			dijkstra(sv,ev);
			for(Vertex v=ev.p;v!=sv;v=v.p) {
				i.n.add(v);
			}
			i = ni;
		}
		return reslist;
	}
	public void dfs(Vertex n,List<Vertex> res) {
		res.pushBack(n);
		n.d = 1;
		ListNode<Edge> b=n.es.h.n,e=n.es.t;
		for(ListNode<Edge> i=b;i!=e;i=i.n) {
			if(!i.v.inT || i.v.to.d==1)continue;
			dfs(i.v.to,res);
		}
	}
}
