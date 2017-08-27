package com.app.weather.service;

import static com.app.weather.util.Constants.BOM_CLIMATE_API_BASE;
import static com.app.weather.util.Constants.BOM_CSV_RECORD_HUMIDITY;
import static com.app.weather.util.Constants.BOM_CSV_RECORD_PRESSURE;
import static com.app.weather.util.Constants.BOM_CSV_RECORD_SIZE;
import static com.app.weather.util.Constants.BOM_CSV_RECORD_TEMPERATURE;
import static com.app.weather.util.Constants.WEATHER_HISTORY_REQUIRED_IN_MONTHS;
import static com.app.weather.util.Utils.parseToDouble;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.app.weather.model.Location;
import com.app.weather.model.WeatherHistory;
import com.app.weather.util.Operations;

/**
 * This class is used for retrieving the weather history of location from
 * http://www.bom.gov.au/
 * 
 * @author Vaishaag Subhagan
 */
public class DataService {

	private static DataService instance = null;

	private DataService() {
	}

	public static DataService getInstance() {
		if (null == instance) {
			instance = new DataService();
		}
		return instance;
	}

	/**
	 * This method returns the weather history for a location. A service request is
	 * made to BOM with the locations IATA code and the required month and year. A
	 * CSV file which contains data for that particular month is received as the
	 * response, which is then parsed and the required details like temperature,
	 * pressure and humidity are collected and returned.
	 * 
	 * @param location
	 *            - the location for which weather history is to be retrieved
	 * @return the weather history for the specified location
	 */
	public WeatherHistory getWeatherHistoryForLocation(Location location) {
		List<Double> temperatureList = new LinkedList<Double>();
		List<Double> pressureList = new LinkedList<Double>();
		List<Double> relativeHumidityList = new LinkedList<Double>();

		LocalDate startDate = LocalDate.now().minusMonths(WEATHER_HISTORY_REQUIRED_IN_MONTHS);

		for (int i = 0; i <= WEATHER_HISTORY_REQUIRED_IN_MONTHS; i++) {
			LocalDate date = startDate.plusMonths(i);

			Iterable<CSVRecord> records = getWeatherDetailsForMonth(location, date);
			for (CSVRecord csvRecord : records) {
				if (csvRecord.size() == BOM_CSV_RECORD_SIZE) {
					temperatureList.add(parseToDouble(csvRecord.get(BOM_CSV_RECORD_TEMPERATURE)));
					relativeHumidityList.add(parseToDouble(csvRecord.get(BOM_CSV_RECORD_HUMIDITY)));
					pressureList.add(parseToDouble(csvRecord.get(BOM_CSV_RECORD_PRESSURE)));
				}
			}
		}

		return createWeatherHistory(temperatureList, pressureList, relativeHumidityList);
	}

	/**
	 * This method returns the weather details for a location for the specified
	 * month and year
	 * 
	 * @param location
	 *            - the location for which weather details is to be collected
	 * @param date
	 *            - the date (month and year) for which the weather data is to be
	 *            collected
	 * @return weather details for the specified location and date as a list of
	 *         CSVRecords
	 */
	private Iterable<CSVRecord> getWeatherDetailsForMonth(Location location, LocalDate date) {
		String iataCode = location.getIataCode();
		String yearAndMonth = date.format(DateTimeFormatter.ofPattern("YYYYMM"));

		String urlString = BOM_CLIMATE_API_BASE + yearAndMonth + "/text/" + iataCode + "." + yearAndMonth + ".csv";
		System.out.println("INFO: Getting data from URL " + urlString);

		Iterable<CSVRecord> records = Operations.getCSVRecordsFromUrl(urlString);
		return records;
	}

	/**
	 * Creates an instance of WeatherHistory
	 * 
	 * @param temperatureList
	 *            - previous temperature details of a location as a list
	 * @param pressureList
	 *            - previous pressure details of a location as a list
	 * @param relativeHumidityList
	 *            - previous humidity details of a location as a list
	 * @return an instance of WeatherHistory
	 */
	private WeatherHistory createWeatherHistory(List<Double> temperatureList, List<Double> pressureList,
			List<Double> relativeHumidityList) {
		WeatherHistory weatherHistory = new WeatherHistory();
		weatherHistory.setTemperature(temperatureList);
		weatherHistory.setPressure(pressureList);
		weatherHistory.setRelativeHumidity(relativeHumidityList);

		return weatherHistory;
	}

}
