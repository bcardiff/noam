package noam.utils;

import java.util.Iterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class JoinIterator<E> implements Iterator<E> 
{
	private final Iterator<E> first;
	private final Iterator<E> second;

	public JoinIterator(Iterator<E> first, Iterator<E> second) {
		this.first = first;
		this.second = second;
	}

	
	public boolean hasNext() {
		return first.hasNext() || second.hasNext();
	}

	
	public E next() {
		if (first.hasNext())
			return first.next();
		else
			return second.next();
	}

	
	public void remove() {
		throw new NotImplementedException();		
	}
	
}
