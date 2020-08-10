package com.macys.mst.transportation.functional.pageobjects;

import com.macys.mst.artemis.selenium.PageObject;
import com.macys.mst.artemis.selenium.SeUiContextBase;
import com.macys.mst.artemis.testNg.LocalDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasePage extends PageObject {

	private static Logger log = Logger.getLogger(BasePage.class.getName());
	static LocalDriverManager localDriverManager = LocalDriverManager.getInstance();
	public static WebDriver driver = localDriverManager.getDriver();
	private SeUiContextBase helperClass = new SeUiContextBase();

	public BasePage() {
		super(driver);
	}

	public WebDriverWait getWait() {
		return new WebDriverWait(driver, 60);
	}

	public WebDriverWait getWait(int sec) {
		return new WebDriverWait(driver, sec);
	}

	public static void cleanAllDriverActivities() {
		localDriverManager.driverCleanup();
	}

	public SeUiContextBase getRequiredAction() {
		return helperClass;
	}

	public static Map<String, String> getScreenElementsMap(String xpath) {
		Map<String, String> screenMap = new HashMap<>();
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

}
