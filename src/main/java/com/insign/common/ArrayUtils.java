package com.insign.common;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by ilion on 28.02.2015.
 */
public class ArrayUtils {
	public static <T extends Comparable> T[] merge(T[] first, T[] second) {
		if (Objects.isNull(first))
			if (Objects.isNull(second))
				return null;
			else return Arrays.copyOf(second, second.length);
		else if (Objects.isNull(second))
			return Arrays.copyOf(first, first.length);

		Class<?> componentType = first.getClass().getComponentType();
		T[] merged = (T[]) Array.newInstance(componentType, first.length + second.length);
		int firstIndex = 0,
				secondIndex = 0,
				mergedIndex = 0;
		while (firstIndex < first.length && secondIndex < second.length) {
			if (first[firstIndex].compareTo(second[secondIndex]) < 0)
				merged[mergedIndex++] = first[firstIndex++];
			else
				merged[mergedIndex++] = second[secondIndex++];
		}
		for (int k = firstIndex; k < first.length; k++)
			merged[mergedIndex++] = first[k];
		for (int k = secondIndex; k < second.length; k++)
			merged[mergedIndex++] = second[k];
		return merged;
	}

	public static double[] merge(final double[] first, final double[] second) {
		double[] merged = new double[first.length + second.length];
		int firstIndex = 0,
				secondIndex = 0,
				mergedIndex = 0;
		while (firstIndex < first.length && secondIndex < second.length) {
			if (first[firstIndex] < second[secondIndex])
				merged[mergedIndex++] = first[firstIndex++];
			else
				merged[mergedIndex++] = second[secondIndex++];
		}
		for (int k = firstIndex; k < first.length; k++)
			merged[mergedIndex++] = first[k];
		for (int k = secondIndex; k < second.length; k++)
			merged[mergedIndex++] = second[k];
		return merged;
	}

	public static double[] union(final double[] first, final double[] second) {
		SortedSet<Double> set = new TreeSet<Double>();
		for (double e : first)
			set.add(e);
		for (double e :second)
			set.add(e);
		double[] united = new double[set.size()];
		int k = 0;
		for (double e : set)
			united[k++] = e;
		return united;
	}
}
