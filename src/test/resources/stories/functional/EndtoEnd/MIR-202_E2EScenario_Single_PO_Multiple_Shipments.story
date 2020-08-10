Meta:
@issue E2E scenario
@tags Project: Trasportation, Product: FOM, Program: Logistics
@automatedBy BH13814_Retheesh_Rajendran

Narrative:
In order to automate FOM E2E scenarios
As a Quality assurance person
I want to create a PO and shipment

Scenario:1 User should be able to create a PO with the details provided and check it in FOM application and create shipment in FOM
Meta:
@id Shipment Scenario
@tag Type: integration , Module: Shipment
@automatedBy BH13814_Retheesh_Rajendran


Given a single Purchase Order is created in BUY with Final Approved and Distor Complete status for the selected Vendor User
|REQUESTURL                                      |VENDORDUNSNBR| DEPT_NBR | LOC_NBR | VND_NUMERIC_DESC |VND_NBR |   POSTATUS  |SKU       |QTY      |SOURCE |PayloadFileName               |PID |LOCATIONDATA|TCID|PO_STAT|
|PurchaseOrderManagerService.CreatePurchaseOrder|2136794|     358     |     12    |        504           |   24101     |200     |      4935421614573    |10     | BUY   |createPOBuyPayloadTemplate    |P001|[{"LocNbr": 010,"LocQty": 50}]|TC01|FinalApproved|
And Vendor user has access to access to FOM application
And FOM application is available
And <PO> is integrated on to FOM via Purchase Order Manager
And Build Shipment is enabled on the select PO for the vendor with
|ScenarioNumber|weight|Volume|Casecount|SRR|
|1001|1500|500|70|Collect Pre-Assigned Carrier|
|1002|2800|1800|270|none|
And multiple shipments are created for same division or destination DC in FOM
Then logout from FOM application successfully
!-- And shipments are integrated into TMS successfully
!-- And a new single load is assigned to both shipments
!-- When a load is tendered and accepted in TMS
!-- Then a single appointment is created successfully in FLO

Examples:
|ScenarioNumber|EarliestAvailable|LatestAvailable|EarliestDelivery|LatestDelivery|Origin|Destination|SLM|LineItem|PO|
|1001|2/19/20|2/20/20|2/24/20|2/25/20|50957364 276|1482033  152|Shipment Level|2 |2700|

