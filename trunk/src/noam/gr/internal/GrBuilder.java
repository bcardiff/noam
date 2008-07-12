package noam.gr.internal;

import noam.gr.Grammar;
import noam.gr.IGrammarBuilder;
import noam.gr.Production;

public class GrBuilder implements IGrammarBuilder {
	Grammar output;

	public GrBuilder() {
		this.output = new Grammar();
	}

	public void addNonTerminal(String s) {
		output.addNonTerminal(s);
	}

	public void addProduction(Production p) {
		output.addProduction(p);
	}

	public void addTerminal(String s) {
		output.addTerminal(s);
	}

	public void setDistinguishedSymbol(String s) {
		output.setDistSymbol(s);
	}

	public Grammar getOutput() {
		return output;
	}
}
