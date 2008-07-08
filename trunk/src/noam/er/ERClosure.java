package noam.er;

public class ERClosure extends ER {

	private ER erInner = null;

	public ERClosure(ER erInner) {
		this.erInner = erInner;
	}

	public ER getErInner() {
		return erInner;
	}

	@Override
	Object accept(IVisitor v) {
		return v.visit(this);
	}
	
}
