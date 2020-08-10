package com.macys.mst.transportation.functional.pageobjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.macys.mst.transportation.functional.util.CommonUtils;
import com.macys.mst.transportation.functional.util.StepsDataStore;


public class ShipmentManagerPage extends BasePage{
	
	StepsDataStore dataStorage = StepsDataStore.getInstance();
	
	@FindBy(id= "deliveryOrderManagerFormSearchHeader_Go") public WebElement goButton;
	@FindBy(id = "page_form") public WebElement pageForm;
	@FindBy(xpath="//span[text()='Shipment Manager' and @class='windowTabText']") public WebElement purchase;
	@FindBy(xpath="//select[@name='userFilters']") public WebElement filter;
	@FindBy(xpath="//input[@name='mgrRow']") public WebElement checkBox;
	@FindBy(xpath="//span[contains(text(),'Build Shipment')]") public WebElement buildShipment;
	@FindBy(xpath="//a[@id='null']//span[contains(text(),'Done')]") public WebElement done;
	
	@FindBy(xpath="//input[@id='SHIPMENT_WEIGHT']") public WebElement weight;
	@FindBy(xpath="//input[@id='SHIPMENT_CASE_COUNT']") public WebElement casecount;
	@FindBy(xpath="//input[@id='SHIPMENT_VOLUME']']") public WebElement volume;
	@FindBy(xpath="//span[contains(text(),'Shipment Line Items')]") public WebElement shipmentLineItems;
	
	
	public void verifyShipmentCreated() throws InterruptedException {
				
		System.out.println("shipment created");
	}
	
	public void verifyShipmentPage() throws InterruptedException {
		getWait().until(ExpectedConditions.titleContains("JDA : Shipment Manager"));
			
	}

	public void filterAllPurchaseOrders() throws InterruptedException {
		
		driver.switchTo().frame("appFrame");
		Select userFilter = new Select(filter);
		userFilter.selectByIndex(1);
		TimeUnit.SECONDS.sleep(5);
		CommonUtils.doJbehavereportConsolelogAndAssertion("Filtered", "All Purchase Orders", true);
		
	}
	
public void selectPO() throws InterruptedException {
		
		//driver.switchTo().frame("appFrame");
		//Select userFilter = new Select(filter);
		//userFilter.selectByIndex(1);
		TimeUnit.SECONDS.sleep(5);
		getWait().until(ExpectedConditions.visibilityOf(checkBox));
		checkBox.click();
	}
 	public void clickOnBuildShipment() throws InterruptedException {
	
	//driver.switchTo().frame("appFrame");
	//Select userFilter = new Select(filter);
	//userFilter.selectByIndex(1);
	TimeUnit.SECONDS.sleep(5);
	getWait().until(ExpectedConditions.visibilityOf(buildShipment));
	buildShipment.click();
	
}	
	public void enterShipmentdetails() throws InterruptedException {
		
		//driver.switchTo().frame("appFrame");
		//Select userFilter = new Select(filter);
		//userFilter.selectByIndex(1);
		TimeUnit.SECONDS.sleep(5);		
		getWait().until(ExpectedConditions.visibilityOf(shipmentLineItems));
		weight.sendKeys("1200");
		casecount.sendKeys("100");
		volume.sendKeys("2600");
		
	}
	
	public void clickOnShipmentDonebutton() throws InterruptedException {
		
		//driver.switchTo().frame("appFrame");
		//Select userFilter = new Select(filter);
		//userFilter.selectByIndex(1);
		TimeUnit.SECONDS.sleep(5);
		getWait().until(ExpectedConditions.visibilityOf(done));
		done.click();
		CommonUtils.doJbehavereportConsolelogAndAssertion("shipment created", "All Purchase Orders", true);
		
	}	
	public void clickGoButton() throws InterruptedException {
		
		goButton.click();
		TimeUnit.SECONDS.sleep(5); // or common util wait
		CommonUtils.doJbehavereportConsolelogAndAssertion("Clicked on", "Go", true);
		
		TimeUnit.SECONDS.sleep(5);
		
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		driver.switchTo().defaultContent();
		
	}

}
