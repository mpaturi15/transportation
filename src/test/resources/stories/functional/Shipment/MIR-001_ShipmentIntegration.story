Meta:
@issue Shipment Integration
@tags Project: Trasportation, Product: FOM, Program: Logistics
@automatedBy BH13948_ClindaThomas,BH12766_RevathiSrinivasan

Narrative:
In order to automate Shipment realated scenarios
As a Quality assurance person
I want to search for a shipment in FOM

Scenario:1 Positive scenarios - Shipment search and validation
Meta:
@id Shipment Scenario
@tag Type: integration , Module: Shipment
@automatedBy BH13948_ClindaThomas,BH12766_RevathiSrinivasan

Given PO is Final Approved
And the <PO> is integrated to JDAFOM
Then Create a Shipment by selecting other shipment details
Then Change the shipment status to confirmed
Then logout from FOM application

Examples:
|ScenarioNumber|EarliestAvailable|LatestAvailable|EarliestDelivery|LatestDelivery|Origin|Destination|SLM|LineItem|PO|weight|Volume|Casecount|SRR|ADV_NOTICE|VEN_LOAD|SSR_PALLETIZE|SSR_APPNT|SSR_DROPLIVE|SSR_TEMP|SSR_VNDR_PCKUP|
|1001|2/19/20|2/20/20|2/24/20|2/25/20|50957364 276|1482033  152|Shipment Level|2 |78816|1500|500|70|Collect Pre-Assigned Carrier|24hr Advance Notice|Driver Load|None|Yes|DROP|Yes|None|
|1001|2/19/20|2/20/20|2/24/20|2/25/20|50957364 276|1482033  152|Shipment Level|2 |78817|2600|1500|70|None|24hr Advance Notice|Driver Load|None|Yes|DROP|Yes|None|




