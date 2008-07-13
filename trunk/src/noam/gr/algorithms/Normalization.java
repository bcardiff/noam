package noam.gr.algorithms;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import noam.gr.Grammar;
import noam.gr.Production;
import noam.utils.IteratorHelper;

public class Normalization {
	public static Grammar normalize(Grammar g) {
		Set<Production> lambda = getLowerLambdaProductions(g);
		HashSet<Production> newProductions = new HashSet<Production>();
		
		for (Production lambdaProd : lambda) {
			Iterator<Production> pIt = g.getProductions();
			String nt = lambdaProd.getLeft().peekFirst();

			while (pIt.hasNext()) {
				Production p = pIt.next();
				if (IteratorHelper.contains(p.getRight().iterator(), nt)) {
					// si esta A->\ reemplazo B->aA por B->a y B->aA
					Production newP = new Production(p.getLeft().peekFirst());
					Iterator<String> rightIt = p.getRight().iterator();
					while (rightIt.hasNext()) {
						String t = rightIt.next();
						if (!t.equals(nt)) {
							newP.addRight(t);
						}
					}
					newProductions.add(newP);
				}
			}
		}
		
		removeProductions(g, lambda);
		addProductions(g, newProductions.iterator());
		
		return g;
	}

	private static void addProductions(Grammar g, Iterator<Production> pIt) {
		while (pIt.hasNext()) {
			Production p = pIt.next();
			g.addProduction(p);
		}
		
	}

	// Devuelve los no terminales que derivan en lambda y no son el simbolo distinguido
	private static Set<Production> getLowerLambdaProductions(Grammar g) {
		Set<Production> lambda = new HashSet<Production>();
		Iterator<Production> pIt = g.getProductions();

		while (pIt.hasNext()) {
			Production p = pIt.next();
			if (p.getRight().size() == 0) {
				if (!p.getLeft().peekFirst().equals(g.getDistSymbol())) {
					lambda.add(p);
				}
			}
		}
		
		return lambda;
	}
	
	private static void removeProductions(Grammar g, Collection<Production> c) {
		Iterator<Production> pIt = g.getProductions();

		while (pIt.hasNext()) {
			Production p = pIt.next();
			Iterator<Production> it = c.iterator();
			if (IteratorHelper.contains(it, p)) {
				pIt.remove();
			}
		}
		
	}
}
