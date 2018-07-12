package servletPackage;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import containerPackage.Heap;
import containerPackage.List;
import graphPackage.Hamilton;
import graphPackage.Vertex;
import matchPackage.BoyerMoore;
import nodePackage.ListNode;
import projectPackage.C;

@WebServlet("/user")
public class UserServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		synchronized(C.syncer){
			PrintWriter out=response.getWriter();
			String type = request.getParameter("type");
			String lgt = request.getParameter("lgp");//last get pic
			long secale = C.ale.getTime();
			long seclgt = Long.parseLong(lgt);
			if(type != null && lgt != null) {
				if(type.equals("getGraph")) {
					out.println(C.g.toString());
				}else if(seclgt <= secale) {
					out.println("please refresh.");
				}else {
					if(type.equals("keyword")) {
						String key = request.getParameter("key");
						if(key == null)out.println("operation fail.");
						else {
							BoyerMoore bm = new BoyerMoore(key);
							List<Vertex> res = new List<Vertex>();
							for(ListNode<Vertex> i=C.g.vs.h.n;i!=C.g.vs.t;i=i.n) {
								if(bm.isSubStrOf(i.v.name)||bm.isSubStrOf(i.v.content)) {
									res.pushBack(i.v);
								}
							}
							out.println(C.getListRes(res));
						}
					}else if(type.equals("close")) {
						String vname = request.getParameter("vname");
						if(vname == null)out.println("operation fail.");
						else {
							List<Vertex> res = new List<Vertex>();
							Vertex v = C.g.vHash.get(vname);
							if(v != null) {
								C.g.dijkstra(v,null);
								Heap<Vertex> que = new Heap<Vertex>();
								for(ListNode<Vertex> i=C.g.vs.h.n;i!=C.g.vs.t;i=i.n) {
									que.push(i.v);
								}
								while(!que.isEmpty()) {
									res.pushBack(que.pop());
								}
							}
							out.println(C.getListRes(res));
						}
					}else if(type.equals("path")) {
						String v1 = request.getParameter("v1");
						String v2 = request.getParameter("v2");
						if(v1 == null||v2 == null)out.println("operation fail.");
						else {
							List<Vertex> res = C.g.getShortestPath(v1,v2);
							out.println(C.getListRes(res));
						}
					}else if(type.equals("Hamilton")) {
						List<Vertex> res = new Hamilton(C.g).getPath();
						out.println(C.getListRes(res));
					}else out.println("operation fali.");
				}
			}else out.println("please verify.");
		}
	}
}
