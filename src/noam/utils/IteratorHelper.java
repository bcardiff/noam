package noam.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

public class IteratorHelper {
	public static <E> boolean contains(Iterator<E> source, E e) {
		while (source.hasNext()) {
			if (source.next().equals(e))
				return true;
		}
		return false;
	}

	public static <E> Iterator<E> intersect(Iterator<E> a, Set<E> b) {
		LinkedList<E> r = new LinkedList<E>();
		while (a.hasNext()) {
			E c = a.next();
			if (b.contains(c)) {
				r.add(c);
			}
		}
		return r.iterator();
	}

	public static <E> void assertSameElements(Iterator<E> source,
			E ... expected) {
		HashSet<E> exp = new HashSet<E>();
		for (E e : expected) {
			exp.add(e);
		}

		while (source.hasNext()){
			E e = source.next();
			Assert.assertTrue(e.toString() + " is unexpected.", exp.contains(e));
			exp.remove(e);
		}
		
		if (!exp.isEmpty()){
			Assert.fail(exp.iterator().next().toString() + " is missing.");
		}
	}

	public static <E> void addAll(Set<E> set, Iterator<E> elems) {
		while (elems.hasNext()) {
			set.add(elems.next());
		}
	}
}
