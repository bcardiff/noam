package noam.af.grammar;

import java.io.StringReader;

import noam.af.IAutomataBuilder;
import noam.af.Transition;

import org.junit.Before;
import org.junit.Test;

public class AfgrammarFixture {

	IAutomataBuilder nullBuilder;
	@Before
	public void setUp(){
		nullBuilder = new IAutomataBuilder(){
			
			public void addFinalState(String id) {
			}

			
			public void addState(String id) {
			}

			
			public void addTerminal(String t) {
			}

			
			public void addTransition(Transition t) {
			}

			
			public void setInitialState(String id) {
			}
		};
	}
	
	@Test
	public void parseAutomata(){
		parse("<(I),(),(),I,(I)>", nullBuilder);
		parse("<(I),(),(   ),\n   I,(I)>", nullBuilder);
		parse("<(I,F),(a),( (I,a,F) ),I,(F)>", nullBuilder);
		parse("<(I,F),(a),( (I,a,F) (I,LAMBDA,F) ),I,(F)>", nullBuilder);
		parse("<(I,L),(a),( (I,a,L) (I,LAMBDA,L) ),I,(L)>", nullBuilder);
	}

	private void parse(String text, IAutomataBuilder builder) {
		try {
			StringReader reader = new StringReader(text);
			AfLexer lexer = new AfLexer(reader);
			AfParser parser = new AfParser(lexer);
			parser.automata(builder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}	
}
