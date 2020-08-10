package com.macys.mst.transportation.functional.db;

public class SQLInventory {

	
	public static final String SCHEMA_MPM = "MPM";
	public static final String SCHEMA_OCAINBIZA = "OCAINBIZA";
	public static final String SCHEMA_SQLSERVER = "SQLSERVER";
	
	public static final String GET_VENDOR_DETAILS_COUNT_X = "SELECT V.VND_NBR, V.VND_DUNS_NBR, V.VND_NAME, PH.LOC_NBR, PH.DEPT_NBR, PH.VND_NUMERIC_DESC " + 
			"FROM PO_HDR PH, VENDOR V " + 
			"WHERE V.VND_NBR = PH.VND_NBR AND ROWNUM <= %s"; //  ORDER BY PH.LAST_UPD_TS DESC
	
	public static final String GET_VENDOR_DETAILS = "SELECT  V.VND_NBR, V.VND_DUNS_NBR, V.VND_NAME, PH.LOC_NBR, PH.DEPT_NBR, PH.VND_NUMERIC_DESC " + 
			"FROM PO_HDR PH, VENDOR V " + 
			"WHERE V.VND_NBR = PH.VND_NBR " + 
			"AND V.VND_DUNS_NBR = %s AND PH.DEPT_NBR = %s " + 
			"AND PH.LOC_NBR = %s AND VND_NUMERIC_DESC = %s FETCH FIRST 1 ROWS ONLY";
	
	
	public static final String GET_SKU_UPC = "SELECT SKU_UPC_NBR FROM PROD_SKU WHERE DEPT_NBR = %s AND LOC_NBR = %s AND VND_NUMERIC_DESC = %s ORDER BY CREATE_TS DESC FETCH FIRST 1 ROWS ONLY";
	
	public static final String GET_PO_HEADER_DETAILS = "SELECT * FROM [dbo].[ORD_MGT_HEADER] WHERE OrderReferenceNumber IN ( %s )";
	
	public static final String GET_APPT_DETAILS = "select * from FB where FB_NBR in ( %s )";

}
