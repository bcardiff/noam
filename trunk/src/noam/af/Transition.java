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

}
