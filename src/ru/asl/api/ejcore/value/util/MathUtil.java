package ru.asl.api.ejcore.value.util;

import java.util.Random;

/**
 * <p>MathUtil class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class MathUtil {

	/**
	 * <p>randomBoolean.</p>
	 *
	 * @return a boolean
	 */
	public static boolean randomBoolean() {
		int rnd = new Random().nextInt(100);

		if (rnd > 50)
			return true;
		else
			return false;
	}

	/**
	 * <p>getIntRandomRange.</p>
	 *
	 * @param min a int
	 * @param max a int
	 * @return a int
	 */
	public static int getIntRandomRange(int min, int max) {
		return (int) getRandomRange(min, min);
	}

	/**
	 * <p>getRandomRange.</p>
	 *
	 * @param min a double
	 * @param max a double
	 * @return a double
	 */
	public static double getRandomRange(double min, double max) {
		if (min == max) return min;

		if (min >= max) {
			double temp = max;
			max = min;
			min = temp;
		}

		return min + (max - min) * new Random().nextDouble();
	}

	/**
	 * <p>incrementByPercents.</p>
	 *
	 * @param value a double
	 * @param inrease a double
	 * @return a double
	 */
	public static double incrementByPercents(double value, double inrease) {
		return (value + (value * (inrease / 100)));
	}

	/**
	 * <p>decrementByPercents.</p>
	 *
	 * @param value a double
	 * @param decrement a double
	 * @return a double
	 */
	public static double decrementByPercents(double value, double decrement) {
		return (value + (value * (decrement / 100)));
	}

	/**
	 * <p>getPercentsOfValue.</p>
	 *
	 * @param value a double
	 * @param percents a double
	 * @return a double
	 */
	public static double getPercentsOfValue(double value, double percents) {
		return (value * (percents / 100));
	}



	/**
	 * <p>incrementRangeValue.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @param increment a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String incrementRangeValue(String value, String increment) {
		String[] incSplit = increment.split("-");

		if (ValueUtil.isNumber(incSplit[0])) {
			if (incSplit.length == 1)
				return incrementRangeValue(value, ValueUtil.parseDouble(incSplit[0]));

			double[] values = ValueUtil.parseDouble(value.split("-"));
			double[] increments = ValueUtil.parseDouble(incSplit);

			for (int i = 0 ; i < values.length && i < increments.length ; i++) {
				values[i] += increments[i];
			}

			return Math.min(values[0], values[1]) + "-" + Math.max(values[0], values[1]);
		}

		return value;
	}

	/**
	 * <p>incrementRangeByPercents.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @param percents a double
	 * @return a {@link java.lang.String} object
	 */
	public static String incrementRangeByPercents(String value, double percents) {
		double[] values = ValueUtil.parseDouble(value.split("-"));
		if (values.length == 1) return value;

		for (int i = 0 ; i < values.length ; i++)
			values[i] = incrementByPercents(values[i], percents);

		return Math.min(values[0], values[1]) + "-" + Math.max(values[0], values[1]);
	}

	/**
	 * <p>incrementRangeValue.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @param increment a double
	 * @return a {@link java.lang.String} object
	 */
	public static String incrementRangeValue(String value, double increment) {
		double[] values = ValueUtil.parseDouble(value.split("-"));
		if (values.length == 1) return value;

		for (int i = 0 ; i < values.length ; i++)
			values[i] += increment;

		return Math.min(values[0], values[1]) + "-" + Math.max(values[0], values[1]);
	}

}
