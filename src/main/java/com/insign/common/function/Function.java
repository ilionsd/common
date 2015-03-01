package com.insign.common.function;

/**
 * Created by ilion on 04.02.2015.
 */
public interface Function<Argument, Result> extends Arrow<Argument, Result>{
	Result derivative(final int order, final Argument x);
}
