Meta:
@issue PO Integration
@tags Project: Trasportation, Product: FOM, Program: Logistics
@automatedBy BH13012_Abhijit_Kumar

Narrative: 
In order to automate PO realated scenarios
As a Quality assurance person
I want to invoke a po post service and validate in FOM

Scenario:1 Positive scenarios - po creation and validation
Meta:
@id TC001
@tag Type: integration , Module: PurchaseOrder
@automatedBy BH13012_Abhijit_Kumar


!--Given clear data storage

When PO is created successfully
|requestUrl                       				 |PayloadFileName       |PID |ResponseFileName|RID  |OperationName|PathParms|QueryParms|Headers|ExpHttpRespCd|ResponseTimeSLA|  ExpMsg     |ExpJson|
|PurchaseOrderManagerService.CreatePurchaseOrder |createPOBuyPayload    |P001|                |R001 |  POST       |         |          |       |     200     |    500        |  Approved   |  {}   |
|PurchaseOrderManagerService.CreatePurchaseOrder2|createPOBuyPayload    |P002|                |R001 |  PUT        |         |          |       |     200     |    500        |  InProgress |  {}   |
|PurchaseOrderManagerService.CreatePurchaseOrder3|createPOBuyPayload2   |P001|                |R001 |  GET        |         |          |       |     200     |    500        |  Scheduled  |  {}   |

Then user signed in to JDA FOM
Then user searched for Purchase Order Manager
!-- When user filters All Purchase Orders
!-- And user clicked GO button
!-- more steps
Then logout from FOM



