package noam.er;


public class EREmpty extends ER{

	@Override
	public
	Object accept(IVisitor v) {
		return v.visit(this);
	}

	@Override
	public boolean equals(Object arg0) {
		return arg0 instanceof EREmpty;
	}
}
