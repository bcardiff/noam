package noam;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import noam.af.AF;
import noam.af.algorithms.AFRenamed;
import noam.af.algorithms.Complete;
import noam.af.algorithms.Determination;
import noam.af.algorithms.Minimization;
import noam.af.algorithms.Reachables;
import noam.er.ER;
import noam.gr.Grammar;

public abstract class FormalismConverter<T> {

	protected String input;
	protected T formalism;

	public FormalismConverter(String input) {
		this.input = input;
		this.formalism = null;
	}

	public boolean isFormalismOk() {
		try {
			formalism = parseInput();
			return true;
		} catch (RecognitionException e) {
			e.printStackTrace();
			return false;
		} catch (TokenStreamException e) {
			e.printStackTrace();
			return false;
		}
	}

	public abstract T parseInput() throws RecognitionException,
			TokenStreamException;

	public abstract ER toER();

	public abstract AF toAF();

	public AF toAFD() {
		return AFRenamed.CanonicalNamed(new Determination(toAF()));
	}

	public AF toAFM() {
		return AFRenamed.CanonicalNamed(new Minimization(
				new Reachables(Complete.ensureComplete(toAFD()))));
	}

	public abstract Grammar toGR();

}
