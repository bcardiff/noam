package noam.utils;

import java.util.Iterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class IteratorMapping<T, E> implements Iterator<E> {

	private Iterator<T> iterator;
	private final Function<T, E> operation;

	public IteratorMapping(Iterator<T> it, Function<T, E> operation) {
		this.iterator = it;
		this.operation = operation;

	}

	public boolean hasNext() {

		return iterator.hasNext();
	}

	public E next() {
		return operation.apply(iterator.next());
	}

	public void remove() {
		throw new NotImplementedException();
	}

}
