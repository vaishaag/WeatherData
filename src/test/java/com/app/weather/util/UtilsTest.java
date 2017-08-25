package com.app.weather.util;

import org.junit.Test;


import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void testParseToDouble() throws Exception {

    }

    @Test
    public void testListToPrimitiveArray() throws Exception {

    }

    @Test
    public void testFormatDecimal() throws Exception {
    	Double number = 23.123456;
	    assertEquals("23.12", Utils.formatDecimal(number));
    }

    @Test
    public void testFormatDecimal1() throws Exception {
    	Double number = 23.123456;
		String format = "00.0000";
	    assertEquals("23.1235", Utils.formatDecimal(number, format));
    }
}