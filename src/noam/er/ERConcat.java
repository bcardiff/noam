package noam.er;

import static noam.utils.ObjectHelper.safetyEquals;

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
	public
	Object accept(IVisitor v) {
		return v.visit(this);
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof ERConcat){
			ERConcat that = (ERConcat) arg0;
			return safetyEquals(this.erLeft, that.erLeft) &&
					safetyEquals(this.erRight, that.erRight);
		} else {
			return false;
		}
	}
}
