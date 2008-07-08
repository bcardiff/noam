package noam.er;

public class EREmpty extends ER{

	@Override
	Object accept(IVisitor v) {
		return v.visit(this);
	}

}
