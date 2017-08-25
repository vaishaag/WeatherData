package com.app.weather.util;

import static com.app.weather.util.Utils.formatDecimal;
import static com.app.weather.util.Utils.listToPrimitiveArray;
import static com.app.weather.util.Utils.parseToDouble;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testParseToDouble() throws Exception {
		String input = "1a23.45";
		double output = parseToDouble(input);
		assertEquals(0.0, output, 0);
	}

	@Test
	public void testParseToDouble2() throws Exception {
		String input = "123.45";
		double output = parseToDouble(input);
		assertEquals(123.45, output, 0);
	}

	@Test
	public void testListToPrimitiveArray() throws Exception {
		double[] expected = new double[] { 123.45, -111.234 };
		
		List<Double> doubleList = new LinkedList<Double>();
		doubleList.add(123.45);
		doubleList.add(-111.234);
		double[] actual = listToPrimitiveArray(doubleList);
		
		assertArrayEquals(expected, actual, 0);
	}

	@Test
	public void testFormatDecimal() throws Exception {
		Double number = 23.123456;
		assertEquals("23.12", formatDecimal(number));
	}

	@Test
	public void testFormatDecimal2() throws Exception {
		Double number = 23.123456;
		String format = "00.0000";
		assertEquals("23.1235", formatDecimal(number, format));
	}
}