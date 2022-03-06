package edu.arapahoe.csc245;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.*;

////////////////////////////////////////////////////////////////////////////////////
//
// This program was created for Arapahoe Community College's CSC-245 course and
// identifies the current temperature for a location using the Open Weather Map API.
//
// The use of the API (openweathermap.org) was applied for and access granted 202010321
// The key comes with several technical constraints regarding its usage, including:
//     Hourly forecast: unavailable
//     Daily forecast: unavailable
//     Calls per minute: 60
//     3 hour forecast: 5 days
//
// Details on the use of the API can be found here:
//     https://openweathermap.org/current
//
// The default location is Castle Rock, CO (encoded as Castle Rock, US) but can be
// changed, as required. The GPS coordinates for Castle Rock, CO is
// latitude 39.3722        longitude -104.8561
//
// CSC 245 Secure Software Development
//
// Change log:
//      20210321 API access granted
//      20210322 Initially created (ddl)
//
// Dependencies:
//      gson-2.2.2.jar is needed for correct functioning
//		Oracle JDK 1.8
//
////////////////////////////////////////////////////////////////////////////////////


public class CSC245_Project3_Insecure {

	// Java Maps are used with many API interactions. OpenWeatherMap also uses Java Maps.
	public static Map<String, Object> jsonToMap(String str) {
		Map<String, Object> map = new Gson().fromJson(
				str, new TypeToken<HashMap<String, Object>>() {}.getType()
				);
		return map;
	}

	public static String getTempForCity (String cityString, String api_key) {
		String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" +
				cityString + "&appid=" + api_key + "&units=imperial";
		try {
			StringBuilder result = new StringBuilder();
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null)
				result .append(line);
			System.out.println(result);

			Map<String, Object > respMap = jsonToMap (result.toString());
			Map<String, Object > mainMap = jsonToMap (respMap.get("main").toString());

			return mainMap.get("temp").toString();

		} catch (IOException e){
			System.out.println(e.getMessage());
			return "Temp not available (API problem?)";
		}

	}

	public static void main(String[] args) {
		String owm = "",		// Include the API key here
				LOCATION = "Castle Rock, US";
		String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION +
				"&appid=" + owm + "&units=imperial";

		// The following line is out of scope for mitigation and can be ignored.
//		System.out.println("URL invoked to get temperature data=" + urlString);

		for (int i=0;i<10;i++);
			System.out.println("Current temperature in " + LOCATION +" is: "
					+ getTempForCity(LOCATION,owm) + " degrees.");

		urlString = "";

	}

}
