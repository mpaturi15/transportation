Meta:
@issue PO Regression Test
@tags Project: Transportation, Product: FOM, Program: Logistics
@automatedBy BH13012_Abhijit_Kumar

Narrative: 
In order to automate PO regression scenarios
As a Quality assurance person
I want to invoke a po post service and validate in FOM

Scenario:1 Regression scenarios - po creation and validation
Meta:
@id TC002
@tag Type: integration , Module: PurchaseOrder
@automatedBy BH13012_Abhijit_Kumar




Given A VENDOR and DEPT and LOC exist in FOM and BUY
|REQUESTURL                                      |VENDORDUNSNBR | DEPT_NBR | LOC_NBR | VND_NUMERIC_DESC  |   POSTATUS  |SKU       |QTY      |SOURCE |PayloadFileName               |PID |
|PurchaseOrderManagerService.CreatePurchaseOrder |              |          |         |                   |             |          |1000     | BUY   |createPOBuyPayloadTemplate    |P001|




Then user signed in to JDA FOM
Then user searched for Purchase Order Manager