package com.app.weather.util;

/**
 * This class holds all the constants used in this application.
 * 
 * @author Vaishaag Subhagan
 */
public class Constants {

	// Config file and output file names
	public static final String OUTPUT_FILE = "./PredictedWeatherDetails.txt";

	// Separators used in output
	public static final String SEPARATOR = "|";
	public static final String LINE_SEPARATOR = System.lineSeparator();

	// Google Maps APIs used for geo code, elevation, time zone
	public static final String GEOCODE_GOOGLE_API = "http://maps.googleapis.com/maps/api/geocode/json";
	public static final String ELEVATION_GOOGLE_API = "http://maps.googleapis.com/maps/api/elevation/json";
	public static final String TIMEZONE_GOOGLE_API = "https://maps.googleapis.com/maps/api/timezone/json";

	// BOM climate API used for collecting historical data
	public static final String BOM_CLIMATE_API_BASE = "http://www.bom.gov.au/climate/dwo/";

	// Forecast size for prediction and history required details
	public static final int FORECAST_SIZE = 30;
	public static final int WEATHER_HISTORY_REQUIRED_IN_MONTHS = 12;

	// CSV record details
	public static final int BOM_CSV_RECORD_SIZE = 22;
	public static final int BOM_CSV_RECORD_TEMPERATURE = 10;
	public static final int BOM_CSV_RECORD_HUMIDITY = 11;
	public static final int BOM_CSV_RECORD_PRESSURE = 15;

}
