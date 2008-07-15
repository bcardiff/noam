package noam;

public class BaseFormalismConverterFixture {
	protected void assertCanConvert(FormalismConverter<?> c) {
		System.out.append("\nInput: ");
		System.out.append(c.getInput());
		System.out.append("\n  AF:  ");
		System.out.append(IO.print(c.toAF()));
		System.out.append("\n  AFD: ");
		System.out.append(IO.print(c.toAFD()));
		System.out.append("\n  AFM: ");
		System.out.append(IO.print(c.toAFM()));
		System.out.append("\n  ER:  ");
		System.out.append(IO.print(c.toER()));
		System.out.append("\n  GR:  ");
		System.out.append(IO.print(c.toGR()));
	}
}
