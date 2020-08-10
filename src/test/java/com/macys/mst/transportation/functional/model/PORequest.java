package com.macys.mst.transportation.functional.model;

import java.util.List;

import lombok.Data;

@Data
public class PORequest {
	
	private MessageHeader messageHdr;
	private List<MessageDetail> messageDetail;

}
