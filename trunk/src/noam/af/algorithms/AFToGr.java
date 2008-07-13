package noam.af.algorithms;

import java.util.Iterator;

import noam.af.AF;
import noam.af.Transition;
import noam.gr.Grammar;
import noam.gr.Production;
import noam.gr.internal.GrBuilder;

public class AFToGr {

	public static Grammar convert(AF af) {
		GrBuilder builder = new GrBuilder();
		Iterator<String> statesIt = af.getStates();
		Iterator<String> alphIt = af.getAlphabet();

		// Alfabeto
		while (alphIt.hasNext()) {
			String token = alphIt.next();
			builder.addTerminal(token);
		}
		
		// Estados
		while (statesIt.hasNext()) {
			String st = statesIt.next();
			builder.addNonTerminal(st);

			Iterator<Transition> transIt = af.getTransitions(st);
			while (transIt.hasNext()) {
				Transition t = transIt.next();
				Production p = new Production(st);

				p.addRight(t.getLabel());
				p.addRight(t.getTo());

				builder.addProduction(p);
			}
		}

		// Estados finales
		Iterator<String> finalIt = af.getFinalStates();
		while (finalIt.hasNext()) {
			String st = finalIt.next();
			Production p = new Production(st);
			builder.addProduction(p);
		}

		// Estado inicial
		builder.setDistinguishedSymbol(af.getInitialState());

		return builder.getOutput();
	}

}
