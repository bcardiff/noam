package noam;

import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import noam.af.AF;
import noam.er.ER;
import noam.er.ERToAutomata;
import noam.er.grammar.ERLexer;
import noam.er.grammar.ERParser;
import noam.gr.Grammar;

public class ErConverter extends FormalismConverter<ER> {

	public ErConverter(String input) {
		super(input);
	}

	@Override
	public ER parseInput() throws RecognitionException, TokenStreamException {
		StringReader reader = new StringReader(input);
		ERLexer lexer = new ERLexer(reader);
		ERParser parser = new ERParser(lexer);
		return parser.regexp();
	}

	@Override
	public AF toAF() {
		return (AF) formalism.accept(new ERToAutomata());
	}

	@Override
	public ER toER() {
		return formalism;
	}

	@Override
	public Grammar toGR() {
		// TODO AFD->GR, USE this.toAFD()
		return null;
	}

}
