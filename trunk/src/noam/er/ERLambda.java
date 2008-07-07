package noam.er;

public class ERLambda extends ER{

	@Override
	void accept(IVisitor v) {
		v.visit(this);
	}

}
