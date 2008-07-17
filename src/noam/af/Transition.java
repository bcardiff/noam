package noam.af;

import java.util.Comparator;

public class Transition {
	private final String from;

	private final String label;

	private final String to;

	public Transition(String from, String label, String to) {
		this.from = from;
		this.label = label;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public String getLabel() {
		return label;
	}

	public String getTo() {
		return to;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Transition) {
			Transition that = (Transition) arg0;
			return this.from.equals(that.from) && this.label.equals(that.label)
					&& this.to.equals(that.to);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return from.hashCode() + label.hashCode() + to.hashCode();
	}

	public static Comparator<Transition> comparator() {
		return new Comparator<Transition>() {
			//@Override
			public int compare(Transition o1, Transition o2) {
				int res;
				res = o1.getFrom().compareTo(o2.getFrom());
				if (res != 0)
					return res;
				res = o1.getLabel().compareTo(o2.getLabel());
				if (res != 0)
					return res;
				res = o1.getTo().compareTo(o2.getTo());
				return res;
			}
		};
	}
}
