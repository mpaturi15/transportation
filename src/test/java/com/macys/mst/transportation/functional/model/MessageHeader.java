package com.macys.mst.transportation.functional.model;

import lombok.Data;

@Data
public class MessageHeader {
		
	private String orderSource;
	private int orderReferenceNumber;
	private int divisionNumber;
	private String sourceSystemTokenId;
	private String createDate;
	private String createUserId;
	private int divisionId;
	private int purchaseOrderNumber;
	private int departmentNumber;
	private int vendorNumber;
	private int poGenratedByNumber;
	private String messageType;
	private int reqStatus;
	private String shipDate;
	private String cancelDate;
	private String distroByDate;
	private String poDescription;
	private String upcFlag;
	private String mergeSplitCode;
	private String poStatusnbr;
	

}
