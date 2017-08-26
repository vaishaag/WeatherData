# WeatherData

WeatherData artificially simulates the weather based on past weather details and outputs the data in a standard format.

### Build

* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 1.8 or higher ([```JAVA_HOME```](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) and [```PATH```](https://en.wikipedia.org/wiki/PATH_(variable)) set) for compile and execution
* [Apache Maven](https://maven.apache.org/download.cgi) 3.3 or higher ([```MVN_HOME```](https://maven.apache.org/install.html) and [```PATH```](https://en.wikipedia.org/wiki/PATH_(variable)) set) for build

> Open Internet connection is required for downloading the past weather data

### Installation

From project base directory, [_WeatherData_](https://github.com/vaishaag/WeatherData), where [pom.xml](https://github.com/vaishaag/WeatherData/blob/master/pom.xml) is present. 

Execute the command:

<pre>
<b>mvn clean install</b>
</pre>

>To skip test cases during the build, use the following command.
>
><pre>
><b>mvn clean install -Dmaven.test.skip=true</b>
></pre>

## Configuration/Input

### locations.json

The [_locations.json_](https://github.com/vaishaag/WeatherData/blob/master/locations.json) file contains list of locations, for which the weather is to be predicted. The JSON file consists of an array of locations, in which name and IATA code for each location is specified.

## Execution

After successful build, the JAR file is created in the target directory, which will be in the project root folder. 

From the traget directory, execute the below command:

<pre>
<b>java -jar weather-forecast-jar-with-dependencies.jar locations.json</b>
</pre>

## Output

Once the execution is completed, the prediceted weather details will be written to the file *PredictedWeatherDetails.txt*, which will be in the same directory as that of the execuatable JAR file.

Format of data in the file: ```Location|Position|Local Time|Conditions|Temperature|Pressure|Humidity```

where 
* Location is an optional label describing one or more positions,
* Position is a comma-separated triple containing latitude, longitude, and elevation in metres above sea level,
* Local time is an [ISO8601](https://en.wikipedia.org/wiki/ISO_8601) date time,
* Conditions is either Snow, Rain, Sunny,
* Temperature is in °C,
* Pressure is in hPa, and
* Relative humidity is a %
