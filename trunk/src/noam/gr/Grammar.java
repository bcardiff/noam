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
	
	public void setDistSymbol(String s) {
		distSymbol = s;
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
	
	public void addNonTerminal(String nt) {
		nonTerminals.add(nt);
	}
		
	public void addTerminal(String t) {
		terminals.add(t);
	}
	
	// FIXME: probablemente estos 3 que siguen no se usen
	public void addProduction(String nt) {
		productions.add(new Production(nt));
	}
	
	public void addProduction(String nt, String t) {
		Production p = new Production(nt);
		p.addRight(t);
		productions.add(p);
	}
	
	public void addProduction(String ntLeft, String t, String ntRight) {
		Production p = new Production(ntLeft);
		p.addRight(t);
		p.addRight(ntRight);
		productions.add(p);
	}
	
	public void addProduction(Production p) {
		productions.add(p);
	}
}
