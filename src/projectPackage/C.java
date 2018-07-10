package projectPackage;

import java.util.Date;

import graphPackage.Edge;
import graphPackage.Graph;
import graphPackage.Vertex;

public class C {
	public static final String password = "abc";
	public static Integer syncer = 0;
	public static Date ale = new Date();//admin last edit
	public static Graph g = new Graph();
	public static boolean setPix(String pid,String pcontent) {
		String[] sp = pid.split("-");
		if(sp.length == 1) {
			Vertex vte = g.vHash.get(pid);//Vertex to edit
			if(vte == null)return false;
			vte.pixes = pcontent;
			return true;
		}else if(sp.length == 2) {
			if(sp[0].compareTo(sp[1]) == 1) {
				String t = sp[1];
				sp[1] = sp[0];
				sp[0] = t;
			}//保证sp[0]小于sp[1]
			pid = sp[0]+"-"+sp[1];
			Edge ete = g.eHash.get(pid);//Edge to edit
			if(ete == null) return false;
			//System.out.println(pid+" "+pcontent);
			ete.pixes = pcontent;
			return true;
		}
		return false;
	}
}
