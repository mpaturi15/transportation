package com.macys.mst.transportation.functional.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jbehave.core.model.ExamplesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macys.mst.transportation.functional.stepdefinitions.MIR201EndtoEndFlow;

public class PoDetails {
	//private static Logger log = Logger.getLogger(PoDetails.class.getName());
	
	
//	List<Map<String, String>> poHeaderDeatils = null;
	List<String> totalPONum = new ArrayList<String>();
	
	public PoDetails(List<Map<String, String>> poHeaderDeatils) {
		// TODO Auto-generated constructor stub
		
		
	}

	public List getPOnumber(List<Map<String, String>> poHeaderDeatils) {
		
		for(Map<String, String> poheader : poHeaderDeatils) {
			//
			totalPONum.add(poheader.get("PurchaseOrderNumber"));
					
		}
		for (int i=0; i<totalPONum.size();i++){
			System.out.println("totalPONum" + " " + totalPONum.get(i));
		}
		return totalPONum;
			 
		}
	
}
		
		
		
		
	

	


