package noam.utils;

import java.util.Iterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SingletonIterator<E> implements Iterator<E> {

	private boolean hasNext;
	private final E element;

	public SingletonIterator(E element) {
		this.element = element;
		hasNext = true;
	}

	
	public boolean hasNext() {
		return hasNext;
	}

	
	public E next() {
		hasNext = false;
		return element;
	}

	
	public void remove() {
		throw new NotImplementedException();
	}

}
