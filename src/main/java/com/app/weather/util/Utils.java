package com.app.weather.util;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * This class contains some utility methods
 * 
 * @author Vaishaag Subhagan
 */
public class Utils {

	/**
	 * This method parses an input string to the corresponding double value. If the
	 * input can not be parsed to a double values, 0.0 is returned.
	 * 
	 * @param input
	 *            - the input value to be parsed
	 * @return the corresponding Double value
	 */
	public static Double parseToDouble(String input) {
		Double output = 0.0;
		if (NumberUtils.isParsable(input))
			output = Double.parseDouble(input);
		return output;
	}

	/**
	 * This method converts a List of double to array of primitive type double
	 * 
	 * @param inputList
	 *            - the list of Double values
	 * @return array of double values corresponding to the input list
	 */
	public static double[] listToPrimitiveArray(List<Double> inputList) {
		double[] outputArray = ArrayUtils.toPrimitive(inputList.toArray(new Double[inputList.size()]));
		return outputArray;
	}

	/**
	 * This method is used for formatting the Decimal values. The input Double
	 * number is formatted according to the give format
	 * 
	 * @param number
	 * @param format
	 * @return
	 */
	public static String formatDecimal(Double number, String format) {
		DecimalFormat decimalFormat = new DecimalFormat(format);
		String numberString = decimalFormat.format(number);
		return numberString;
	}

	public static String formatDecimal(Double number) {
		return formatDecimal(number, "0.00");
	}
}
