package noam.gr.grammar;

import java.io.StringReader;


import noam.af.Transition;
import noam.af.grammar.AfLexer;
import noam.af.grammar.AfParser;
import noam.gr.IGrammarBuilder;
import noam.gr.Production;

import org.junit.Before;
import org.junit.Test;

public class GRGrammarFixture {

	IGrammarBuilder nullBuilder;
	@Before
	public void setUp(){
		nullBuilder = new IGrammarBuilder(){

			public void addNonTerminal(String s) {
			}

			public void addProduction(Production p) {
			}

			public void setDistinguishedSymbol(String s) {
			}

			public void addTerminal(String s) {
			}
		};
	}
	
	@Test
	public void parseGrammar(){
		parse("<(S),(),(),S>", nullBuilder);
		parse("<(S),(),(   ),\n   S >", nullBuilder);
		parse("<(S,F),(a),( (S,a) ),S>", nullBuilder);
		parse("<(S,F),(a),( (S,a,F) ),S>", nullBuilder);
		//No anda con mas de una produccion.
		parse("<(S,F),(a),( (S,a)(S,F) ),S>", nullBuilder);
		parse("<(S,F),(a),( (S,a)(S,F) (S, a) (S,LAMBDA) ),S>", nullBuilder);
		parse("<(S,L),(a),( (S,L) (L,LAMBDA) ),S>", nullBuilder);
	}

	private void parse(String text, IGrammarBuilder builder) {
		try {
			StringReader reader = new StringReader(text);
			GrLexer lexer = new GrLexer(reader);
			GrParser parser = new GrParser(lexer);
			parser.gramatica(builder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
}
