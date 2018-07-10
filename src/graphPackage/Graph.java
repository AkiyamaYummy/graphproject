package graphPackage;

import containerPackage.Hash;
import containerPackage.Heap;
import containerPackage.List;
import nodePackage.ListNode;

public class Graph {
	public List<Vertex> vs = new List<Vertex>();
	public Hash<String,Vertex> vHash = new Hash<String,Vertex>();
	public Hash<String,Edge> eHash = new Hash<String,Edge>();
	public boolean addVertex(String vname,String vcontent) {//如果原本就有同名顶点，直接返回false
		if(vHash.get(vname) != null)return false;
		Vertex nv = new Vertex(vname,vcontent);
		nv.ln = vs.pushBack(nv);//在链表中添加新点，设置nv当中的ln
		vHash.set(nv.name,nv);//在hash中添加新点
		return true;
	}
	public boolean addEdge(String vname1,String vname2,int len) {
		if(vname1.compareTo(vname2) == 1) {
			String t = vname2;
			vname2 = vname1;
			vname1 = t;
		}//保证vname1小于vname2
		String ename = vname1+"-"+vname2;
		//C.debug.println(ename);
		if(eHash.get(ename) != null) {
			//C.debug.println("return addedge 1");
			return false;//如果原本就有边，直接返回false
		}
		Vertex v1 = vHash.get(vname1),v2 = vHash.get(vname2);
		if(v1 == null || v2 == null) {
			//C.debug.println("return addedge 2");
			return false;//如果需要连接的点不存在，直接返回false
		}
		Edge e1 = new Edge(len,v2),e2 = new Edge(len,v1);
		e1.inv = e2;
		e2.inv = e1;//绑定互为反的边
		v1.addEdge(e1);
		v2.addEdge(e2);//在链表中添加新边，在addEdge中会设置e1和e2中的ln
		eHash.set(ename,e1);//在hash中添加新边
		return true;
	}
	public boolean removeEdge(String vname1,String vname2) {//如果原本没有边，直接返回false
		if(vname1.compareTo(vname2) == 1) {
			String t = vname2;
			vname2 = vname1;
			vname1 = t;
		}//保证vname1小于vname2
		String ename = vname1+"-"+vname2;
		Edge etr1 = eHash.get(ename),etr2; // Edge to remove
		if(etr1 == null)return false;//如果原本没有边，直接返回false
		eHash.remove(ename);//从hash中删除边
		etr2 = etr1.inv;
		etr1.ln.remove();
		etr2.ln.remove();//从链表中删除边
		return true;
	}
	public boolean removeVertex(String vname) {//如果原本没有该点，直接返回false
		Vertex vtr = vHash.get(vname);//Vertex to remove
		if(vtr == null)return false;
		vHash.remove(vname);//从hash中删除点
		vtr.ln.remove();//从链表中删除点
		while(!vtr.es.isEmpty()) {
			ListNode<Edge> lnetr1 = vtr.es.h.n,lnetr2 = lnetr1.v.inv.ln;
			lnetr1.remove();
			lnetr2.remove();
		}
		return true;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"V\":{");
		for(ListNode<Vertex> i=vs.h.n;i!=vs.t;i=i.n) {
			String p = (i.v.pixes==null)?"[]":i.v.pixes;
			sb.append("\""+i.v.name+"\":{\"content\":\""+i.v.content+"\",\"pix\":"+p+"}");
			sb.append(((i.n != vs.t)?",":""));
		}
		sb.append("},");
		sb.append("\"E\":{");
		for(ListNode<Vertex> i=vs.h.n;i!=vs.t;i=i.n) {
			sb.append("\""+i.v.name+"\":{");
			for(ListNode<Edge> j=i.v.es.h.n;j!=i.v.es.t;j=j.n) {
				String p = (j.v.pixes==null)?"[]":j.v.pixes;
				sb.append("\""+j.v.to.name+"\":{\"length\":"+j.v.len+",\"pix\":"+p+"}");
				if(j.n != i.v.es.t)sb.append(",");
			}
			sb.append("}"+((i.n != vs.t)?",":""));
		}
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}
	public List<Vertex> getShortestPath(String sta,String end){
		Vertex sv = vHash.get(sta),ev = vHash.get(end);
		if(sv != null&&ev != null&&dijkstra(sv,ev)) {
			List<Vertex> res = new List<Vertex>();
			for(Vertex i=ev;i!=sv;i=i.p) res.pushFront(i);
			res.pushFront(sv);
			return res;
		}else return null;
	}
	public boolean dijkstra(String sta,String end) {
		Vertex sv = vHash.get(sta),ev = new Vertex(end,null);
		return dijkstra(sv,ev);
	}
	public boolean dijkstra(Vertex sv,Vertex ev) {
		String end = (ev==null)?null:(ev.name);
		if(sv == null)return false;
		for(ListNode<Vertex> i=vs.h.n;i!=vs.t;i=i.n) {
			i.v.d = Vertex.maxd;
			i.v.p = null;
		}
		sv.d = 0;
		Heap<Vertex> que = new Heap<Vertex>();
		que.push(new Vertex(sv,0));
		while(!que.isEmpty()) {
			Vertex t = que.pop(),n=t.v;
			if(end != null && t.v.name.equals(end)) {
				return true;
			}
			ListNode<Edge> b=n.es.h.n,e=n.es.t;
			if(t.d > n.d)continue;
			for(ListNode<Edge> i=b;i!=e;i=i.n) {
				Vertex s=i.v.to;
				int len = i.v.len;
				if(t.d+len < s.d) {
					s.d = t.d+len;
					s.p = n;
					que.push(new Vertex(s,s.d));
				}
			}
		}
		return true;
	}
}
