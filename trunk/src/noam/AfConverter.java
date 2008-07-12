package noam;

import java.io.StringReader;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import noam.af.AF;
import noam.af.grammar.AfLexer;
import noam.af.grammar.AfParser;
import noam.af.internal.AFNDBuilder;
import noam.er.ER;
import noam.gr.Grammar;

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
		// TODO AFD->ER, USE this.toAFD();
		throw new NotImplementedException();
	}

	@Override
	public Grammar toGR() {
		// TODO AFD->ER, USE this.toAFD();
		throw new NotImplementedException();
	}

}
