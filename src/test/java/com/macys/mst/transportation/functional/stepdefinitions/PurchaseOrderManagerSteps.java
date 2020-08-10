package com.macys.mst.transportation.functional.stepdefinitions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macys.mst.artemis.testNg.LocalDriverManager;
import com.macys.mst.transportation.functional.model.PORequest;
import com.macys.mst.transportation.functional.pageobjects.PurchaseOrderManagerPage;
import com.macys.mst.transportation.functional.services.PurchaseOrderManagerService;
import com.macys.mst.transportation.functional.util.CommonUtils;
import com.macys.mst.transportation.functional.util.CustomTestDataProvider;
import com.macys.mst.transportation.functional.util.StepsDataStore;
import com.macys.mtp.dataprovider.DataRow;
import com.macys.mtp.reportsutils.StepLogger;
import com.macys.mtp.runners.BaseServiceTest;
import com.macys.mtp.utils.RestUtilities;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PurchaseOrderManagerSteps extends BaseServiceTest {

	private static Logger log = Logger.getLogger(PurchaseOrderManagerSteps.class.getName());
	StepLogger stepLogger = new StepLogger();
	StepsDataStore dataStorage = StepsDataStore.getInstance();
	CustomTestDataProvider dataProvider = new CustomTestDataProvider();
	WebDriver driver = LocalDriverManager.getInstance().getDriver();
	PurchaseOrderManagerPage purchaseOrderManagerPage = PageFactory.initElements(driver, PurchaseOrderManagerPage.class);
	PurchaseOrderManagerService purchaseOrderManagerService = new PurchaseOrderManagerService();

	@When("PO is created successfully $values")
	@Step("PO is created successfully")
	@Description("Create PO BUY by OM Load Message Service")
	public void createPurchaseOrder(ExamplesTable values) throws Exception {

		for (Map<String, String> row : values.getRows()) {

			DataRow datarow = dataProvider.testdataProvider(row);
			String endPoint = CommonUtils.getUrl(row.get("requestUrl"));

			// test log to show datarow is populated
			log.info(endPoint);
			log.info(String.format("Payload File = %s, PID = %s, Operation = %s", datarow.getPayloadFileName(), datarow.getpId(),
					datarow.getOperationName()));
			log.info("Payload = " + datarow.getJsonString());

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			PORequest poRequest = gson.fromJson(datarow.getJsonString(), PORequest.class);

			int orderReferenceNumber = CommonUtils.getNextOrderReferenceNumber();
			String uuTokenId = UUID.randomUUID().toString();

			poRequest.getMessageHdr().setOrderReferenceNumber(orderReferenceNumber);
			poRequest.getMessageHdr().setSourceSystemTokenId(uuTokenId);

			Map<String, String> headers = new HashMap<>();
			//headers.put("x-whm-client", "Inventory");

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String payload = ow.writeValueAsString(poRequest);
			//String json = gson.toJson(poRequest); // or by gson

			Response omresponse = RestUtilities.postRequestResponseWithHeaders(endPoint, payload, headers);
			String responseStatus = String.valueOf(omresponse.getStatusCode());

			/*CommonUtils.doJbehavereportConsolelogAndAssertion("Validating PO POST response status for ref no " + String.valueOf(orderReferenceNumber),
					responseStatus, responseStatus.equals(datarow.getExpHttpRespCd()));
			stepLogger.logStepPassed(this.getClass());*/
		}
	}

	@When("user filters All Purchase Orders")
	public void filterAllPurchaseOrders() throws InterruptedException {
		purchaseOrderManagerPage.filterAllPurchaseOrders();
	}

	@Then("user clicked GO button")
	public void clickGoButton() throws InterruptedException {
		purchaseOrderManagerPage.clickGoButton();
	}

}
