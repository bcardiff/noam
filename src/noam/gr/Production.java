package noam.gr;

import java.util.LinkedList;

public class Production {
	private LinkedList<String> left;

	private LinkedList<String> right;

	public Production(String left) {
		this.left = new LinkedList<String>();
		this.addLeft(left);
		this.right = new LinkedList<String>();
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
}
