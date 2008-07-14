package noam.af.algorithms;

import noam.IO;
import noam.af.AF;

import org.junit.Test;


public class MinimizationFixture {
	
	@Test
	public void littleAf() {
		AF a = IO.parseAF("<(A,B,X),(a,b),((A,a,B)(A,b,X)(B,a,X)(B,b,X)(X,a,X)(X,b,X)),A,(B)>");
		AF b = new Minimization(a);
		System.out.append(IO.printDot(b));
	}
	
	@Test
	public void Hopcroft_48_412() throws Exception {
		AF a = IO.parseAF("<(A,B,C,D,E,F,G,H),(0,1),((A,0,B)(A,1,F)(B,0,G)(B,1,C)(C,0,A)(C,1,C)(D,0,C)(D,1,G)(E,0,H)(E,1,F)(F,0,C)(F,1,G)(G,0,G)(G,1,E)(H,0,G)(H,1,C)),A,(C)>");
		AF b = new Minimization(a);
		System.out.append(IO.printDot(b));
	}
	
	@Test
	public void testTerminal() throws Exception {
		AF a = new Reachables(Complete.ensureComplete(IO.parseAF("<(A,B),(a),((A,a,B)),A,(B)>")));
		AF b = new Minimization(a);
		System.out.append(IO.printDot(b));
	}
}
