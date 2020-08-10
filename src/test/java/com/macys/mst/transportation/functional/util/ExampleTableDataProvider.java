package com.macys.mst.transportation.functional.util;


import java.util.List;
import java.util.Map;
import org.jbehave.core.model.ExamplesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExampleTableDataProvider {
	
	public static  Map<String, String> getExampleRow(ExamplesTable table,String ScenarioNo) {
		
		final Logger logger = LoggerFactory.getLogger(ExampleTableDataProvider.class);
		List<Map<String, String>> list = table.getRows();
		Map<String, String> row = null;
	  try
       {
		for (Map<String, String> map : list) 
		{
		    for (Map.Entry<String, String> entry : map.entrySet())
		    {
		        String key = entry.getKey();
		        String value = entry.getValue();
		        if(key.equals("ScenarioNumber") &&value.equals(ScenarioNo))
		        		{
		        	       row=map;
		        	       return row;
		        		}
		        
		    }
         }
       }catch (Exception e) {
    	  logger.info("No match found for Scenario");
		  return null;
	 }
		return null;

	}
}
		
		
		
		
	

	


