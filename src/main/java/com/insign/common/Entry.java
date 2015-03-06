package com.insign.common;

/**
 * Created by ilion on 06.03.2015.
 */
public interface Entry<K, V> {
	K getKey();
	void setKey(K key);
	V getValue();
	void setValue(V value);
}
