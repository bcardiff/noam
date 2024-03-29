package noam;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

import noam.af.AF;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int c = 0;
		boolean printDot = false;
		String inputFormalism = null;
		String outputFormalism = null;
		StringBuffer i = new StringBuffer();
		StringBuffer o = new StringBuffer();
		LongOpt[] longopts = new LongOpt[3];

		longopts[0] = new LongOpt("input", LongOpt.REQUIRED_ARGUMENT, i, 'i');
		longopts[1] = new LongOpt("output", LongOpt.REQUIRED_ARGUMENT, o, 'o');
		longopts[2] = new LongOpt("dot", LongOpt.NO_ARGUMENT, new StringBuffer(), 'd');

		Getopt g = new Getopt("noam", args, "i:o:d", longopts);

		while ((c = g.getopt()) != -1) {
			switch (c) {
			case 'i':
				inputFormalism = g.getOptarg();
				if (inputFormalism == null) {
					System.err.println("Opcion -i sin argumento.");
					System.exit(-1);
				}
				break;
			case 'o':
				outputFormalism = g.getOptarg();
				if (outputFormalism == null) {
					System.err.println("Opcion -o sin argumento.");
					System.exit(-1);
				}
				break;
			case 'd':
				printDot = true;
				break;
			case '?':
			case ':':
				System.err.println("Error!");
				System.exit(-4);
				break;
			default:
				System.err.println("Opcion invalida");
				System.exit(-6);
				break;
			}
		}
		
		if (inputFormalism == null) {
			System.err.println("Falta el tipo de formalismo de entrada");
			System.exit(-7);
		}
		
		FormalismConverter f = initializeConverter(inputFormalism, new InputStreamReader(System.in));
		
		if (f == null) {
			System.err.println("Tipo de formalismo de entrada invalido: " + inputFormalism);
			System.exit(-2);
		}
		
		if (!f.isFormalismOk()) {
			System.err.println("Formalismo mal formado");
			System.exit(-3);
		}
		
		convertFormalism(f, outputFormalism, System.out, printDot);
	}

	private static void convertFormalism(FormalismConverter f, String outputFormalism, PrintStream out, boolean printDot) {
		if (outputFormalism != null) {
			if (outputFormalism.equals("ER")) {
				out.println(IO.print(f.toER()));
			} else if (outputFormalism.equals("AF")) {
				out.println(printAF(f.toAF(), printDot));
			} else if (outputFormalism.equals("AFD")) {
				out.println(printAF(f.toAFD(), printDot));
			} else if (outputFormalism.equals("AFM")) {
				out.println(printAF(f.toAFM(), printDot));
			} else if (outputFormalism.equals("GR")) {
				out.println(IO.print(f.toGR()));
			} else {
				System.err.println("Tipo de formalismo de salida invalido");
			}
		} else {
			out.println("El formalismo es correcto.");
		}
	}
	
	private static String printAF(AF af, boolean printDot){
		if (!printDot)
			return IO.print(af);
		else
			return IO.printDot(af);
	}

	private static FormalismConverter initializeConverter(String inputFormalism, Reader input) {
		if (inputFormalism.equals("ER")) {
			return new ErConverter(input);
		} else if (inputFormalism.equals("AF")) {
			return new AfConverter(input);
		} else if (inputFormalism.equals("AFD")) {
			return new AfdConverter(input);
		} else if (inputFormalism.equals("AFM")) {
			return new AfmConverter(input);
		} else if (inputFormalism.equals("GR")) {
			return new GrConverter(input);
		} else {
			return null;
		}
	}
}
