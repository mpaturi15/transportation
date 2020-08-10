/**
 * 
 */
package com.macys.mst.transportation.functional.stepdefinitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.ToContext.RetentionLevel;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.context.StepsContext;

import com.macys.mst.artemis.config.ConfigProperties;
import com.macys.mst.transportation.functional.configuration.Context;
import com.macys.mst.transportation.functional.db.DBMethods;
import com.macys.mst.transportation.functional.db.SQLInventory;
import com.macys.mst.transportation.functional.services.PurchaseOrderManagerService;
import com.macys.mst.transportation.functional.util.CommonUtils;
import com.macys.mst.transportation.functional.util.CustomTestDataProvider;
import com.macys.mtp.dataprovider.DataRow;
import com.macys.mtp.runners.BaseServiceTest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;

/**
 * @author bh13012
 *
 */
public class PurchaseOrderIntegrationSteps extends BaseServiceTest {

	private static Logger log = Logger.getLogger(PurchaseOrderIntegrationSteps.class.getName());
	private StepsContext stepsContext;
	CustomTestDataProvider dataProvider = new CustomTestDataProvider();
	PurchaseOrderManagerService purchaseOrderManagerService = new PurchaseOrderManagerService();
	ConfigProperties propConfig = ConfigProperties.getInstance("config.properties");
	 String po_num;
	 Map<String, String> totalponum = new HashMap<String, String>();
		

	public PurchaseOrderIntegrationSteps(StepsContext stepsContext) {
		this.stepsContext = stepsContext;
	}	
	
	@Given("A VENDOR and DEPT and LOC exist in FOM and BUY $values")
	@Step("A VENDOR and DEPT and LOC exist in FOM and BUY")
	@Description("A VENDOR and DEPT and LOC exist in FOM and BUY")
	public Map<String, String> poIntegration(ExamplesTable values) throws Exception {
		
		stepsContext.resetScenario();
		
		for (Map<String, String> row : values.getRows()) {
			DataRow datarow = dataProvider.testdataProvider(row);
			List<Map<String, String>> vendorDetails;
			List<List<Map<String, String>>> skuUpcList = new ArrayList<List<Map<String, String>>>();
			
			if (StringUtils.isBlank(row.get("VENDORDUNSNBR")) || StringUtils.isBlank(row.get("DEPT_NBR")) || StringUtils.isBlank(row.get("LOC_NBR")) || StringUtils.isBlank(row.get("VND_NBR"))
					|| StringUtils.isBlank(row.get("SKU"))) {
				log.info(String.format("All inputs not provided, fetching %s records from DB", propConfig.getProperty("NO_OF_PO")));
				vendorDetails = DBMethods.getData(String.format(SQLInventory.GET_VENDOR_DETAILS_COUNT_X, propConfig.getProperty("NO_OF_PO")), SQLInventory.SCHEMA_MPM);
			} else {
				log.info(String.format("Trying to fetch vendor details by input Provided as VENDORDUNSNBR %s and DEPT_NBR %s and LOC_NBR %s VND_NUMERIC_DESC %s ", row.get("VENDORDUNSNBR"),
						row.get("DEPT_NBR"), row.get("LOC_NBR"), row.get("VND_NUMERIC_DESC")));
				vendorDetails = DBMethods.getData(String.format(SQLInventory.GET_VENDOR_DETAILS, row.get("VENDORDUNSNBR"), row.get("DEPT_NBR"), row.get("LOC_NBR"), row.get("VND_NUMERIC_DESC")),
						SQLInventory.SCHEMA_MPM);
			}
			
			for (Map<String, String> vendorMap : vendorDetails) {
				List<Map<String, String>> skuList = DBMethods.getData(String.format(SQLInventory.GET_SKU_UPC, vendorMap.get("DEPT_NBR"), vendorMap.get("LOC_NBR"), vendorMap.get("VND_NUMERIC_DESC")),
						SQLInventory.SCHEMA_OCAINBIZA);

				skuUpcList.add(skuList);				
			}
			Random rand = new Random(); 
			  
	        // Generate random integers in range 0 to 999 
	        int i = rand.nextInt(1000); 
	    
			stepsContext.put(Context.VENDOR_DETAILS.name()+"_"+i, vendorDetails, RetentionLevel.SCENARIO);
			stepsContext.put(Context.ATTRIBUTE_DETAILS.name()+"_"+i, skuUpcList, RetentionLevel.SCENARIO);

			purchaseOrderManagerService.populatePOBuyRequest(datarow, row, vendorDetails, skuUpcList, stepsContext);
			purchaseOrderManagerService.postCreatePurchaseOrder(CommonUtils.getUrl(row.get("REQUESTURL")), stepsContext);
			totalponum = purchaseOrderManagerService.retreivePOHeaderDetails(stepsContext,row);
			
			
			
			//totalponum.put((row.get("TCID")), po_num);
			
//			log.info("PO numbers");
//			log.info(totalponum.get(row.get("TCID")));
			
//			for(Map<String, String> eachponumber[] : totalponum){
//				   log.info("Each PO "+ " " + eachponumber);
//				}
//			
		}
		return totalponum;
	}
	
}
