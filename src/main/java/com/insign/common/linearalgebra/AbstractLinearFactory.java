package com.insign.common.linearalgebra;

import org.reflections.Reflections;

/**
 * Created by ilion on 04.03.2015.
 */
public class AbstractLinearFactory {
	protected static final String PACKAGE_NAME = AbstractLinearFactory.class.getPackage().getName();
	protected static final Reflections reflections = new Reflections(PACKAGE_NAME);

	protected enum Constructors {
		SIZE_CONSTRUCTOR, REFERENCE_CONSTRUCTOR, UNKNOWN_CONSTRUCTOR;
	}
}
