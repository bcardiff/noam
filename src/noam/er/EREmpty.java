package noam.er;

public class EREmpty extends ER{

	@Override
	void accept(IVisitor v) {
		v.visit(this);
	}

}
