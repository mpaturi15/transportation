package com.macys.mst.transportation.functional.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.openqa.selenium.By;
import com.macys.mst.artemis.selenium.SeUiContextBase;
import com.macys.mst.transportation.functional.stepdefinitions.ShipmentIntegration;
import com.macys.mst.transportation.functional.util.CommonUtils;

import cucumber.api.java.eo.Se;

public class PurchaseOrderLineItems extends BasePage {

	private static Logger log = Logger.getLogger(PurchaseOrderLineItems.class.getName());
	
	@FindBy(xpath = "(//span[@class='windowTabText'])[1]")
	public WebElement pageHeader;
	@FindBy(id = "PURCHASE_ORDER_PO_NUMBER")
	public WebElement ponumber;
	@FindBy(xpath = "//a[@id='purchaseOrderManagerFormSearchHeader_Go']")
	public WebElement goButton;
	@FindBy(xpath = "(//input[@id='PURCHASE_ORDER_ORIGIN_LOCATION']/following::img)[1]")
	public WebElement origin;
	@FindBy(xpath = "(//input[@id='PURCHASE_ORDER_DESTINATION_LOCATION']/following::img)[1]")
	public WebElement destination;
	@FindBy(xpath = "//div[@id='DM_Viewport']//tbody[2]//td[4]//a")
	private WebElement poResult;
	@FindBy(xpath = "//div[@id='DM_Viewport']//tbody[2]//td[3]")
	private WebElement buildShipment;
	
	
	// "(//img[contains(@alt, 'View Shipment')]"
	@FindBy(xpath = "//td[@class='j-grid-non-text-row-header valign-middle align-center']//a//img[@class='valign-middle']" )
	public WebElement viewShipment;
	//@FindBy(xpath = "//div[@id='DM_Viewport']" )
	//public WebElement viewShipmenttable;
	@FindBy(xpath = "//table[@class='j-grid-body width-100pct']" )
	public WebElement viewShipmenttable1;
	@FindBy(xpath = "//td[@class='j-grid-non-text-row-header valign-middle align-center']" )
	public WebElement viewShipmenttable2;
	
	//@FindBy(xpath = "//img[@class='valign-middle']")
	//public WebElement viewShipmentDetails;
	  @FindBy(xpath = "//*[@id='DM_Viewport']/table/tbody[2]/tr[1]/td[1]/a/img")
	    public WebElement viewShipmentDetails;
	
	@FindBy(xpath = "//table[@class='table-1-3 width-100pct']//select[@id='SHIPMENT_SHIPMENT_STATUS']")
	public WebElement shipmentStaus;
	@FindBy(xpath = "//option[contains(text(),'Confirmed')]")
	public WebElement shipmentStausConfirmed;
	@FindBy(xpath = "//table[@class='j-grid-body table-1-1 width-100pct']//tr[@class='j-grid-auto-checkbook']//td[@class='j-grid-checkbox-header align-center']//input[@name='selectedLine']")
	public WebElement shipmentStausCheckbox;
	@FindBy(xpath = "//div[@id='tblvppgbtns']//td[1]//div[1]//a[1]//div[1]//div[1]")
	public WebElement statusChangeDone;
	@FindBy(xpath = "//span[@id=\"shellUsername\"]")
	public WebElement logoutFomUserName;
	@FindBy(xpath = "//div[@id=\"shellLogoutAction\"]")
	public WebElement logoutFomAction;
	
	 @FindBy(xpath = "//div[@id='DM_Viewport']//a[1]" )
	    public WebElement viewShipmenttable;
	
	
	public void PurchaseOrderLineItemsPageLoad() throws InterruptedException {
		getWait(60).until(ExpectedConditions.titleContains("JDA : Purchase Order Manager"));
		getWait().until(ExpectedConditions.visibilityOf(pageHeader));

	}

	public void enterPONumber(String PoNumber) {
		
		driver.switchTo().frame("appFrame");
		getRequiredAction();
		SeUiContextBase.sendkeys(ponumber, PoNumber);

	}

	public void clickGoButton() throws InterruptedException {
		getRequiredAction().clickElement(goButton);
		Thread.sleep(2500);
		
		CommonUtils.doJbehavereportConsolelogAndAssertion("Searched for given PO Number",
				ponumber.getAttribute("value"), true);
	}

	public void changeShipmentToConfirmedold() throws InterruptedException {
		driver.switchTo().defaultContent();		
	    driver.switchTo().frame("appFrame");
		getWait().until(ExpectedConditions.visibilityOf(viewShipment));
		getRequiredAction().clickElement(viewShipment);
		getWait().until(ExpectedConditions.visibilityOf(viewShipmenttable));
	    getWait().until(ExpectedConditions.visibilityOf(viewShipmenttable1));
	    getWait().until(ExpectedConditions.visibilityOf(viewShipmenttable2));
		getWait().until(ExpectedConditions.visibilityOf(viewShipmentDetails));
		getRequiredAction().clickElement(viewShipmentDetails);
		getWait().until(ExpectedConditions.visibilityOf(shipmentStaus));
		getRequiredAction().clickElement(shipmentStaus);
		getWait().until(ExpectedConditions.visibilityOf(shipmentStausConfirmed));
		getRequiredAction().clickElement(shipmentStausConfirmed);
		getWait(15).until(ExpectedConditions.visibilityOf(shipmentStausCheckbox));
		getRequiredAction().clickElement(shipmentStausCheckbox);
		getWait(15).until(ExpectedConditions.visibilityOf(statusChangeDone));
		getRequiredAction().clickElement(statusChangeDone);
		Thread.sleep(60000);
		
	}
	public void changeShipmentToConfirmed() throws InterruptedException {
        driver.switchTo().defaultContent();       
        driver.switchTo().frame("appFrame");
        getWait().until(ExpectedConditions.visibilityOf(viewShipment));
        getRequiredAction().clickElement(viewShipment);
        getWait().until(ExpectedConditions.visibilityOf(viewShipmenttable));
        getWait().until(ExpectedConditions.visibilityOf(viewShipmenttable1));
        getWait().until(ExpectedConditions.visibilityOf(viewShipmenttable2));
        getWait().until(ExpectedConditions.visibilityOf(viewShipmentDetails));
        log.info("view shipment");
        getRequiredAction().clickElement(viewShipmentDetails);
        getWait().until(ExpectedConditions.visibilityOf(shipmentStaus));
        getRequiredAction().clickElement(shipmentStaus);
        getWait().until(ExpectedConditions.visibilityOf(shipmentStausConfirmed));
        getRequiredAction().clickElement(shipmentStausConfirmed);
        getWait(15).until(ExpectedConditions.visibilityOf(shipmentStausCheckbox));
        getRequiredAction().clickElement(shipmentStausCheckbox);
        getWait(15).until(ExpectedConditions.visibilityOf(statusChangeDone));
        getRequiredAction().clickElement(statusChangeDone);
        Thread.sleep(60000);
        WebElement element = driver.findElement(By.xpath("//*[@id='DM_Viewport']/table/tbody[2]/tr/td[3]/span"));
        String strng = element.getText().trim();
        System.out.println(strng);
        Assert.assertEquals("Confirmed",strng);
        
        log.info("Shipment Status Changed to Confirmed");
    }
	
	public void logoutFOM() throws InterruptedException {
		driver.switchTo().defaultContent();			
		Thread.sleep(2500);
		getWait().until(ExpectedConditions.visibilityOf(logoutFomUserName));
		getRequiredAction().clickElement(logoutFomUserName);
		Thread.sleep(2500);
		getWait().until(ExpectedConditions.visibilityOf(logoutFomAction));
		getRequiredAction().clickElement(logoutFomAction);
		log.info("FOM application logged out successfully");
//		CommonUtils.doJbehavereportConsolelogAndAssertion("Searched for given PO Number",
//				ponumber.getAttribute("value"), true);
	}

	public void clickShipmentButton() throws Exception {
		getWait().until(ExpectedConditions.visibilityOf(poResult));
		
			/*Assert.assertEquals(poResult.getText(), ponumber.getAttribute("value"));*/
		try {
			getRequiredAction();
			if (SeUiContextBase.isElementEnabled(buildShipment))
				getRequiredAction().clickElement(buildShipment);
			log.info("Shipment button clicked");
		} catch (Exception e) {
			CommonUtils.doJbehavereportConsolelogAndAssertion("BulidShipment not enabled.WARNING!!!!!!!!Can't screate the Shipment", "", true);
		}

		Thread.sleep(3000);
	}

}
