package noam.af;

import java.util.HashMap;
import java.util.Vector;

public class AFND extends AF {
	
	// cada nodo tiene asociada una lista de adyacencia en la cual la transicion es la key
	private HashMap<String, HashMap<String, String> > graph;
	private String initialSt;
	private Vector<String> finalSts;

	public AFND() {
	}
	
	public void addNode(String name) {
		graph.put(name, new HashMap<String, String>());
	}
	
	public void addTransition(String src, String trans, String dst) {
		graph.get(src).put(trans, dst);
	}
	
	public void setInitialState(String initial) {
		initialSt = initial;
	}
	
	

}
