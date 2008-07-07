package noam.er;

public abstract class ER {

	public ER() {
		
	}

	abstract void accept(IVisitor v);
	
}
