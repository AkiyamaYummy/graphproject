package matchPackage;

public class BoyerMoore {
	public final int ascSize = 256;
	public int[] bc = new int[ascSize],ss,gs;
	public String p;
	public BoyerMoore(String P) {
		p = P;
		ss = new int[p.length()];
		gs = new int[p.length()];
		buildBC();
		buildSS();
		buildGS();
	}
	public void buildBC() {
		for(int i=0;i<ascSize;i++) bc[i] = -1;
		for(int i=0;i<p.length();i++)bc[p.charAt(i)] = i;
	}
	public void buildSS() {
		ss[p.length()-1] = p.length();
		for(int lo=p.length()-1,hi=p.length()-1,j=lo-1;j>=0;j--){
			if((lo<j) && (ss[p.length()-hi+j-1]<=j-lo))
                ss[j] = ss[p.length()-hi+j-1];
            else{
                hi = j; lo = Math.min(lo,hi);
                while((0<=lo) && (p.charAt(lo)==p.charAt(p.length()-hi+lo-1)))
                    lo--;
                ss[j] = hi-lo;
            }
		}
	}
	public void buildGS() {
        for(int j=0;j<p.length();j++)gs[j] = p.length();
        for(int i=0,j=p.length()-1;j>=0;j--)
            if(j+1 == ss[j])
                while(i < p.length()-j-1)
                    gs[i++] = p.length()-j-1;
        for(int j=0;j<p.length()-1;j++)
        	gs[p.length()-ss[j]-1] = p.length()-j-1;
	}
	public int match(String t){
        int i = 0;
        while(t.length() >= i+p.length()){
            int j = p.length()-1;
            while(p.charAt(j) == t.charAt(i+j))
                if(0 > --j)break;
            if(0 > j)break;
            else i += Math.max(gs[j],j-bc[t.charAt(i+j)]);
        }
        return i;
	}
	public boolean isSubStrOf(String t) {
		return match(t)+p.length() <= t.length();
	}
}
