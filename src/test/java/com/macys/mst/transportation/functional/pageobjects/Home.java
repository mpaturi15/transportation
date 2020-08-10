package com.macys.mst.transportation.functional.pageobjects;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.macys.mst.artemis.config.FileConfig;
import com.macys.mst.artemis.config.GetPasswordCyberArk;
import com.macys.mst.transportation.functional.stepdefinitions.MIR201EndtoEndFlow;
import com.macys.mst.transportation.functional.util.CommonUtils;
import com.macys.mst.transportation.functional.util.StepsDataStore;
import com.macys.mst.transportation.functional.util.UserType;
import com.macys.mtp.reportsutils.StepLogger;

import io.qameta.allure.Step;

public class Home extends BasePage {

	private static Logger log = Logger.getLogger(Home.class.getName());
	private String cyberarkSafe = FileConfig.getInstance().getStringConfigValue("cyberark.safe");
	private String cyberarkAppid = FileConfig.getInstance().getStringConfigValue("cyberark.appid");
	private String cyberarkPwdObjId = FileConfig.getInstance().getStringConfigValue("cyberark.AppPwdobjectid");
	private String formURL = FileConfig.getInstance().getStringConfigValue("AppUrls.FOMurl");
	private String username = FileConfig.getInstance().getStringConfigValue("AppUrls.userName");
	private String password = FileConfig.getInstance().getStringConfigValue("AppUrls.password");
	private String vendorUsername = FileConfig.getInstance().getStringConfigValue("AppUrls.userName");
	private String vendorPassword = FileConfig.getInstance().getStringConfigValue("AppUrls.password");
	
	StepsDataStore dataStorage = StepsDataStore.getInstance();
	StepLogger stepLogger = new StepLogger();

	@FindBy(xpath = "//*[@id=\"loginBox\"]/input[1]")
	WebElement userName;

	@FindBy(xpath = "//*[@id=\"loginBox\"]/input[2]")
	WebElement passWord;

	@FindBy(xpath = "//*[@id=\"loginBox\"]/span[2]/span")
	WebElement loginButton;

	@FindBy(xpath = "//*[@id=\"shellUsername\"]")
	WebElement profileName;

	@FindBy(xpath = "//*[@id=\"shellLogoutAction\"]")
	WebElement logout;

	@FindBy(xpath = "//*[@id=\"_directoryInput\"]")
	WebElement searchBox;

	@FindBy(xpath = "//*[@id=\"directory\"]/div/div/div/span[1]")
	WebElement searchResult;

	@Given("user signed in to JDA FOM")
	@Then("user signed in to JDA FOM")
	@Step("user signed in to JDA FOM")
	public void login(UserType userType) {
		driver.navigate().to(formURL);
		getWait().until(ExpectedConditions.visibilityOf(userName));
		
		if(UserType.MACYSUSER == userType) {
			userName.clear();
			userName.sendKeys(username);
			passWord.clear();
			passWord.sendKeys(password);
	    	 
	    	   log.info("Logged in as Macys User");
	       }else if(UserType.VENDORUSER == userType) {
	    	   userName.clear();
				userName.sendKeys(vendorUsername);
				passWord.clear();
				passWord.sendKeys(vendorPassword);
	    	   log.info("Logged in as Vendor User");
	       }
		
		loginButton.click();
		getWait().until(ExpectedConditions.elementToBeClickable(searchBox));

		CommonUtils.doJbehavereportConsolelogAndAssertion("Logged in to", "JDA FOM", true);
		stepLogger.logStepPassed(this.getClass());
	}

	@Given("user searched for $value")
	@Then("user searched for $value")
	@Step("user searched for module")
	public void searchForModule(String searchKey) throws InterruptedException {
		searchBox.click();
		searchBox.sendKeys(searchKey);
		getWait().until(ExpectedConditions.elementToBeClickable(searchResult));
		searchResult.click();
		CommonUtils.doJbehavereportConsolelogAndAssertion("Searched for module", searchKey, true);
		stepLogger.logStepPassed(this.getClass());
	}

	@Then("logout from FOM")
	@Step("logout from FOM")
	public void logout() throws InterruptedException {
		profileName.click();
		TimeUnit.SECONDS.sleep(15);
		logout.click();
		getWait().until(ExpectedConditions.elementToBeClickable(loginButton));
		CommonUtils.doJbehavereportConsolelogAndAssertion("Loged Out from", " JDA FOM", true);
		stepLogger.logStepPassed(this.getClass());
	}

	@Then("close the window")
	public void cleanUpDriver() {
		driver.quit();
	}

	@Then("clean all driver activities")
	public void driverCelanup() {
		BasePage.cleanAllDriverActivities();
	}

	@Given("clear data storage")
	public void clearDataStore() {
		dataStorage.getStoredData().clear();
	}
}
