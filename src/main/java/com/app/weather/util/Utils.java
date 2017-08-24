package com.app.weather.util;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Utils {

	public static Double parseToDouble(String input) {
		Double output = 0.0;
		if (NumberUtils.isParsable(input))
			output = Double.parseDouble(input);
		return output;
	}

	public static double[] listToPrimitiveArray(List<Double> inputList) {
		double[] outputArray = ArrayUtils.toPrimitive(inputList.toArray(new Double[inputList.size()]));
		return outputArray;
	}
	
	public static String formatDecimal(Double number, String format) {
		DecimalFormat decimalFormat = new DecimalFormat(format);
		String numberString = decimalFormat.format(number);
		return numberString;
	}

	public static String formatDecimal(Double number) {
		return formatDecimal(number, "0.00");
	}
}
