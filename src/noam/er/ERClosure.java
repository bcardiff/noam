package noam.er;

import static noam.utils.ObjectHelper.safetyEquals;

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
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof ERClosure){
			ERClosure that = (ERClosure) arg0;
			return safetyEquals(this.erInner, that.erInner);
		} else {
			return false;
		}
	}
}
