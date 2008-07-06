package noam.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

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
}
