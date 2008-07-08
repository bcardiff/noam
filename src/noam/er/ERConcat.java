package noam.er;

public class ERConcat extends ER {

	private ER erLeft = null;
	private ER erRight = null;
	
	public ERConcat(ER erLeft, ER erRight) {
		this.erLeft = erLeft;
		this.erRight = erRight;
	}

	public ER getErLeft() {
		return erLeft;
	}

	public ER getErRight() {
		return erRight;
	}

	@Override
	Object accept(IVisitor v) {
		return v.visit(this);
	}
	
	
	
}
