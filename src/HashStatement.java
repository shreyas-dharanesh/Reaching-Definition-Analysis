
import java.util.HashMap;
import java.util.*;

public class HashStatement {
	private HashMap<String, String> hash = new HashMap<String, String>();
	
	public void put(String k, String v) {
		hash.put(k, v);
	}
	
	public String toString() {
		String output = "";
		Iterator it = hash.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			output += "(" + pair.getKey() + "," +pair.getValue() + ")";
		}
		return output;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashStatement hs = new HashStatement();
	}

}
