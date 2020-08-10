package com.macys.mst.transportation.functional.util;

public class TransportationConstants {

	public static final String FILE_ORD_REF_NO = "src/test/resources/orderReferenceNumber.txt";
	
	

	public enum POStatusType {

		APPROVED("Approved"), AVAILABLE("Available"), DISCREPANCY("Discrepancy");

		private String description;

		private POStatusType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return this.description;
		}

		public static POStatusType valueFromDescription(String description) {
			for (POStatusType p : POStatusType.values()) {
				if (p.description.equalsIgnoreCase(description))
					return p;
			}
			return null;
		}
	}
}
