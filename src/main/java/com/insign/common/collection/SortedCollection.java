package com.insign.common.collection;

import java.util.*;

/**
 * Created by ilion on 26.04.2015.
 */
public class SortedCollection<E extends Comparable< ? super E>> extends AbstractCollection<E> {

	private List<E> list = new ArrayList();

	public SortedCollection() {
		super();
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean add(E e) {
		int insertionIndex = SortedCollection.insertionIndexSearch(this, e);
		list.add(insertionIndex, e);
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c)
			add(e);
		return true;
	}

	public E get(int index) {
		return list.get(index);
	}

	public E getFirst() {
		return get(0);
	}

	public E getLast() {
		return get(size() - 1);
	}

	public E getIfContains(E e) {
		int index = insertionIndexSearch(this, e);
		if (index < size() && get(index).equals(e))
			return get(index);
		else
			return null;
	}

	public SortedCollection<E> getLessThan(E e) {
		int insertionIndex = SortedCollection.insertionIndexSearch(this, e);
		SortedCollection<E> lessThan = new SortedCollection<E>();
		for (int k = 0; k < insertionIndex; k++)
			lessThan.add(get(k));
		return lessThan;
	}

	public SortedCollection<E> getMoreThan(E e) {
		int insertionIndex = SortedCollection.insertionIndexSearch(this, e);
		SortedCollection<E> moreThan = new SortedCollection<E>();
		for (int k = insertionIndex; k < size(); k++)
			moreThan.add(get(k));
		return moreThan;
	}

	public SortedCollection<E> copy(int startIndex) {
		int length = size() - startIndex;
		return copy(startIndex, length);
	}

	public SortedCollection<E> copy(int startIndex, int length) {
		SortedCollection<E> copy = new SortedCollection<E>();
		for (int k = startIndex;  k < startIndex + length; k++) {
			copy.add(get(k));
		}
		return copy;
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] array) {
		return  list.toArray(array);
	}

	protected static <E extends Comparable< ? super E>> int insertionIndexSearch(SortedCollection<E> list, E e) {
		int minIndex = 0,
				maxIndex = list.size() - 1,
				middleIndex = 0;

		while (minIndex <= maxIndex) {
			middleIndex = minIndex + (maxIndex - minIndex) / 2;
			Comparable<? super E> middleValue = list.get(middleIndex);
			int cmp = middleValue.compareTo(e);
			if (cmp < 0)
				minIndex = middleIndex + 1;
			else if (cmp > 0)
				maxIndex = middleIndex - 1;
			else
				break;
		}

		return minIndex + (maxIndex - minIndex) / 2;
	}
}
