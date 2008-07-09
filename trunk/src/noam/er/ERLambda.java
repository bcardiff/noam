package noam.er;


public class ERLambda extends ER{

	@Override
	Object accept(IVisitor v) {
		return v.visit(this);
	}

	@Override
	public boolean equals(Object arg0) {
		return (arg0 instanceof ERLambda);
	}
}
