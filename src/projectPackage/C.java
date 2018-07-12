package projectPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import containerPackage.List;
import containerPackage.Vector;
import graphPackage.Graph;
import graphPackage.Vertex;
import nodePackage.ListNode;

public class C {
	public static final String password = "abc";
	public static final String filename = "C:\\Users\\yummy\\Desktop\\in.txt";
	public static Vector<String> initCmd = getCmdFromFile(filename);
	public static Integer syncer = 0;
	public static Date ale = new Date();//admin last edit
	public static Graph g = new Graph(initCmd);
	public static String getListRes(List<Vertex> lv) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(ListNode<Vertex> i=lv.h.n;i!=lv.t;i=i.n) {
			sb.append("\""+i.v.name+"\""+(i.n==lv.t?"":","));
		}
		sb.append("]");
		return sb.toString();
	}
	public static Vector<String> getCmdFromFile(String fileName){
		Vector<String> res = new Vector<String>();
		try {
			File file = new File(fileName);
	        BufferedReader reader = null;  
	        reader = new BufferedReader(new FileReader(file));
	        String line = null;
	        // 一次读入一行，直到读入null为文件结束 
	        while ((line = reader.readLine()) != null) {  
	            res.pushBack(line);
	        }
	        reader.close();
		} catch (Exception e) {}
        return res;
	}
	public static boolean syncWithFile() {
		try {
			Vector<String> cmds = g.toCmds();
			File file=new File(filename);
	        if(!file.exists())file.createNewFile();
	        FileOutputStream out=new FileOutputStream(file,false);        
	        StringBuffer sb=new StringBuffer();
	        for(int i=0;i<cmds.size();i++) {
	        	sb.append(cmds.get(i)+"\r\n");
	        }
			out.write(sb.toString().getBytes("utf-8"));
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
