package noam.er;

public class ERTerminal extends ER{

	private String terminal = "";

	public ERTerminal(String term) {
		terminal = term;
	}

	@Override
	void accept(IVisitor v) {
		v.visit(this);
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
	
}
