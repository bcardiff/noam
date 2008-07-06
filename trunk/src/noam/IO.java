package noam;

import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import noam.af.AF;
import noam.af.grammar.AfLexer;
import noam.af.grammar.AfParser;
import noam.af.internal.AFNDBuilder;

public class IO {
	public static AF parseAF(String text) {
		StringReader reader = new StringReader(text);
		AfLexer lexer = new AfLexer(reader);
		AfParser parser = new AfParser(lexer);
		AFNDBuilder builder = new AFNDBuilder();
		try {
			parser.automata(builder);
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		} catch (TokenStreamException e) {
			throw new RuntimeException(e);
		}
		return builder.getAutomata();
	}
}
