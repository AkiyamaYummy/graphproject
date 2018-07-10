package servletPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import projectPackage.C;

@WebServlet("/admin")
public class AdminServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		synchronized(C.syncer){
			PrintWriter out=response.getWriter();
			String type = request.getParameter("type");
			String vpass = request.getParameter("password");
			if(type != null && vpass != null && C.password.equals(vpass)) {
				C.ale = new Date();
				if(type.equals("addEdge")) {
					String v1 = request.getParameter("v1");
					String v2 = request.getParameter("v2");
					Integer len = Integer.valueOf(request.getParameter("l"));
					if(v1 == null || v2 == null || len == null)
						out.println("operation fail.");
					else {
						if(len > 0) {
							if(C.g.addEdge(v1, v2, len))
								out.println(C.g.toString());
							else out.println("operation fali.");
						}else out.println("operation fail.");
					}
				}else if(type.equals("removeEdge")) {
					String v1 = request.getParameter("v1");
					String v2 = request.getParameter("v2");
					if(v1 == null || v2 == null)
						out.println("operation fail.");
					else {
						if(C.g.removeEdge(v1, v2))
							out.println(C.g.toString());
						else out.println("operation fali.");
					}
				}else if(type.equals("addVertex")) {
					String vname = request.getParameter("vname");
					String vcontent = request.getParameter("vcontent");
					if(vname == null)
						out.println("operation fail.");
					else {
						if(C.g.addVertex(vname,vcontent))
							out.println(C.g.toString());
						else out.println("operation fali.");
					}
				}else if(type.equals("removeVertex")) {
					String vname = request.getParameter("vname");
					if(vname == null)
						out.println("operation fail.");
					else {
						if(C.g.removeVertex(vname))
							out.println(C.g.toString());
						else out.println("operation fali.");
					}
				}else if(type.equals("getGraph")) {
					out.println(C.g.toString());
				}else if(type.equals("setPic")) {
					String pid = request.getParameter("pid");
					String pcontent = request.getParameter("pcontent");
					if(pid == null || pcontent == null)
						out.println("operation fail.");
					else {
						//System.out.println(pcontent);
						C.setPix(pid,pcontent);
						out.println(C.g.toString());
					}
				}
			}else out.println("please verify.");
		}
	}
}
