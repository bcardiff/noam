package noam;

import java.io.Reader;
import java.io.StringReader;

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

	private String input;
	private Reader reader;
	protected T formalism;

	public FormalismConverter(String input) {
		this.input = input;
		this.reader = new StringReader(input);
		this.formalism = null;
	}
	
	public FormalismConverter(Reader reader) {
		this.input = "--NOT AVAILABLE--";
		this.reader = reader;
		this.formalism = null;
	}

	public String getInput() {
		return input;
	}

	public boolean isFormalismOk() {
		try {
			formalism = parseInput(reader);
			return true;
		} catch (RecognitionException e) {
			System.out.append(e.getMessage() + " line: "
					+ Integer.toString(e.getLine()) + " column: "
					+ Integer.toString(e.getColumn()) + "\n");
			return false;
		} catch (TokenStreamException e) {
			System.out.append(e.getMessage()+ "\n");
			return false;
		}
	}

	public abstract T parseInput(Reader reader) throws RecognitionException,
			TokenStreamException;

	public abstract ER toER();

	public abstract AF toAF();

	public AF toAFD() {
		return AFRenamed.CanonicalNamed(new Determination(toAF()));
	}

	public AF toAFM() {
		return AFRenamed.CanonicalNamed(new Minimization(new Reachables(
				Complete.ensureComplete(toAFD()))));
	}

	public abstract Grammar toGR();

}
