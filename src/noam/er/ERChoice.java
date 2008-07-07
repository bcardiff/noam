package noam.er;

public class ERChoice extends ER {

	private ER erLeft = null;
	private ER erRight = null;
	
	public ER getErLeft() {
		return erLeft;
	}

	public void setErLeft(ER erLeft) {
		this.erLeft = erLeft;
	}

	public ER getErRight() {
		return erRight;
	}

	public void setErRight(ER erRight) {
		this.erRight = erRight;
	}

	@Override
	void accept(IVisitor v) {
		v.visit(this);
	}

	public ERChoice(ER erLeft, ER erRight) {
		super();
		this.erLeft = erLeft;
		this.erRight = erRight;
	}

}
