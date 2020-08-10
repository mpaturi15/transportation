package com.macys.mst.transportation.functional.pageobjects;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.macys.mst.artemis.config.FileConfig;
import com.macys.mst.artemis.selenium.SeUiContextBase;
import com.macys.mst.artemis.testNg.LocalDriverManager;
import com.macys.mst.transportation.functional.util.CommonUtils;
import com.macys.mst.transportation.functional.util.StepsDataStore;
import com.macys.mst.transportation.functional.util.UserType;

import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;

public class LoadAcceptPage extends BasePage {
	StepsDataStore dataStorage = StepsDataStore.getInstance();

	private static Logger log = Logger.getLogger(LoadAcceptPage.class.getName());
	static LocalDriverManager localDriverManager = LocalDriverManager.getInstance();
	//public static WebDriver driver = localDriverManager.getDriver();
	public int loadid;
	String planID = "2012";
    
	private String tmsURL = FileConfig.getInstance().getStringConfigValue("tms.url");
	private String tmsusername = FileConfig.getInstance().getStringConfigValue("tms.userName");
	private String tmspassword = FileConfig.getInstance().getStringConfigValue("tms.passWord");
	
	private String adminUsername = FileConfig.getInstance().getStringConfigValue("tms.userName");
	private String adminPassword = FileConfig.getInstance().getStringConfigValue("tms.passWord");
	
	//************ TMS Login*********//
	
	@FindBy(xpath = "//input[@name='loginUser']")
	WebElement tmsuserName;
	@FindBy(xpath = "//input[@id='dspLoginPassword']")
	WebElement tmspassWord;
	@FindBy(xpath = "//a[contains(text(),'Login')]")
	WebElement tmsLogin;
	@FindBy(xpath = "//b[contains(text(),'Transportation Manager:')]")
	WebElement tmsHome;
	//************** Navigate to Transportation Smart Bench ****************//
	
	@FindBy(xpath = "i2ui_shell_content")
	WebElement frameOne;
	@FindBy(xpath = "navFrame")
	WebElement frameTwo;
	@FindBy(xpath = "//a[contains(text(),'Transportation Smartbench')]")
	WebElement tranSMBench;
	@FindBy(xpath = "//span[contains(text(),'Transportation Smartbench')]")
	WebElement tmsSBHome;
	// ************* Search Shipment ID ***************************//
	
	
	
	@FindBy(xpath = "//*[@id='isc_PickListMenu_0_row_4']/td/div")
	WebElement shipmentIDInCombo;
	@FindBy(xpath = "//textarea[@name='isc_TextAreaItem_0']")
	WebElement shipmentIDTextBox;	
	@FindBy(xpath = "//div[@id='isc_V']")
	WebElement tmSmSearchafterPageload;
	
	//************ Load Accept *******//
	//@FindBy(xpath = "//td[@class='cell']//span[@class='checkboxFalse']")
	
	@FindBy(xpath = "(//span[@class='checkboxFalse'])[1]")
	WebElement selectShipmentId;
	
	@FindBy(xpath = "//*[contains(@id,'isc_4C')]")
	WebElement shipmentLegs;	
	@FindBy(xpath = "//td[@class='cell']//span[@class='checkboxFalse']")
	WebElement selectShipmentIdLoad;
	@FindBy(xpath = "//div[@id='isc_AH']")
	WebElement assignToNewLoad;
	@FindBy(xpath = "//div[@id='isc_C1']")
	WebElement planningOperations;
	@FindBy(xpath = "//td[@class='primaryDialogButton']")
	WebElement planIDPopUp;
	@FindBy(xpath = "//div[contains(text(),'Attach to Plan')]")
	WebElement attachToPlan;
	@FindBy(xpath = "//input[@id='isc_GU']")
	WebElement enterPlanID;
	@FindBy(xpath = "//div[@id='isc_H2']")
	WebElement setPlanIDOK;
	@FindBy(xpath="//div[@id='isc_AK']")
	WebElement removeOpenLoad;
	@FindBy(xpath="//div[@id='isc_GC']")
	WebElement removeOpenLoadOKButton;
	//@FindBy(xpath="//div[@id='isc_G3']")
	@FindBy(xpath="//div[@id='isc_HA']")				
	WebElement loadAssigned;
	
	@FindBy(xpath="//table[@id='isc_RHtable']//tbody//tr")
	WebElement loadDetails;

	@FindBy(xpath="//img[@name='isc_1Cicon']")
	WebElement closeAllPanels;
	
	@FindBy(xpath = "//*[@id='isc_16']/table/tbody/tr/td/table/tbody/tr/td[1]/img")
	WebElement search;
	@FindBy(xpath = "//*[@class='comboBoxItemPickerLite']")
	WebElement comboBox;
	@FindBy(xpath = "//div[contains(text(),'Load')]")
	WebElement loadIDInCombo;
	@FindBy(xpath = "//textarea[@name='isc_TextAreaItem_0']")
	WebElement loadIDTextBox;
	@FindBy(xpath = "//td[@class='primaryToolStripButton']")
	WebElement searchOkButton;
	@FindBy(xpath = "//td[@class='cell']//span[@class='checkboxFalse']")
	WebElement selectLoadId;
	@FindBy(xpath="//div[@id='isc_I4']")
	WebElement rateLoad;
	@FindBy(xpath="//div[@id='isc_I6']")
	WebElement setToPlannedLoad;
	@FindBy(xpath="//div[@id='isc_J0']")
	WebElement loadTender;
	@FindBy(xpath="//div[@id='isc_NN']")
	WebElement submitTender;
	@FindBy(xpath="//div[@id='isc_K4']")
	WebElement tenderOperations;
	@FindBy(xpath="//div[contains(text(),'Accept Load Tender')]")
	WebElement acceptLoad;	
	@FindBy(xpath="//td[@class='primaryToolStripButtonOver'])[2]")
	WebElement submitLoad;
					
	// **************//
	
	// Login to TMS
	
	public void TSMLogin() throws InterruptedException {
	      
        driver.navigate().to(tmsURL);
        driver.switchTo().defaultContent();
       driver.switchTo().frame("i2ui_shell_content");
       driver.switchTo().frame("results");
       log.info("TMS login page");
       
       getWait().until(ExpectedConditions.visibilityOf(tmsuserName));
       tmsuserName.sendKeys(tmsusername);
       tmsuserName.clear();           
        tmsuserName.sendKeys(tmsusername);
       getWait().until(ExpectedConditions.visibilityOf(tmspassWord));
       tmspassWord.sendKeys(tmspassword);
       getWait().until(ExpectedConditions.visibilityOf(tmsLogin));
       tmsLogin.click();
  		getWait().until(ExpectedConditions.visibilityOf(tmsHome));
  		log.info("Successfully logged into TMS");
        
	}
	public void TSMLogin(UserType userType) throws InterruptedException {
	      
        driver.navigate().to(tmsURL);
        String actualTitle = driver.getTitle();
        
        driver.switchTo().defaultContent();
        driver.switchTo().frame("i2ui_shell_content");
        driver.switchTo().frame("results"); 
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        String currentFrame = (String) jsExecutor.executeScript("return self.name");
       
        log.info("Navigated to TMS login page");
        //getWait().until(ExpectedConditions.visibilityOf(tmsuserName));
        //tmsuserName.sendKeys(tmsusername);
        //tmsuserName.clear();           
        
       getWait().until(ExpectedConditions.visibilityOf(tmspassWord));
       if(UserType.MTOUSER == userType) {
    	   tmsuserName.sendKeys(adminUsername);
    	   tmspassWord.sendKeys(adminPassword);
    	   log.info("Logged in as MTO User");
       }else if(UserType.VENDORUSER == userType) {
    	   tmsuserName.sendKeys(tmsusername);
    	   tmspassWord.sendKeys(tmspassword);
    	   log.info("Logged in as Vendor User");
       }
       
       getWait().until(ExpectedConditions.visibilityOf(tmsLogin));
       tmsLogin.click();
  		getWait().until(ExpectedConditions.visibilityOf(tmsHome));
  		log.info("Successfully logged into TMS");
        
}
	
	// Navigate to Transportation smart bench
	
	public void navigatetoTranSM() throws InterruptedException {
	       
		driver.switchTo().defaultContent();
        driver.switchTo().frame("i2ui_shell_content");
       driver.switchTo().frame("navFrame");
        getWait().until(ExpectedConditions.visibilityOf(tranSMBench));	            
        tranSMBench.click();
    	getWait().until(ExpectedConditions.visibilityOf(tmsSBHome));
    	log.info("Navigated to Transportation smart Bench");

} 
	// Search Shipment ID
	
	public void searchShipment(String shipId) throws InterruptedException {
	   getWait().until(ExpectedConditions.visibilityOf(tmsSBHome));
	   search.click();
       String popup = driver.getWindowHandle();        
       comboBox.click();
       shipmentIDInCombo.click();
       shipmentIDTextBox.click();
       shipmentIDTextBox.sendKeys(shipId);
       searchOkButton.click();
       getWait().until(ExpectedConditions.visibilityOf(tmSmSearchafterPageload));
       log.info("Searching for " + " " + shipId );
       
	}
		
	public void selectShipmentID() throws InterruptedException {
	     getWait().until(ExpectedConditions.visibilityOf(selectShipmentId));
	     selectShipmentId.click();
	 
	}
	public void clickOnShipmentLegs() throws InterruptedException {
	     getWait().until(ExpectedConditions.visibilityOf(shipmentLegs));
	     shipmentLegs.click();
	     log.info("Shipment Leg assgined");
	 
	}
	public void searchShipmentforLoad() throws InterruptedException {
	     getWait().until(ExpectedConditions.visibilityOf(selectShipmentIdLoad));
	     selectShipmentIdLoad.click();
	 
	}
	public int clickOnAssignToNewLoad() throws InterruptedException {	     
	     getWait().until(ExpectedConditions.visibilityOf(planningOperations));
	     planningOperations.click();
	     getWait().until(ExpectedConditions.visibilityOf(attachToPlan));
	     attachToPlan.click();
	     getWait().until(ExpectedConditions.visibilityOf(enterPlanID));
	     enterPlanID.click();
	     getRequiredAction();
		 SeUiContextBase.sendkeys(enterPlanID, planID);
	     getWait().until(ExpectedConditions.visibilityOf(setPlanIDOK));
	     setPlanIDOK.click();
	     Thread.sleep(10000);
	     getWait().until(ExpectedConditions.visibilityOf(assignToNewLoad));
	     WebDriverWait wait = new WebDriverWait(driver, 25);
	     WebElement el = wait.until(ExpectedConditions.elementToBeClickable(assignToNewLoad));
	     el.click();   
	     getWait().until(ExpectedConditions.visibilityOf(loadAssigned));
	     String loaddetails = loadAssigned.getText();	
	     String[] parts = loaddetails.split(" ");
	     String ES = parts[9];
	     String fes = ES.replace(".", "");
	     loadid=Integer.parseInt(fes);
	    log.info("New Load ID created" + "  " +loadid);
	 return loadid;	 
	}
	public void removeLoad() {
		try {
			
			getWait().until(ExpectedConditions.visibilityOf(removeOpenLoad));
			removeOpenLoad.click();
			if (removeOpenLoadOKButton.isDisplayed()){
				removeOpenLoadOKButton.click();
				
			}
			//getWait().until(ExpectedConditions.visibilityOf(removeOpenLoadOKButton));
			//removeOpenLoadOKButton.click();
			log.info("Existing load has been removed");
			
		}catch (Exception e) {
			
		}
	}
	public void closeAllPanels() {
		getWait().until(ExpectedConditions.visibilityOf(closeAllPanels));
		   WebDriverWait wait = new WebDriverWait(driver, 25);
		    WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(closeAllPanels));
		     ele.click();
		     
		
		
	}
	public void loadSearch() {
		
	     getWait().until(ExpectedConditions.visibilityOf(search));
	     search.click();
	     comboBox.click();
	     loadIDInCombo.click();
	     loadIDTextBox.click();
	     loadIDTextBox.sendKeys(String.valueOf(loadid));
	     searchOkButton.click();
	     log.info("Searched Load ID and it is present" );
	     
	}
	public void loadAccept() throws InterruptedException {
		getWait().until(ExpectedConditions.visibilityOf(selectLoadId));
		selectLoadId.click();
		getWait().until(ExpectedConditions.visibilityOf(rateLoad));
		rateLoad.click();
		log.info("Load id" + " "  + loadid + " " + "is Rated successfully" );
		Thread.sleep(8000);
		WebDriverWait wait = new WebDriverWait(driver, 25);
		WebElement eles = wait.until(ExpectedConditions.elementToBeClickable(setToPlannedLoad));
		eles.click();
		log.info("Load id" + " "  + loadid + " " + "is set to planned successfully" );
		Thread.sleep(4000);		 
		getWait().until(ExpectedConditions.visibilityOf(tenderOperations));
		Thread.sleep(2000);
		tenderOperations.click();
		Thread.sleep(2000);
		getWait().until(ExpectedConditions.visibilityOf(acceptLoad));
		Thread.sleep(2000);
		acceptLoad.click();
		Thread.sleep(2000);
//		getWait().until(ExpectedConditions.visibilityOf(submitLoad));
//		Thread.sleep(2000);
//		submitLoad.click();
			
		log.info("Load id" + " "  + loadid + " " + "is Tender Accepted" );
	}
	          		
}
