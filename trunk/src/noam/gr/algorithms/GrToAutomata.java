package noam.gr.algorithms;

import java.util.Iterator;

import noam.af.AF;
import noam.af.Terminal;
import noam.af.Transition;
import noam.af.internal.AFNDBuilder;
import noam.gr.Grammar;
import noam.gr.Production;

/**
 * Esta clase se encarga de convertir gramaticas en automatas.
 * El algoritmo implementado es el siguiente:
 * - Los simbolos no terminales de la gramatica seran estados en el automata.
 * - Los simbolos terminales de la gramatica seran labels de las transiciones
 *   en el automata. 
 * - El simbolo distinguido de la gramatica sera estado inicial en el automata.
 * - Si existen producciones de la forma NoTerminal -> terminal, se crea un
 *   estado final con una transicion entre el estado que representa al no terminal y
 *   este nuevo estado.
 * - Si existen producciones NoTerminal -> lambda, el estado que representa al no
 *   terminal pasa a ser estado final y se crea una transicion entre este estado y
 *   si mismo, por lambda.
 *   
 * El automata generado es un AFND-lambda, posiblemente no minimo.
 */
public class GrToAutomata {
	static String lastDeadState = null;
	
	public static String getLastDeadState() {
		return lastDeadState;
	}
	
	public static AF convert(Grammar g) {
		lastDeadState = null;
		AFNDBuilder builder = new AFNDBuilder();
		Iterator<String> ntIt = g.getNonTerminals();

		while (ntIt.hasNext()) {
			String s = ntIt.next();
			builder.addState(s);
		}		
		builder.setInitialState(g.getDistSymbol());
		
		Iterator<String> tIt = g.getTerminals();
		while (tIt.hasNext()) {
			builder.addTerminal(tIt.next());			
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
					to = genDeadStateName(g);
					if (lastDeadState == null) {
						lastDeadState = to;
						builder.addState(to);
						builder.addFinalState(to);
					}
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
	
	private static String genDeadStateName(Grammar g) {
		StringBuilder buffer = new StringBuilder();
		Iterator<String> it = g.getNonTerminals();
		buffer.append("Z");
		while (it.hasNext()) {
			buffer.append(it.next());
		}
		return buffer.toString();
	}
	
}
