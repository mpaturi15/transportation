package com.macys.mst.transportation.functional.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.macys.mst.artemis.config.FileConfig;
import com.macys.mst.artemis.reports.StepDetail;

public class CommonUtils {
	private static Logger logger = Logger.getLogger(CommonUtils.class.getName());
	private static ObjectMapper mapper = new ObjectMapper();
	private static StepsDataStore dataStorage = StepsDataStore.getInstance();

	public static void doJbehavereportConsolelogAndAssertion(String info, String content, boolean condition) {
		logger.info(info + " = " + content);
		StepDetail.addDetail(info + " = " + content, condition);
		Assert.assertTrue(info + " = ", condition);
	}

	/*
	 * Function to get List of Maps from json String
	 *
	 */
	public static List<Map<String, String>> getListOfMapsFromJsonArray(JSONArray jsonArray) {
		List<Map<String, String>> responseMap = new ArrayList<>();
		try {

			for (Object object : jsonArray) {
				JSONObject jsonObject = (JSONObject) object;
				Map<String, String> map = getMapFromJson(jsonObject.toString());
				responseMap.add(map);
			}

		} catch (Exception e) {
			logger.error("Exception while converting json String to Map", e);
			Assert.fail("Exception while converting json String to Map");
		}
		return responseMap;
	}

	/*
	 * Function to get Map from json String
	 *
	 */
	public static Map<String, String> getMapFromJson(String response) {
		Map<String, String> responseMap = new HashMap<String, String>();
		try {
			if (response != JSONObject.NULL) {
				// JSONObject jsonObject = new JSONObject(response);
				responseMap = mapper.readValue(response, new TypeReference<Map<String, Object>>() {
				});
			} else {
				Assert.fail("response String is null");
			}
		} catch (IOException e) {
			logger.error("Exception while converting json String to Map", e);
			Assert.fail("Exception while converting json String to Map");
		}
		return responseMap;
	}

	public static Map<String, String> getScreenElementsMap(WebDriver aDriver, String xpath) {
		Map<String, String> screenMap = new HashMap<String, String>();
		List<WebElement> allElements = aDriver.findElements(By.xpath(xpath));
		String screenElements = "";
		String key = null;
		String val = null;
		int size = allElements.size();
		for (int i = 0; i < size; i++) {
			String menuName = allElements.get(i).getText();

			if (i == size - 1) {
				screenMap.put(menuName.trim(), menuName.trim());

			} else {
				if (menuName.contains(":")) {
					int index = menuName.indexOf(":");
					if (index == menuName.length() - 1) {
						key = StringUtils.chop(menuName);
						val = StringUtils.chop(menuName);
					} else {
						key = menuName.substring(0, index);
						val = menuName.substring(index, menuName.length() - 1);
					}
				} else {
					val = menuName;
					key = menuName;
				}
				screenMap.put(key.trim(), val.trim());
			}

		}

		return screenMap;
	}

	public static Map<String, String> getScreenElementData(WebDriver driver, String xpath) {
		Map<String, String> screenMap = new HashMap<String, String>();
		List<WebElement> allFields = driver.findElements(By.xpath(xpath));
		for (WebElement element : allFields) {
			String text = element.getText().trim();
			if (StringUtils.isNotEmpty(text)) {
				if (text.contains(":")) {
					String[] keyValue = text.split(":");
					screenMap.put(keyValue[0].trim(), keyValue.length > 1 ? keyValue[1].trim() : "");
				} else {
					screenMap.put(text, text);
				}
			}
		}
		return screenMap;
	}

	public static void compareValues1(Map<String, String> screenValue, Map<String, Object> dbValue) {
		String validation = "";

		for (Map.Entry<String, String> entry : screenValue.entrySet()) {
			try {

				if (commonValidation1(dbValue, entry.getKey(), entry.getValue().toString())) {
					validation = validation + " " + entry.getKey() + " " + entry.getValue() + " true ";
				} else {
					validation = validation + " " + entry.getKey() + " " + entry.getValue() + " false ";
				}

			} catch (Exception e) {
				Assert.assertFalse(true);
			}
		}
		StepDetail.addDetail("Validation value string : " + validation, true);
		if (validation.contains("false")) {
			Assert.assertTrue(false);
		}

	}

	public static boolean commonValidation1(Map<String, Object> actual, String key, String value) {
		if (null != actual && !actual.isEmpty()) {
			if (null != value && !value.isEmpty() && null != key && !key.isEmpty()) {
				if (actual.containsKey(key)) {
					Object object = actual.get(key);
					if (value.equalsIgnoreCase(object.toString())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * function two validate list1 values with list2
	 */
	public static void validateLists(List<String> list1, List<String> list2) {

		if (null != list1 && null != list2 && !list1.isEmpty() && !list2.isEmpty()) {
			for (String value : list1) {
				if (org.apache.commons.lang.StringUtils.isNotBlank(value.trim()) && !list2.contains(value.trim())) {
					String infoMsg = String.format("List are not matching with value: %s", value);
					logger.info(infoMsg);
					StepDetail.addDetail(infoMsg, true);
					Assert.fail(infoMsg);
				} else {
				}
			}
		} else {
			Assert.fail("Any one of the list is Empty");
		}
	}

	public static void compareValues(Map<String, String> screenValue, Map<String, String> dbValue) {
		String validation = "";
		for (Map.Entry<String, String> entry : screenValue.entrySet()) {
			try {

				if (commonValidation(dbValue, entry.getKey(), entry.getValue())) {
					validation = validation + String.format("%1$s%2$s%3$s%4$s%5$s", " ", entry.getKey(), " ", entry.getValue(), " true");
				} else {
					validation = validation + String.format("%1$s%2$s%3$s%4$s%5$s", " ", entry.getKey(), " ", entry.getValue(), " false");
				}

			} catch (Exception e) {
				Assert.assertFalse(true);
			}
		}
		StepDetail.addDetail("Validation value string : " + validation, true);
		if (validation.contains("false")) {
			Assert.assertTrue(false);
		}

	}

	public static boolean commonValidation(Map<String, String> actual, String key, String value) {
		if (null != actual && !actual.isEmpty()) {
			if (null != value && !value.isEmpty() && null != key && !key.isEmpty()) {
				if (actual.containsKey(key)) {
					Object object = actual.get(key);
					if (value.equalsIgnoreCase(object.toString())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static int getRandomNumber(int size) {
		Random rand = new Random();
		return rand.nextInt(size);
	}

	//returns the url
	public static String getUrl(String tagVal) {
		return FileConfig.getInstance().getStringConfigValue("services." + tagVal);
	}

	public static String getEnvConfigValue(String tagVal) {
		return FileConfig.getInstance().getStringConfigValue(tagVal);
	}

	public static Map<String, String> getParamsToMap(String params) {
		return Splitter.on(",").withKeyValueSeparator(":").split(params);
	}

	public static Map<String, String> getParamsToMutableMap(String params) {
		return new HashMap<>(getParamsToMap(params));
	}

	public static String getMapToStringWithBraces(Map<String, ?> map) {
		return "{" + Joiner.on(",").withKeyValueSeparator(":").join(map) + "}";
	}

	public static String getMapToString(Map<String, ?> map) {
		return Joiner.on(",").withKeyValueSeparator(":").join(map);
	}

	public static String getListOfMapToString(List<Map<String, String>> mapList) {
		if (!Objects.isNull(mapList)) {
			String listToString = mapList.stream().map(CommonUtils::getMapToStringWithBraces).collect(Collectors.toList()).toString();
			return listToString.substring(1, listToString.length() - 1).replaceAll("\\s", "");
		}
		return null;
	}

	public static List<String> removeDuplicatesFromList(List<String> list) {
		return list.stream().distinct().collect(Collectors.toList());
	}

	public static String listWithDoubleQuotes(List<String> list) {
		return list.stream().collect(Collectors.joining("\", \"", "\"", "\""));
	}

	public static String decryptBase64(String encryptString) {
		return new String(Base64.getDecoder().decode(encryptString));
	}

	public static Map<String, String> getFilledParamsToMap(String params) {
		Map<String, String> queryParams = getParamsToMap(params);
		return queryParams.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey,
						e -> (dataStorage.getStoredData().containsKey(e.getValue())) ? dataStorage.getStoredData().get(e.getValue()).toString()
								: e.getValue()));
	}

	public static void waitSec(long sec) {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			logger.error("Error in waitSec", e);
		}
	}

	public static String toPrettyJson(String jsonString) {
		try {
			if (org.apache.commons.lang3.StringUtils.isNotBlank(jsonString)) {
				JsonElement jsonElement = new JsonParser().parse(jsonString);
				return new GsonBuilder().setPrettyPrinting().create()
						.toJson((jsonElement.isJsonObject()) ? jsonElement.getAsJsonObject() : jsonElement.getAsJsonArray());
			}
			throw new JsonSyntaxException("Unable to parse the Json");
		} catch (JsonSyntaxException e) {
			return jsonString;
		}
	}

	public static boolean isAlertPresent(WebDriver aDriver) {
		try {
			waitSec(1);
			aDriver.switchTo().alert();
			return true;
		} catch (Exception var2) {
			return false;
		}
	}

	public static Map<String, String> getHeaderProps() {
		Map<String, String> headerProps = new HashMap<>();
		//headerProps.put("SomeHeaderkey","headerValue");
		return headerProps;
	}

	public static int getNextOrderReferenceNumber() {
		String value = "";
		try {
			File file = ResourceUtils.getFile(TransportationConstants.FILE_ORD_REF_NO);
			//File f = new File("src/test/resources/orderReferenceNumber.txt"); // both same

			value = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
			int nextVal = Integer.valueOf(value) + 1;
			FileUtils.writeStringToFile(file, String.valueOf(nextVal), StandardCharsets.UTF_8, false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.valueOf(value);
	}
	
	public static String getCommaSeperatedValue(Set<String> keys){
        StringBuffer retVal = new StringBuffer();
        for(String key : keys){
        	retVal.append(key);
        	retVal.append(", ");
        }
        return retVal.substring(0, retVal.lastIndexOf(",")).toString();
    }

}
