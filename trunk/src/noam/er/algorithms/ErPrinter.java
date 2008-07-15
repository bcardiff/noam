package noam.er.algorithms;

import noam.er.ER;
import noam.er.ERChoice;
import noam.er.ERClosure;
import noam.er.ERConcat;
import noam.er.EREmpty;
import noam.er.ERLambda;
import noam.er.ERTerminal;
import noam.er.IVisitor;

public class ErPrinter implements IVisitor {

	private final StringBuilder output;
	ER parent;

	public ErPrinter(StringBuilder output) {
		this.output = output;
		this.parent = null;
	}

	@Override
	public Object visit(ERChoice e) {
		boolean shouldUseParen = shouldUseParens(e);
		if (shouldUseParen)
			output.append("(");
		parent = e;
		e.getErLeft().accept(this);
		output.append("|");
		parent = e;
		e.getErRight().accept(this);
		if (shouldUseParen)
			output.append(")");
		return null;
	}

	@Override
	public Object visit(ERClosure e) {
		boolean shouldUseParen = shouldUseParens(e);
		if (shouldUseParen)
			output.append("(");
		parent = e;
		e.getErInner().accept(this);
		output.append("*");
		if (shouldUseParen)
			output.append(")");
		return null;
	}

	@Override
	public Object visit(ERConcat e) {
		boolean shouldUseParen = shouldUseParens(e);
		if (shouldUseParen)
			output.append("(");
		parent = e;
		e.getErLeft().accept(this);
		output.append(".");
		parent = e;		
		e.getErRight().accept(this);
		if (shouldUseParen)
			output.append(")");
		return null;
	}

	private boolean shouldUseParens(ER e) {
		return parent != null && !e.getClass().equals(parent.getClass());
	}

	@Override
	public Object visit(EREmpty e) {
		output.append("VACIO");
		return null;
	}

	@Override
	public Object visit(ERLambda e) {
		output.append("LAMBDA");
		return null;
	}

	@Override
	public Object visit(ERTerminal e) {
		output.append(e.getTerminal());
		return null;
	}

}
