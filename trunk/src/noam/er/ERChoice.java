package noam.er;

public class ERChoice extends ER {

	private ER erLeft = null;
	private ER erRight = null;
	
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

	public ERChoice(ER erLeft, ER erRight) {
		super();
		this.erLeft = erLeft;
		this.erRight = erRight;
	}

}
