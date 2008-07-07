package noam.er;

public class ERClosure extends ER {

	private ER erInner = null;

	public ERClosure(ER erInner) {
		this.erInner = erInner;
	}

	public ER getErInner() {
		return erInner;
	}

	public void setErInner(ER erInner) {
		this.erInner = erInner;
	}

	@Override
	void accept(IVisitor v) {
		v.visit(this);
	}
	
}
