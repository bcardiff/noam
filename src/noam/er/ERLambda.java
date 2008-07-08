package noam.er;

public class ERLambda extends ER{

	@Override
	Object accept(IVisitor v) {
		return v.visit(this);
	}

}
