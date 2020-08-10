package com.macys.mst.transportation.functional.util;


import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macys.mtp.dataprovider.DataRow;
import com.macys.mtp.reportsutils.Log;

public class CustomDataProviderBuilder {
    List<String> withindexrowList = null;
    List<String> headerColumn = null;
    Map<String, String> dataSet = null;
    Map<String, String> responseSet = null;
    private static final Logger logger = LoggerFactory.getLogger(CustomDataProviderBuilder.class);;
    public static final String DELIMITER_ESCAPED = "\\|";
    
    private String readDataFiles(Map<String, String> row){
    	StringBuffer keys =  new StringBuffer("|");
    	StringBuffer values = new StringBuffer("|");
    	
    	for(Map.Entry<String, String> entry: row.entrySet()) {
    		keys.append(entry.getKey()).append("|");
    		values.append(entry.getValue()).append("|");
    	}    	
    	return keys.append(System.getProperty("line.separator")).append(values).toString();    	
    	//return Stream.of(keys.toString());    	
    }

    public void createHeaderAndRowWithIndex(Map<String, String> row) {

        List<String> rowList = new ArrayList<>();        
        try {
        	rowList = new BufferedReader(new StringReader(readDataFiles(row))).lines().collect(Collectors.toList());
            headerColumn = listOfHeaderColumns(rowList.get(0));
            int headerColumnCount = headerColumn.size();
            
            rowList.remove(0);
            if (rowList.isEmpty())
                throw new RuntimeException("There is no test data passed in given step");
            withindexrowList = addindextorow(rowList);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> listOfHeaderColumns(String headerRow) {
        String headerRowithindex = addindextorowheader(headerRow);
        return Arrays.asList(headerRowithindex.split(DELIMITER_ESCAPED));
    }

    /**
     * Removes comment rows and inserts a row number in the first testdata column
     */
    private List<String> addindextorow(List<String> rowList) {
        int counter = 0;
        int indexCounter = 0;
        ListIterator<String> iterator = rowList.listIterator();
        while (iterator.hasNext()) {
            String row = iterator.next();
            if (row.startsWith("//")) {
                iterator.remove();
            } else {
                rowList.set(counter, String.valueOf(indexCounter + row));
                counter++;
            }
            indexCounter++;
        }
        return rowList;
    }

    private String addindextorowheader(String headerRow) {
        return "index" + headerRow;
    }


    private Object[][] copyDataRowtoArray(List<DataRow> dataRows) {
        int rowCount = dataRows.size();
        Object[][] dataobj = null;
        if (rowCount > 0) {
            dataobj = new Object[rowCount][1];
            for (int i = 0; i < rowCount; i++) {
                dataobj[i][0] = dataRows.get(i);
            }

        } else {
            throw new RuntimeException("Data in data file is corrupted ");
        }
        return dataobj;
    }

    public List<String> getWithindexrowList() {
        return withindexrowList;
    }

    public void setWithindexrowList(List<String> withindexrowList) {
        this.withindexrowList = withindexrowList;
    }

    public List<String> getHeaderColumn() {
        return headerColumn;
    }

    public void setHeaderColumn(List<String> headerColumn) {
        this.headerColumn = headerColumn;
    }

    public Map<String, String> getDataSet() {
        return dataSet;
    }

    public void setDataSet(Map<String, String> dataSet) {
        this.dataSet = dataSet;
    }

    public Map<String, String> getResponseSet() {
        return responseSet;
    }

    public void setResponseSet(Map<String, String> responseSet) {
        this.responseSet = responseSet;
    }
}
