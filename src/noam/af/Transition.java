package noam.af;

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
}
