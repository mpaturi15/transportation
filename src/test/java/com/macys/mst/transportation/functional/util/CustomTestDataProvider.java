package com.macys.mst.transportation.functional.util;

import static com.macys.mtp.utils.TestCaseConstants.payloadFilePath;
import static com.macys.mtp.utils.TestCaseConstants.respDataFilePath;
import static com.macys.mtp.utils.TestCaseConstants.txtFileExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

import com.macys.mtp.TxtDataLoader;
import com.macys.mtp.dataprovider.DataRow;
import com.macys.mtp.reportsutils.Log;

public class CustomTestDataProvider {


    private static final Logger logger = LoggerFactory.getLogger(CustomTestDataProvider.class);
    public static final String DELIMITER_ESCAPED = "\\|";
    CustomDataProviderBuilder dataProviderBuilder=new CustomDataProviderBuilder();
    Map<String, Map<String, String>> fileRowMap = new HashMap<>();
    public CustomTestDataProvider() {
    }



    @DataProvider(name = "TestDataProvider", parallel = false)
    public DataRow testdataProvider(Map<String, String> row) {
        dataProviderBuilder.createHeaderAndRowWithIndex(row);
        List<DataRow> dataRows=buildDataRowList();
        Object[][] dataobj =copyDataRowtoArray(dataRows);
        return (DataRow) dataobj[0][0];
    }


    public List<DataRow> buildDataRowList() {

        DataRow dataRow=null;
        List<DataRow> dataRows=new ArrayList<>();
        String columnValues[]=null;
        int headerColumnCount=dataProviderBuilder.headerColumn.size();
        for (int row = 0; row < dataProviderBuilder.withindexrowList.size(); row++) {
            boolean skip=false;
            columnValues = dataProviderBuilder.withindexrowList.get(row).split(DELIMITER_ESCAPED);
            
         //   Log.info("Colum Values"+ columnValues );
            
            if (columnValues.length != headerColumnCount) {
                logger.error("Improperly formated test data at or near row: " + row + ", Row data = " + dataProviderBuilder.withindexrowList.get(row));
            }
                dataRow=new DataRow();
                    for (int column = 1; column < columnValues.length; column++) {
                        if(!skip) {
                            String key = dataProviderBuilder.headerColumn.get(column).trim();
                            String value = columnValues[column].trim();
                
                            switch (key) {
                                case "TCID":
                                    dataRow.setTestCaseId(value);
                                    break;
                                case "Execute":
                                    if (value.equalsIgnoreCase("no"))
                                        skip = true;
                                    break;
                                case "TestCaseName":
                                    dataRow.setTestCaseName(value);
                                    break;
                                case "PayloadFileName":
                                    if (value != null && !value.equals("")) {
                                        value = payloadFilePath + value + txtFileExtension;
                                        dataRow.setPayloadFileName(value);
                                        readPayloadResponeFile(value);

                                    } else {
                                        dataRow.setPayloadFileName("");
                                        dataRow.setpId("");
                                    }
                                    break;
                                case "PID":
                                    if (value != null && !value.equals("")) {
                                        dataRow.setJsonString(getRowFromMap(dataRow.getPayloadFileName(), value));
                                        dataRow.setpId(value);
                                    } else {
                                        dataRow.setJsonString("");
                                        dataRow.setpId("");
                                    }
                                    break;
                                case "ResponseFileName":
                                    if (value != null && !value.equals("")) {
                                        value = respDataFilePath + value + txtFileExtension;
                                        dataRow.setResponseFileName(value);
                                        readPayloadResponeFile(value);

                                    } else {
                                        dataRow.setResponseFileName("");
                                        dataRow.setrId("");
                                    }
                                    break;
                                case "RID":
                                    if (value != null && !value.equals("")) {
                                        dataRow.setExpJson(getRowFromMap(dataRow.getResponseFileName(), value));
                                        dataRow.setrId(value);
                                    } else {
                                        dataRow.setExpJson("");
                                        dataRow.setrId("");
                                    }
                                    break;
                                case "OperationName":
                                    if (value != null && !value.equals("")) {
                                        dataRow.setOperationName(value);
                                    }

                                case "PathParms":
                                    String pathArr[] = null;
                                    if (!value.equals("")) {
                                        List<String> pathParams = new ArrayList<>();
                                        if (value.contains(";"))
                                            pathArr = value.split(";");
                                        else
                                            pathArr = new String[]{value};
                                        for (String path : pathArr) {
                                            pathParams.add(path);
                                        }
                                        dataRow.setPathParams(pathParams);
                                    }
                                    break;
                                case "QueryParms":
                                    String queryArr[] = null;
                                    if (!value.equals("")) {
                                        Map<String, String> queryParams = new HashMap<>();
                                        if (value.contains(";"))
                                            queryArr = value.split(";");
                                        else
                                            queryArr = new String[]{value};
                                        for (int i = 0; i < queryArr.length; i++) {
                                            String keyValueArr[] = queryArr[i].split("=");
                                            queryParams.put(keyValueArr[0], keyValueArr[1]);
                                        }
                                        dataRow.setQueryParams(queryParams);
                                    }
                                    break;
                                case "Headers":
                                    String header[] = null;
                                    if (!value.equals("")) {
                                        Map<String, String> headers = new HashMap<>();
                                        if (value.contains(";"))
                                            header = value.split(";");
                                        else
                                            header = new String[]{value};
                                        for (int i = 0; i < header.length; i++) {
                                            String keyValueArr[] = header[i].split("=");
                                            headers.put(keyValueArr[0], keyValueArr[1]);
                                        }
                                        dataRow.setHeaders(headers);
                                    }
                                    break;
                                case "ResponseHeader":
                                    String respheader[] = null;
                                    if (!value.equals("")) {
                                        Map<String, String> headers = new HashMap<>();
                                        if (value.contains("#"))
                                            header = value.split("#");
                                        else
                                            header = new String[]{value};
                                        for (int i = 0; i < header.length; i++) {
                                            if(header[i].contains(":")) {
                                                String keyValueArr[] = header[i].split(":");
                                                headers.put(keyValueArr[0], keyValueArr[1]);
                                            }
                                        }
                                        dataRow.setExpRespheaders(headers);
                                    }
                                    break;
                                case "ExpHttpRespCd":
                                    dataRow.setExpHttpRespCd(value);
                                    break;
                                case "ExpJson":
                                    if (value!=null && !value.equals("")) {
                                        dataRow.setExpJson(value);
                                    }
                                    break;
                                case "ExpMsg":
                                    dataRow.setExpMsg(value);
                                    break;

                                case "ResponseTimeSLA":
                                    dataRow.setResponseTime(value);
                                    break;
                                default:
                                    logger.info("Non MTP header column found, ignoring");
                                    break;
                            }
                        }
                    }
                    if(!skip)
                        dataRows.add(dataRow);
               

           

        }
        return dataRows;
    }
    private Object[][] copyDataRowtoArray(List<DataRow> dataRows)
    {
        int rowCount=dataRows.size();
        Object[][] dataobj = null;
        if(rowCount>0) {
            dataobj = new Object[rowCount][1];
            for (int i = 0; i < rowCount; i++) {
                dataobj[i][0]= dataRows.get(i);
            }

        }else
        {
            throw new RuntimeException("Data in data file is corrupted ");
        }
        return dataobj;
    }
    public void readPayloadResponeFile(String fileName) {
        if (fileRowMap != null && fileRowMap.size() > 0) {
            if (fileRowMap.containsKey(fileName)) {
                return;
            } else {
                Map<String, String> idRowMap = new HashMap<>();
                idRowMap = new TxtDataLoader(fileName).getData();
                if (idRowMap != null && idRowMap.size() > 0)
                    fileRowMap.put(fileName, idRowMap);
            }

        } else {
            Map<String, String> idRowMap = new HashMap<>();
            idRowMap = new TxtDataLoader(fileName).getData();
            if (idRowMap != null && idRowMap.size() > 0)
                fileRowMap.put(fileName, idRowMap);
        }

    }
    public String getRowFromMap(String fileName,String rowId)
    {
        String jsonString="";
        if(fileRowMap!=null && fileRowMap.size()>0)
        {
            Map<String,String>rowMap= fileRowMap.get(fileName);
            if(rowMap!=null && rowMap.size()>0)
            {
                jsonString=rowMap.get(rowId);
            }
        }
        return jsonString;
    }

}

