package com.app.weather;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.app.weather.service.DataServiceTest;
import com.app.weather.service.LocationServiceTest;
import com.app.weather.service.PredictorTest;
import com.app.weather.util.OperationsTest;
import com.app.weather.util.UtilsTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	ApplicationTest.class, 
	DataServiceTest.class,
	LocationServiceTest.class,
	PredictorTest.class,
	OperationsTest.class,
	UtilsTest.class
})
public class TestSuite {

}
