package com.insign.common.function;

/**
 * Created by ilion on 27.02.2015.
 */
public interface Reparameterizable<FunctionType extends Function> {
	public Reparameterizable reparameterize(FunctionType function);
}
