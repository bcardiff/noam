package noam.er;

import static noam.utils.ObjectHelper.safetyEquals;

public class ERTerminal extends ER{

	private String terminal = "";

	public ERTerminal(String term) {
		terminal = term;
	}

	@Override
	public
	Object accept(IVisitor v) {
		return v.visit(this);
	}

	public String getTerminal() {
		return terminal;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof ERTerminal){
			ERTerminal that = (ERTerminal) arg0;
			return safetyEquals(this.terminal, that.terminal);
		} else {
			return false;
		}
	}
}
