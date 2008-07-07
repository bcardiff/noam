package noam.er;

public interface IVisitor {

	public Object visit(ERChoice e);
	public Object visit(ERClosure e);
	public Object visit(ERConcat e);
	public Object visit(EREmpty e);
	public Object visit(ERLambda e);
	public Object visit(ERTerminal e);
	
}
