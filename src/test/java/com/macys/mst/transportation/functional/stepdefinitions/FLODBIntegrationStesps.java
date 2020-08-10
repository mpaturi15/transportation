package com.macys.mst.transportation.functional.stepdefinitions;

import org.apache.log4j.Logger;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.ToContext.RetentionLevel;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.context.StepsContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.macys.mst.artemis.config.ConfigProperties;
import com.macys.mst.artemis.testNg.LocalDriverManager;
import com.macys.mst.transportation.functional.configuration.Context;
import com.macys.mst.transportation.functional.db.DBMethods;
import com.macys.mst.transportation.functional.db.SQLInventory;
import com.macys.mst.transportation.functional.pageobjects.BasePage;
import com.macys.mst.transportation.functional.pageobjects.FLODBIntegrationPage;
import com.macys.mst.transportation.functional.pageobjects.LoadAcceptPage;
import com.macys.mst.transportation.functional.util.StepsDataStore;
import com.macys.mst.transportation.functional.util.UserType;
import com.macys.mtp.dataprovider.TestDataProvider;
import com.macys.mtp.reportsutils.StepLogger;

import cucumber.api.Pending;
import cucumber.api.java.en.And;
import io.qameta.allure.Step;

public class FLODBIntegrationStesps extends BasePage{

	private static Logger log = Logger.getLogger(FLODBIntegrationStesps.class.getName());
	StepLogger stepLogger = new StepLogger();
	StepsDataStore dataStorage = StepsDataStore.getInstance();
	TestDataProvider dataProvider = new TestDataProvider();
//	WebDriver driver = LocalDriverManager.getInstance().getDriver();
	FLODBIntegrationPage flodbIntegrationPage = PageFactory.initElements(driver, FLODBIntegrationPage.class);
	ConfigProperties propConfig = ConfigProperties.getInstance("config.properties");
	private List<Map<String, String>> appointmentDetails;
	private StepsContext stepsContext;
	private String shipmentID = "12346752";
	
	public FLODBIntegrationStesps(StepsContext stepsContext) {
		this.stepsContext = stepsContext;
	}
		
	@Given("user logged into FLO DB")
	@Step("user logged into FLO DB")
	public void userLoggedIntoFLODB() throws Exception{	
		
		// Need to move out the below line to shipmentCreation file once shipment successfully created in FOM application.
		//stepsContext.put(Context.SHIPMENT_ID.name(), shipmentID, RetentionLevel.SCENARIO);
		String shipId = (String) stepsContext.get(Context.SHIPMENT_ID.name());
		
		log.info("shipId" + " " + shipId);
		appointmentDetails = DBMethods.getData(String.format(SQLInventory.GET_APPT_DETAILS, shipmentID), SQLInventory.SCHEMA_MPM);
		log.info("DB Connection established");
		
	}

	@Then("searched shipment ID in FB table")
	@Step("searched shipment ID in FB table")
	public void thenSearchedShipmentIDInFBTable() {
		log.info("APPOINTMENT DETAILS" + " " + appointmentDetails);
	  
	}

	@Then("retrieve appointment number")
	@Step("retrieve appointment number")
	public void thenRetrieveAppointmentNumber() {
		Map<String, String> Appointment_number = appointmentDetails.get(0);
		log.info("APPOINTMENT NUMBER" + " " + Appointment_number.get("APPT_NBR"));
		log.info("SHIPMENT NUMBER" + " " + Appointment_number.get("FB_NBR"));
		
	  }
}
