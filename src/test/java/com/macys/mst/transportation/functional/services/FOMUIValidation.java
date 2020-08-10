package com.macys.mst.transportation.functional.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.ToContext.RetentionLevel;
import org.jbehave.core.steps.context.StepsContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;

import com.macys.mst.artemis.config.ConfigProperties;
import com.macys.mst.artemis.testNg.LocalDriverManager;
import com.macys.mst.transportation.functional.configuration.Context;
import com.macys.mst.transportation.functional.db.DBMethods;
import com.macys.mst.transportation.functional.db.SQLInventory;
import com.macys.mst.transportation.functional.util.CommonUtils;
import com.macys.mst.transportation.functional.util.PoDetails;
import com.macys.mtp.dataprovider.DataRow;
import com.macys.mtp.reportsutils.StepLogger;
import com.macys.mtp.utils.RestUtilities;

import org.openqa.selenium.support.FindBy;
import io.restassured.response.Response;

public class FOMUIValidation extends BaseService {
	private static Logger log = Logger.getLogger(FOMUIValidation.class.getName());
	private ConfigProperties propConfig = ConfigProperties.getInstance("config.properties");
	static LocalDriverManager localDriverManager = LocalDriverManager.getInstance();
	public static WebDriver driver = localDriverManager.getDriver();
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat distroSdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat distroSdfmin = new SimpleDateFormat("yyyy-MM-dd HH:MM AM");
	StepLogger stepLogger = new StepLogger();
	PoDetails podetails;
	private String ponum;
	private String canceldt;
	private String createdt;
	private String InDCdt;
	private String RTSdt;
	
public void validateshipmentdatedetails(StepsContext stepsContext) throws Exception, InterruptedException  {
	

	Map<String, String> poRequestMap = (Map<String, String>) stepsContext.get(Context.PO_REQUEST_MAP.name());
	
	String orderRefNoList = CommonUtils.getCommaSeperatedValue(poRequestMap.keySet());
	
	log.info("Retrieving PO header details for order ref = " + orderRefNoList + " after " + propConfig.getProperty("SQLSERVER_MAX_TIMEOUT_SEC") + " sec");
	
	CommonUtils.waitSec(Long.valueOf(propConfig.getProperty("SQLSERVER_MAX_TIMEOUT_SEC")));
	
	List<Map<String, String>> poHeaderDeatils = null;
	int retry = 1;
	boolean allPOsApproved = false;
	  
	while(retry <= Long.valueOf(propConfig.getProperty("SQLSERVER_MAX_RETRY")) && !allPOsApproved) {
		log.info("Retry count = "+retry);
		poHeaderDeatils = DBMethods.getData(String.format(SQLInventory.GET_PO_HEADER_DETAILS, orderRefNoList), SQLInventory.SCHEMA_SQLSERVER);
		for(Map<String, String> poheader : poHeaderDeatils) {
			
				canceldt = poheader.get("CancelDate");
				createdt  = poheader.get("InDistributionCentreDate");
				InDCdt = poheader.get("CreateDate");
				RTSdt = poheader.get("CreateDate");
				validateCreateDate(createdt);
				validateCancelDate(canceldt);
				validateINDCDate(InDCdt) ;
				validateRTSDate(createdt);
		}
		
	}	}
	
public void validateCreateDate(String createdt) {

	WebElement createdatefom = driver.findElement(By.xpath("//*[@id='DM_Viewport']/table/tbody[2]/tr[2]/td[6]/span"));
    String createdatefomUI = createdatefom.getText().trim();
    System.out.println(createdatefomUI);
	
     String createdateFOMDBval = distroSdfmin.format(createdt);
     System.out.println("formatted createdate" +createdateFOMDBval);
	// use org.junit.Assert for pass or fail

	CommonUtils.doJbehavereportConsolelogAndAssertion("Validating BUY and fom create date "+createdateFOMDBval,createdatefomUI,createdateFOMDBval.equals(createdatefomUI));

}
public void validateRTSDate(String createdt) {

	WebElement RTSdatefom = driver.findElement(By.xpath("//*[@id='DM_Viewport']/table/tbody[2]/tr[2]/td[6]/span"));
    String RTSdatefomUI = RTSdatefom.getText().trim();
    System.out.println(RTSdatefomUI);
	
     String RTSdateFOMDBval = sdf.format(createdt);
     System.out.println("formatted createdate" +RTSdateFOMDBval);
	// use org.junit.Assert for pass or fail

	CommonUtils.doJbehavereportConsolelogAndAssertion("Validating BUY and fom RTS date "+RTSdateFOMDBval,RTSdatefomUI,RTSdateFOMDBval.equals(RTSdatefomUI));

}

public void validateCancelDate(String canceldt) {

	WebElement canceldatefom = driver.findElement(By.xpath("//*[@id='DM_Viewport']/table/tbody[2]/tr/td[3]/span"));
    String canceldatefomUI = canceldatefom.getText().trim();
    System.out.println(canceldatefomUI);
	
     String canceldateFOMDBval = sdf.format(canceldt);
     System.out.println("formatted canceldate" +canceldateFOMDBval);
	// use org.junit.Assert for pass or fail

	CommonUtils.doJbehavereportConsolelogAndAssertion("Validating BUY and fom cancel date "+canceldateFOMDBval,canceldatefomUI,canceldateFOMDBval.equals(canceldatefomUI));

}

public void validateINDCDate(String InDCdt) {

	WebElement INDCdatefom = driver.findElement(By.xpath("//*[@id='SHIPMENT_PLANNED_EARLIEST_DELIVERY_DATE_SPANID']"));
    String InDCdatefomUI = INDCdatefom.getText().trim();
    System.out.println(InDCdatefomUI);
	
     String INDCdateFOMDBval = sdf.format(InDCdt);
     System.out.println("formatted Indcate" +INDCdateFOMDBval);
	// use org.junit.Assert for pass or fail

	CommonUtils.doJbehavereportConsolelogAndAssertion("Validating BUY and fom Indc date "+INDCdateFOMDBval,InDCdatefomUI,INDCdateFOMDBval.equals(InDCdatefomUI));

}
	public void validatePOStatus(String otherValue, String status) {

		
		String poNumber = (String) dataStore().get("PO_NBR");

		// use org.junit.Assert for pass or fail

		CommonUtils.doJbehavereportConsolelogAndAssertion("Validating BUY PO status for PO no " + poNumber, status, status.equals(otherValue));

	}

	
}
