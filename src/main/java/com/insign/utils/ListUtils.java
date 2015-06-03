package com.insign.utils;

import java.util.List;
import java.util.Objects;

/**
 * Created by ilion on 12.05.2015.
 */
public class ListUtils {
	public ListUtils() {super();}

	public static <E> void setAll(List<? super E> list,  E value) {
		Objects.requireNonNull(list);
		for (int k = 0; k < list.size(); k++)
			list.set(k, value);
	}
}
