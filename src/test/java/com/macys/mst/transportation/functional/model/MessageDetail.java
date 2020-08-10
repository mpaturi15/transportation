package com.macys.mst.transportation.functional.model;

import java.util.List;

import lombok.Data;

@Data
public class MessageDetail {
	
	private String pid;
	private String pidDescription;
	private String managingLevel;
	private int classNumber;
	private int subClassNumber;
	private String masterStyleNumber;
	private int unitCost;
	private int ownedRetail;
	private int unitTicketedRetail;
	private int orderQuantity;
	private int orderMultiOfQuantity;
	private String deletedFlag;
	private String groupLevel;
	private String savePoOnlyFlag;
	private int markStyle;
	private int colorNumber;
	private String colorDescription;
	private String sizeGroupModelName;
	private int packMultiple;
	private String sizeScaleCode;
	private int sizeGroupScalePacks;
	private int sizeGroupScaleUnits;
	private int packsPerCarton;
	private int packLineItem;
	private int parentSkuUpcNumber;
	private int parentSizeNumber;
	private String vendorCasePackFlag;
	private int sizeNumber;
	private String sizeDescription;
	private long skuUpcNumber;
	private int sizeScalePercent;
	private int sizeScaleQuantity;
	private int sizeScaleUnits;
	private int uccId;
	
	private List<LocationData> locationData;
	

}
