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
import com.macys.mst.artemis.testNg.LocalDriverManager;
import com.macys.mst.transportation.functional.configuration.Context;
import com.macys.mst.transportation.functional.pageobjects.BasePage;
import com.macys.mst.transportation.functional.pageobjects.LoadAcceptPage;
import com.macys.mst.transportation.functional.util.StepsDataStore;
import com.macys.mst.transportation.functional.util.UserType;
import com.macys.mtp.dataprovider.TestDataProvider;
import com.macys.mtp.reportsutils.StepLogger;

import cucumber.api.Pending;
import cucumber.api.java.en.And;
import io.qameta.allure.Step;

public class LoadAcceptSteps extends BasePage {

	private static Logger log = Logger.getLogger(LoadAcceptSteps.class.getName());
	StepLogger stepLogger = new StepLogger();
	StepsDataStore dataStorage = StepsDataStore.getInstance();
	TestDataProvider dataProvider = new TestDataProvider();
	//WebDriver driver = LocalDriverManager.getInstance().getDriver();
	LoadAcceptPage loadAcceptPage = PageFactory.initElements(driver, LoadAcceptPage.class);
	private StepsContext stepsContext;
	private String shipmentID = "3670";
	
	public LoadAcceptSteps(StepsContext stepsContext) {
		this.stepsContext = stepsContext;
	}
	
	@Given ("Vendor user logged into JDA transportation Manager")
	@Step("Vendor user logged into JDA transportation Manager")
	public void userloggedintoJDAtransportationManager() throws InterruptedException {
		loadAcceptPage.TSMLogin(UserType.VENDORUSER);
	}
	
	@Given ("MTO user logged into JDA transportation Manager")
	@Step("MTO user logged into JDA transportation Manager")
	public void MtouserloggedintoJDAtransportationManager() throws InterruptedException {
		loadAcceptPage.TSMLogin(UserType.MTOUSER);
	}
	
	@When ("User clicks on Transportation Smart bench")
    @Step("User clicks on Transportation Smart bench")
	public void userclicksonTransportationSmartbench() throws InterruptedException {
		loadAcceptPage.navigatetoTranSM();
		
	}
	@Then ("user search for shipment Id")
	@Step ("user search for shipment Id")
	public void usersearchforshipmentId() throws InterruptedException {
		//stepsContext.put(Context.SHIPMENT_ID.name(), shipmentID, RetentionLevel.SCENARIO);
String shipId = (String) stepsContext.get(Context.SHIPMENT_ID.name());
			loadAcceptPage.searchShipment(shipId);	
			log.info("Shipment ID retrieved successfully");
	}
	@Then ("user search for multiple shipment Id")
	@Step ("user search for multiple shipment Id")
	public void usersearchformultipleshipmentId(List<String> totalShipmentID) throws InterruptedException {
		//stepsContext.put(Context.SHIPMENT_ID.name(), shipmentID, RetentionLevel.SCENARIO);
//String shipId = (String) stepsContext.get(Context.SHIPMENT_ID.name());
		for (String eachShipmID:totalShipmentID) {
			loadAcceptPage.searchShipment(eachShipmID);	
			log.info("Shipment ID retrieved successfully");
			
		}
			
	}
	@Then ("user select the shipmentId displayed")
	@Step ("user select the shipmentId displayed")
	public void userselecttheshipmentIddisplayed() throws InterruptedException {
		
			loadAcceptPage.selectShipmentID();
			loadAcceptPage.clickOnShipmentLegs();
			loadAcceptPage.searchShipmentforLoad();
		//	loadAcceptPage.removeLoad();					
			
	}
	@When ("user clicks on Assign load button")
	@Step ("user clicks on Assign load button")
	public void userclicksonAssignloadbutton() throws InterruptedException {
		
		loadAcceptPage.clickOnAssignToNewLoad();
		loadAcceptPage.closeAllPanels();
	}
	@Then ("user searches the new load id")
	@Step ("user searches the new load id")
	public void usersearchesthenewloadid() throws InterruptedException {
		loadAcceptPage.loadSearch();
	}
	@Then ("user tender the load and accepts it")
	@Step ("user tender the load and accepts it")
	public void usertendertheloadandacceptsit() throws InterruptedException {
		loadAcceptPage.loadAccept();
	}
}
