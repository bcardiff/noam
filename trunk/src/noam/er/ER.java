package noam.er;

public abstract class ER {

	public ER() {
		
	}

	public abstract Object accept(IVisitor v);

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		this.accept(new ErPrinter(sb));
		return sb.toString();
	}
}
