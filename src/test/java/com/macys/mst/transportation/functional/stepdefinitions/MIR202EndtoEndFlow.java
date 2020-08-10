package com.macys.mst.transportation.functional.stepdefinitions;

import org.apache.log4j.Logger;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.ToContext.RetentionLevel;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.context.StepsContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.macys.mst.artemis.testNg.LocalDriverManager;
import com.macys.mst.transportation.functional.configuration.Context;
import com.macys.mst.transportation.functional.pageobjects.BasePage;
import com.macys.mst.transportation.functional.pageobjects.Home;
import com.macys.mst.transportation.functional.pageobjects.LoadAcceptPage;
import com.macys.mst.transportation.functional.pageobjects.MIR201EndToEndFlowPage;
import com.macys.mst.transportation.functional.pageobjects.PurchaseOrderLineItems;
import com.macys.mst.transportation.functional.util.StepsDataStore;
import com.macys.mst.transportation.functional.util.UserType;
import com.macys.mtp.dataprovider.TestDataProvider;
import com.macys.mtp.reportsutils.StepLogger;
import com.macys.mtp.runners.BaseServiceTest;

import cucumber.api.java.en.And;
import io.qameta.allure.Step;

public class MIR202EndtoEndFlow extends BasePage{

	private static Logger log = Logger.getLogger(MIR202EndtoEndFlow.class.getName());
	ShipmentIntegration shipmentIntegration;		
	Home home=PageFactory.initElements(driver, Home.class);
	PurchaseOrderLineItems purchaseOrderLineItems= PageFactory.initElements(driver, PurchaseOrderLineItems.class);
	LoadAcceptPage loadAcceptPage = PageFactory.initElements(driver, LoadAcceptPage.class);
	//LoadAcceptSteps loadAcceptSteps = PageFactory.initElements(driver, LoadAcceptSteps.class);
	private StepsContext stepsContext;
	private List shipment;
	public MIR202EndtoEndFlow(StepsContext stepsContext) {
		this.stepsContext = stepsContext;
		shipmentIntegration = new ShipmentIntegration(stepsContext);
	}
	List <String> shipmentID;
	List <String> totalShipmentID = new ArrayList();
	
	//Map<Integer, String> totalShipmentIDs = new HashMap<Integer,String>();
	
	
	
	@Given("a single Purchase Order is created in BUY with Final Approved and Distor Complete status for the selected Vendor User $values")
	public void givenASinglePurchaseOrderIsCreatedInBUYWithFinalApprovedAndDistorCompleteStatusForTheSelectedVendorUser(ExamplesTable values) throws Exception{
		
  PurchaseOrderIntegrationSteps purchaseOrderIntegrationSteps =new PurchaseOrderIntegrationSteps(stepsContext);
		purchaseOrderIntegrationSteps.poIntegration(values);
	}
	

	@Given("Vendor user has access to access to FOM application")
	
	public void givenVendorUserHasAccessToAccessToFOMApplication() {
		home.login(UserType.VENDORUSER);
	}

	@Given("FOM application is available")
	
	public void givenFOMApplicationIsAvailable() throws InterruptedException {

	    home.searchForModule("Purchase Order Manager");
	    purchaseOrderLineItems.PurchaseOrderLineItemsPageLoad();
	}

	@Given("<PO> is integrated on to FOM via Purchase Order Manager")
	
	public void givenPOIsIntegratedOnToFOMViaPurchaseOrderManager(@Named("PO") String po) throws Exception {
		   purchaseOrderLineItems.enterPONumber(po);
		    purchaseOrderLineItems.clickGoButton();
		    
		    log.info("PO is integrated to JDAFOM");
	}

	
	
	@Given("Build Shipment is enabled on the select PO for the vendor with $values")
	
	public void givenBuildShipmentIsEnabledOnTheSelectPOForTheVendorWith(ExamplesTable values) throws Exception{
		
		
		shipmentID=shipmentIntegration.thenCreateMultipleTLShipment(values);
		log.info("Total shipments created" );
		for (int i=0;i<shipmentID.size();i++) {			
			log.info(shipmentID.get(i) );		
		}		
		
	}
	@Given("multiple shipments are created for same division or destination DC in FOM")
	public void givenMultipleShipmentsAreCreatedForSameDivisionOrDestinationDCInFOM() throws Exception {
		 
		
		log.info("MULTIPLE SHIPMENTS CREATED" );
	}
	
	

	@Then("shipments are integrated into TMS successfully")
	@Step("shipments are integrated into TMS successfully")
	
	public void thenShipmentsAreIntegratedIntoTMSSuccessfully() throws Exception {
		
		LoadAcceptSteps loadAcceptSteps = new LoadAcceptSteps(stepsContext);
		loadAcceptSteps.userloggedintoJDAtransportationManager();
		loadAcceptSteps.userclicksonTransportationSmartbench();
		// stub 
		List<String> temp = new ArrayList();
		int po1 = 2874;
		int po2 = 2872;
		 String str1 = Integer.toString(po1); 
		 String str2 = Integer.toString(po2);
		    
		temp.add(str1);
		temp.add(str2);
		loadAcceptSteps.usersearchformultipleshipmentId(temp);
		//
		//loadAcceptSteps.usersearchformultipleshipmentId(shipmentID);
		
		loadAcceptSteps.userselecttheshipmentIddisplayed();
		loadAcceptSteps.userclicksonAssignloadbutton();
		loadAcceptSteps.usersearchesthenewloadid();	
	}
	
	@Then("a new single load is assigned to both shipments")

	public void givenANewSingleLoadIsAssignedToBothShipments() {
		
	 
	}

	@When("a load is tendered and accepted in TMS")
	
	public void whenALoadIsTenderedAndAcceptedInTMS() {
	  // PENDING
	}

	@Then("a single appointment is created successfully in FLO")
	
	public void thenASingleAppointmentIsCreatedSuccessfullyInFLO() {
	  
	}
	
}
