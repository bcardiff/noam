package noam;

import static noam.utils.IteratorHelper.addAll;
import static noam.utils.IteratorHelper.sorted;
import java.io.StringReader;
import java.util.Iterator;
import java.util.TreeSet;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import noam.af.AF;
import noam.af.Terminal;
import noam.af.Transition;
import noam.af.grammar.AfLexer;
import noam.af.grammar.AfParser;
import noam.af.internal.AFNDBuilder;
import noam.er.ER;
import noam.er.grammar.ERLexer;
import noam.er.grammar.ERParser;
import noam.gr.Grammar;
import noam.gr.grammar.GrLexer;
import noam.gr.grammar.GrParser;
import noam.gr.internal.GrBuilder;

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

	public static ER parseER(String text) {
		StringReader reader = new StringReader(text);
		ERLexer lexer = new ERLexer(reader);
		ERParser parser = new ERParser(lexer);
		try {
			return parser.regexp();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		} catch (TokenStreamException e) {
			throw new RuntimeException(e);
		}
	}

	public static Grammar parseGr(String text) {
		StringReader reader = new StringReader(text);
		GrLexer lexer = new GrLexer(reader);
		GrParser parser = new GrParser(lexer);
		GrBuilder b = new GrBuilder();
		try {
			parser.gramatica(b);
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		} catch (TokenStreamException e) {
			throw new RuntimeException(e);
		}

		return b.getOutput();
	}

	public static String printDot(AF automaton) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {");
		sb.append(automaton.getInitialState() + " [shape=box]");
		Iterator<String> its = automaton.getFinalStates();
		while (its.hasNext()) {
			sb.append(its.next() + " [shape=diamond]");
		}
		Iterator<String> it = automaton.getStates();
		while (it.hasNext()) {
			Iterator<Transition> itt = automaton.getTransitions(it.next());
			while (itt.hasNext()) {
				Transition t = itt.next();
				String label = t.getLabel().equals(Terminal.LAMBDA) ? "lambda"
						: t.getLabel();
				sb.append(t.getFrom() + " -> " + t.getTo() + " [label=" + label
						+ "]");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public static String print(AF af) {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		appendListStrings(sb, sorted(af.getStates()));
		sb.append(",");
		appendListStrings(sb, sorted(af.getAlphabet()));
		sb.append(",");
		appendListTransitions(sb, af);
		sb.append(",");
		sb.append(af.getInitialState());
		sb.append(",");
		appendListStrings(sb, sorted(af.getFinalStates()));
		sb.append(">");
		return sb.toString();
	}

	private static void appendListTransitions(StringBuilder sb, AF af) {
		sb.append("(");
		Iterator<String> states = sorted(af.getStates());
		while (states.hasNext()) {
			String s = (String) states.next();
			TreeSet<Transition> tset = new TreeSet<Transition>(Transition
					.comparator());
			addAll(tset, af.getTransitions(s));
			Iterator<Transition> transitions = tset.iterator();
			while (transitions.hasNext()) {
				Transition t = (Transition) transitions.next();
				sb.append("(");
				sb.append(t.getFrom());
				sb.append(",");
				if (t.getLabel().equals(Terminal.LAMBDA))
					sb.append("LAMBDA");
				else
					sb.append(t.getLabel());
				sb.append(",");
				sb.append(t.getTo());
				sb.append(")");
			}
		}
		sb.append(")");
	}

	private static void appendListStrings(StringBuilder sb,
			Iterator<String> source) {
		sb.append("(");
		if (source.hasNext()) {
			sb.append(source.next());
		}
		while (source.hasNext()) {
			sb.append(",");
			sb.append(source.next());
		}
		sb.append(")");
	}

	public static String print(ER er) {
		return er.toString();
	}

	public static String print(Grammar gr) {
		return gr.toString();
	}
}
