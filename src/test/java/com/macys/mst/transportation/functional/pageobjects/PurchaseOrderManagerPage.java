package com.macys.mst.transportation.functional.pageobjects;

import java.util.concurrent.TimeUnit;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.macys.mst.transportation.functional.util.CommonUtils;
import com.macys.mst.transportation.functional.util.StepsDataStore;

public class PurchaseOrderManagerPage extends BasePage {
	StepsDataStore dataStorage = StepsDataStore.getInstance();

	@FindBy(linkText = "Go")
	public WebElement goButton;

	public void filterAllPurchaseOrders() throws InterruptedException {
		// code here to click all purchase orders
		
		TimeUnit.SECONDS.sleep(5);
		CommonUtils.doJbehavereportConsolelogAndAssertion("Filtered", "All Purchase Orders", true);
	}

	public void clickGoButton() throws InterruptedException {
		goButton.click();
		TimeUnit.SECONDS.sleep(5); // or common util wait
		CommonUtils.doJbehavereportConsolelogAndAssertion("Clicked on", "Go", true);
	}

	// add more page related operation/steps here
}
