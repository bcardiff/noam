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
}
