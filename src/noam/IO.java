package noam;

import java.io.StringReader;
import java.util.Iterator;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import noam.af.AF;
import noam.af.Terminal;
import noam.af.Transition;
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
	
	public static String printDot(AF automaton){
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {");
		sb.append(automaton.getInitialState() + " [shape=box]");
		Iterator<String> its = automaton.getFinalStates();
		while(its.hasNext()){
			sb.append(its.next() + " [shape=diamond]");
		}
		Iterator<String> it = automaton.getStates();
		while(it.hasNext()){
			Iterator<Transition> itt = automaton.getTransitions(it.next());
			while (itt.hasNext()){
				Transition t = itt.next();
				String label = t.getLabel().equals(Terminal.LAMBDA) ? "lambda" : t.getLabel();
				sb.append(t.getFrom() + " -> " + t.getTo() + " [label=" + label + "]");
			}
		}
		sb.append("}");
		return sb.toString();
	}
}