package graphPackage;

import containerPackage.Hash;
import containerPackage.Heap;
import containerPackage.List;
import containerPackage.Vector;
import nodePackage.ListNode;

public class Graph {
	public List<Vertex> vs;
	public Hash<String,Vertex> vHash;
	public Hash<String,Edge> eHash;
	public Graph() {
		vs = new List<Vertex>();
		vHash = new Hash<String,Vertex>();
		eHash = new Hash<String,Edge>();
	}
	public Graph(Vector<String> cmds) {
		vs = new List<Vertex>();
		vHash = new Hash<String,Vertex>();
		eHash = new Hash<String,Edge>();
		readCmd(cmds);
	}
	public boolean addVertex(String vname,String vcontent) {//如果原本就有同名顶点，直接返回false
		if(vHash.get(vname) != null)return false;
		Vertex nv = new Vertex(vname,vcontent);
		nv.ln = vs.pushBack(nv);//在链表中添加新点，设置nv当中的ln
		vHash.set(nv.name,nv);//在hash中添加新点
		return true;
	}
	public boolean addEdge(String vname1,String vname2,int len) {
		if(len <= 0)return false;
		if(vname1.compareTo(vname2) == 1) {
			String t = vname2;
			vname2 = vname1;
			vname1 = t;
		}//保证vname1小于vname2
		String ename = vname1+"-"+vname2;
		//C.debug.println(ename);
		if(eHash.get(ename) != null) {
			//System.out.println("return addedge 1");
			return false;//如果原本就有边，直接返回false
		}
		Vertex v1 = vHash.get(vname1),v2 = vHash.get(vname2);
		if(v1 == null || v2 == null) {
			//System.out.println("return addedge 2");
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
		List<Vertex> res = new List<Vertex>();
		Vertex sv = vHash.get(sta),ev = vHash.get(end);
		if(sv != null&&ev != null&&dijkstra(sv,ev)) {
			for(Vertex i=ev;i!=sv;i=i.p) res.pushFront(i);
			res.pushFront(sv);
		}
		return res;
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
	public String readCmd(Vector<String> cmds) {
		StringBuffer sb = new StringBuffer(); 
		for(int i=0;i<cmds.size();i++) {
			String[] ops = cmds.get(i).split(" ");
			for(int j=0;j<ops.length;j++)
				ops[j] = ops[j].replaceAll("_"," ");
			//在命令行单个操作数或操作符中，用“_”代替“ ”
			try {
				if(ops[0].equals("av") && ops.length == 3) {
					if(!addVertex(ops[1],ops[2]))
						throw new Exception("1:"+cmds.get(i));
				}else if(ops[0].equals("rv") && ops.length == 2) {
					if(!removeVertex(ops[1]))
						throw new Exception("2:"+cmds.get(i));
				}else if(ops[0].equals("ae") && ops.length == 4) {
					if(!addEdge(ops[1],ops[2],Integer.parseInt(ops[3])))
						throw new Exception("3:"+cmds.get(i));
				}else if(ops[0].equals("re") && ops.length == 3) {
					if(!removeEdge(ops[1],ops[2]))
						throw new Exception("4:"+cmds.get(i));
				}else if(ops[0].equals("sp") && ops.length == 3) {
					if(!setPix(ops[1],ops[2])) {
						throw new Exception("5:"+cmds.get(i));
					}
				}else throw new Exception("6:"+cmds.get(i));
			}catch(Exception e) {
				//e.printStackTrace();
				sb.append("\""+cmds.get(i)+"\"failed.<br/>");
			}
		}
		return sb.toString();
	}
	public boolean setPix(String pid,String pcontent) {
		String[] sp = pid.split("-");
		if(sp.length == 1) {
			Vertex vte = vHash.get(pid);//Vertex to edit
			if(vte == null) return false;
			vte.pixes = pcontent;
			return true;
		}else if(sp.length == 2) {
			if(sp[0].compareTo(sp[1]) == 1) {
				String t = sp[1];
				sp[1] = sp[0];
				sp[0] = t;
			}//保证sp[0]小于sp[1]
			pid = sp[0]+"-"+sp[1];
			Edge ete = eHash.get(pid);//Edge to edit
			if(ete == null) {
				return false;
			}
			//System.out.println(pid+" "+pcontent);
			ete.pixes = pcontent;
			return true;
		}
		return false;
	}
	public Vector<String> toCmds(){
		Vector<String> res = new Vector<String>();
		for(ListNode<Vertex> i=vs.h.n;i!=vs.t;i=i.n) {
			String vn = i.v.name,vc = i.v.content,vp = i.v.pixes;
			vn = vn.replace(" ","_");
			vc = vc.replace(" ","_");
			res.pushBack("av "+vn+" "+vc);
			if(vp != null) {
				vp = vp.replace(" ","_");
				res.pushBack("sp "+vn+" "+vp);
			}
		}
		for(ListNode<Vertex> i=vs.h.n;i!=vs.t;i=i.n) {
			for(ListNode<Edge> j=i.v.es.h.n;j!=i.v.es.t;j=j.n) {
				String v1 = i.v.name,v2 = j.v.to.name,ep = j.v.pixes;
				if(v1.compareTo(v2) > 0) {
					continue;
				}
				//System.out.println(v1+" "+v2);
				int len = j.v.len;
				v1 = v1.replace(" ","_");
				v2 = v2.replace(" ","_");
				res.pushBack("ae "+v1+" "+v2+" "+len);
				if(ep != null) {
					ep = ep.replace(" ","_");
					res.pushBack("sp "+v1+"-"+v2+" "+ep);
				}
			}
		}
		return res;
	}
}
