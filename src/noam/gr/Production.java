package noam.gr;

import java.util.Iterator;
import java.util.LinkedList;

public class Production {
	private LinkedList<String> left;

	private LinkedList<String> right;

	public Production(String left) {
		this.left = new LinkedList<String>();
		this.addLeft(left);
		this.right = new LinkedList<String>();
	}
	
	public Production(String left, String t) {
		this(left);
		this.addRight(t);
	}

	public Production(String left, String t, String nt) {
		this(left, t);
		this.addRight(nt);
	}

	public LinkedList<String> getLeft() {
		return left;
	}

	public LinkedList<String> getRight() {
		return right;
	}

	public void addLeft(String s) {
		left.add(s);
	}

	public void addRight(String s) {
		right.add(s);
	}
	
	private boolean equalsLists(LinkedList<String> l1, LinkedList<String> l2) {
		if (l1.size() != l2.size()) {
			return false;
		}
		
		Iterator<String> l1It = l1.iterator();
		Iterator<String> l2It = l2.iterator();
		
		while (l1It.hasNext()) {
			String l1Elem = l1It.next();
			String l2Elem = l2It.next();
			if (!l1Elem.equals(l2Elem)) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return this.left.get(0).hashCode();
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Production) {
			Production p = (Production) arg0;
			return this.equalsLists(this.left, p.left) &&
				this.equalsLists(this.right, p.right);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		String ret = new String();
		
		Iterator<String> leftIt = left.iterator();
		while (leftIt.hasNext()) {
			String token = leftIt.next();
			ret += token;
			ret += " ";
		}
		
		ret += "-> ";
		
		if (right.size() == 0) {
			ret += " \\";
		} else {
			Iterator<String> rightIt = right.iterator();
			while (rightIt.hasNext()) {
				String token = rightIt.next();
				ret += token;
				ret += " ";
			}
		}
		
		return ret;
	}
}
