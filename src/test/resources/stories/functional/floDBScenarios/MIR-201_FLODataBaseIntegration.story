Meta:
@issue FLO DB Integration
@tags Project: Trasportation, Product: FOM, Program: Logistics
@automatedBy BH13814_Retheesh_Rajendran

Narrative:
In order to automate FLO DB related scenarios
As a Quality assurance person
I want to search for an appointment in FLO DB

Scenario:1 User should be able to retrive appointment number created and validate it.
Meta:
@id Appointment creation Scenario
@tag Type: integration , Module: Shipment
@automatedBy BH13814_Retheesh_Rajendran


Given user logged into FLO DB
Then searched shipment ID in FB table
Then retrieve appointment number


