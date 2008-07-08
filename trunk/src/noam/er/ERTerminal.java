package noam.er;

public class ERTerminal extends ER{

	private String terminal = "";

	public ERTerminal(String term) {
		terminal = term;
	}

	@Override
	Object accept(IVisitor v) {
		return v.visit(this);
	}

	public String getTerminal() {
		return terminal;
	}
	
	
}
