package noam.er;

public abstract class ER {

	public ER() {
		
	}

	public abstract Object accept(IVisitor v);
	
}
