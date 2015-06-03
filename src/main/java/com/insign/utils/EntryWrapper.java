package com.insign.utils;

/**
 * Created by ilion on 06.03.2015.
 */
public class EntryWrapper<K, V> implements Entry<K, V>{
	private K key;
	private V value;

	public EntryWrapper(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public void setKey(K key) {
		this.key = key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public void setValue(V value) {
		this.value = value;
	}
}
