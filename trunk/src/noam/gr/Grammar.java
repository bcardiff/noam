package noam.gr;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Grammar {

	private Set<String> nonTerminals;
	private Set<String> terminals;
	private Set<Production> productions;
	private String distSymbol;
	
	public Grammar() {
		nonTerminals = new HashSet<String>();
		terminals = new HashSet<String>();
		productions = new HashSet<Production>();
	}
	
	public String getDistSymbol() {
		return distSymbol;
	}
	
	public Iterator<String> getNonTerminals() {
		return nonTerminals.iterator();
	}
	
	public Iterator<String> getTerminals() {
		return terminals.iterator();
	}
	
	public Iterator<Production> getProductions() {
		return productions.iterator();
	}
		
}
