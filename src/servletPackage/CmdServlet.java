package servletPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import containerPackage.Vector;
import projectPackage.C;

@WebServlet("/cmd")
public class CmdServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		synchronized(C.syncer){
			PrintWriter out=response.getWriter();
			String content = request.getParameter("content");
			String vpass = request.getParameter("password");
			if(content != null && vpass != null && C.password.equals(vpass)) {
				C.ale = new Date();
				//管理员执行的是会改动图的操作，更新上次修改时间。
				String []cmds = content.split("\n");
				String res = C.g.readCmd(new Vector<String>(cmds));
				out.println(res);
			}else out.println("please verify.");
		}
	}
}
