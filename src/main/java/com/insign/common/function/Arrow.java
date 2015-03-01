package com.insign.common.function;

/**
 * Created by ilion on 26.02.2015.
 */
public interface Arrow<Argument, Result> {
	Result valueIn(final Argument x);
}
