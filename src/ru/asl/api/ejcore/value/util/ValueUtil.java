package ru.asl.api.ejcore.value.util;

import java.util.Random;

/**
 * <p>ValueUtil class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class ValueUtil {

	/**
	 * <p>isNegative.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean isNegative(String value) {
		if (isNumber(value) && Double.parseDouble(value) < 0) return true;

		if (value.startsWith("-")) return true;
		return false;
	}

	/**
	 * <p>isNumber.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean isNumber(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>isTrue.</p>
	 *
	 * @param chance a double
	 * @param random a int
	 * @return a boolean
	 */
	public static boolean isTrue(double chance, int random) {
		return chance >= new Random().nextInt(random);
	}

	/**
	 * <p>isString.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean isString(String value) {
		return !isNumber(value);
	}

	/**
	 * <p>isPercent.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean isPercent(String value) {
		if (isString(value)) return value.contains("%");
		return false;
	}

	/**
	 * <p>parseLong.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a {@link java.lang.Long} object
	 */
	public static Long parseLong(String value) {
		try {
			return new Long(Long.parseLong(value));
		} catch (final NumberFormatException e) {
			e.printStackTrace();
			return new Long(0L);
		}
	}

	/**
	 * <p>parseDouble.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a {@link java.lang.Double} object
	 */
	public static Double parseDouble(String value) {
		try {
			return new Double(Double.parseDouble(value));
		} catch (final NumberFormatException e) {
			e.printStackTrace();
			return new Double(0.D);
		}
	}

	/**
	 * <p>parseDouble.</p>
	 *
	 * @param values a {@link java.lang.String} object
	 * @return an array of {@link double} objects
	 */
	public static double[] parseDouble(String... values) {
		if (values.length < 1 || values[0] == null || !isNumber(values[0])) return new double[] { 0d };
		final double[] result = new double[values.length];

		for (int i = 0 ; i < values.length ; i++) {
			result[i] = parseDouble(values[i]);
		}

		return result;
	}

	/**
	 * <p>parseInteger.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a {@link java.lang.Integer} object
	 */
	public static Integer parseInteger(String value) {
		final Long req = parseLong(value);

		return new Integer((req < Integer.MIN_VALUE ? Integer.MIN_VALUE :
			req > Integer.MAX_VALUE ? Integer.MAX_VALUE :
				req.intValue()));
	}

	/**
	 * <p>parseShort.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a {@link java.lang.Short} object
	 */
	public static Short parseShort(String value) {
		final Integer req = parseInteger(value);

		return new Short((req < Short.MIN_VALUE ? Short.MIN_VALUE :
			req > Short.MAX_VALUE ? Short.MAX_VALUE :
				req.shortValue()));
	}

	/**
	 * <p>parseFloat.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a {@link java.lang.Float} object
	 */
	public static Float parseFloat(String value) {
		final Double req = parseDouble(value);

		return new Float((req < Float.MIN_VALUE ? Float.MIN_VALUE :
			req > Float.MAX_VALUE ? Float.MAX_VALUE :
				req.floatValue()));
	}

}
