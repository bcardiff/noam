package noam;

import java.io.StringReader;

import noam.af.AF;
import noam.af.algorithms.AFDtoER;
import noam.af.algorithms.AFToGr;
import noam.af.grammar.AfLexer;
import noam.af.grammar.AfParser;
import noam.af.internal.AFNDBuilder;
import noam.er.ER;
import noam.gr.Grammar;
import noam.gr.algorithms.Normalization;
import antlr.RecognitionException;
import antlr.TokenStreamException;

public class AfConverter extends FormalismConverter<AF> {

	public AfConverter(String input) {
		super(input);
	}

	@Override
	public AF parseInput() throws RecognitionException, TokenStreamException {
		StringReader reader = new StringReader(input);
		AfLexer lexer = new AfLexer(reader);
		AfParser parser = new AfParser(lexer);
		AFNDBuilder builder = new AFNDBuilder();
		parser.automata(builder);
		return builder.getAutomata();
	}

	@Override
	public AF toAF() {
		return formalism;
	}

	@Override
	public ER toER() {
		AFDtoER afdToER = new AFDtoER(toAFD());		
		return afdToER.convert();		
	}

	@Override
	public Grammar toGR() {
		return Normalization.normalize(AFToGr.convert(this.toAFD()));
	}

}
