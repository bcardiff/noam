package noam;

import java.io.StringReader;

import noam.af.AF;
import noam.er.ER;
import noam.gr.Grammar;
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
		// TODO check constrains: lambda solo en una, after calling super	
		return super.isFormalismOk();
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
		// TODO AFD->ER, USE this.toAFD()		
		return null;
	}

	@Override
	public Grammar toGR() {
		return formalism;
	}

}
