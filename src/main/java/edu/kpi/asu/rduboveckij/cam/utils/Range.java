package edu.kpi.asu.rduboveckij.cam.utils;

import java.util.Iterator;

public class Range {
	/**
	 * Range generator
	 * @param start value
	 * @param stop value
	 * @param step value
	 * @return collection integer from start to stop with step
	 */
	public static Iterable<Integer> range(final int start, final int stop,
			final int step) {
		if (step <= 0)
			throw new IllegalArgumentException("step > 0 isrequired!");

		return new Iterable<Integer>() {
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {
					private int counter = start;

					public boolean hasNext() {
						return counter < stop;
					}

					public Integer next() {
						try {
							return counter;
						} finally {
							counter += step;
						}
					}

					public void remove() {
					}
				};
			}
		};
	}
	
	/**
	 * Range generator with default step 1
	 * @param start
	 * @param stop
	 * @return collection integer from start to stop with step 1
	 */
	public static Iterable<Integer> range(final int start, final int stop) {
		return range(start, stop, 1);
	}

	/**
	 * Range generator with default step 1 and start 0
	 * @param stop
	 * @return collection integer from 0 to stop with step 1
	 */
	public static Iterable<Integer> range(final int stop) {
		return range(0, stop, 1);
	}
}