Meta:
@issue PO Integration
@tags Project: Trasportation, Product: FOM, Program: Logistics
@automatedBy BH13012_Abhijit_Kumar

Narrative: 
In order to automate PO realated scenarios
As a Quality assurance person
I want to Verify the PO reached GEMS

Scenario:1 Positive scenarios - po Information in Queue and validation
Meta:
@id TC002
@tag Type: integration , Module: PurchaseOrder
@automatedBy BH13012_Abhijit_Kumar

Given EMS Connection
Then receive message from Queue
And verify message