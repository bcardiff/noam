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

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public E next() {
		hasNext = false;
		return element;
	}

	@Override
	public void remove() {
		throw new NotImplementedException();
	}

}
