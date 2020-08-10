package com.macys.mst.transportation.functional.stepdefinitions;

import org.apache.log4j.Logger;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

public class MIR201EndtoEndFlow extends BasePage{

	private static Logger log = Logger.getLogger(MIR201EndtoEndFlow.class.getName());
	ShipmentIntegration shipmentIntegration;		
	Home home=PageFactory.initElements(driver, Home.class);
	PurchaseOrderLineItems purchaseOrderLineItems= PageFactory.initElements(driver, PurchaseOrderLineItems.class);
	LoadAcceptPage loadAcceptPage = PageFactory.initElements(driver, LoadAcceptPage.class);
	//LoadAcceptSteps loadAcceptSteps = PageFactory.initElements(driver, LoadAcceptSteps.class);
	Map<String, String> totalpo_num = new HashMap<String, String>();
	Map<String, String> totalShipment_Ids = new HashMap<String, String>();
	String PO1 = "TC01";
	String PO2 = "TC02";
	String firtPO = null;
	String SecondPO =null;
	String po1Dummy = "2700";
	String po2Dummy = "2701";
	private StepsContext stepsContext;
	private List shipment;
	public MIR201EndtoEndFlow(StepsContext stepsContext) {
		this.stepsContext = stepsContext;
		shipmentIntegration = new ShipmentIntegration(stepsContext);
	}
	
	@Given("data is available to create PO $values")
	public void givenDataIsAvailableToCreatePO(ExamplesTable values) throws Exception{
		PurchaseOrderIntegrationSteps purchaseOrderIntegrationSteps =new PurchaseOrderIntegrationSteps(stepsContext);
		totalpo_num = purchaseOrderIntegrationSteps.poIntegration(values);
//		for(Entry<String, String> ponum : totalpo_num.entrySet()) {
//			log.info("E2E PurchaseOrderNumber regression = " +ponum);
//			
//		}
		
	}
	

	@Given("PO is created successfully in BUY")
	public void givenPOIsCreatedSuccessfullyInBUY() {
//		List<Map<String, String>> poHeaderDeatils =  (List<Map<String, String>>) stepsContext.get(Context.PURCHASE_ORDER_MAP.name());
//		for(Map<String, String> poheader : poHeaderDeatils) {
//			log.info("E2E PurchaseOrderNumber = " +poheader.get("PurchaseOrderNumber"));
//			
//		}
		for(Entry<String, String> ponum : totalpo_num.entrySet()) {
			log.info("E2E PurchaseOrderNumber regression = " +ponum);
			
		}
		
	}
		
	@Given("PO is created successfully in BUY for Macys and Bloomingdale divisions")
	public void thenPOIsCreatedSuccessfullyInBUYForMacysAndBloomingdaleDivisions() {
		
		firtPO = totalpo_num.get(PO1);
		SecondPO = totalpo_num.get(PO2);
		
		log.info("FIRST and Second POS"+ " " + firtPO+ " "  + SecondPO);
		
		
		
	
		
	}
	
	@Given("FOM UI is available")	
	public void givenFOMUIIsAvailable() {
	  log.info("FOM UI is available");
	}
	@Given("Vendor is logged in successfully")
	public void givenVendorIsLoggedInSuccessfully() {
		home.login(UserType.VENDORUSER);
	}

	@Given("Macys user is logged in successfully")
	public void givenMacysUserIsLoggedInSuccessfully() {
		 home.login(UserType.MACYSUSER);
	}
	
	@Given("<PO> is integrated to JDAFOM")
	@Step("PO is integrated to JDAFOM")
	public void givenPOisintegratedtoJDAFOM(@Named("PO") String po) throws Exception  {
		shipmentIntegration.givenPOIsIntegratedToJDAFOM(po);
		
	}
	
	@Given("PO is integrated to JDAFOM Regression")
	@Step("PO is integrated to JDAFOM Regression")
	public void givenPOIsIntegratedToJDAFOMRegression(@Named("TCID") String TCID) throws Exception  {
		
		String po = totalpo_num.get(TCID);
		log.info("PO numbers for shipment creation");
		log.info(po);
	//	To be uncommented once the PO integration delay added in the code
		//shipmentIntegration.givenPOIsIntegratedToJDAFOM(po);
		shipmentIntegration.givenPOIsIntegratedToJDAFOM("2700");
		
	}
	
@Given("PO1 is integrated to JDAFOM")
	
	public void givenPO1IsIntegratedToJDAFOM() throws Exception {
	 
	    home.searchForModule("Purchase Order Manager");
	    purchaseOrderLineItems.PurchaseOrderLineItemsPageLoad();
		
		
		   purchaseOrderLineItems.enterPONumber(po1Dummy);
		    purchaseOrderLineItems.clickGoButton();
		    
		    log.info("PO1 is integrated to JDAFOM");
		    purchaseOrderLineItems.clickShipmentButton();
	}

@Given("PO2 is integrated to JDAFOM")

public void givenPO2IsIntegratedToJDAFOM() throws Exception {
	Thread.sleep(4000);
	 home.searchForModule("Purchase Order Manager");
	    purchaseOrderLineItems.PurchaseOrderLineItemsPageLoad();
	   purchaseOrderLineItems.enterPONumber(po2Dummy);
	    purchaseOrderLineItems.clickGoButton();
	    
	    log.info("PO2 is integrated to JDAFOM");
	    purchaseOrderLineItems.clickShipmentButton();
}
	@Then ("data is available to create Shipment")
	public void thenDataIsAvailableToCreateShipment() {
	  log.info("data is availabe for shipment");
	}

	
    @Given("Create a Shipment by selecting shipment values <weight> <Volume> <Casecount> <SRR>")
    public void thenCreateATLShipmentBySelecting(@Named("weight") String weight,@Named("Volume") String Volume,@Named("Casecount") String Casecount, @Named("SRR") String SRR,@Named("ADV_NOTICE") String ADV_NOTICE,@Named("VEN_LOAD") String VEN_LOAD,@Named("SSR_PALLETIZE") String SSR_PALLETIZE,@Named("SSR_APPNT") String SSR_APPNT,@Named("SSR_DROPLIVE") String SSR_DROPLIVE,@Named("SSR_TEMP") String SSR_TEMP,@Named("SSR_VNDR_PCKUP") String SSR_VNDR_PCKUP) throws InterruptedException,Exception  {
    	
    	shipmentIntegration.thenCreateATLShipmentBySelecting(weight,Volume,Casecount,SRR,ADV_NOTICE,VEN_LOAD,SSR_PALLETIZE,SSR_APPNT,SSR_DROPLIVE,SSR_TEMP,SSR_VNDR_PCKUP);   
    	             
    }
    
    @Given("Create multiple Shipment by selecting shipment values <weight> <Volume> <Casecount> <SRR> <TCID>")
    public void thenCreateMultipleShipmentBySelecting(@Named("weight") String weight,@Named("Volume") String Volume,@Named("Casecount") String Casecount, @Named("SRR") String SRR, @Named("TCID") String TCID,@Named("ADV_NOTICE") String ADV_NOTICE,@Named("VEN_LOAD") String VEN_LOAD,@Named("SSR_PALLETIZE") String SSR_PALLETIZE,@Named("SSR_APPNT") String SSR_APPNT,@Named("SSR_DROPLIVE") String SSR_DROPLIVE,@Named("SSR_TEMP") String SSR_TEMP,@Named("SSR_VNDR_PCKUP") String SSR_VNDR_PCKUP) throws InterruptedException,Exception  {
    	
    	totalShipment_Ids = shipmentIntegration.thenCreateMultipleShipmentBySelecting(weight,Volume,Casecount,SRR, TCID,ADV_NOTICE,VEN_LOAD,SSR_PALLETIZE,SSR_APPNT,SSR_DROPLIVE,SSR_TEMP,SSR_VNDR_PCKUP);   
    	String shipment_ID = totalShipment_Ids.get(TCID);
		log.info("Shipment ID created");
		log.info(shipment_ID);
    	
    }
    
    @Then("Create a TL Shipment by selecting other shipment details $values")
    public void thenCreateATLShipmentBySelectingOtherShipmentDetails(ExamplesTable values) throws Exception {
        shipmentIntegration.thenCreateAShipmentBySelectingOtherShipmentDetails(values);
        
        
    }
    
	@Given("Change the shipment status to confirmed")
	@Step("Change the shipment status to confirmed")
	public void givenChangeTheShipmentStatusToConfirmed() throws Exception  {
		shipmentIntegration.shipmentStatusChangedToConfirmed();
		log.info("Shipment status has been changed to Confirmed");
	}
    @Then("Change the shipment status from Unconfirmed to confirmed")
    @Step("Change the shipment status from Unconfirmed to confirmed")
    public void thenShipmentStatusChangedFromUnconfirmedToConfirmed() throws Exception  {
        shipmentIntegration.thenShipmentStatusChangedToConfirmed();
        log.info("change shipment status"); 
    }
    @Given("Create a Shipment by selecting other shipment details")
    public void thenCreateAShipmentBySelectingOtherShipmentDetails(@Named("weight") String weight,@Named("Volume") String Volume,@Named("Casecount") String Casecount,@Named("SRR") String SRR,@Named("ADV_NOTICE") String ADV_NOTICE,@Named("VEN_LOAD") String VEN_LOAD,@Named("SSR_PALLETIZE") String SSR_PALLETIZE,@Named("SSR_APPNT") String SSR_APPNT,@Named("SSR_DROPLIVE") String SSR_DROPLIVE,@Named("SSR_TEMP") String SSR_TEMP,@Named("SSR_VNDR_PCKUP") String SSR_VNDR_PCKUP) throws InterruptedException,Exception
    {
    	shipmentIntegration.thenCreateAShipmentBySelectingOtherShipmentDetails(weight,Volume,Casecount,SRR,ADV_NOTICE,VEN_LOAD,SSR_PALLETIZE,SSR_APPNT,SSR_DROPLIVE,SSR_TEMP,SSR_VNDR_PCKUP);
    	
    }

	  @Then("logout from FOM application successfully")
      @Step("logout from FOM application successfully")
    public void thenLogoutFromFOMApplicationSuccessfully() throws Exception  {
        shipmentIntegration.thenlogoutFromFOMApplication();
        log.info("Loggedout");
    }
	
	@Then("Shipment is integrated on to TMS successfully")
	public void thenShipmentIsIntegratedOnToTMSSuccessfully() throws InterruptedException {
		LoadAcceptSteps loadAcceptSteps = new LoadAcceptSteps(stepsContext);
		loadAcceptSteps.userloggedintoJDAtransportationManager();
		loadAcceptSteps.userclicksonTransportationSmartbench();
		loadAcceptSteps.usersearchforshipmentId();
		//String shipId = (String) stepsContext.get(Context.SHIPMENT_ID.name());
		
	}

	@Then("Load is created and assigned")
	public void thenLoadIsCreatedAndAssigned() throws InterruptedException{
		LoadAcceptSteps loadAcceptSteps1 = new LoadAcceptSteps(stepsContext);
		loadAcceptSteps1.userselecttheshipmentIddisplayed();
		loadAcceptSteps1.userclicksonAssignloadbutton();
		loadAcceptSteps1.usersearchesthenewloadid();				
		}

	@When("Load is tendered/accepted successfully in TMS")
	public void whenLoadIsTenderedacceptedSuccessfullyInTMS() throws InterruptedException{
		LoadAcceptSteps loadAcceptSteps2 = new LoadAcceptSteps(stepsContext);
		loadAcceptSteps2.usertendertheloadandacceptsit();
	}

	@Then("Appointment is created is created in FLO with unassigned status")	
	public void thenAppointmentIsCreatedIsCreatedInFLOWithUnassignedStatus() throws Exception {
		FLODBIntegrationStesps fLODBIntegrationStesps = new FLODBIntegrationStesps(stepsContext);
		fLODBIntegrationStesps.userLoggedIntoFLODB();
		fLODBIntegrationStesps.thenSearchedShipmentIDInFBTable();
		fLODBIntegrationStesps.thenRetrieveAppointmentNumber();
	}
	
}
