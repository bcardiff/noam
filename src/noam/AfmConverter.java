package noam;

import noam.af.AF;
import noam.af.algorithms.AFRenamed;
import noam.af.algorithms.Complete;
import noam.af.algorithms.Minimization;
import noam.utils.IteratorHelper;

public class AfmConverter extends AfdConverter {

	public AfmConverter(String input) {
		super(input);
	}

	@Override
	public boolean isFormalismOk() {
		return super.isFormalismOk() && isMinimal(formalism);
	}

	/**
	 * 
	 * @param automata
	 *            (deterministic)
	 * @return
	 */
	private static boolean isMinimal(AF automata) {
		AF minimal = new Minimization(Complete.ensureComplete(automata));

		return IteratorHelper.countOf(minimal.getStates()) == IteratorHelper
				.countOf(automata.getStates());
	}

	@Override
	public AF toAFM() {
		return formalism;
	}
}
