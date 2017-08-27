package com.app.weather.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;

import com.app.weather.model.Location;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * This class performs some of the operation like file reading and writing, HTTP
 * requests
 * 
 * @author Vaishaag Subhagan
 */
public class Operations {

	/**
	 * This methods reads and parses a JSON file and creates the corresponding JSON
	 * object.
	 * 
	 * @param fileName
	 *            - the JSON file name which is to be read and parsed
	 * @return the JsonObject corresponding to the file which is parsed
	 * @throws FileNotFoundException
	 */
	public static JsonObject readJsonFile(String fileName) throws FileNotFoundException {
		FileReader fileReader = new FileReader(fileName);
		JsonObject jsonObject = new GsonBuilder().create().fromJson(fileReader, JsonObject.class);
		return jsonObject;
	}

	/**
	 * This method retrieves a JSON file from an URL and converts it to the
	 * corresponding JsonObject
	 * 
	 * @param urlString
	 *            - the URL from which the JSON file is to be retrieved
	 * @return the corresponding JsonObject for the JSON file which is retrieved
	 *         from the URL
	 */
	public static JsonObject getJsonObjectFromURL(String urlString) {
		try {
			URL url = new URL(urlString);
			InputStreamReader reader = new InputStreamReader(url.openStream());
			JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
			return jsonObject;
		} catch (IOException e) {
			System.err.println("ERROR: Failed to make network connection and retrieve data from URL " + urlString);
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * This method writes all the location details to a file
	 * 
	 * @param locationList
	 *            - the list with details of all the locations
	 * @param fileName
	 *            - the output file name
	 */
	public static void writeLocationDetailsToFile(List<Location> locationList, String fileName) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName);
			for (Location location : locationList) {
				fileWriter.write(location.toString());
			}
		} catch (IOException e) {
			System.err.println("ERROR: Failed to write details to file " + fileName);
			System.err.println(e.getMessage());
		} finally {
			IOUtils.closeQuietly(fileWriter);
		}
	}

	/**
	 * This method retrieves CSV file from an URL and converts it to a list of
	 * CSVRecords
	 * 
	 * @param urlString
	 *            - the URL from which the CSV file is to be retrieved
	 * @return a list of CSVRecords which is parsed from the file retrieved from the
	 *         URL
	 */
	public static Iterable<CSVRecord> getCSVRecordsFromUrl(String urlString) {
		Reader reader = null;
		InputStream inputStream = null;
		Iterable<CSVRecord> records = null;
		try {
			inputStream = new URL(urlString).openStream();
			byte[] buffer = IOUtils.toByteArray(inputStream);
			reader = new CharSequenceReader(new String(buffer));
			records = CSVFormat.DEFAULT.withQuote(null).withDelimiter(',').parse(reader);
		} catch (IOException e) {
			System.err.println("ERROR: Failed to retrieve and parser data from URL " + urlString);
			System.err.println(e.getMessage());
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(reader);
		}
		return records;
	}
}
