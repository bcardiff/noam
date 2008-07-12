package noam;

import noam.af.AF;
import noam.af.algorithms.Complete;
import noam.af.algorithms.Determination;
import noam.af.algorithms.Minimization;
import noam.er.ER;
import noam.gr.Grammar;

public abstract class FormalismConverter<T> {
	
	protected T input;
	
	public FormalismConverter(T input) {
		this.input = input;
	}
	
	public abstract ER toER();

	public abstract AF toAF();

	public AF toAFD(){
		// TODO rename of states
		return new Determination(toAF());
	}

	public AF toAFM(){
		// TODO rename of states
		return new Minimization(Complete.ensureComplete(toAFD()));
	}
	
	public abstract Grammar toGR();
	
}
