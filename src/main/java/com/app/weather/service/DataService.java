package com.app.weather.service;

import static com.app.weather.util.Constants.BOM_CLIMATE_API_BASE;
import static com.app.weather.util.Constants.BOM_CSV_RECORD_HUMIDITY;
import static com.app.weather.util.Constants.BOM_CSV_RECORD_PRESSURE;
import static com.app.weather.util.Constants.BOM_CSV_RECORD_SIZE;
import static com.app.weather.util.Constants.BOM_CSV_RECORD_TEMPERATURE;
import static com.app.weather.util.Constants.WEATHER_HISTORY_REQUIRED_IN_MONTHS;
import static com.app.weather.util.Utils.parseToDouble;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.app.weather.model.Location;
import com.app.weather.model.WeatherHistory;
import com.app.weather.util.Operations;

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

	public WeatherHistory getWeatherHistoryForLocation(Location location) throws IOException {
		List<Double> temperatureList = new LinkedList<Double>();
		List<Double> pressureList = new LinkedList<Double>();
		List<Double> relativeHumidityList = new LinkedList<Double>();
		
		LocalDateTime startDate = LocalDateTime.now().minusMonths(WEATHER_HISTORY_REQUIRED_IN_MONTHS);
		
		for (int i = 0; i <= WEATHER_HISTORY_REQUIRED_IN_MONTHS; i++) {
			String dataForYearAndMonth = startDate.plusMonths(i).format(DateTimeFormatter.ofPattern("YYYYMM"));

			String urlString = BOM_CLIMATE_API_BASE + dataForYearAndMonth + "/text/" + location.getIataCode() + "."
					+ dataForYearAndMonth + ".csv";
			System.out.println("INFO: Getting data from URL " + urlString);

			Iterable<CSVRecord> records = Operations.getCSVRecordsFromUrl(urlString);
			for (CSVRecord csvRecord : records) {
				if (csvRecord.size() == BOM_CSV_RECORD_SIZE) {
					temperatureList.add(parseToDouble(csvRecord.get(BOM_CSV_RECORD_TEMPERATURE)));
					relativeHumidityList.add(parseToDouble(csvRecord.get(BOM_CSV_RECORD_HUMIDITY)));
					pressureList.add(parseToDouble(csvRecord.get(BOM_CSV_RECORD_PRESSURE)));
				}
			}
		}

		WeatherHistory weatherHistory = new WeatherHistory();
		weatherHistory.setTemperature(temperatureList);
		weatherHistory.setPressure(pressureList);
		weatherHistory.setRelativeHumidity(relativeHumidityList);

		return weatherHistory;
	}

}
