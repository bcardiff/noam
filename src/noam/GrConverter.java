package noam;

import java.io.StringReader;
import java.util.Iterator;

import noam.af.AF;
import noam.af.algorithms.AFDtoER;
import noam.er.ER;
import noam.gr.Grammar;
import noam.gr.Production;
import noam.gr.algorithms.GrToAutomata;
import noam.gr.grammar.GrLexer;
import noam.gr.grammar.GrParser;
import noam.gr.internal.GrBuilder;
import antlr.RecognitionException;
import antlr.TokenStreamException;

public class GrConverter extends FormalismConverter<Grammar> {

	public GrConverter(String input) {
		super(input);
	}

	@Override
	public boolean isFormalismOk() {
		return super.isFormalismOk()
				&& lambdaTransitionOnlyOnInitial(formalism);
	}

	private static boolean lambdaTransitionOnlyOnInitial(Grammar gr) {
		Iterator<Production> productions = gr.getProductions();
		while (productions.hasNext()) {
			Production prod = (Production) productions.next();
			if (prod.getRight().size() == 0) {
				// Lambda production
				if (!prod.getLeft().get(0).equals(gr.getDistSymbol())) {
					return false; // lambda production not from distinguished
									// symbol.
				}
			}
		}
		return true;
	}

	@Override
	public Grammar parseInput() throws RecognitionException,
			TokenStreamException {
		StringReader reader = new StringReader(input);
		GrLexer lexer = new GrLexer(reader);
		GrParser parser = new GrParser(lexer);
		GrBuilder builder = new GrBuilder();
		parser.gramatica(builder);
		return builder.getOutput();
	}

	@Override
	public AF toAF() {
		return GrToAutomata.convert(formalism);
	}

	@Override
	public ER toER() {
		AFDtoER afdToER = new AFDtoER(toAFD());		
		return afdToER.convert();
	}

	@Override
	public Grammar toGR() {
		return formalism;
	}

}
