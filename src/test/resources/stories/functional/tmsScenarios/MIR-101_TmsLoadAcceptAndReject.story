Meta:
@issue TMS login
@tags Project: Trasportation, Product: FOM, Program: Logistics
@automatedBy BH13814_Retheesh_Rajendran

Narrative:
In order to automate TMS related scenarios
As a Quality assurance person
I want to search for a shipment in TMS

Scenario:1 User should be able to UPDATE the existing PO in Shipment that is already integrated in TMS and the modified order information are getting updated in JDA application
Meta:
@id Shipment Scenario
@tag Type: integration , Module: Shipment
@automatedBy BH13814_Retheesh_Rajendran


Given Vendor user logged into JDA transportation Manager
When User clicks on Transportation Smart bench
Then user search for shipment Id
Then user select the shipmentId displayed
When user clicks on Assign load button
Then user searches the new load id
Then user tender the load and accepts it

