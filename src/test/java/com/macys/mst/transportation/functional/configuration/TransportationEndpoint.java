package com.macys.mst.transportation.functional.configuration;

import com.macys.mst.artemis.config.FileConfig;

public class TransportationEndpoint {
	public static final String PO_POST_ENDPOINT = FileConfig.getInstance().getStringConfigValue("services.PurchaseOrderManagerService.CreatePurchaseOrder");

}
