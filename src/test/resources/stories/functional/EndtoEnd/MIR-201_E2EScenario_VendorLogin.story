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


Given data is available to create PO

|REQUESTURL                                      |VENDORDUNSNBR| DEPT_NBR | LOC_NBR | VND_NUMERIC_DESC |VND_NBR |   POSTATUS  |SKU       |QTY      |SOURCE |PayloadFileName               |PID |LOCATIONDATA|TCID|PO_STAT|
|PurchaseOrderManagerService.CreatePurchaseOrder|2136794|     358     |     12    |        504           |   24101     |200     |      4935421614573    |10     | BUY   |createPOBuyPayloadTemplate    |P001|[{"LocNbr": 010,"LocQty": 50}]|TC01|FinalApproved|

And PO is created successfully in BUY
Given FOM UI is available
And Vendor is logged in successfully
And <PO> is integrated to JDAFOM
Then Create a Shipment by selecting other shipment details
Then Change the shipment status to confirmed
Then logout from FOM application successfully
And Shipment is integrated on to TMS successfully
And Load is created and assigned
When Load is tendered/accepted successfully in TMS
Then Appointment is created is created in FLO with unassigned status

Examples:
|ScenarioNumber|EarliestAvailable|LatestAvailable|EarliestDelivery|LatestDelivery|Origin|Destination|SLM|LineItem|PO|weight|Volume|Casecount|SRR|ADV_NOTICE|VEN_LOAD|SSR_PALLETIZE|SSR_APPNT|SSR_DROPLIVE|SSR_TEMP|SSR_VNDR_PCKUP|
|1001|2/19/20|2/20/20|2/24/20|2/25/20|50957364 276|1482033  152|Shipment Level|2 |78820|1500|500|70|Collect Pre-Assigned Carrier|24hr Advance Notice|Driver Load|None|Yes|DROP|Yes|None|
|1001|2/19/20|2/20/20|2/24/20|2/25/20|50957364 276|1482033  152|Shipment Level|2 |78818|2600|1500|70|None|24hr Advance Notice|Driver Load|None|Yes|DROP|Yes|None|


