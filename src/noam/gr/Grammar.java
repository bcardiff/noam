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
		Production p = new Production(nt, t);
		productions.add(p);
	}
	
	public void addProduction(String ntLeft, String t, String ntRight) {
		Production p = new Production(ntLeft, t, ntRight);
		productions.add(p);
	}
	
	public void addProduction(Production p) {
		productions.add(p);
	}
	
	@Override
	public String toString() {
		String s = new String();
		boolean first = true;
		
		s += "<";
		
		// No terminales
		s += "(";
		for (String nt : nonTerminals) {
			if (!first) {
				s += ",";
			} else {
				first = false;
			}
			s += nt;
		}
		s += "),";
		
		// Terminales
		first = true;
		s += "(";
		for (String t : terminals) {
			if (!first) {
				s += ",";
			} else {
				first = false;
			}
			s += t;
		}
		s += "),";
		
		// Producciones
		s += "(";
		for (Production p : productions) {
			first = true;
			s += "(";
			// FIXME: esto imprime producciones genericas (que son soportadas por Production) pero a su vez esto hace que potencialmente genere una salida incompatible con la gramatica pedida en el TP
			for (String t : p.getLeft()) {
				if (!first) {
					s += ",";
				} else {
					first = false;
				}
				s += t; 
			}
			if (p.getRight().size() == 0) {
				s += ",LAMBDA";
			} else {
				for (String t : p.getRight()) {
					s += ",";
					s += t;
				}
			}
			s += ")";
		}
		s += "),";
		
		// Simbolo distinguido
		s += distSymbol;
		
		s += ">";
		
		return s;
	}
}
