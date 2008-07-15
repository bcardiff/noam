package noam;

import java.io.Reader;

import noam.af.AF;
import noam.af.algorithms.AFRenamed;
import noam.af.algorithms.AFToGr;
import noam.er.ER;
import noam.er.algorithms.ERToAutomata;
import noam.er.grammar.ERLexer;
import noam.er.grammar.ERParser;
import noam.gr.Grammar;
import noam.gr.algorithms.Normalization;
import antlr.RecognitionException;
import antlr.TokenStreamException;

public class ErConverter extends FormalismConverter<ER> {

	public ErConverter(String input) {
		super(input);
	}

	public ErConverter(Reader reader) {
		super(reader);
	}

	@Override
	public ER parseInput(Reader reader) throws RecognitionException, TokenStreamException {
		ERLexer lexer = new ERLexer(reader);
		ERParser parser = new ERParser(lexer);
		return parser.regexp();
	}

	@Override
	public AF toAF() {
		return AFRenamed.CanonicalNamed((AF) formalism.accept(new ERToAutomata()));
	}

	@Override
	public ER toER() {
		return formalism;
	}

	@Override
	public Grammar toGR() {
		return Normalization.normalize(AFToGr.convert(this.toAFD()));
	}

}
