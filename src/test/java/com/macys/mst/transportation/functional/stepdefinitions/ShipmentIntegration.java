package com.macys.mst.transportation.functional.stepdefinitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext.RetentionLevel;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.context.StepsContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.macys.mst.artemis.testNg.LocalDriverManager;
import com.macys.mst.transportation.functional.configuration.Context;
import com.macys.mst.transportation.functional.pageobjects.BasePage;
import com.macys.mst.transportation.functional.pageobjects.Home;
import com.macys.mst.transportation.functional.pageobjects.PurchaseOrderLineItems;
import com.macys.mst.transportation.functional.pageobjects.PurchaseOrderManagerPage;
import com.macys.mst.transportation.functional.pageobjects.ShipmentLineItems;
import com.macys.mst.transportation.functional.pageobjects.ShipmentManagerPage;
import com.macys.mst.transportation.functional.util.StepsDataStore;
import com.macys.mst.transportation.functional.util.UserType;
import com.macys.mtp.dataprovider.TestDataProvider;
import com.macys.mtp.reportsutils.StepLogger;
import com.macys.mst.transportation.functional.util.CustomTestDataProvider;
import io.qameta.allure.Step;
import com.macys.mtp.dataprovider.DataRow;

public class ShipmentIntegration extends BasePage {
	private static Logger log = Logger.getLogger(ShipmentIntegration.class.getName());
	
	
	ShipmentManagerPage shipmentManagerPage = PageFactory.initElements(driver, ShipmentManagerPage.class);
	Home home=PageFactory.initElements(driver, Home.class);
	PurchaseOrderLineItems purchaseOrderLineItems= PageFactory.initElements(driver, PurchaseOrderLineItems.class);
	ShipmentLineItems shipmentLineItems= PageFactory.initElements(driver, ShipmentLineItems.class);
	private StepsContext stepsContext;
	public String ShipmentID;

	 Map<String, String> multipleShipmentID = new HashMap<String, String>();
	 CustomTestDataProvider dataProvider = new CustomTestDataProvider();
	public ShipmentIntegration(StepsContext stepsContext) {
		this.stepsContext = stepsContext;
	}
	@Given("PO is Final Approved")
    @Step("PO is Final Approved")
	public void givenPOIsFinalApproved() {
	
		log.info("PO to FOM Integration has not performed.So hardcoding PONumber as of now"); 
		
	}

	@Given("the <PO> is integrated to JDAFOM")
	@Step("PO is integrated to JDAFOM")
	public void givenPOIsIntegratedToJDAFOM(@Named("PO") String po) throws Exception {
	
    
    home.searchForModule("Purchase Order Manager");
    purchaseOrderLineItems.PurchaseOrderLineItemsPageLoad();
    
    /*********To Select PO Created via PurchaseOrderIntegration.***********/
    
    
   //String PoNumber = (String) stepsContext.get(Context.PURCHASE_ORDER_MAP.name());
   //log.info("PO Number" + PoNumber);
    /****Reading PO From StoryFile***********/
    
    purchaseOrderLineItems.enterPONumber(po);
    purchaseOrderLineItems.clickGoButton();
    purchaseOrderLineItems.clickShipmentButton();
    log.info("PO is integrated to JDAFOM");
    
   	}

	
	@Then("Create a  multiple TL Shipments")
	@Step("Create a  multiple TL Shipments ")
	public List<String> thenCreateMultipleTLShipment(ExamplesTable values) throws InterruptedException {		
		List<String> shipment = new ArrayList();
		shipment = shipmentLineItems.enterShipmentDetailsMulptiple(values);		
		return shipment;		
}

	@Then("Shipment status changed to confirmed")
	@Step("Shipment status changed to confirmed")
	public void shipmentStatusChangedToConfirmed() throws InterruptedException {		
	    purchaseOrderLineItems.changeShipmentToConfirmed();	   
		}
	@Then("logout from FOM application dup")
	@Step("logout from FOM application dup")
	public void logoutFromFOM() throws InterruptedException {		
	    purchaseOrderLineItems.logoutFOM();	   
		}
	
	@Then("Create a TL Shipment by selecting other shipment values <weight> <Volume> <Casecount> <SRR>")    //@Step("Create a TL Shipment with $values")
    public void thenCreateATLShipmentBySelecting(@Named("weight") String weight,@Named("Volume") String Volume,@Named("Casecount") String Casecount, @Named("SRR") String SRR,@Named("ADV_NOTICE") String ADV_NOTICE,@Named("VEN_LOAD") String VEN_LOAD,@Named("SSR_PALLETIZE") String SSR_PALLETIZE,@Named("SSR_APPNT") String SSR_APPNT,@Named("SSR_DROPLIVE") String SSR_DROPLIVE,@Named("SSR_TEMP") String SSR_TEMP,@Named("SSR_VNDR_PCKUP") String SSR_VNDR_PCKUP) throws Exception,InterruptedException {
		stepsContext.resetScenario();
        log.info("other ship details page selection");
        shipmentLineItems.enterShipmentDetailsvalues(weight,Volume,Casecount);
        shipmentLineItems.navigateShipmentDetails();
        shipmentLineItems.enterSRRDetails(SRR);
        shipmentLineItems.enterSSRvalues(ADV_NOTICE,VEN_LOAD,SSR_PALLETIZE,SSR_APPNT,SSR_DROPLIVE,SSR_TEMP,SSR_VNDR_PCKUP);
        
        shipmentLineItems.createShipment();
        ShipmentID=shipmentLineItems.getShipmentId();
       
        stepsContext.put(Context.SHIPMENT_ID.name(),ShipmentID , RetentionLevel.SCENARIO);
        String ShipmentID_C = (String) stepsContext.get(Context.SHIPMENT_ID.name());
  
    } 

	@Then("Create a Shipment by selecting other shipment details $values")    //@Step("Create a TL Shipment with $values")
    public void thenCreateAShipmentBySelectingOtherShipmentDetails(ExamplesTable values) throws Exception {
       
        log.info("other ship details page selection");
        //for (Map<String, String> row : SRR.getRows()) {
            //System.out.println(SRR.getRows());
        for (Map<String, String> row : values.getRows()) {
			DataRow datarow = dataProvider.testdataProvider(row);   
        shipmentLineItems.enterShipmentDetailsvalues(values);
        shipmentLineItems.navigateShipmentDetails();
        log.info("navigated");
       
        log.info("srr page selection");
        //for (Map<String, String> row : SRR.getRows()) {
            //System.out.println(SRR.getRows());
           
        shipmentLineItems.enterSRRDetails(values);
        log.info("ssr selection");
        shipmentLineItems.enterSSRvalues(values);
  //  shipmentLineItems.enterShipmentDetails(row);
        shipmentLineItems.createShipment();
        ShipmentID=shipmentLineItems.getShipmentId();
       
        stepsContext.put(Context.SHIPMENT_ID.name(),ShipmentID , RetentionLevel.SCENARIO);
        String ShipmentID_C = (String) stepsContext.get(Context.SHIPMENT_ID.name());
       
    log.info("selected all shipment values");
    }}
	@Then("Create a TL Shipment by selecting other shipment values <weight> <Volume> <Casecount> <SRR> <TCID>")    //@Step("Create a TL Shipment with $values")
    public Map<String, String> thenCreateMultipleShipmentBySelecting(@Named("weight") String weight,@Named("Volume") String Volume,@Named("Casecount") String Casecount, @Named("SRR") String SRR, @Named("TCID") String TCID,@Named("ADV_NOTICE") String ADV_NOTICE,@Named("VEN_LOAD") String VEN_LOAD,@Named("SSR_PALLETIZE") String SSR_PALLETIZE,@Named("SSR_APPNT") String SSR_APPNT,@Named("SSR_DROPLIVE") String SSR_DROPLIVE,@Named("SSR_TEMP") String SSR_TEMP,@Named("SSR_VNDR_PCKUP") String SSR_VNDR_PCKUP) throws Exception,InterruptedException {
		stepsContext.resetScenario();
        shipmentLineItems.enterShipmentDetailsvalues(weight,Volume,Casecount);
        shipmentLineItems.navigateShipmentDetails();
        shipmentLineItems.enterSRRDetails(SRR);
        shipmentLineItems.enterSSRvalues(ADV_NOTICE,VEN_LOAD,SSR_PALLETIZE,SSR_APPNT,SSR_DROPLIVE,SSR_TEMP,SSR_VNDR_PCKUP);
        
        shipmentLineItems.createShipment();
        ShipmentID=shipmentLineItems.getShipmentId();
        multipleShipmentID.put(TCID, ShipmentID);
       
       // stepsContext.put(Context.SHIPMENT_ID.name(),ShipmentID , RetentionLevel.SCENARIO);
        //String ShipmentID_C = (String) stepsContext.get(Context.SHIPMENT_ID.name());
  return multipleShipmentID;
    } 
	
	@Then("Change the shipment status to confirmed")   
      @Step("Change the shipment status to confirmed")
    public void thenShipmentStatusChangedToConfirmed() throws InterruptedException {
       
        purchaseOrderLineItems.changeShipmentToConfirmed();      
        }
   
    @Then("logout from FOM application")
    @Step("logout from FOM application")
    public void thenlogoutFromFOMApplication() throws InterruptedException {       
        shipmentLineItems.logoutFomApplication();      
    }
    @Then("Create a Shipment by selecting other shipment details")    //@Step("Create a TL Shipment with $values")
    public void thenCreateAShipmentBySelectingOtherShipmentDetails(@Named("weight") String weight,@Named("Volume") String Volume,@Named("Casecount") String Casecount,@Named("SRR") String SRR,@Named("ADV_NOTICE") String ADV_NOTICE,@Named("VEN_LOAD") String VEN_LOAD,@Named("SSR_PALLETIZE") String SSR_PALLETIZE,@Named("SSR_APPNT") String SSR_APPNT,@Named("SSR_DROPLIVE") String SSR_DROPLIVE,@Named("SSR_TEMP") String SSR_TEMP,@Named("SSR_VNDR_PCKUP") String SSR_VNDR_PCKUP) throws InterruptedException,Exception {
		 stepsContext.resetScenario();
        log.info("other ship details page selection");
        shipmentLineItems.enterShipmentDetailsvalues(weight,Volume,Casecount);
        shipmentLineItems.navigateShipmentDetails();
        shipmentLineItems.enterSRRDetails(SRR);
        shipmentLineItems.enterSSRvalues(ADV_NOTICE,VEN_LOAD,SSR_PALLETIZE,SSR_APPNT,SSR_DROPLIVE,SSR_TEMP,SSR_VNDR_PCKUP);
        shipmentLineItems.createShipment();
        ShipmentID=shipmentLineItems.getShipmentId();
       
        stepsContext.put(Context.SHIPMENT_ID.name(),ShipmentID , RetentionLevel.SCENARIO);
        String ShipmentID_C = (String) stepsContext.get(Context.SHIPMENT_ID.name());
        
  
    } 
}
