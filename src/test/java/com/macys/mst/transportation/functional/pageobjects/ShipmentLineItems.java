package com.macys.mst.transportation.functional.pageobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jbehave.core.annotations.ToContext.RetentionLevel;
import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macys.mst.artemis.selenium.SeUiContextBase;
import com.macys.mst.transportation.functional.configuration.Context;
import com.macys.mst.transportation.functional.util.CommonUtils;

import net.thucydides.core.annotations.findby.By;

public class ShipmentLineItems extends BasePage {

//	@FindBy(id = "SHIPMENT_UDA_SPECIALIZEDROUTINGREQUEST")
	//public WebElement SRR;
	@FindBy(xpath = "//div[@id='DM_Viewport']//tbody[2]/tr")
	public List<WebElement> resultTable;
	@FindBy(name = "selectedLine")
	public WebElement selectPoCheckBox;
	@FindBy(xpath = "//div[@id='null_ButtonDiv']//span[text()='Done']")
	public WebElement doneButton;
	@FindBy(xpath = "//table[@id='vpMsgTbl']//tr/td")
	public WebElement shipmentMessage;
	
	//@FindBy(xpath = "//input[@id='SHIPMENT_WEIGHT']")
	//public WebElement shipmentWeight;
	//@FindBy(xpath = "//input[@id='SHIPMENT_VOLUME']")
	//public WebElement shipmentVolume;
	//@FindBy(xpath = "//input[@id='SHIPMENT_CASE_COUNT']")
	//public WebElement shipmentCaseCount;
	//@FindBy(xpath = "//span[1][contains(text(),'Details')]")   
    //public WebElement shipmentDetailTab;
	
    //@FindBy(id = "SHIPMENT_UDA_SPECIALIZEDROUTINGREQUEST")  BIZ env
        @FindBy(id = "SHIPMENT_UDA_SPCL_RTE_RQST")
        public WebElement SRR;
        @FindBy(id = "SHIPMENT_UDA_SSR_ADVNCE_NOTICE")
		public WebElement ADV_NOTICE;
		@FindBy(id = "SHIPMENT_UDA_SSR_DRIVER_VENDOR_LOAD")
		public WebElement VEN_LOAD;
		@FindBy(id = "SHIPMENT_UDA_SSR_PALLETIZE")
		public WebElement SSR_PALLETIZE;
		@FindBy(id = "SHIPMENT_UDA_SSR_APPNT")
		public WebElement SSR_APPNT;
		@FindBy(id = "SHIPMENT_UDA_SSR_DROPLIVE")
		public WebElement SSR_DROPLIVE;

		@FindBy(id = "SHIPMENT_UDA_SSR_TEMP_EQP_R0")
		public WebElement SSR_TEMP;

		@FindBy(id = "SHIPMENT_UDA_SSR_VNDR_PCKUP")
		public WebElement SSR_VNDR_PCKUP;
    //@FindBy(xpath = "//input[@id='SHIPMENT_WEIGHT']")  BIZ env
        @FindBy(xpath = "//input[@id='DELIVERY_LINE_WEIGHT0']")
        public WebElement shipmentWeight;
        //@FindBy(xpath = "//input[@id='SHIPMENT_VOLUME']")  BIZ env
        @FindBy(xpath = "//input[@id='DELIVERY_LINE_VOLUME0']")
        public WebElement shipmentVolume;
        //@FindBy(xpath = "//input[@id='SHIPMENT_CASE_COUNT']")  BIZ env
        @FindBy(xpath = "//input[@id='DELIVERY_LINE_CASE_COUNT0']")
        public WebElement shipmentCaseCount;
        
  	 @FindBy(xpath = "//span[1][contains(text(),'Details')]")   
        public WebElement shipmentDetailTab;
		@FindBy(xpath = "//table/tbody/tr/td[3]/table/tbody/tr/td[1]/span/span")
        public WebElement drop;
        @FindBy(xpath ="//*[@id='shellLogoutAction']")
                public WebElement logout;

	
	// multiple shipment creation
        
        @FindBy(xpath = "//div[@id='DM_Viewport']//tbody[2]//td[4]//a")
    	private WebElement poResult;
    	@FindBy(xpath = "//div[@id='DM_Viewport']//tbody[2]//td[3]")
    	private WebElement buildShipment;
	
    	List <String> totalShipmentID = new ArrayList();
    	
	private static final Logger logger = LoggerFactory.getLogger(ShipmentLineItems.class);

	/*public void enterShipmentDetails(List<Map<String, String>> row) {
		

		getRequiredAction();
		SeUiContextBase.switchtoframe("appFrame");

		for (Map<String, String> map : row) {
			for (Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				switch (key) {

				case "SRR":
					Select specializedRR = new Select(SRR);
					SeUiContextBase.selectOptionInDropdown(specializedRR, value);
					logger.info("SRR slected" + value);
					break;

				case "EarliestAvailable":
					
					 * Since the data is already available based on Po.It's not read from Example
					 * table
					 break;
				case "LatestAvailable":
					break;
							 * Since the data is already available based on Po.It's not read from Example
							 * table
							 
				case "EarliestDelivery":
					break;
							 * Since the data is already available based on Po.It's not read from Example
							 * table
							 
				case "LatestDelivery":
					break;
							 * Since the data is already available based on Po.It's not read from Example
							 * table
							 
				case "Origin":
					break;
							 * Since the data is already available based on Po.It's not read from Example
							 * table
							 
				case "Destination":
					break;
							 * Since the data is already available based on Po.It's not read from Example
							 * table
							 
				case "SLM":
					break;
							 * Since the data is already available based on Po.It's not read from Example
							 * table
							 
				case "LineItem":
					int rowsize = resultTable.size();
					for (int i = 0; i < rowsize; i++) {
                        Not Sure about the SeUiContextBase Builtin methods to handle Webtable
						List<WebElement> tds = resultTable.get(i).findElements(By.tagName("td"));
						String lineItem = tds.get(3).findElement(By.tagName("span")).getText().replaceAll(" ", "");
						if (lineItem.equals(value)) {

							getRequiredAction().clickElement(tds.get(0).findElement(By.tagName("input")));
							logger.info("Selected mentioned LineItem");
						}
					}

					break;
				default:
					logger.info("Non Shipment attribute found, ignoring");
					break;

				}
			}

		}

	}*/
	public void enterShipmentDetails(int ScenarioNumber,int weight,int Volume,int Casecount,String SRR1) throws InterruptedException {
		getRequiredAction();
		SeUiContextBase.switchtoframe("appFrame");
		String newWeight = String.valueOf(weight) ;
		String newVolume = String.valueOf(Volume) ;
		String newCasecount = String.valueOf(Casecount) ;
		
		Thread.sleep(4000);
		Select specializedRR = new Select(SRR);
		SeUiContextBase.selectOptionInDropdown(specializedRR, SRR1);
		SeUiContextBase.sendkeys(shipmentWeight, newWeight);
		SeUiContextBase.sendkeys(shipmentVolume, newVolume);
		SeUiContextBase.sendkeys(shipmentCaseCount, newCasecount);
		logger.info("Shipemnt creation data" + " " +SRR1+ " " + newWeight + " "+ newVolume+ " " + newCasecount);
		
	}
public void enterSSRvalues(String ADVA_NOTICE,String VEND_LOAD,String SSR_ta_PALLETIZE,String SSR_ta_APPNT, String SSR_ta_DROPLIVE,String SSR_ta_TEMP, String SSR_ta_VNDR_PCKUP ) throws Exception {
		
		//stepsContext.resetScenario();
		
		
			getRequiredAction();
			Select advance_Notice = new Select(ADV_NOTICE);
			SeUiContextBase.selectOptionInDropdown(advance_Notice, ADVA_NOTICE);
			logger.info("Selected advance_Notice");
			Select VENDOR_LOAD = new Select(VEN_LOAD);
			SeUiContextBase.selectOptionInDropdown(VENDOR_LOAD, VEND_LOAD);
			logger.info("Selected VENDOR_LOAD");
			Select SSR_PALLETIZE_step = new Select(SSR_PALLETIZE);
			SeUiContextBase.selectOptionInDropdown(SSR_PALLETIZE_step, SSR_ta_PALLETIZE);
			logger.info("Selected SSR_PALLETIZE");
			Select SSR_Appointment = new Select(SSR_APPNT);
			SeUiContextBase.selectOptionInDropdown(SSR_Appointment, SSR_ta_APPNT);
			logger.info("Selected SSR_Appointment");
			Select SSR_DROPLIVE_step = new Select(SSR_DROPLIVE);
			SeUiContextBase.selectOptionInDropdown(SSR_DROPLIVE_step, SSR_ta_DROPLIVE);
			logger.info("Selected SSR_DROPLIVE_step");
			Select SSR_Temperature = new Select(SSR_TEMP);
			SeUiContextBase.selectOptionInDropdown(SSR_Temperature, SSR_ta_TEMP);
			logger.info("Selected SSR_Temperature");
			Select SSR_VNDR_PCKUP_step = new Select(SSR_VNDR_PCKUP);
			SeUiContextBase.selectOptionInDropdown(SSR_VNDR_PCKUP_step, SSR_ta_VNDR_PCKUP);
		logger.info("Selected SSR_VNDR_PCKUP");
		

}
	public List<String> enterShipmentDetailsMulptiple(ExamplesTable values) throws InterruptedException {
		for (Map<String, String> row : values.getRows()) {
			
			getWait().until(ExpectedConditions.visibilityOf(poResult));
			
			/*Assert.assertEquals(poResult.getText(), ponumber.getAttribute("value"));*/
		try {
			getRequiredAction();
			if (SeUiContextBase.isElementEnabled(buildShipment))
				getRequiredAction().clickElement(buildShipment);
		} catch (Exception e) {
			CommonUtils.doJbehavereportConsolelogAndAssertion("BulidShipment not enabled.WARNING!!!!!!!!Can't screate the Shipment", "", true);
		}

		Thread.sleep(3000);
	
			
			getRequiredAction();
			SeUiContextBase.switchtoframe("appFrame");
			String newWeight = String.valueOf(row.get("weight")) ;
			String newVolume = String.valueOf(row.get("Volume")) ;		
			String newCasecount = String.valueOf(row.get("Casecount")) ;
			String newSRR = String.valueOf(row.get("SRR")) ;
			
			 logger.info("FIRST ITERATION"   + newWeight + " "+ newVolume+ " " + newCasecount+ " " + newSRR);
			 logger.info("All VALUES"   + values.getRows());
			//
			
			 getRequiredAction();
			    SeUiContextBase.sendkeys(shipmentWeight, newWeight);
			        SeUiContextBase.sendkeys(shipmentVolume, newVolume);
			    SeUiContextBase.sendkeys(shipmentCaseCount, newCasecount);
			        logger.info("Shipemnt creation data"   + newWeight + " "+ newVolume+ " " + newCasecount);
			//
			
		
			 driver.switchTo().defaultContent();
			    driver.switchTo().frame("appFrame");
			    Thread.sleep(6000);
			     
			        getWait().until(ExpectedConditions.visibilityOf(shipmentDetailTab));
	
			        getRequiredAction().clickElement(shipmentDetailTab);
			        
			Thread.sleep(4000);
	
			
			getRequiredAction();
	        Select specializedRR = new Select(SRR);
	     
	        SeUiContextBase.selectOptionInDropdown(specializedRR, row.get("SRR"));
	        
	        logger.info("LAST ITERATION"   + newWeight + " "+ newVolume+ " " + newCasecount+ " " + newSRR);
			
			
	        getWait().until(ExpectedConditions.visibilityOf(doneButton));
			getRequiredAction().clickElement(doneButton);
			logger.info("Shipment Created");
			getWait(60).until(ExpectedConditions.titleContains("JDA : Purchase Order Manager"));
			String Message[]=shipmentMessage.getText().split(" ");
			logger.info(shipmentMessage.getText());
			logger.info(Message[1]);
			totalShipmentID.add(Message[1]);
		}
		return totalShipmentID;
	}
	 public void enterShipmentDetailsvalues(ExamplesTable values)throws InterruptedException {
	        Thread.sleep(6000);
	       
	        logger.info("select value");
	        //SeUiContextBase.switchtoframe("appFrame");
	        for (Map<String, String> row : values.getRows()) {
				
	        getRequiredAction();
	    SeUiContextBase.sendkeys(shipmentWeight, row.get("weight"));
	        SeUiContextBase.sendkeys(shipmentVolume, row.get("Volume"));
	    SeUiContextBase.sendkeys(shipmentCaseCount, row.get("Casecount"));
	        logger.info("Shipemnt creation data"   + row.get("weight") + " "+ row.get("Volume")+" " +row.get("Casecount"));
	       
	    }}
	public void createShipment() {
		getWait().until(ExpectedConditions.visibilityOf(doneButton));
		getRequiredAction().clickElement(doneButton);
	
	}

	public String getShipmentId() {
		getWait(60).until(ExpectedConditions.titleContains("JDA : Purchase Order Manager"));
		String Message[]=shipmentMessage.getText().split(" ");
		logger.info(shipmentMessage.getText());
		logger.info(Message[1]);
		return Message[1];

	}
	public void enterSRRDetails(String SRRValue)throws InterruptedException {
        Thread.sleep(6000);
        getRequiredAction();
        Select specializedRR = new Select(SRR);
        //specializedRR.SelectByVisibleText(SRRValue);
        SeUiContextBase.selectOptionInDropdown(specializedRR, SRRValue);
    
    }
	public void enterSRRDetails(ExamplesTable values)throws InterruptedException {
        Thread.sleep(6000);
        
        logger.info("select srr");
        //SeUiContextBase.switchtoframe("appFrame");
        for (Map<String, String> row : values.getRows()) {
        getRequiredAction();
        Select specializedRR = new Select(SRR);
        logger.info(row.get("SRR"));
        //specializedRR.SelectByVisibleText(SRRValue);
        SeUiContextBase.selectOptionInDropdown(specializedRR, row.get("SRR"));
        
        logger.info("Shipemnt creation data" + " " +row.get("SRR")); 
        } 
    }
	public void enterSSRvalues(ExamplesTable values) throws Exception {
		
		//stepsContext.resetScenario();
		
		for (Map<String, String> row : values.getRows()) {
			getRequiredAction();
			Select advance_Notice = new Select(ADV_NOTICE);
			SeUiContextBase.selectOptionInDropdown(advance_Notice, row.get("ADV_NOTICE"));
			logger.info("Selected advance_Notice");
			Select VENDOR_LOAD = new Select(VEN_LOAD);
			SeUiContextBase.selectOptionInDropdown(VENDOR_LOAD, row.get("VEN_LOAD"));
			logger.info("Selected VENDOR_LOAD");
			Select SSR_PALLETIZE_step = new Select(SSR_PALLETIZE);
			SeUiContextBase.selectOptionInDropdown(SSR_PALLETIZE_step, row.get("SSR_PALLETIZE"));
			logger.info("Selected SSR_PALLETIZE");
			Select SSR_Appointment = new Select(SSR_APPNT);
			SeUiContextBase.selectOptionInDropdown(SSR_Appointment, row.get("SSR_APPNT"));
			logger.info("Selected SSR_Appointment");
			Select SSR_DROPLIVE_step = new Select(SSR_DROPLIVE);
			SeUiContextBase.selectOptionInDropdown(SSR_DROPLIVE_step, row.get("SSR_DROPLIVE"));
			logger.info("Selected SSR_DROPLIVE_step");
			Select SSR_Temperature = new Select(SSR_TEMP);
			SeUiContextBase.selectOptionInDropdown(SSR_Temperature, row.get("SSR_TEMP"));
			logger.info("Selected SSR_Temperature");
			Select SSR_VNDR_PCKUP_step = new Select(SSR_VNDR_PCKUP);
			SeUiContextBase.selectOptionInDropdown(SSR_VNDR_PCKUP_step, row.get("SSR_VNDR_PCKUP"));
		logger.info("Selected SSR_VNDR_PCKUP");
		
}
}
	  public void navigateShipmentDetails()throws InterruptedException {
	        Thread.sleep(6000);
	    System.out.println(shipmentDetailTab);
	    driver.switchTo().defaultContent();
	    driver.switchTo().frame("appFrame");
	    Thread.sleep(6000);
	     //driver.switchTo().window("appWindow");
	        getWait().until(ExpectedConditions.visibilityOf(shipmentDetailTab));
	        //getWait().until(ExpectedConditions.visibilityOf(shipmentDetailTab));
	        getRequiredAction().clickElement(shipmentDetailTab);
	        
	    }
	  public void logoutFomApplication()throws InterruptedException {
	        Thread.sleep(6000);
	        
	        logger.info("select logout");
	        driver.switchTo().defaultContent();
	        Thread.sleep(6000);
	        //SeUiContextBase.switchtoframe("appFrame");
	        getRequiredAction();
	        getWait(15).until(ExpectedConditions.visibilityOf(drop));
	        getRequiredAction().clickElement(drop);

	 

	        getWait(15).until(ExpectedConditions.visibilityOf(logout));
	        getRequiredAction().clickElement(logout);
	        
	        //SeUiContextBase.selectOptionInDropdown(specializedRR, "Logout");
	        
	        logger.info("Loggedout"); 
	        
	    }
	  public void enterShipmentDetailsvalues(String WeightValue,String Volumevalue,String Casecountvalue)throws InterruptedException {
	        Thread.sleep(6000);
	       
	        //SeUiContextBase.switchtoframe("appFrame");
	        getRequiredAction();
	    SeUiContextBase.sendkeys(shipmentWeight, WeightValue);
	        SeUiContextBase.sendkeys(shipmentVolume, Volumevalue);
	    SeUiContextBase.sendkeys(shipmentCaseCount, Casecountvalue);
	        logger.info("Parameters used for shipment creation"+ " "  + WeightValue + " "+ Volumevalue+ " " + Casecountvalue);
	       
	    }
	
}
