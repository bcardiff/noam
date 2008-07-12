package noam.gr.algorithms;

import java.util.Iterator;
import noam.af.Terminal;
import noam.af.Transition;
import noam.af.internal.AFNDBuilder;
import noam.gr.Grammar;
import noam.gr.Production;

public class GrToAutomata {

	public Object visit(Grammar g) {
		AFNDBuilder builder = new AFNDBuilder();
		Iterator<String> ntIt = g.getNonTerminals();

		while (ntIt.hasNext()) {
			String s = ntIt.next();
			builder.addState(s);
			if (s.equals(g.getDistSymbol())) {
				builder.setInitialState(s);
			}
		}

		Iterator<Production> pIt = g.getProductions();

		while (pIt.hasNext()) {
			Production p = pIt.next();
			String from = p.getLeft().peekFirst();
			Iterator<String> right = p.getRight().iterator();
			String to;
			String label;

			if (right.hasNext()) {
				label = right.next();
				if (right.hasNext()) {
					// produccion NT -> t NT
					to = right.next();
				} else {
					// produccion NT -> t
					to = from;
					builder.addFinalState(to);
				}
			} else {
				// produccion NT -> \
				label = Terminal.LAMBDA;
				to = from;
				builder.addFinalState(to);
			}

			Transition t = new Transition(from, label, to);
			builder.addTransition(t);
		}

		return builder.getAutomata();
	}
}
