package com.macys.mst.transportation.functional.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.ToContext.RetentionLevel;
import org.jbehave.core.steps.context.StepsContext;
import org.testng.Assert;

import com.macys.mst.artemis.config.ConfigProperties;
import com.macys.mst.transportation.functional.configuration.Context;
import com.macys.mst.transportation.functional.db.DBMethods;
import com.macys.mst.transportation.functional.db.SQLInventory;
import com.macys.mst.transportation.functional.util.CommonUtils;
import com.macys.mst.transportation.functional.util.PoDetails;
import com.macys.mtp.dataprovider.DataRow;
import com.macys.mtp.reportsutils.StepLogger;
import com.macys.mtp.utils.RestUtilities;

import io.restassured.response.Response;

public class PurchaseOrderManagerService extends BaseService {
	private static Logger log = Logger.getLogger(PurchaseOrderManagerService.class.getName());
	private ConfigProperties propConfig = ConfigProperties.getInstance("config.properties");
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat distroSdf = new SimpleDateFormat("yyyy-MM-dd");
	StepLogger stepLogger = new StepLogger();
	PoDetails podetails;
	private String ponum;
	private String po_Stat_FA = null;
	
	 Map<String, String> multiplePONum = new HashMap<String, String>();

	public void validatePOStatus(String otherValue, String status) {

		
		String poNumber = (String) dataStore().get("PO_NBR");

		// use org.junit.Assert for pass or fail

		CommonUtils.doJbehavereportConsolelogAndAssertion("Validating BUY PO status for PO no " + poNumber, status, status.equals(otherValue));

	}

	public void populatePOBuyRequest(DataRow datarow, Map<String, String> row, List<Map<String, String>> vendorDetails,	List<List<Map<String, String>>> skuUpcList, StepsContext stepsContext) {
		
		stepsContext.resetScenario();
		
		Map<String, String> poRequestMap = new HashMap<String, String>();
		
		for (int i = 0; i < vendorDetails.size(); i++) {
			
			if(skuUpcList.get(i).isEmpty()) {
				log.info("SKU is empty, skipping");
				continue;
			}
			Calendar today = Calendar.getInstance();
			//today.add(Calendar.DATE, 10);
			String poRequest = datarow.getJsonString();
log.info("poRequest test" + poRequest);
			int orderReferenceNumber = CommonUtils.getNextOrderReferenceNumber();
			String uuTokenId = UUID.randomUUID().toString();

			poRequest = poRequest.replace("\"$orderReferenceNumber\"", String.valueOf(orderReferenceNumber)) 
								 .replace("$sourceSystemTokenId", uuTokenId)
					             .replace("$orderSource", row.get("SOURCE"));
					            // .replace("$shipDate", sdf.format(today.getTime()))
					            // .replace("$distroByDate", distroSdf.format(today.getTime()));
			// Setting up diff dates
			
			
			po_Stat_FA = row.get("PO_STAT");
			
			 switch (po_Stat_FA) {
             case "FinalApproved":
            	 today.add(Calendar.DATE, 10);
            	 poRequest = poRequest.replace("$shipDate", sdf.format(today.getTime()))
            			 .replace("$distroByDate", distroSdf.format(today.getTime()));
            	 
                 break;
             case "CancelPending":
            	 today.add(Calendar.DATE, -1);
            	 poRequest = poRequest.replace("$shipDate", sdf.format(today.getTime()))
            			 .replace("$distroByDate", distroSdf.format(today.getTime()));
                 break;
             case "Cancelled":
                 
                 break;
			 }  
			 //
			today.add(Calendar.DATE, 10);
			poRequest = poRequest.replace("$cancelDate", sdf.format(today.getTime()));
			
			if (!StringUtils.isBlank(row.get("VND_NBR"))) {
				poRequest = poRequest.replace("\"$vendorNumber\"", row.get("VND_NUMERIC_DESC"));
			} else {
				poRequest = poRequest.replace("\"$vendorNumber\"", vendorDetails.get(i).get("VND_NUMERIC_DESC"));
			}
			
			if (!StringUtils.isBlank(row.get("DEPT_NBR"))) {
				poRequest = poRequest.replace("\"$departmentNumber\"", row.get("DEPT_NBR"));
			} else {
				poRequest = poRequest.replace("\"$departmentNumber\"", vendorDetails.get(i).get("DEPT_NBR"));
			}
			
			if (!StringUtils.isBlank(row.get("LOC_NBR"))) {
				poRequest = poRequest.replace("\"$divisionId\"", row.get("LOC_NBR"));
			} else {
				poRequest = poRequest.replace("\"$divisionId\"", vendorDetails.get(i).get("LOC_NBR"));
			}
			
			if (!StringUtils.isBlank(row.get("SKU"))) {
				poRequest = poRequest.replace("\"$skuUpcNumber\"", row.get("SKU"));
			} else {
				poRequest = poRequest.replace("\"$skuUpcNumber\"", skuUpcList.get(i).get(0).get("SKU_UPC_NBR"));
			}
			
			if (!StringUtils.isBlank(row.get("POSTATUS"))) {
				poRequest = poRequest.replace("$poStatusnbr", row.get("POSTATUS"));
			} else {
				poRequest = poRequest.replace("$poStatusnbr", String.valueOf(30));
			}
			
			if (!StringUtils.isBlank(row.get("QTY"))) {
				poRequest = poRequest.replace("\"$orderQuantity\"", row.get("QTY"));
			} else {
				poRequest = poRequest.replace("\"$orderQuantity\"", String.valueOf(1000));
			}
			if (!StringUtils.isBlank(row.get("LOCATIONDATA"))) {
				poRequest = poRequest.replace("\"$locationData\"", row.get("LOCATIONDATA"));
			} else {
				poRequest = poRequest.replace("\"$locationData\"", String.valueOf(1000));
			}
						
			log.info("PO Request : " +poRequest);
			
			poRequestMap.put(String.valueOf(orderReferenceNumber), poRequest);
		}
//		Random rand = new Random(); 
//		  
//        // Generate random integers in range 0 to 999 
//        int i = rand.nextInt(1000);
		stepsContext.put(Context.PO_REQUEST_MAP.name(), poRequestMap, RetentionLevel.SCENARIO);

	}

	public void postCreatePurchaseOrder(String endPoint, StepsContext stepsContext) {

		Map<String, String> headers = new HashMap<>();
		//headers.put("x-fom-client", "CREATEPO");

		Map<String, String> poRequestMap = (Map<String, String>) stepsContext.get(Context.PO_REQUEST_MAP.name());
		log.info("Number of PO to be posted = "+poRequestMap.size());
		for (Map.Entry<String, String> entry : poRequestMap.entrySet()) {
			log.info("Posting PO for OrderReferenceNumber = " + entry.getKey());
			Response omresponse = RestUtilities.postRequestResponseWithHeaders(endPoint, entry.getValue(), headers);
			String responseStatus = String.valueOf(omresponse.getStatusCode());
			CommonUtils.doJbehavereportConsolelogAndAssertion("Validating BUY PO status for OrderReferenceNumber " + entry.getKey(), responseStatus, responseStatus.equals("200"));
		}
		
	}

	public Map<String, String> retreivePOHeaderDetails(StepsContext stepsContext, Map<String, String> row) throws Exception, InterruptedException {
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
				if(poheader.get("PurchaseOrderNumber").equals("0") || !poheader.get("StatusNumber").equals("30") ) {
					allPOsApproved = false;
					CommonUtils.waitSec(Long.valueOf(propConfig.getProperty("SQLSERVER_MAX_TIMEOUT_SEC")));
					break;
				}else {
					allPOsApproved = true;
				}
			}
			
			retry++;
		}
		
		 if (retry == Long.valueOf(propConfig.getProperty("SQLSERVER_MAX_RETRY")) || !allPOsApproved) {
             log.error("Maximum retry to check APPROVED status reached.");
             //stepLogger.logStepFailed(this.getClass());
             Assert.assertTrue(false, "Maximum retry to check APPROVED status reached.");
		 }else {
			stepsContext.put(Context.PURCHASE_ORDER_MAP.name(), poHeaderDeatils, RetentionLevel.SCENARIO);
			
			for(Map<String, String> poheader : poHeaderDeatils) {
				log.info("Approved PurchaseOrderNumber = " +poheader.get("PurchaseOrderNumber"));
				 
				multiplePONum.put((row.get("TCID")) , poheader.get("PurchaseOrderNumber"));
				
				//poheader.keySet();
				//log.info("FUll PO header details" + poheader.entrySet());
			}
			//podetails = new PoDetails(poHeaderDeatils);
			//podetails.getPOnumber(poHeaderDeatils);
			
			
		 }
		 return multiplePONum;
	
	}
	
}
