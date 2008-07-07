package noam.gr;

public interface IGrammarBuilder {
	void setDistinguishedSymbol(String s);

	void addNonTerminal(String s);

	void addTerminal(String s);

	void addProduction(Production p);
}
