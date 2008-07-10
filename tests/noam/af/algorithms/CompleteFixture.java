package noam.af.algorithms;

import static noam.utils.IteratorHelper.*;
import noam.IO;
import noam.af.AF;
import noam.af.Transition;

import org.junit.Test;

public class CompleteFixture {

	@Test
	public void completeTransitionsToDeadState(){
		Complete a = new Complete(IO.parseAF("<(A,B,C),(x,y),((A,x,B)(B,y,C)),A,(C)>"));
		
		assertSameElements(a.getStates(), "A","B","C", a.getDeadState());
		assertSameElements(a.getFinalStates(), "C");
		assertSameElements(a.getTransitions("A"), new Transition("A","x","B"), new Transition("A","y",a.getDeadState()));
		assertSameElements(a.getTransitions("B"), new Transition("B","y","C"),new Transition("B","x",a.getDeadState()));
		assertSameElements(a.getTransitions("C"), new Transition("C","x",a.getDeadState()),new Transition("C","y",a.getDeadState()));
		System.out.append(IO.printDot(a));
	}
}