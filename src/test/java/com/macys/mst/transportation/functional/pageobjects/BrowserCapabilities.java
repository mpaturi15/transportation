package com.macys.mst.transportation.functional.pageobjects;

import org.openqa.selenium.chrome.ChromeOptions;

import com.macys.mst.artemis.config.ConfigProperties;
import com.macys.mst.artemis.testNg.LocalDriverFactory;

public class BrowserCapabilities {
	ConfigProperties propConfig = ConfigProperties.getInstance("config.properties");
	public void setBrowserCapability() {

		try {
			Boolean headers = new Boolean(propConfig.getProperty("isHeadless"));
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			options.addArguments("disable-extensions");
			options.addArguments("--start-maximized");
			options.addArguments("--disable-notifications");
			options.setHeadless(headers);
			options.setAcceptInsecureCerts(true);
			LocalDriverFactory.setChromeOptions(options);

		} catch (Exception e) {
			e.printStackTrace();
			throw new AssertionError(e.getMessage());
		}

	}

}
