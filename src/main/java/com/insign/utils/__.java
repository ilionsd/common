package com.insign.utils;

/**
 * Created by ilion on 04.03.2015.
 *
 * Simulating reference with wrappers
 */
public class __<E> {
	private E reference;

	public __() {
		super();
		reference = null;
	}

	public __(E e) {
		super();
		reference = e;
	}

	public E getRef() {
		return reference;
	}

	public void setRef(E e) {
		reference = e;
	}

	@Override
	public String toString() {
		String str = "ref< ";
		if (reference != null)
			str += reference.toString() + " >";
		else str += "null >";
		return str;
	}
}
