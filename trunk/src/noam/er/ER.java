package noam.er;

public abstract class ER {

	public ER() {
		
	}

	abstract Object accept(IVisitor v);
	
}
