package noam.er;

import static noam.utils.ObjectHelper.safetyEquals;

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
	public
	Object accept(IVisitor v) {
		return v.visit(this);
	}

	public ERChoice(ER erLeft, ER erRight) {
		super();
		this.erLeft = erLeft;
		this.erRight = erRight;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof ERChoice){
			ERChoice that = (ERChoice) arg0;
			return safetyEquals(this.erLeft, that.erLeft) &&
					safetyEquals(this.erRight, that.erRight);
		} else {
			return false;
		}
	}
}
